## 基于Spring Boot+Layui的Excite内容管理系统

## 模块划分：

> 有的项目按MVC架构划分模块，如controller、service、domain，分别为一个模块；
> 
> 也有的项目按业务功能划分模块，比如系统system、客户customer;
> 
> 此项目使用后者，按照业务功能划分模块。

**模块详细说明：**

+ excite-admin：Web服务入口，提供访问；
+ excite-common：通用工具层，封装了一些供其他模块调用的一些工具类；
+ excite-system：system模块，主要是RBAC的service层和dao层；
+ excite-framework：项目系统配置模块，集成框架的各种配置信息。

**依赖关系：**

common → system → framework → admin

> 如果再有其他模块，则与 `system` 并列。