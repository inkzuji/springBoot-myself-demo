package com.zuji.controller;

import com.github.pagehelper.PageInfo;
import com.zuji.pojo.WxUserParamVo;
import com.zuji.service.IWxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ink足迹
 * @create 2019-06-26 16:39
 **/
@RestController
@RequestMapping("/user")
public class WxUserController {
    @Autowired
    private IWxUserService wxUserService;

    @GetMapping("/list")
    public PageInfo<WxUserParamVo> getList(){
        return wxUserService.getList();
    }
}
