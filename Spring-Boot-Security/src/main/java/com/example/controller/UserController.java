package com.example.controller;

import com.example.common.config.JWTConfig;
import com.example.common.utils.JWTTokenUtil;
import com.example.common.utils.ResultUtil;
import com.example.common.utils.SecurityUtil;
import com.example.security.entity.SelfUserEntity;
import com.example.security.handler.UserLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用户相关API
 *
 * @author Ink足迹
 * @create 2020-07-23 16:34
 **/
@RestController
@RequestMapping("/vip")
public class UserController {

    @Autowired
    private UserLoginSuccessHandler userLoginSuccessHandler;

    @GetMapping("/mbLogin")
    public String mobileLogin(@RequestParam String mobile, @RequestParam String password) {
        SelfUserEntity userInfo = new SelfUserEntity();
        userInfo.setUserId(1L);
        userInfo.setUsername(mobile);
        userInfo.setPassword("$2a$10$wjt9MeTEFOzhj3khYMD5ReYGxLb8EXnY9KZMxORlDvdsGYUlrrih6");
        userInfo.setStatus("NORMAL");

        if (!new BCryptPasswordEncoder().matches(password, userInfo.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }


        Set<GrantedAuthority> authorities = new HashSet<>();

        new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
        String token = JWTTokenUtil.createAccessToken(userInfo);
        token = JWTConfig.tokenPrefix + token;
        return token;
    }


    @GetMapping("/get")
    public String getUser() {
        return SecurityUtil.getUserId() + "---" + SecurityUtil.getUserName();
    }

    /**
     * 管理端信息
     *
     * @return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Map<String, Object> userLogin() {
        Map<String, Object> result = new HashMap<>();
        result.put("title", "管理端信息");
        return ResultUtil.resultSuccess(result);
    }

    /**
     * 拥有ADMIN或者USER角色可以访问
     *
     * @return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map<String, Object> list() {
        Map<String, Object> result = new HashMap<>();
        result.put("title", "拥有用户或者管理员角色都可以查看");
        return ResultUtil.resultSuccess(result);
    }

    /**
     * 拥有ADMIN和USER角色可以访问
     *
     * @return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @RequestMapping(value = "/menuList", method = RequestMethod.GET)
    public Map<String, Object> menuList() {
        Map<String, Object> result = new HashMap<>();
        result.put("title", "拥有用户和管理员角色都可以查看");
        return ResultUtil.resultSuccess(result);
    }

    /**
     * 拥有sys:user:info权限可以访问
     * hasPermission 第一个参数是请求路径 第二个参数是权限表达式
     *
     * @return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasPermission('/admin/userList','sys:user:info')")
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public Map<String, Object> userList() {
        Map<String, Object> result = new HashMap<>();
        result.put("title", "拥有sys:user:info权限都可以查看");
        return ResultUtil.resultSuccess(result);
    }

    /**
     * 拥有ADMIN角色和sys:role:info权限可以访问
     *
     * @return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('ADMIN') and hasPermission('/vip/adminRoleList','sys:role:info')")
    @RequestMapping(value = "/adminRoleList", method = RequestMethod.GET)
    public Map<String, Object> adminRoleList() {
        Map<String, Object> result = new HashMap<>();
        result.put("title", "拥有ADMIN角色和sys:role:info权限可以访问");
        return ResultUtil.resultSuccess(result);
    }

}
