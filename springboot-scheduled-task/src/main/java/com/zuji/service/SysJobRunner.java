package com.zuji.service;

import com.alibaba.fastjson.JSONObject;
import com.zuji.pojo.SysJobVo;
import com.zuji.task.CronTaskRegistrar;
import com.zuji.task.SchedulingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 初始化定时任务类
 *
 * @author Ink足迹
 * @create 2019-06-19 16:30
 **/
@Service
public class SysJobRunner implements CommandLineRunner {
    private final static Logger log = LoggerFactory.getLogger(SysJobRunner.class);

    @Autowired
    private ISysTaskService sysTaskService;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) throws Exception {
        List<SysJobVo> list = sysTaskService.selectTask(0);
        log.info(">>>>初始化定时任务 list={}",JSONObject.toJSONString(list));
        for (SysJobVo jobVo : list) {
            SchedulingRunnable task = new SchedulingRunnable(jobVo.getBeanName(), jobVo.getMethodName(), jobVo.getMethodParams());
            cronTaskRegistrar.addCronTask(task, jobVo.getCronExpression());
        }
        log.info(">>>>>定时任务初始化完毕<<<<<");
    }
}
