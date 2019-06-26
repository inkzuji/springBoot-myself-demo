package com.zuji.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zuji.mapper.WxUserMapper;
import com.zuji.pojo.WxUserParamVo;
import com.zuji.service.IWxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信用户信息
 *
 * @author Ink足迹
 * @create 2019-06-26 16:32
 **/
@Service
public class WxUserServiceImpl implements IWxUserService {
    @Autowired
    private WxUserMapper wxUserMapper;

    @Override
    public PageInfo<WxUserParamVo> getList() {
        PageHelper.startPage(1, 5);
        List<WxUserParamVo> list = wxUserMapper.findByIsDelete();
        PageInfo<WxUserParamVo> result = new PageInfo<>(list);
        return result;
    }

    @Override
    public void addData(WxUserParamVo paramVo) {

    }

    @Override
    public void updateData(WxUserParamVo paramVo) {

    }

    @Override
    public void deleteData(long wxUserId) {

    }
}
