# cf-plastic-mes-machine-mqtt-monitor
## 系统需求
- JDK>=1.8
- maven>=3.0
- masql>=5.5
### 项目结构
```
MES
|
├──cf.webapi                        --后台api
|  |
|  ├──com.cf.web
|  |
|  |  ├──controller           --工具类
|  |  |
|  |  ├──config               --配置
|  |  |
|  |  ├──RunApplication       --启动系统
|  |
|  ├──resourse                --配置文件
|
├──cf-common                        --工具类
|  |
|  ├──com.cf.common
|  |
|  |  ├──annotation           --工具类
|  |  |
|  |  ├──config               --全局配置
|  |  |
|  |  ├──constatnt            --核心控制，通用常量配置
|  |  |
|  |  ├──enums                --通用枚举
|  |  |
|  |  ├──exception            --异常处理
|  |  |
|  |  ├──filter               --过滤器
|  |  |
|  |  ├──utils                --通用类
|
├──cf-framework                     --框架核心
|  |
|  ├──com.cf.framework
|  |
|  |  ├──aspectj              --注解实现
|  |  |
|  |  ├──config               --系统配置
|  |  |
|  |  ├──datasourse           --数据源处理
|  |  |
|  |  ├──interceptor          --拦截器
|  |  |
|  |  ├──manager              --异步处理
|  |  |
|  |  ├──security             --权限控制
|  |  |
|  |  ├──web                  --web控制
|
├──cf-mes                            --mes业务接口与实现
|  |
|  ├──com.cf.mes
|  |
|  |  ├──config               --mes配置
|  |  |
|  |  ├──domain               --实体对象
|  |  |
|  |  ├──mapper               --dao层
|  |  |
|  |  ├──service              --业务逻辑接口与实现
|  |
|  ├──resourse                --mapper文件
|
├──cf-quartz                          --定时器
|  |
|  ├──com.cf.quartz
|  |
|  |  ├──config               --配置
|  |  |
|  |  ├──domain               --实体对象
|  |  |
|  |  ├──mapper               --dao层
|  |  |
|  |  ├──service              --业务逻辑接口与实现
|  |  |
|  |  ├──task                 --任务调度
|  |  |
|  |  ├──util                 --通用类
|  |
|  ├──resourse                --mapper文件
|
├──cf-system                          --系统基础数据接口与实现
|  |
|  ├──com.cf.system
|  |
|  |  ├──domain               --实体对象
|  |  |
|  |  ├──mapper               --dao层
|  |  |
|  |  ├──service              --业务逻辑接口与实现
|  |
|  ├──resourse                --mapper文件
```
### 核心技术
- springBoot
- spring security
### 功能实现
- 分页排序实现
  - 添加pagehelper依赖
  - 关键代码
  ```java
  //执行语句以后的第一个查询（Select）语句得到的数据进行分页
  PageHelper.startPage(pageIndex, pageSize, orderBy); 
  ```
- 事务管理
  - 关键代码
  ```java
  @Transactional
  ```
  - 注解常用属性
  
  属性| 说明
  ----  | ----  
  propagation| 事务的传播行为，默认值为 REQUIRED。当一个事务调用另一个事务时，该如何处理|
  isolation| 事务的隔离度，默认值采用 DEFAULT，事务的隔离级别要得到底层数据库引擎的支持
  timeout  | 事务的超时时间，默认值为-1，不超时。如果设置了超时时间(单位秒)，那么如果超过该时间限制了但事务还没有完成，则自动回滚事务。
  read-only| 指定事务是否为只读事务，默认值为 false；为了忽略那些不需要事务的方法，比如读取数据，可以设置 read-only 为 true。
  rollbackFor| 用于指定能够触发事务回滚的异常类型，如果有多个异常类型需要指定，各类型之间可以通过逗号分隔。{xxx1.class, xxx2.class,……}
  noRollbackFo|抛出 no-rollback-for 指定的异常类型，不回滚事务。{xxx1.class, xxx2.class,……}
  - 注意
    - 因为Spring的默认的事务规则是遇到运行异常（RuntimeException）和程序错误（Error）才会回滚。如果想针对检查异常进行事务回滚，可以在@Transactional注解里使用 rollbackFor属性明确指定异常。
    - 在业务层捕捉异常后，发现事务不生效，推荐做法：在业务层统一抛出异常，然后在控制层统一处理。
