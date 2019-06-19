package com.zuji.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * @author YuanSongMing
 * @version 0.0.1
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    public static void copyPropertiesIgnoreNullValue(Object source, Object target, String... ignoreProperties)
            throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (value != null) {
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties '" + targetPd.getName() + "'  from source to target", ex);
                    }
                }
            }
        }
    }

    public static void copyPropertiesIgnoreNullValue(Object source, Object target) throws BeansException {
        copyPropertiesIgnoreNullValue(source, target, (String[]) null);
    }

    /**
     * 校验参数值是否发生变化，未发送变化的参数赋值为null
     *
     * @param source      修改源
     * @param target      数据库中源
     * @param ignoreParam 需要忽略的字段
     * @throws Exception
     */
    public static void checkParamIsChangeIgnoreNullValue(Object source, Object target, String... ignoreParam) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = source.getClass();
        PropertyDescriptor[] sourcePds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreParam != null ? Arrays.asList(ignoreParam) : null);
        for (PropertyDescriptor sourcePd : sourcePds) {
            if (ignoreList == null || !ignoreList.contains(sourcePd.getName())) {
                PropertyDescriptor targetPd = getPropertyDescriptor(target.getClass(), sourcePd.getName());
                if (targetPd != null && targetPd.getReadMethod() != null) {
                    try {
                        Method targetReadMethod = targetPd.getReadMethod();
                        if (!Modifier.isPublic(targetReadMethod.getDeclaringClass().getModifiers())) {
                            targetReadMethod.setAccessible(true);
                        }
                        Object targetVal = targetReadMethod.invoke(target);
                        if (targetVal != null) {
                            Method sourceReadMethod = sourcePd.getReadMethod();
                            if (!Modifier.isPublic(sourceReadMethod.getDeclaringClass().getModifiers())) {
                                sourceReadMethod.setAccessible(true);
                            }
                            Object sourceVal = sourceReadMethod.invoke(source);
                            if (sourceVal != null && StringUtils.equals(targetVal.toString(), sourceVal.toString())) {
                                Method writeMethod = sourcePd.getWriteMethod();
                                if (writeMethod == null) {
                                    continue;
                                }
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(source, new Object[]{null});
                            }
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not check param '" + sourcePd.getName() + "'  from source to target", ex);
                    }
                }
            }
        }
    }
}
