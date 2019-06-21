在**Spring Boot**项目中，可以通过`@EnableScheduling`注解和`@Scheduled`注解实现定时任务，也可以通过`SchedulingConfigurer`接
口来实现定时任务。但是这两种方式不能动态添加、删除、启动、停止任务。要实现动态增删启停定时任务功能，
比较广泛的做法是集成**Quartz**框架。但是这样就需要依赖框架，在满足项目需求的情况下，尽量少的依赖其它框架，避免项目过于臃肿和复杂。
查看`spring-context`这个jar包中`org.springframework.scheduling.ScheduledTaskRegistrar`这个类的源代码，发现可以通过改造这个类
就能实现动态增删启停定时任务功能。

- `com.zuji.config.SchedulingConfig` 执行定时任务的线程池配置类。 
- `com.zuji.task.ScheduledTask` 配置`ScheduledFuture`的包装类。`ScheduledFuture`是`ScheduledExecutorService`定时任务线程池的执行结果。
- `com.zuji.task.SchedulingRunnable` 实现Runnable接口实现类，被定时任务线程池调用，用来执行指定bean里面的方法。
- `com.zuji.task.CronTaskRegistrar` 定时任务注册类，用来增加、删除定时任务。
- `com.zuji.service.DemoTest` 定时任务示例类 
- `com.zuji.pojo.SysJobVo` 定时任务实体类 
- `com.zuji.service.SysTaskServiceImpl` 定时任务实现类
    - `com.zuji.service.SysJobRunner` 实现了`CommandLineRunner`接口的`run`类，当**Spring Boot**项目启动完成后，初始化数据库中的定时任务。