- 异常处理
  - 目的
    
    - 通常一个web框架中，有大量需要处理的异常。比如业务异常，权限不足等等。前端通过弹出提示信息的方式告诉用户出了什么错误。 通常情况下我们用try.....catch....对异常进行捕捉处理，但是在实际项目中对业务模块进行异常捕捉，会造成代码重复和繁杂，我们希望代码中只有业务相关的操作，所有的异常我们单独设立一个类来处理它。全局异常就是对框架所有异常进行统一管理。 我们在可能发生异常的方法里throw抛给控制器。然后由全局异常处理器对异常进行统一处理。 如此，我们的Controller中的方法就可以很简洁了
  - 关键代码
    - @RestControllerAdvice结合方法型注解@ExceptionHandler，用于捕获Controller中抛出的指定类型的异常，从而达到不同类型的异常区别处理的目的
    ```java
    @RestControllerAdvice
    @ExceptionHandler 
    ```
- 参数验证
  - 关键代码
  ```java
  @Validated //在controller上声明@Validated需要对数据进行校验。
  @Null//在对应字段或其Get方法加上参数校验注解
  ```
  - 常用校验注解
  
  注解名称| 功能
  --- | -----
  @Null|检查该字段为空
  @NotNull|不能为null
  @NotBlank|不能为空，常用于检查空字符串
  @NotEmpty|不能为空，多用于检测list是否size是0
  ...|...
- 系统日志
  - 在需要被记录日志的方法上添加@Log注解，使用方法如下：
  ```java
  @Log(title = "User", businessType = BusinessType.DELETE, remark = "")  
  ```
  - 支持参数：title，remark，businessType，isSaveRequestData,被注解的方法将会保存记录到cfmes.sys_operation_log
- 定时任务
  - 定时任务配置
  - @PostConstruct 从数据库获取任务，创建定时任务
- 系统接口
  
  - 通过Swagger，可在Controller中添加注解来描述接口信息（如：@Api）
- 国际化
  - 在resource下新建目录，i18n
  - 根据需要国际化的页面，新建porperties配置文件
  - 配置指定读取国际化配置文件的路径 spring.messages.basename
  - 实现WebMvcConfigurer，根据url动态改变当地语言
  - 委托给spring messageSource获取对应语言
- rabbitMQ
  - 添加依赖
  - 在yml文件配置连接信息，消息接收确认模式更改为手动确认
  - RabbitMQConfig配置，包含收发消息序列化配置，队列配置，交换机配置，将消息队列与交换机绑定
  - RabbitTemplateConfirmCallbackConfig：消息发送确认
  - RabbitTemplateReturnCallbackConfig：消息从交换器发送到对应队列失败时触发
  - 注入RabbitTemplate实现收发消息（service实现）
- redis
  - 添加依赖
  - 在yml文件配置连接信息
  - redis序列化配置
  - 使用RedisTemplate
- mybatis
  - 添加依赖
  - 添加mybatis配置
  - MyBatisConfig配置，配置多模块的mapper-locations
- Keycloak和springSecurity
  
  - 自定义AuthenticationProvider
- 权限注解
  - @PreAuthorize
  - 安全表达式
  
  表达式| 说明
    --- | -----
    hasRole（role）|用户拥有指定的角色时返回true
    hasAnyRole（[role1,role2]）|用户拥有任意一个指定的角色时返回true
    hasAuthority（authority）|用户拥有指定的权限时返回true
    ...|
- 上传下载
  
  - OSS-INNO
- 导入导出
- druid
- 数据多语言
  
  - 通过注解


