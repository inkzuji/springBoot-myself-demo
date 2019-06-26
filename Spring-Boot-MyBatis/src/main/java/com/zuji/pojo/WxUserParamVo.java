package com.zuji.pojo;

import lombok.Data;

/**
 * @author Ink足迹
 * @create 2019-06-26 16:33
 **/
@Data
public class WxUserParamVo {
    private Long wxUserId;

    private Long userId;

    private String appId;

    private String unionId;

    private String wechatNo;

    private String openId;

    private String avatarUrl;

    private String nickName;

    private Byte gender;

    private String mobile;

    private String country;

    private String province;

    private String city;

    private String language;
}
