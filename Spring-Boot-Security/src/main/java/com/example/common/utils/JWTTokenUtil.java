package com.example.common.utils;

import com.example.common.config.JWTConfig;
import com.example.security.entity.SelfUserEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author Ink足迹
 * @create 2020-07-23 17:18
 **/
public class JWTTokenUtil {
    private static final Logger log = LoggerFactory.getLogger(JWTTokenUtil.class);


    /**
     * 生成Token
     * @param selfUserEntity 用户安全实体
     * @return token
     */
    public static String createAccessToken(SelfUserEntity selfUserEntity) {
        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 放入用户名和用户ID
                // .setId(selfUserEntity.getUserId() + "")
                // // 主题
                // .setSubject(selfUserEntity.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("Inkzuji")
                .setClaims(JacksonUtils.bean2Bean(selfUserEntity, new TypeReference<Map<String, Object>>() {
                }))
                // 自定义属性 放入用户拥有权限
                // .claim("authorities", JacksonUtils.json2Str(selfUserEntity.getAuthorities()))
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expiration))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, JWTConfig.secret)
                .compact();
        return token;
    }
}
