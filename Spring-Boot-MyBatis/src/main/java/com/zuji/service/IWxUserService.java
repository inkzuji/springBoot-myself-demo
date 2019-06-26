package com.zuji.service;

import com.github.pagehelper.PageInfo;
import com.zuji.pojo.WxUserParamVo;

/**
 * 微信用户信息
 *
 * @author Ink足迹
 * @create 2019-06-26 16:31
 **/
public interface IWxUserService {
    PageInfo<WxUserParamVo> getList();

    void addData(WxUserParamVo paramVo);

    void updateData(WxUserParamVo paramVo);

    void deleteData(long wxUserId);
}
