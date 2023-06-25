### 系统简介

企业级通用开发平台，前后端分离架构，单工程，多模块，部署形态为单体应用。
前端基于vue3.2.47，element-plus 2.1.0，前端框架vue-element-plus-admin1.9.4深度整合改造。
后端SSM+MybatisPlus，使用SpringBoot 2.3.0。
数据库使用MySql 5.7.36.

重度使用MybatisPlus，包括主键策略、逻辑删除、乐观锁、自动填充、数据分页、CURD接口、条件构造器等，
二次封装和扩展代码生成器，实现entity、dao、service、controller、vo及前端vue页面生成。

整体架构图如下：
![输入图片说明](resource/1.png)

技术选型，详见专栏博客：https://blog.csdn.net/seawaving/article/details/130015830

### 后端架构

到目前为止，整个工程项目，后端共计16个模块，架构图和依赖关系如下图所示：
![输入图片说明](resource/2.png)

这16个模块分成三类，一类是平台内核模块，命名规则是platform+模块功能名称，图中用蓝色标示；一类是能力扩展模块，命名规则是platform-boot-starter+模块功能名称，图中用绿色标示；剩下的一类是接口平台，命名规则是cip+模块功能名称，图中用紫色标示，相对平台独立，但又作为平台的重要组成部分。

#### 平台内核模块

platform-common作为公用基础模块，主要包括工具类、公用注解、公共父类、公共常量、公共枚举值，与前端UI交互定义的vo类，该模块为最基础的模块，无前置依赖。

platform-system是平台最核心的模块，主要包括组织机构、人员、角色（用户组）、权限、日志、系统参数、模块这些实体和服务的实现，需要注意的是，权限控制、日志记录，并不是在该模块实现，而是在platform-framework平台框架中实现，该模块依赖于platform-common。

platform-framework是平台框架，负责身份认证、权限控制、全局配置、数据分页、日志处理、自动填充（创建人、创建时间、修改人、修改时间），因为身份认证、权限控制等功能，不可避免需要使用处于platform-system模块中的人员、用户组等实体和服务，因此依赖于platform-system。

platform-support是一个业务支撑模块，基于技术组件进行功能设计与封装，实现一些通用的功能设计，更方便业务逻辑的实现，提供附件管理、通知公告、内容模板（用于短信、邮件、消息）、单据流水号、门户等功能。这些支撑模块同样需要位于platform-system模块中的人员、组织机构等实体和服务，因此依赖于platform-system。

platform-entityconfig属于低代码配置范畴，定义了业务实体的元数据，通过模块、实体、模型、视图多级配置，结合模板技术，实现细粒度的代码生成控制。

platform-boot-starter：平台启动项目，整合平台基础功能，类似于spring-boot-starter，业务系统引入该包进行依赖。该模块自身没有实体与服务，而是汇总整合，把platform-framework引用进来，同时进行配置。配置分两方面，一方面是做一个配置类，加一些注解（如：@EnableRetry、@ServletComponentScan、@EnableTransactionManagement），使用开发平台实现的业务系统，就不需要在启动类上重复添加这些注解；另一方面，是位于yml配置文件中的配置信息，也分为两部分，一部分是三方组件自身的，如数据源、连接池、redis、quartz、logback，另一方面是自定义的系统参数，如用户默认密码、导出excel数据的批次最大行数量。

platform-boot-starter-demo：示例项目，实际是模拟业务系统如何使用开发平台，用于平台自身功能开发与调试。

#### 能力扩展模块

绿色标示的四个模块，比较好理解，通常是对第三方组件的封装与整合，依赖于公共基础模块platform-common，这些模块可以不断扩展的，业务系统按需引入即可，这样就实现了核心模块必选、扩展模块可选的目的。
platform-boot-starter-mail：邮件，集成springmail组件，实现邮件的发送功能封装
platform-boot-starter-oss： 对象存储，用于文件存储封装，底层可基于多种模式，如本地磁盘、对象存储系统等
platform-boot-starter-scheduler：任务调度，集成quartz组件，实现任务调度可视化配置
platform-boot-starter-notification：消息通知，基于netty实现的websocket，用于系统内置消息

