package com.zuji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 定时任务的线程池配置类
 *
 * @author Ink足迹
 * @create 2019-06-19 14:40
 **/
@Configuration
public class SchedulingConfig {

    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler poolTaskScheduler = new ThreadPoolTaskScheduler();

        // 定时任务执行线程池核心线程数
        poolTaskScheduler.setPoolSize(4);

        // 开启 remove-on-cancel
        poolTaskScheduler.setRemoveOnCancelPolicy(true);
        poolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler--");
        return poolTaskScheduler;
    }
}
