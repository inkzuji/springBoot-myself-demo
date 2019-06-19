package com.zuji.controller;

import com.zuji.pojo.SysJobVo;
import com.zuji.service.ISysTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 定时任务测试类
 *
 * @author Ink-足迹
 * @create 2019-06-19 20:08
 **/
@RestController
public class ScheduleTaskController {
    @Autowired
    private ISysTaskService sysTaskService;

    @GetMapping(value = "/list")
    public List<SysJobVo> list(@RequestParam Integer status) {
        return sysTaskService.selectTask(status);
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody SysJobVo jobVo) {
        sysTaskService.addTask(jobVo);
    }

    @PostMapping(value = "upt")
    public void upt(@RequestBody SysJobVo jobVo) {
        sysTaskService.updateTask(jobVo);
    }

    @GetMapping(value = "del")
    public void del(@RequestParam Integer jobId) {
        sysTaskService.deleteTask(jobId);
    }
}