对于扩展模块，平台的核心模块实际也可能会用到，例如platform-support中的附件功能，就会用到platform-boot-starter-oss；platform-system中的自动解锁用户功能，就会用到platform-boot-starter-scheduler。

#### 接口平台

之前开源了一套通用接口平台，详见专栏https://blog.csdn.net/seawaving/category_11610162.html。
现在，将通用接口平台作为一个模块，整合到新的应用开发平台当中来，由通用接口平台统一对外暴露应用系统的API数据接口，以及推送事件消息。
之前的的通用接口平台，主要由两个模块组成，一个是platform-cip（cip是common interface platform缩写），即接口平台的主体，另外一个是platform-cip-common，被platform-cip依赖。
实际上，接口平台的主体，platform-cip，里面包含了三块内容：
1.对外提供API数据接口，提供API服务
2.基于netty的web socket服务端提供消息服务
3.平台自身基础数据的维护，如应用、API服务清单、消息服务清单、订阅等。
本次整合，不是简单的迁移，而是包括重构优化，将platform-cip进一步拆分为三个模块，一共4个模块：
platform-cip-common：公共基础
platform-cip-api：API服务
platform-cip-message：消息服务
platform-cip-manage：平台管理
4个模块内关系为manage依赖common，api和manage相互独立，但都依赖于manage。

### 如何运行

#### 1. 准备工作

预装redis、nodejs、mysql、ide

#### 2. 初始化数据库

执行/resource目录下的init.sql,创建名字为abc的数据库。

#### 3 .前端

nodejs >=14.6，执行pnpm install命令，若nodejs版本过低会提示

使用vscode打开platform-web目录，执行pnpm install安装npm module

执行前建议设置国内镜像以加快下载速度
pnpm config set registry https://registry.npm.taobao.org/

执行结束会提示如下错误，不用理会，因为把husky移除导致的，不影响系统正常运行，进行下步dev脚本即可
husky install
'husky' 不是内部或外部命令，也不是可运行的程序
或批处理文件。

执行dev脚本，默认打开localhost:4000

#### 4 .后端

标准SpringBoot项目，多模块，启动类位于platform-boot-starter-demo下，默认端口8080。

注：系统的下拉数据源，也即数据字典使用redis缓存，按上述步骤构建后，部分查询界面不显示中文名称，可在系统登录后，访问系统管理-》系统维护菜单下的“重建缓存”按钮，系统会自动将数据库的字典数据写入到redis中。

#### 5 .接口平台对接客户端

cip-client是一个模拟的接口平台客户端，是一个独立的springboot，相当于第三方系统，有自己独立的数据库，数据库脚本参见\cip-client\src\main\resources\init.sql

### 常见问题

1.项目克隆后，初始运行有可能编译错误，lombok插件失效，编辑器报类似如下警告：
java: You aren't using a compiler supported by lombok, so lombok will not work and has been disabled.
Your processor is: com.sun.proxy.$Proxy8
Lombok supports: OpenJDK javac, ECJ

解决方式1是彻底清理缓存，即执行invalidate caches，一般能解决，不过更推荐如下设置
![3.jpg](resource%2F3.jpg)
-Djps.track.ap.dependencies=false

### 未来规划

客观地说，目前开发平台已经实现了大部分常用常见功能，可以投入使用了。
由于是一路狂奔模式，速度提升，时间缩短，但不可避免一些功能遗留了待办项，以及未充分测试导致存在bug，后面需要再循着功能过一遍，进行重构、测试，完善功能，输出设计。特别是低代码配置部分，需要持续完善与改进，简化配置，进一步提升开发效率。

后面几块是平台欠缺的，需要补全和完善，每一块都是硬骨头，难度和工作量都不小，做了一些简单初步的了解，具体如下：

输出系统使用手册
集成图表组件
集成工作流
实现可视化表单
移动端实现
自定义查询
实现数据权限

### 系统设计资料

参见csdn博客专栏 [http://t.csdn.cn/Zug2R](http://t.csdn.cn/Zug2R)
平台研发过程中的设计思路、遇到的问题和方案的选择等一并分享出来，欢迎交流与讨论。


