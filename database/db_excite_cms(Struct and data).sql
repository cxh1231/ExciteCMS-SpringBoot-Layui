/*
 Navicat Premium Data Transfer

 Source Server         : 本机数据库
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : db_excite_cms

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 24/12/2021 21:08:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login`  (
  `id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_request
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_request`;
CREATE TABLE `sys_log_request`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int NULL DEFAULT NULL COMMENT '请求用户ID',
  `ip` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求IP',
  `user_agent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器Agent',
  `req_uri` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求的URI',
  `req_method` varchar(31) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法：GET | POST | DELETE',
  `req_function` varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求的函数',
  `req_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求的数据',
  `res_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '返回的数据',
  `time_cost` int NULL DEFAULT NULL COMMENT '耗时',
  `status` tinyint NULL DEFAULT NULL COMMENT '请求状态：0-失败 | 1-成功',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_request
-- ----------------------------
INSERT INTO `sys_log_request` VALUES (1, 10001, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 96.0.4664.45', '/system/role/list', 'GET', 'SysRoleController.getRoleList(..)', '[null, null, null, null]', '{\"msg\":\"获取角色列表成功\",\"code\":200,\"data\":[{\"id\":100,\"name\":\"超级管理员\",\"permission\":\"admin\",\"remark\":\"\",\"sort\":100,\"status\":2,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 17:10:24\",\"deleteTime\":null},{\"id\":101,\"name\":\"围观角色\",\"permission\":\"visit\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":\"2021年09月23日 19:36:08\",\"updateTime\":\"2021年12月07日 11:35:31\",\"deleteTime\":null},{\"id\":102,\"name\":\"test2\",\"permission\":\"test2\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 21:02:38\",\"deleteTime\":null},{\"id\":103,\"name\":\"系统管理员\",\"permission\":\"system\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月04日 20:55:13\",\"updateTime\":\"2021年12月06日 20:23:26\",\"deleteTime\":null},{\"id\":104,\"name\":\"系统管理员\",\"permission\":\"system\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月04日 20:56:45\",\"updateTime\":\"2021年12月06日 20:19:40\",\"deleteTime\":null},{\"id\":105,\"name\":\"测试\",\"permission\":\"1111\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月08日 20:55:59\",\"updateTime\":null,\"deleteTime\":null}],\"success\":true,\"count\":6}', 12, 1, '2021-12-09 21:17:00', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (2, 10006, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 96.0.4664.45', '/system/role/list', 'GET', 'SysRoleController.getRoleList(..)', '[null, null, null, null]', '{\"msg\":\"获取角色列表成功\",\"code\":200,\"data\":[{\"id\":100,\"name\":\"超级管理员\",\"permission\":\"admin\",\"remark\":\"\",\"sort\":100,\"status\":2,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 17:10:24\",\"deleteTime\":null},{\"id\":101,\"name\":\"围观角色\",\"permission\":\"visit\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":\"2021年09月23日 19:36:08\",\"updateTime\":\"2021年12月07日 11:35:31\",\"deleteTime\":null},{\"id\":102,\"name\":\"test2\",\"permission\":\"test2\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 21:02:38\",\"deleteTime\":null},{\"id\":103,\"name\":\"系统管理员\",\"permission\":\"system\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月04日 20:55:13\",\"updateTime\":\"2021年12月06日 20:23:26\",\"deleteTime\":null},{\"id\":104,\"name\":\"系统管理员\",\"permission\":\"system\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月04日 20:56:45\",\"updateTime\":\"2021年12月06日 20:19:40\",\"deleteTime\":null},{\"id\":105,\"name\":\"测试\",\"permission\":\"1111\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月08日 20:55:59\",\"updateTime\":null,\"deleteTime\":null}],\"success\":true,\"count\":6}', 12, 1, '2021-12-09 21:17:00', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (3, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/goEdit/102', 'GET', 'SysRoleController.goEdit(..)', '[102, {}]', '\"system/role/edit\"', 6, 1, '2021-12-09 21:18:09', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (4, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/update', 'POST', 'SysRoleController.updateRole(..)', '[SysRole(id=102, name=test2, permission=test2, remark=, sort=50, status=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@334ebc53]', '{\"msg\":\"更新角色成功\",\"code\":200,\"success\":true}', 12, 1, '2021-12-09 21:18:11', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (5, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/list?page=1&limit=10&name=&permission=', 'GET', 'SysRoleController.getRoleList(..)', '[1, 10, , ]', '{\"msg\":\"获取角色列表成功\",\"code\":200,\"data\":[{\"id\":100,\"name\":\"超级管理员\",\"permission\":\"admin\",\"remark\":\"\",\"sort\":100,\"status\":2,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 17:10:24\",\"deleteTime\":null},{\"id\":101,\"name\":\"围观角色\",\"permission\":\"visit\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":\"2021年09月23日 19:36:08\",\"updateTime\":\"2021年12月07日 11:35:31\",\"deleteTime\":null},{\"id\":102,\"name\":\"test2\",\"permission\":\"test2\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 21:18:10\",\"deleteTime\":null},{\"id\":103,\"name\":\"系统管理员\",\"permission\":\"system\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月04日 20:55:13\",\"updateTime\":\"2021年12月06日 20:23:26\",\"deleteTime\":null},{\"id\":104,\"name\":\"系统管理员\",\"permission\":\"system\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月04日 20:56:45\",\"updateTime\":\"2021年12月06日 20:19:40\",\"deleteTime\":null},{\"id\":105,\"name\":\"测试\",\"permission\":\"1111\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月08日 20:55:59\",\"updateTime\":null,\"deleteTime\":null}],\"success\":true,\"count\":6}', 4, 1, '2021-12-09 21:18:12', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (6, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/goEdit/102', 'GET', 'SysRoleController.goEdit(..)', '[102, {}]', '\"system/role/edit\"', 8, 1, '2021-12-09 21:20:25', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (7, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/update', 'POST', 'SysRoleController.updateRole(..)', '[SysRole(id=102, name=test2, permission=test2, remark=, sort=50, status=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@64105909]', '{\"msg\":\"更新角色成功\",\"code\":200,\"success\":true}', 12, 1, '2021-12-09 21:20:26', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (8, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/list?page=1&limit=10&name=&permission=', 'GET', 'SysRoleController.getRoleList(..)', '[1, 10, , ]', '{\"msg\":\"获取角色列表成功\",\"code\":200,\"data\":[{\"id\":100,\"name\":\"超级管理员\",\"permission\":\"admin\",\"remark\":\"\",\"sort\":100,\"status\":2,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 17:10:24\",\"deleteTime\":null},{\"id\":101,\"name\":\"围观角色\",\"permission\":\"visit\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":\"2021年09月23日 19:36:08\",\"updateTime\":\"2021年12月07日 11:35:31\",\"deleteTime\":null},{\"id\":102,\"name\":\"test2\",\"permission\":\"test2\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 21:20:26\",\"deleteTime\":null},{\"id\":103,\"name\":\"系统管理员\",\"permission\":\"system\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月04日 20:55:13\",\"updateTime\":\"2021年12月06日 20:23:26\",\"deleteTime\":null},{\"id\":104,\"name\":\"系统管理员\",\"permission\":\"system\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月04日 20:56:45\",\"updateTime\":\"2021年12月06日 20:19:40\",\"deleteTime\":null},{\"id\":105,\"name\":\"测试\",\"permission\":\"1111\",\"remark\":\"\",\"sort\":50,\"status\":0,\"isDelete\":null,\"createTime\":\"2021年12月08日 20:55:59\",\"updateTime\":null,\"deleteTime\":null}],\"success\":true,\"count\":6}', 3, 1, '2021-12-09 21:20:27', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (9, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/goAdd', 'GET', 'SysRoleController.goAdd()', '[]', '\"system/role/add\"', 1, 1, '2021-12-09 21:20:34', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (10, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/add', 'POST', 'SysRoleController.addRole(..)', '[SysRole(id=null, name=哈哈哈, permission=sdffsdfsdfsdf, remark=, sort=50, status=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@3cfafd7b]', '{\"msg\":\"error:角色实体不能为null\",\"code\":500,\"success\":false}', 3, 1, '2021-12-09 21:20:47', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (11, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/1', 'POST', 'SysRoleController.changeStatus(..)', '[1, [Ljava.lang.Integer;@43de3908]', '{\"msg\":\"1个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 41, 1, '2021-12-09 21:29:28', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (12, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/add', 'POST', 'SysRoleController.addRole(..)', '[SysRole(id=null, name=5655, permission=555, remark=, sort=50, status=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@616d7561]', '{\"msg\":\"error:角色实体不能为null\",\"code\":500,\"success\":false}', 2, 0, '2021-12-09 21:30:03', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (13, 10006, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 96.0.4664.45', '/system/role/get/sdd', 'GET', 'SysRoleController.getRole(..)', '[sdd]', '{\"msg\":\"error:For input string: \\\"sdd\\\"\",\"code\":500,\"success\":false}', 3, 0, '2021-12-09 21:35:27', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (14, 10006, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 96.0.4664.45', '/system/role/get/0', 'GET', 'SysRoleController.getRole(..)', '[0]', '/ by zero', 3, 0, '2021-12-09 21:37:05', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (15, 10006, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 96.0.4664.45', '/system/role/get/0', 'GET', 'SysRoleController.getRole(..)', '[0]', '{\"msg\":\"获取角色失败\",\"code\":400,\"success\":false}', 10, 0, '2021-12-09 21:44:59', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (16, 10006, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 96.0.4664.45', '/system/role/get/101', 'GET', 'SysRoleController.getRole(..)', '[101]', '{\"msg\":\"获取角色成功\",\"code\":200,\"data\":{\"id\":101,\"name\":\"围观角色\",\"permission\":\"visit\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":\"2021年09月23日 19:36:08\",\"updateTime\":\"2021年12月07日 11:35:31\",\"deleteTime\":null},\"success\":true}', 12, 1, '2021-12-09 21:49:46', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (17, 10006, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 96.0.4664.45', '/system/role/get/100', 'GET', 'SysRoleController.getRole(..)', '[100]', '{\"msg\":\"获取角色成功\",\"code\":200,\"data\":{\"id\":100,\"name\":\"超级管理员\",\"permission\":\"admin\",\"remark\":\"\",\"sort\":100,\"status\":2,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 17:10:24\",\"deleteTime\":null},\"success\":true}', 2, 1, '2021-12-09 21:49:50', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (18, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/get/100', 'GET', 'SysRoleController.getRole(..)', '[100]', '{\"msg\":\"获取角色成功\",\"code\":200,\"data\":{\"id\":100,\"name\":\"超级管理员\",\"permission\":\"admin\",\"remark\":\"\",\"sort\":100,\"status\":2,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 17:10:24\",\"deleteTime\":null},\"success\":true}', 48, 1, '2021-12-10 08:42:00', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (19, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/get/100', 'GET', 'SysRoleController.getRole(..)', '[100]', '{\"msg\":\"获取角色成功\",\"code\":200,\"data\":{\"id\":100,\"name\":\"超级管理员\",\"permission\":\"admin\",\"remark\":\"\",\"sort\":100,\"status\":2,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 17:10:24\",\"deleteTime\":null},\"success\":true}', 4, 1, '2021-12-10 08:42:12', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (20, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/get/100', 'GET', 'SysRoleController.getRole(..)', '[100]', '{\"msg\":\"获取角色成功\",\"code\":200,\"data\":{\"id\":100,\"name\":\"超级管理员\",\"permission\":\"admin\",\"remark\":\"\",\"sort\":100,\"status\":2,\"isDelete\":null,\"createTime\":null,\"updateTime\":\"2021年12月09日 17:10:24\",\"deleteTime\":null},\"success\":true}', 3, 1, '2021-12-10 08:42:24', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (21, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/get/101', 'GET', 'SysRoleController.getRole(..)', '[101]', '{\"msg\":\"获取角色成功\",\"code\":200,\"data\":{\"id\":101,\"name\":\"围观角色\",\"permission\":\"visit\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":\"2021年09月23日 19:36:08\",\"updateTime\":\"2021年12月07日 11:35:31\",\"deleteTime\":null},\"success\":true}', 13, 1, '2021-12-10 08:43:01', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (22, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/get/101', 'GET', 'SysRoleController.getRole(..)', '[101]', '{\"msg\":\"获取角色成功\",\"code\":200,\"data\":{\"id\":101,\"name\":\"围观角色\",\"permission\":\"visit\",\"remark\":\"\",\"sort\":50,\"status\":1,\"isDelete\":null,\"createTime\":\"2021年09月23日 19:36:08\",\"updateTime\":\"2021年12月07日 11:35:31\",\"deleteTime\":null},\"success\":true}', 3, 1, '2021-12-10 08:43:15', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (23, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/menu/update', 'POST', 'SysMenuController.updateMenu(..)', '[SysMenu(id=24, name=日志管理, icon=fa-list-alt, path=, permission=system:log, component=null, parentId=1, type=C, sort=50, editable=null, removable=null, status=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null, child=null, checkArr=0)]', '{\"msg\":\"更新菜单成功\",\"code\":200,\"success\":true}', 68, 1, '2021-12-13 19:06:45', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (24, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/menu/add', 'POST', 'SysMenuController.addMenu(..)', '[SysMenu(id=null, name=请求日志, icon=fa-low-vision, path=/system/log/request, permission=system:log:request, component=null, parentId=24, type=M, sort=50, editable=null, removable=null, status=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null, child=null, checkArr=0)]', '{\"msg\":\"菜单添加成功\",\"code\":200,\"success\":true}', 13, 1, '2021-12-13 19:07:47', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (25, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/update', 'POST', 'SysRoleController.updateRole(..)', '[SysRole(id=100, name=超级管理员, permission=admin, remark=, sort=100, status=2, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@1df0f612]', '{\"msg\":\"更新角色成功\",\"code\":200,\"success\":true}', 32, 1, '2021-12-13 19:08:04', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (26, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/menu/update', 'POST', 'SysMenuController.updateMenu(..)', '[SysMenu(id=32, name=操作日志, icon=fa fa-low-vision, path=/system/log/request, permission=system:log:request, component=null, parentId=24, type=M, sort=50, editable=null, removable=null, status=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null, child=null, checkArr=0)]', '{\"msg\":\"更新菜单成功\",\"code\":200,\"success\":true}', 14, 1, '2021-12-13 19:16:22', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (27, NULL, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/login', 'POST', 'SysUserController.login(..)', '[10000@zxdmy.com, 123456, vg0ko, null]', '{\"msg\":\"验证码已失效或验证码错误，请重试\",\"code\":400,\"success\":false}', 35, 0, '2021-12-18 19:27:19', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (28, NULL, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/login', 'POST', 'SysUserController.login(..)', '[10000@zxdmy.com, 123456, th2i1, null]', '{\"msg\":\"登录成功！\",\"code\":200,\"data\":{\"tokenName\":\"com.zxdmy.excite\",\"tokenValue\":\"c65f833c-1caf-4f67-8603-3e11be5311e7\",\"isLogin\":true,\"loginId\":\"10000\",\"loginType\":\"login\",\"tokenTimeout\":2592000,\"sessionTimeout\":2592000,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":-1,\"loginDevice\":\"default-device\",\"tag\":null},\"success\":true}', 85, 1, '2021-12-18 19:27:26', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (29, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/logout', 'POST', 'SysUserController.logout()', '[]', '{\"msg\":\"已退出登录！\",\"code\":200,\"success\":true}', 9, 1, '2021-12-18 19:33:36', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (30, NULL, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/login', 'POST', 'SysUserController.login(..)', '[10000@zxdmy.com, 123456, 5b7e, null]', '{\"msg\":\"登录成功！\",\"code\":200,\"data\":{\"tokenName\":\"com.zxdmy.excite\",\"tokenValue\":\"a431509a-8fc3-4741-8da6-42d1151d5a59\",\"isLogin\":true,\"loginId\":\"10000\",\"loginType\":\"login\",\"tokenTimeout\":2592000,\"sessionTimeout\":2592000,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":-1,\"loginDevice\":\"default-device\",\"tag\":null},\"success\":true}', 11, 1, '2021-12-18 19:34:07', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (31, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/update', 'POST', 'SysRoleController.updateRole(..)', '[SysRole(id=100, name=超级管理员, permission=admin, remark=超级管理员，禁止禁用和删除！, sort=100, status=2, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@29e95f77]', '{\"msg\":\"更新角色成功\",\"code\":200,\"success\":true}', 69, 1, '2021-12-20 17:07:26', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (32, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/delete/10006', 'POST', 'SysUserController.delete(..)', '[10006]', '{\"msg\":\"用户删除失败\",\"code\":400,\"success\":false}', 4, 0, '2021-12-20 20:36:02', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (33, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/delete/10009', 'POST', 'SysUserController.delete(..)', '[10009]', '{\"msg\":\"用户删除失败\",\"code\":400,\"success\":false}', 1, 0, '2021-12-20 20:36:50', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (34, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/delete/10006', 'POST', 'SysUserController.delete(..)', '[10006]', '{\"msg\":\"用户删除失败\",\"code\":400,\"success\":false}', 1, 0, '2021-12-20 21:12:21', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (35, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/delete/10008', 'POST', 'SysUserController.delete(..)', '[10008]', '{\"msg\":\"用户删除失败\",\"code\":400,\"success\":false}', 0, 0, '2021-12-20 21:13:02', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (36, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/delete/10006', 'POST', 'SysUserController.delete(..)', '[10006]', '{\"msg\":\"用户删除失败\",\"code\":400,\"success\":false}', 0, 0, '2021-12-20 21:13:17', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (37, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/0', 'POST', 'SysRoleController.changeStatus(..)', '[0, [Ljava.lang.Integer;@4a377b68]', '{\"msg\":\"1个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 8, 1, '2021-12-20 21:26:42', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (38, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/1', 'POST', 'SysRoleController.changeStatus(..)', '[1, [Ljava.lang.Integer;@58c2b827]', '{\"msg\":\"1个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 5, 1, '2021-12-20 21:26:48', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (39, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/1', 'POST', 'SysRoleController.changeStatus(..)', '[1, [Ljava.lang.Integer;@58d353c]', '{\"msg\":\"2个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 7, 1, '2021-12-20 21:29:24', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (40, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/0', 'POST', 'SysRoleController.changeStatus(..)', '[0, [Ljava.lang.Integer;@2f1f9c78]', '{\"msg\":\"2个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 7, 1, '2021-12-20 21:29:31', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (41, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/0', 'POST', 'SysRoleController.changeStatus(..)', '[0, [Ljava.lang.Integer;@6de56542]', '{\"msg\":\"2个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 3, 1, '2021-12-20 21:30:31', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (42, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/1', 'POST', 'SysRoleController.changeStatus(..)', '[1, [Ljava.lang.Integer;@aa68c8d]', '{\"msg\":\"2个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 6, 1, '2021-12-20 21:30:36', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (43, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/add', 'POST', 'SysUserController.add(..)', '[SysUser(id=null, username=10000@zxdmy.com, avatar=null, sex=0, email=, phone=, password=123456, status=1, loginIp=null, loginDate=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null), null]', '{\"msg\":\"新用户添加失败，请重试！\",\"code\":400,\"success\":false}', 49, 0, '2021-12-22 21:33:56', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (44, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/add', 'POST', 'SysUserController.add(..)', '[SysUser(id=null, username=10000@zxdmy.com, avatar=null, sex=0, email=, phone=, password=123456, status=1, loginIp=null, loginDate=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@1a999fd2]', '{\"msg\":\"新用户添加失败，请重试！\",\"code\":400,\"success\":false}', 2, 0, '2021-12-22 21:36:17', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (45, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/add', 'POST', 'SysUserController.add(..)', '[SysUser(id=null, username=10000@zxdmy.com, avatar=null, sex=0, email=, phone=, password=123456, status=1, loginIp=null, loginDate=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@5639e2ef]', '{\"msg\":\"新用户添加失败，请重试！\",\"code\":400,\"success\":false}', 5, 0, '2021-12-22 21:43:07', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (46, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/add', 'POST', 'SysUserController.add(..)', '[SysUser(id=null, username=10000@zxdmy.com, avatar=null, sex=0, email=00, phone=, password=123456, status=1, loginIp=null, loginDate=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@7ac340df]', '{\"msg\":\"新用户添加失败，请重试！\",\"code\":400,\"success\":false}', 9, 0, '2021-12-22 21:48:46', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (47, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/add', 'POST', 'SysUserController.add(..)', '[SysUser(id=null, username=10000@zxdmy.com, avatar=null, sex=0, email=00, phone=, password=123456, status=1, loginIp=null, loginDate=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@24868e0]', '{\"msg\":\"新用户添加失败，请重试！\",\"code\":400,\"success\":false}', 3, 0, '2021-12-22 21:48:54', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (48, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/user/add', 'POST', 'SysUserController.add(..)', '[SysUser(id=null, username=10000@zxdmy.com, avatar=null, sex=0, email=00, phone=, password=123456, status=1, loginIp=null, loginDate=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null), [Ljava.lang.Integer;@71451880]', '{\"msg\":\"新用户添加失败，请重试！\",\"code\":400,\"success\":false}', 2, 0, '2021-12-22 21:49:23', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (49, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/0', 'POST', 'SysRoleController.changeStatus(..)', '[0, [Ljava.lang.Integer;@22a0d3f]', '{\"msg\":\"1个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 42, 1, '2021-12-23 15:40:47', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (50, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/0', 'POST', 'SysRoleController.changeStatus(..)', '[0, [Ljava.lang.Integer;@342e7519]', '{\"msg\":\"1个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 4, 1, '2021-12-23 15:40:48', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (51, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/1', 'POST', 'SysRoleController.changeStatus(..)', '[1, [Ljava.lang.Integer;@29dc68b3]', '{\"msg\":\"1个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 5, 1, '2021-12-23 15:43:42', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (52, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 86.0.4240.198', '/system/role/changeStatus/1', 'POST', 'SysRoleController.changeStatus(..)', '[1, [Ljava.lang.Integer;@2d792b93]', '{\"msg\":\"2个角色状态更新成功，0个失败！\",\"code\":200,\"success\":true}', 5, 1, '2021-12-23 15:43:47', NULL, NULL);
INSERT INTO `sys_log_request` VALUES (53, 10000, '127.0.0.1', 'Windows 10 or Windows Server 2016;Chrome 96.0.4664.110', '/system/menu/add', 'POST', 'SysMenuController.addMenu(..)', '[SysMenu(id=null, name=第三方功能, icon=fa-fonticons, path=, permission=third, component=null, parentId=1, type=N, sort=50, editable=null, removable=null, status=null, isDelete=null, createTime=null, updateTime=null, deleteTime=null, child=null, checkArr=0)]', '{\"msg\":\"菜单添加成功\",\"code\":200,\"success\":true}', 48, 1, '2021-12-23 18:23:07', NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单/权限ID',
  `name` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单/权限名称',
  `icon` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `path` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单/权限路由：对于后台控制类定义，示例：system/user/get/{id}',
  `permission` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识符：对于后台控制类定义。示例：system:user:list',
  `component` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径：对于前端框架定义。示例：system/user/index',
  `parent_id` int NULL DEFAULT NULL COMMENT '上级菜单ID',
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单类型：C - 目录 | M - 菜单 | B - 按钮',
  `sort` int NULL DEFAULT NULL COMMENT '排序：数值越大越靠前',
  `editable` tinyint NULL DEFAULT NULL COMMENT '可编辑：0-禁止编辑 | 1-可编辑',
  `removable` tinyint NULL DEFAULT NULL COMMENT '可删除：0-禁止删除 | 1-可删除',
  `status` tinyint NULL DEFAULT NULL COMMENT '角色状态：0-封禁 | 1-正常 | 2-正常且禁止封禁',
  `is_delete` tinyint NULL DEFAULT NULL COMMENT '是否删除：0-未删除 | 1-已删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单/权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统功能', 'fa fa-gears', '', 'system:system', NULL, -1, 'N', 100, 0, 0, 2, NULL, '2021-09-16 20:10:10', '2021-12-04 18:53:50', NULL);
INSERT INTO `sys_menu` VALUES (2, '系统管理', 'fa fa-cog', '', 'system:index', NULL, 1, 'C', 80, 0, 0, 2, NULL, '2021-09-16 20:10:10', '2021-12-04 18:53:46', NULL);
INSERT INTO `sys_menu` VALUES (3, '系统用户', 'fa fa-user', '/system/user/index', 'system:user:index', NULL, 2, 'M', 75, 1, 0, 1, NULL, '2021-09-16 20:10:10', '2021-12-09 10:48:48', NULL);
INSERT INTO `sys_menu` VALUES (4, '角色管理', 'fa fa-users', '/system/role/index', 'system:role:index', NULL, 2, 'M', 75, 1, 0, 1, NULL, '2021-09-16 20:10:10', '2021-12-02 21:27:29', NULL);
INSERT INTO `sys_menu` VALUES (5, '菜单管理', 'fa fa-th-list', '/system/menu/index', 'system:menu:index', NULL, 2, 'M', 75, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-02 21:01:06', NULL);
INSERT INTO `sys_menu` VALUES (6, '查询用户列表', 'fa fa fa-mouse-pointer', '/system/user/list/{type}', 'system:user:list', NULL, 3, 'B', 50, 1, 0, 1, NULL, '2021-10-12 20:50:33', '2021-12-04 17:13:17', NULL);
INSERT INTO `sys_menu` VALUES (7, '访问添加用户页面', 'fa fa-address-card', '/system/user/goAdd', 'system:user:goAdd', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-02 21:45:36', NULL);
INSERT INTO `sys_menu` VALUES (8, '添加用户', 'fa fa-500px', '/system/user/add', 'system:user:add', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-02 21:46:11', NULL);
INSERT INTO `sys_menu` VALUES (9, '访问用户编辑页面', 'fa fa-500px', '/system/user/goEdit', 'system:user:goEdit', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-02 21:47:21', NULL);
INSERT INTO `sys_menu` VALUES (10, '更新用户信息', 'fa fa-address-book', '/system/user/update', 'system:user:update', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-04 17:43:00', NULL);
INSERT INTO `sys_menu` VALUES (11, '删除用户', 'fa fa-address-book', '/system/user/delete/{id}', 'system:user:delete', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-07 22:30:37', NULL);
INSERT INTO `sys_menu` VALUES (12, '访问分配角色页面', 'fa fa-address-book', '/system/user/role/{id}', 'system:user:role', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-04 17:25:52', NULL);
INSERT INTO `sys_menu` VALUES (13, '分配角色', 'fa fa-address-book', '/system/user/setRole', 'system:user:setRole', NULL, 3, 'B', 50, 1, 0, 1, NULL, '2021-09-16 20:10:10', '2021-12-04 17:26:50', NULL);
INSERT INTO `sys_menu` VALUES (14, '改变用户状态', 'fa fa-address-book', '/system/user/changeStatus/{userid}/{status}', 'system:user:changeStatus', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-05 19:01:14', NULL);
INSERT INTO `sys_menu` VALUES (15, '访问重置密码', 'fa fa-500px', '/system/user/goResetPassword', 'system:user:goResetPassword', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-04 17:32:32', NULL);
INSERT INTO `sys_menu` VALUES (16, '重置密码', 'fa fa-compass', '/system/user/resetPassword', 'system:user:resetPassword', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-12-02 17:32:40', '2021-12-04 17:42:17', NULL);
INSERT INTO `sys_menu` VALUES (17, '查询用户列表', 'fa fa-address-book', '/system/role/list', 'system:role:list', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-02 21:20:41', '2021-12-04 19:01:55', NULL);
INSERT INTO `sys_menu` VALUES (18, '访问添加角色页面', 'fa fa-fonticons', '/system/role/goAdd', 'system:role:goAdd', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 19:03:32', NULL, NULL);
INSERT INTO `sys_menu` VALUES (19, '添加角色', 'fa fa-fonticons', '/system/role/add', 'system:role:add', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 19:03:54', NULL, NULL);
INSERT INTO `sys_menu` VALUES (20, '访问修改角色页面', 'fa fa-fonticons', '/system/role/goEdit/{id}', 'system:role:goEdit', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 19:04:10', '2021-12-04 21:09:28', NULL);
INSERT INTO `sys_menu` VALUES (21, '修改角色', 'fa fa-fonticons', '/system/role/update', 'system:role:update', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 19:04:35', NULL, NULL);
INSERT INTO `sys_menu` VALUES (22, '删除角色', 'fa fa-fonticons', '/system/role/delete', 'system:role:delete', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 21:21:13', '2021-12-04 21:21:23', NULL);
INSERT INTO `sys_menu` VALUES (23, '改变角色状态', 'fa fa-fonticons', '/system/role/changeStatus/{status}', 'system:role:changeStatus', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 21:35:37', '2021-12-08 20:33:05', NULL);
INSERT INTO `sys_menu` VALUES (24, '日志管理', 'fa fa-list-alt', '', 'system:log', NULL, 1, 'C', 50, 1, 1, 1, NULL, '2021-12-06 15:48:11', '2021-12-13 19:06:45', NULL);
INSERT INTO `sys_menu` VALUES (25, '查询菜单列表', 'fa fa-fonticons', '/system/menu/list/{type}', 'system:menu:list', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:31:13', NULL, NULL);
INSERT INTO `sys_menu` VALUES (26, '访问添加菜单页面', 'fa fa-fonticons', '/system/menu/goAdd', 'system:menu:goAdd', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:31:42', NULL, NULL);
INSERT INTO `sys_menu` VALUES (27, '添加菜单', 'fa fa-fonticons', '/system/menu/add', 'system:menu:add', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:31:52', NULL, NULL);
INSERT INTO `sys_menu` VALUES (28, '访问编辑菜单页面', 'fa fa-fonticons', '/system/menu/goEdit/{id}', 'system:menu:goEdit', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:34:04', '2021-12-07 20:41:21', NULL);
INSERT INTO `sys_menu` VALUES (29, '编辑菜单', 'fa fa-fonticons', '/system/menu/update', 'system:menu:update', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:34:11', '2021-12-07 19:46:47', NULL);
INSERT INTO `sys_menu` VALUES (30, '修改菜单状态', 'fa fa-fonticons', '/system/menu/changeStatus/{status}', 'system:menu:changeStatus', NULL, 5, 'B', 50, 1, 1, 2, NULL, '2021-12-07 11:34:35', '2021-12-07 22:05:17', NULL);
INSERT INTO `sys_menu` VALUES (31, '删除菜单', 'fa fa-fonticons', '/system/menu/delete', 'system:menu:delete', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:34:46', NULL, NULL);
INSERT INTO `sys_menu` VALUES (32, '操作日志', 'fa fa-low-vision', '/system/log/request', 'system:log:request', NULL, 24, 'M', 50, 1, 1, 1, NULL, '2021-12-13 19:07:47', '2021-12-13 19:16:22', NULL);
INSERT INTO `sys_menu` VALUES (33, '第三方功能', 'fa fa-fonticons', '', 'third', NULL, -1, 'N', 50, 1, 1, 1, NULL, '2021-12-23 18:23:07', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `permission` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色权限标识符，如：admin、guest',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序：数值越大越靠前',
  `status` tinyint NULL DEFAULT NULL COMMENT '角色状态：0-封禁 | 1-正常 | 2-正常且禁止封禁',
  `is_delete` tinyint NULL DEFAULT NULL COMMENT '是否删除：0-未删除 | 1-已删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (100, '超级管理员', 'admin', '超级管理员，禁止禁用和删除！', 100, 2, NULL, NULL, '2021-12-20 17:07:26', NULL);
INSERT INTO `sys_role` VALUES (101, '围观角色', 'visit', '', 50, 1, NULL, '2021-09-23 19:36:08', '2021-12-07 11:35:31', NULL);
INSERT INTO `sys_role` VALUES (102, 'test2', 'test2', '', 50, 1, NULL, NULL, '2021-12-09 21:20:26', NULL);
INSERT INTO `sys_role` VALUES (103, '系统管理员', 'system', '', 50, 1, NULL, '2021-12-04 20:55:13', '2021-12-06 20:23:26', NULL);
INSERT INTO `sys_role` VALUES (104, '系统管理员', 'system', '', 50, 1, NULL, '2021-12-04 20:56:45', '2021-12-06 20:19:40', NULL);
INSERT INTO `sys_role` VALUES (105, '测试', '1111', '', 50, 1, NULL, '2021-12-08 20:55:59', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色菜单关联ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  `menu_id` int NOT NULL COMMENT '菜单/权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 294 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色和菜单/权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (89, 104, 24);
INSERT INTO `sys_role_menu` VALUES (90, 103, 1);
INSERT INTO `sys_role_menu` VALUES (91, 103, 2);
INSERT INTO `sys_role_menu` VALUES (92, 103, 4);
INSERT INTO `sys_role_menu` VALUES (93, 103, 17);
INSERT INTO `sys_role_menu` VALUES (94, 103, 18);
INSERT INTO `sys_role_menu` VALUES (95, 103, 19);
INSERT INTO `sys_role_menu` VALUES (96, 103, 20);
INSERT INTO `sys_role_menu` VALUES (97, 103, 21);
INSERT INTO `sys_role_menu` VALUES (98, 103, 22);
INSERT INTO `sys_role_menu` VALUES (99, 103, 23);
INSERT INTO `sys_role_menu` VALUES (126, 101, 1);
INSERT INTO `sys_role_menu` VALUES (127, 101, 2);
INSERT INTO `sys_role_menu` VALUES (128, 101, 3);
INSERT INTO `sys_role_menu` VALUES (129, 101, 6);
INSERT INTO `sys_role_menu` VALUES (130, 101, 7);
INSERT INTO `sys_role_menu` VALUES (131, 101, 9);
INSERT INTO `sys_role_menu` VALUES (132, 101, 12);
INSERT INTO `sys_role_menu` VALUES (133, 101, 4);
INSERT INTO `sys_role_menu` VALUES (134, 101, 17);
INSERT INTO `sys_role_menu` VALUES (135, 101, 18);
INSERT INTO `sys_role_menu` VALUES (136, 101, 20);
INSERT INTO `sys_role_menu` VALUES (137, 101, 5);
INSERT INTO `sys_role_menu` VALUES (138, 101, 25);
INSERT INTO `sys_role_menu` VALUES (139, 101, 26);
INSERT INTO `sys_role_menu` VALUES (140, 101, 28);
INSERT INTO `sys_role_menu` VALUES (141, 101, 24);
INSERT INTO `sys_role_menu` VALUES (223, 102, 1);
INSERT INTO `sys_role_menu` VALUES (224, 102, 2);
INSERT INTO `sys_role_menu` VALUES (225, 102, 5);
INSERT INTO `sys_role_menu` VALUES (226, 102, 29);
INSERT INTO `sys_role_menu` VALUES (227, 102, 30);
INSERT INTO `sys_role_menu` VALUES (228, 102, 31);
INSERT INTO `sys_role_menu` VALUES (229, 102, 24);
INSERT INTO `sys_role_menu` VALUES (262, 100, 1);
INSERT INTO `sys_role_menu` VALUES (263, 100, 2);
INSERT INTO `sys_role_menu` VALUES (264, 100, 3);
INSERT INTO `sys_role_menu` VALUES (265, 100, 6);
INSERT INTO `sys_role_menu` VALUES (266, 100, 7);
INSERT INTO `sys_role_menu` VALUES (267, 100, 8);
INSERT INTO `sys_role_menu` VALUES (268, 100, 9);
INSERT INTO `sys_role_menu` VALUES (269, 100, 10);
INSERT INTO `sys_role_menu` VALUES (270, 100, 11);
INSERT INTO `sys_role_menu` VALUES (271, 100, 12);
INSERT INTO `sys_role_menu` VALUES (272, 100, 13);
INSERT INTO `sys_role_menu` VALUES (273, 100, 14);
INSERT INTO `sys_role_menu` VALUES (274, 100, 15);
INSERT INTO `sys_role_menu` VALUES (275, 100, 16);
INSERT INTO `sys_role_menu` VALUES (276, 100, 4);
INSERT INTO `sys_role_menu` VALUES (277, 100, 17);
INSERT INTO `sys_role_menu` VALUES (278, 100, 18);
INSERT INTO `sys_role_menu` VALUES (279, 100, 19);
INSERT INTO `sys_role_menu` VALUES (280, 100, 20);
INSERT INTO `sys_role_menu` VALUES (281, 100, 21);
INSERT INTO `sys_role_menu` VALUES (282, 100, 22);
INSERT INTO `sys_role_menu` VALUES (283, 100, 23);
INSERT INTO `sys_role_menu` VALUES (284, 100, 5);
INSERT INTO `sys_role_menu` VALUES (285, 100, 25);
INSERT INTO `sys_role_menu` VALUES (286, 100, 26);
INSERT INTO `sys_role_menu` VALUES (287, 100, 27);
INSERT INTO `sys_role_menu` VALUES (288, 100, 28);
INSERT INTO `sys_role_menu` VALUES (289, 100, 29);
INSERT INTO `sys_role_menu` VALUES (290, 100, 30);
INSERT INTO `sys_role_menu` VALUES (291, 100, 31);
INSERT INTO `sys_role_menu` VALUES (292, 100, 24);
INSERT INTO `sys_role_menu` VALUES (293, 100, 32);

-- ----------------------------
-- Table structure for sys_test
-- ----------------------------
DROP TABLE IF EXISTS `sys_test`;
CREATE TABLE `sys_test`  (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_test
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `sex` tinyint NULL DEFAULT NULL COMMENT '用户性别（0-未知 | 1-男 | 2-女）',
  `email` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(31) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `password` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `status` tinyint NULL DEFAULT NULL COMMENT '用户状态',
  `login_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `is_delete` tinyint NULL DEFAULT 0 COMMENT '是否删除（0-未删除 | 1-已删除）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10012 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (10000, '张三', NULL, 1, '10000@zxdmy.com', '18863338888', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', 1, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10006, '李四', NULL, 1, 'test@zxdmy.com', '18863339999', 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10007, '用户2', NULL, 1, '10007@zxdmy.com', '18863336666', 'e10adc3949ba59abbe56e057f20f883e', NULL, 1, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10008, '用户3', NULL, 1, '1000@zxdmy.com3', '18863335555', '819b0643d6b89dc9b579fdfc9094f28e', NULL, 1, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10009, '用户4', NULL, 1, '1000@zxdmy.com4', '18863333333', '34cc93ece0ba9e3f6f235d4af979b16c', NULL, 1, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10010, '用户5', NULL, 1, '1000@zxdmy.com5', '18863331111', 'db0edd04aaac4506f7edab03ac855d56', NULL, 1, NULL, NULL, 1, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10011, '10000@zxdmy.com', NULL, 0, '4444@qq.com', '', '123456', NULL, 1, NULL, NULL, 0, '2021-12-22 21:53:48', NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户角色关联ID',
  `user_id` int NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 10000, 100);
INSERT INTO `sys_user_role` VALUES (2, 1000, 101);
INSERT INTO `sys_user_role` VALUES (3, 1000, 102);
INSERT INTO `sys_user_role` VALUES (4, 10006, 101);
INSERT INTO `sys_user_role` VALUES (5, 10011, 101);
INSERT INTO `sys_user_role` VALUES (6, 10011, 102);

SET FOREIGN_KEY_CHECKS = 1;
