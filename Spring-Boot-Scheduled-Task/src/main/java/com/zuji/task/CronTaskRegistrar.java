package com.zuji.task;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 添加，删除 定时任务类
 * 实现销毁方法
 *
 * @author Ink足迹
 * @create 2019-06-19 15:26
 **/
@Component
public class CronTaskRegistrar implements DisposableBean {

    /**
     * 存放定时任务
     */
    private final Map<Runnable, ScheduledTask> scheduledTaskMap = new ConcurrentHashMap<>();

    @Autowired
    private TaskScheduler taskScheduler;

    /**
     * 添加定时任务类
     *
     * @param task
     * @param cronExpression cron表达式
     */
    public void addCronTask(Runnable task, String cronExpression) {
        addCronTask(new CronTask(task, cronExpression));
    }

    /**
     * 添加定时任务类
     *
     * @param cronTask
     */
    public void addCronTask(CronTask cronTask) {
        if (cronTask != null) {
            Runnable task = cronTask.getRunnable();
            if (this.scheduledTaskMap.containsKey(task)) {
                this.removeCronTask(task);
            }
            this.scheduledTaskMap.put(task, scheduledCronTask(cronTask));
        }

    }

    /**
     * 移除 定时任务，并取消
     *
     * @param task
     */
    public void removeCronTask(Runnable task) {
        ScheduledTask scheduledTask = this.scheduledTaskMap.remove(task);
        if (scheduledTask != null) {
            scheduledTask.cancel();
        }
    }

    /**
     * 调用线程池
     *
     * @param cronTask 定时任务
     * @return
     */
    public ScheduledTask scheduledCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }


    /**
     * 重写销毁方法
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        for (ScheduledTask task : this.scheduledTaskMap.values()) {
            task.cancel();
        }
        this.scheduledTaskMap.clear();
    }
}
