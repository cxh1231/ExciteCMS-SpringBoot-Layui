## 基于Spring Boot+Layui的Excite内容管理系统


## 1、开发环境

| 软件与环境                    | 说明                      |
| :---------------------------- | :--------------------- |
| IntelliJ IDEA Ultimate 2021.2 | Java编程开发的集成环境    |
| JDK 1.8                       | Java Development Kit   |
| Maven 3.6                     | 项目管理和综合工具        |
| MySQL 8.X                     | 关系型数据库             |
| Navicat for MySQL【非必须】     | 数据库辅助工具           |
| Redis 3.X【非必须】             | 缓存数据库。版本任意      |

## 2、主要技术栈

**后端：**


| 技术栈            | 说明                     |
| :---------------- | :----------------------- |
| Spring Boot 2.5.X | Spring框架               |
| Sa-Token          | 权限认证框架             |
| thymeleaf         | 前端模板引擎             |
| nekohtml          | 提供非严格HTML模式支持   |
| mybatis-plus      | Mybatis 的增强工具       |
| freemarker        | 代码生成器模板           |
| lombok            | Lombok，Java开发工具     |
| druid             | 数据库连接池             |
| openapi           | 接口文档                 |
| fastjson          | 阿里巴巴的开源JSON解析库 |

**前端模板：**

+ Layui
+ miniLayui

## 3、模块划分：

> 有的项目按MVC架构划分模块，如controller、service、domain，分别为一个模块；
> 
> 也有的项目按业务功能划分模块，比如系统system、客户customer;
> 
> 此项目使用后者，按照业务功能划分模块。

**模块详细说明：**

+ excite-admin：Web服务入口，提供访问；
+ excite-common：通用工具层，封装了一些供其他模块调用的一些工具类，以及框架通用的配置信息；
+ excite-system：system模块，主要是RBAC的service层和dao层；
+ excite-framework：项目系统配置模块，目前主要进行权限认证与AOP，该模块主要继承自system，作用于admin。

**依赖关系：**

common → system → framework → admin

> 如果再有其他模块（比如客户端`client`），则与 `system` 并列，并根据项目实际情况，决定是否需要由framework继承。

