package com.zuji.service;

import com.zuji.pojo.SysJobVo;
import com.zuji.task.CronTaskRegistrar;
import com.zuji.task.SchedulingRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 定时任务 实现类
 *
 * @author Ink足迹
 * @create 2019-06-19 15:55
 **/
@Service
public class SysTaskServiceImpl implements ISysTaskService {

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    private static List<SysJobVo> jobList;

    static {
        jobList = new ArrayList<>();
        SysJobVo vo = new SysJobVo();
        vo.setJobId(1);
        vo.setBeanName("demoTest");
        vo.setMethodName("taskNoParams");
        vo.setCronExpression("0/10 * * * * *");
        vo.setJobStatus(0);
        jobList.add(vo);

        vo = new SysJobVo();
        vo.setJobId(2);
        vo.setBeanName("demoTest");
        vo.setMethodName("taskNoParams");
        vo.setCronExpression("0/10 * * * * *");
        vo.setJobStatus(1);
        jobList.add(vo);

        vo = new SysJobVo();
        vo.setJobId(3);
        vo.setBeanName("demoTest");
        vo.setMethodName("taskWithParams");
        vo.setMethodParams("我是一号测试");
        vo.setCronExpression("0/10 * * * * *");
        vo.setJobStatus(0);
        jobList.add(vo);

        vo = new SysJobVo();
        vo.setJobId(4);
        vo.setBeanName("demoTest");
        vo.setMethodName("taskWithParams");
        vo.setMethodParams("我是二号测试");
        vo.setCronExpression("0/10 * * * * *");
        vo.setJobStatus(1);
        jobList.add(vo);
    }

    /**
     * 查询进行中的定时任务类
     *
     * @param status
     */
    @Override
    public List<SysJobVo> selectTask(int status) {
        List<SysJobVo> list = jobList.stream()
                .filter(s -> Objects.equals(s.getJobStatus(), status))
                .collect(Collectors.toList());
        return list;
    }

    /**
     * 添加定时任务
     *
     * @param jobVo
     */
    @Override
    public void addTask(SysJobVo jobVo) {
        // 处理数据 插入数据库
        jobList.add(jobVo);

        // 判断定时任务是否开启
        Integer jobStatus = jobVo.getJobStatus();
        if (Objects.equals(jobStatus, 0)) {
            this.changeTaskStatus(Boolean.TRUE, jobVo);
        }

    }

    /**
     * 修改定时任务
     *
     * @param jobVo
     */
    @Override
    public void updateTask(SysJobVo jobVo) {
        // 获取数据库中已存在的数据
        SysJobVo existJob = jobList.stream()
                .filter(s -> Objects.equals(jobVo.getJobId(), s.getJobId()))
                .findFirst().orElse(null);
        if (existJob == null) {
            return;
        }

        // 处理数据 插入数据库

        // 判断 原来的定时任务是否开启，如果开启，则先停止
        if (Objects.equals(existJob.getJobStatus(), 0)) {
            this.changeTaskStatus(Boolean.FALSE, existJob);
        }

        // 判断定时任务是否开启
        if (Objects.equals(existJob.getJobStatus(), 0)) {
            this.changeTaskStatus(Boolean.TRUE, jobVo);
        }
    }

    /**
     * 删除定时任务
     *
     * @param jobVo
     */
    @Override
    public void deleteTask(SysJobVo jobVo) {
        // 获取数据库中已存在的数据
        SysJobVo existJob = new SysJobVo();

        // 处理数据 插入数据库

        // 判断定时任务是否开启
        if (Objects.equals(existJob.getJobStatus(), 0)) {
            this.changeTaskStatus(Boolean.FALSE, existJob);
        }
    }

    /**
     * 修改定时任务类状态
     *
     * @param add
     * @param jobVo
     */
    private void changeTaskStatus(boolean add, SysJobVo jobVo) {
        if (add) {
            SchedulingRunnable task = new SchedulingRunnable(jobVo.getBeanName(), jobVo.getMethodName(), jobVo.getMethodParams());
            cronTaskRegistrar.addCronTask(task, jobVo.getCronExpression());
        } else {
            SchedulingRunnable task = new SchedulingRunnable(jobVo.getBeanName(), jobVo.getMethodName(), jobVo.getMethodParams());
            cronTaskRegistrar.removeCronTask(task);
        }
    }
}
