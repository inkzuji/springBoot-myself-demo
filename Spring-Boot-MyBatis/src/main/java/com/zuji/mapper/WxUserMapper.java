package com.zuji.mapper;

import com.zuji.model.WxUser;
import com.zuji.pojo.WxUserParamVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper 注解声明成mybatis Dao层的Bean。也可以在配置类上使用@MapperScan("com.zuji.mapper")注解声明
 */
@Mapper
public interface WxUserMapper {
    int deleteByPrimaryKey(Long wxUserId);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(Long wxUserId);

    int updateByPrimaryKeySelective(WxUser record);

    @Select({"select wx_user_id, user_id, app_id, union_id, avatar_url, nick_name, country, province, city \n" +
            " from wx_user where is_delete = 0"})
    List<WxUserParamVo> findByIsDelete();

}