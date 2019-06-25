package com.zuji.service;

import org.springframework.stereotype.Component;

/**
 * 测试类
 *
 * @author Ink足迹
 * @create 2019-06-19 15:46
 **/
@Component("demoTest")
public class DemoTest {

    public void taskWithParams(String params) {
        System.out.println(">>>>>执行带参定时任务 params：" + params);
    }

    public void taskNoParams(){
        System.out.println(">>>>>执行无参定时任务");
    }
}
