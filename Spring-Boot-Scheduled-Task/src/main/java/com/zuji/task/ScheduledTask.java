package com.zuji.task;

import java.util.concurrent.ScheduledFuture;

/**
 * 添加ScheduledFuture包装类
 * 取消 定时任务
 *
 * @author Ink足迹
 * @create 2019-06-19 14:47
 **/
public final class ScheduledTask {

    volatile ScheduledFuture<?> future;

    /**
     * 取消 定时任务
     */
    public void cancel(){
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
