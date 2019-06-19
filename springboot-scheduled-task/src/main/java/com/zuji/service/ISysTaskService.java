package com.zuji.service;

import com.zuji.pojo.SysJobVo;

import java.util.List;

/**
 * 定时任务类方法
 *
 * @author Ink足迹
 * @create 2019-06-19 15:55
 **/
public interface ISysTaskService {

    /**
     * 查询进行中定时任务类
     */
    List<SysJobVo> selectTask(int status);

    /**
     * 添加定时任务
     *
     * @param jobVo
     */
    void addTask(SysJobVo jobVo);

    /**
     * 修改定时任务
     *
     * @param jobVo
     */
    void updateTask(SysJobVo jobVo);

    /**
     * 删除定时任务
     *
     * @param jobVo
     */
    void deleteTask(SysJobVo jobVo);
}
