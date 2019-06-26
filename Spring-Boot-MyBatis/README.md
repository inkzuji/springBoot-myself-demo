## 该工程实现内容

- 整合 MyBatis,实现自动生成xml，并实现批量插入，更新
- 整合 log4j2 日志系统
- 整合 pagehelper 分页插件
- 配置打包规则，打包时跳过测试
- 使用Druid数据源，并启用监听功能

## 依赖Jar作用

`MyBatis-Spring-Boot-Starter`

- 自动检测现有的**DataSource**
- 将创建并注册**SqlSessionFactory**的实例，该实例使用**SqlSessionFactoryBean**将该**DataSource**作为输入进行传递。
- 将创建并注册从**SqlSessionFactory**中获取的**SqlSessionTemplate**的实例。
- 自动扫描您的**mappers**，将它们链接到**SqlSessionTemplate**并将其注册到**Spring**上下文，以便将它们注入到您的**bean**中。
- 使用了该**Starter**之后，只需要定义一个**DataSource**即可（**application.properties**或**application.yml**中可配置），它会自动创建使用该**DataSource**的**SqlSessionFactoryBean**以及**SqlSessionTemplate**。会自动扫描你的**Mappers**，连接到**SqlSessionTemplate**，并注册到**Spring**上下文中