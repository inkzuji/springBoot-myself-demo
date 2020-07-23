package com.example.security.service;

import com.example.security.entity.SelfUserEntity;
import com.example.security.jwt.JWTAuthenticationTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义权限注解验证
 *
 * @author Ink足迹
 * @create 2020-07-23 17:41
 **/
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {
    private static final Logger log = LoggerFactory.getLogger(UserPermissionEvaluator.class);

    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * 当然targetUrl不一定是URL可以是数据Id还可以是管理员标识等,这里根据需求自行设计
     *
     * @param authentication 用户身份(在使用hasPermission表达式时Authentication参数默认会自动带上)
     * @param targetUrl      请求路径
     * @param permission     请求路径权限
     * @return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        log.info("--------自定义权限注解验证---------");
        // 获取用户信息
        SelfUserEntity selfUserEntity = (SelfUserEntity) authentication.getPrincipal();

        // 查询用户权限(这里可以将权限放入缓存中提升效率)
        Set<String> permissions = new HashSet<>();
        // List<SysMenuEntity> sysMenuEntityList = sysUserService.selectSysMenuByUserId(selfUserEntity.getUserId());
        // for (SysMenuEntity sysMenuEntity : sysMenuEntityList) {
        //     permissions.add(sysMenuEntity.getPermission());
        // }
        // 权限对比
        // if (permissions.contains(permission.toString())) {
        //     return true;
        // }
        return true;
    }


    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
