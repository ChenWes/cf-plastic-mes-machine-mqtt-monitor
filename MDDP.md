### 开发流程
1.首先根据业务，确定实体类（一般与数据库的表一一对应，表column即实体属性）和封装类（Controller返回的对象类，一般是实体类的部分字段或是实体类的List或者业务衍生出的字段的集合，例如有Mapping关系等对应）**该步骤需要先设计好DB的物理模型，才可以进行代码的编写**

 - 实体类需要创建在com.cf.**.domain目录下（其中星即代表系统当中有分层的需要而产生不同的模块，即使是有模块的区分，其结构也是一致的）

 - 继承BaseEntity后会包含基础属性（新增人、新增时间、修改人、修改时间等四个基础字段和【备注】这个特殊字段）

 - BaseEntity实现了Serializable，使一个类的对象可以被序列化，为了保存在内存中的各种对象的状态（对象的实例变量，不是方法），并且可以把保存的对象状态再读出来，这是java中的提供的保存对象状态的机制—序列化。

 - ```java
   //BaseEntity中，为了保证序列化ID一致
   private static final long serialVersionUID = 1L
   ```

   





2.根据第一步的封装类，编写dao以及对应的mapper，dao的逻辑就是为了产生封装类的属性（部分属性可直接产生，还有一部分属性需要到service层对数据处理才能产生，例如有Mapping关系的对象表），该部分对应到MyBatis

- 新建dao数据接口，DAO接口只说明有这样的方法
- 根据dao建立相应的mapper文件，而Mapper文件，则是使用MyBatis的方法，与DAO接口中的方法对应。







3.根据业务需求，编写service层，service层对dao层产生的数据进行处理，把封装类的属性全部设置进去返回。
- 新建service接口，接口只是一个结构，并没有任何代码实现功能
- 实现service接口（即serviceImpl实现），调用自身Dao及其他service的实现方法







4.最后编写controller层代码，调用service得到返回的封装类
- 继承BaseController（包括转换日期、请求数据分页、响应数据分页、响应返回结构等公共方法）

- 注入需要的service接口，方便Controller的方法调用Service的Impl实现方法
  ###常用自定义注解

- ```java
      @Autowired
      private ISysDicService sysDicService;
  ```

- @Lang:将数据返回相对应语言（该接口存在于serviceImpl实现类中）

- ```java
  @Lang(keyFieldName = LangInfoKeyFieldName.DIC_ID_KEY_FILE_NAME, fieldNames = {LangInfoFileName.DIC_NAME_FILE_NAME})
  ```

- @Log:添加操作记录到mysql（将调用该Controller的API接口的请求参数、返回参数等数据写入数据操作的日志）
  ###常用系统注解

- ```java
  @Log(title = "SysDic", businessType = BusinessType.INSERT)
  ```

- @Service：标注在service的实现类(Impl)上，将类注册到spring容器（事实上，Service也是IOC容器中一个普通的Component）

- @Validated：参数校验，用在controller

- @PathVariable：接收请求路径中占位符的值,用在controller

- @PreAuthorize("hasAnyAuthority('xxx','xxx')：用来鉴别当前登录用户所拥有的角色是否有其中一个xxx权限访问该接口。
  ###常用工具

- ```java
  @PreAuthorize("hasAnyAuthority('"+PermissionConstants.MENU_EDIT+"','"+PermissionConstants.MENU_VIEW+"')")
  ```

  MessageUtils:调用message方法将返回相应语言字符（多语言设计）
  ###常用bean

  ```java
          //check data
          if (StringUtils.isEmpty(dic.getDicCode())) {
              String msg = MessageUtils.message(MessageCode.DIC_DIC_CODE_EMPTY);
              throw new CustomException(msg);
          }
  
          SysDic sysDic = this.getSysDicByCode(dic.getDicCode());
          if (sysDic != null) {
              String msg = MessageUtils.message(MessageCode.DIC_DIC_CODE_EXISTS, sysDic.getDicCode());
              throw new CustomException(msg);
          }
  ```

  TokenService：获取token包含的用户信息（根据传入的Token解析出用户信息）
  ###Controller返回结果

- TableDataInfo：分页请求返回结果

- AjaxResult：其他请求返回结果