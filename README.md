<p align="center">
	<img alt="logo" src="https://excitecms.gitee.io/static/images/logo.png">
</p>

<h1 align="center" >ExciteCMS-Layui</h1>

<p align="center"><b>基于Spring Boot 2 + Layui 开发的内容管理系统 / 快速开发脚手架</b></p>

<p align="center">
	<a href="https://gitee.com/geekrdc/ExciteCMS-SpringBoot-Layui/stargazers" target="_blank">
        <img src="https://gitee.com/geekrdc/ExciteCMS-SpringBoot-Layui/badge/star.svg"></a>
    <a href="https://gitee.com/geekrdc/ExciteCMS-SpringBoot-Layui/members" target="_blank">
        <img src="https://gitee.com/geekrdc/ExciteCMS-SpringBoot-Layui/badge/fork.svg"></a>
	<a href="https://github.com/cxh1231/ExciteCMS-SpringBoot-Layui/stargazers" target="_blank">
        <img src="https://img.shields.io/github/stars/cxh1231/ExciteCMS-SpringBoot-Layui?style=flat-square&logo=GitHub"></a>
	<a href="https://github.com/cxh1231/ExciteCMS-SpringBoot-Layui/network/members" target="_blank">
        <img src="https://img.shields.io/github/forks/cxh1231/ExciteCMS-SpringBoot-Layui?style=flat-square&logo=GitHub"></a>
	<a href="https://gitee.com/geekrdc/ExciteCMS-SpringBoot-Layui" target="_blank">
        <img src="https://img.shields.io/badge/ExciteCMS%20Layui-1.0.0-brightgreen.svg"></a>
	<a href="https://gitee.com/geekrdc/ExciteCMS-SpringBoot-Layui/blob/master/LICENSE" target="_blank">
        <img src="https://img.shields.io/github/license/cxh1231/ExciteCMS-SpringBoot-Layui.svg?style=flat-square"></a>
</p>

## 1、主要功能简介

+ 用户管理：基本的增删改查与授权角色；
+ 角色管理：基本的增删改查与授权用户和授权菜单；
+ 菜单管理：基本的增删改查，其中以树状展示；
+ 日志管理：即操作日志管理、登录日志管理等功能；
+ 监控管理：即服务器监控、数据库监控等功能；
+ 开发功能：即代码生成等功能；
+ 第三方功能：如微信支付、支付宝支付、七牛云OSS等功能；
+

## 2、视频演示

详情请访问：[https://www.bilibili.com/video/BV1Wq4y1h78B](https://www.bilibili.com/video/BV1Wq4y1h78B)

## 3、主要功能演示

> **系统功能**：主要实现了基本的RBAC，即用户管理、权限控制、日志管理、代码生成与系统监控等功能。其预览图如下所示。

<table>
<tr>
<td>
<img src="https://img.zxdmy.com/2022/202201302055725.png">
<center><b>首页</b></center>
</td>
<td>
<img src="https://img.zxdmy.com/2022/202201262036756.png">
<center><b>用户管理</b></center>
</td>
</tr>
<tr>
<td>
<img src="https://img.zxdmy.com/2022/202201262036690.png">
<center><b>角色管理</b></center>
</td>
<td>
<img src="https://img.zxdmy.com/2022/202201262034888.png">
<center><b>菜单/权限管理</b></center>
</td>
</tr>
<tr>
<td>
<img src="https://img.zxdmy.com/2022/202201262041698.png">
<center><b>操作日志</b></center>
</td>
<td>
<img src="https://img.zxdmy.com/2022/202201262042293.png">
<center><b>登录日志</b></center>
</td>
</tr>
<tr>
<td>
<img src="https://img.zxdmy.com/2022/202201262044870.png">
<center><b>代码生成</b></center>
</td>
<td>
<img src="https://img.zxdmy.com/2022/202201262044488.png">
<center><b>服务器监控</b></center>
</td>
</tr>
</table>

> **集成功能**：主要实现了部分第三方组件功能，如支付宝、微信等支付功能，对象存储服务功能，短信服务功能等。其预览见演示视频。

## 4、主要技术栈

**后的技术栈：**

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

## 5、开发文档

网址1：[https://excitecms.gitee.io](https://excitecms.gitee.io)

网址2：[https://excite.zxdmy.com](https://excite.zxdmy.com)

> 以上两个开发文档网址内容一致，区别是部署的仓库不同（Gitee和GitHub）。

## 6、在线体验

网址：[http://demo.excite.zxdmy.com](http://demo.excite.zxdmy.com)

体验账号：demo@zxdmy.com

登录密码：123456

> 注：① 体验账号只有浏览权限，无增删改的权限。② 由于部分页面设置了固定的长宽，使用电脑浏览器访问体验更佳！

## 7、支持

如果你觉得 ExciteCMS 还不错，可以通过点亮仓库右上角的 Star 来支持我们~

## 8、贡献

发现BUG？发现功能错误？欢迎提交**issues**：[Gitee](https://gitee.com/ExciteTeam/ExciteCMS-SpringBoot-Layui/issues) | [GitHub](https://github.com/cxh1231/ExciteCMS-SpringBoot-Layui/issues)

## 9、开源协议

[Apache License 2.0](https://gitee.com/ExciteTeam/ExciteCMS-SpringBoot-Layui/blob/master/LICENSE)