/*
 Navicat Premium Data Transfer

 Source Server         : db_excite_cms
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 1.15.186.61:3306
 Source Schema         : db_excite_cms

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 30/01/2022 18:05:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attach_file
-- ----------------------------
DROP TABLE IF EXISTS `attach_file`;
CREATE TABLE `attach_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件主键',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '上传用户id',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名（服务器内存储的）',
  `file_source_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件原名（上传前）',
  `file_size` int(11) NULL DEFAULT NULL COMMENT '文件大小',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `file_type` tinyint(4) NULL DEFAULT NULL COMMENT '文件类型 1:图片 2:视频 3:文件',
  `file_mime_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型（mime-type）',
  `file_group_id` bigint(20) NULL DEFAULT 0 COMMENT '文件分组id',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1431 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '上传文件记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attach_file
-- ----------------------------

-- ----------------------------
-- Table structure for global_config
-- ----------------------------
DROP TABLE IF EXISTS `global_config`;
CREATE TABLE `global_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `conf_service` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置信息的服务：如qiniu，aliyun',
  `conf_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置信息的主键：如qiniu01、aliyunSms01等等',
  `conf_value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配置信息的JSON值',
  `encrypt` tinyint(4) NULL DEFAULT NULL COMMENT '是否加密：1-是 0-否',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组件配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of global_config
-- ----------------------------
INSERT INTO `global_config` VALUES (2, 'aliyunSms', 'aliyunSms001', '', 0, NULL, '2022-01-03 20:50:41', NULL);
INSERT INTO `global_config` VALUES (6, 'alipay', 'alipay', '666', 1, NULL, '2022-01-12 17:14:01', NULL);

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '登录用户ID',
  `user_account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录用户账号',
  `ip` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  `user_agent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器Agent',
  `req_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求数据',
  `res_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '返回数据',
  `time_cost` int(11) NULL DEFAULT NULL COMMENT '耗时',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '请求状态：0-失败 | 1-成功',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
INSERT INTO `sys_log_login` VALUES (53, 10000, '10000@zxdmy.com', '60.233.1.102', 'Windows 10 or Windows Server 2016;Chrome 97.0.4692.99', '[\"10000@zxdmy.com\",\"Pr+v96V9jqGTN6rgRUkGMHKjXuRyzrIVs70jtGoJmoRATDsvSTudyPD5/WqWVjs1nq4tmQX3gWYySzygIyEnOZoNWLe06AKhzId1P7Q0la75WUuLNzqbAs24kMGKfS/7SjFqmtwURHrzn0PALXtkUARhzbErcWe1h3YhFfaIm0g=\",\"0\",null]', '{\"msg\":\"登录成功！\",\"code\":200,\"data\":{\"tokenName\":\"com.zxdmy.excite\",\"tokenValue\":\"6cf72af4-eff9-4167-88c9-ad6aed4ca6be\",\"isLogin\":true,\"loginId\":\"10000\",\"loginType\":\"login\",\"tokenTimeout\":259200,\"sessionTimeout\":259200,\"tokenSessionTimeout\":-2,\"tokenActivityTimeout\":7200,\"loginDevice\":\"default-device\",\"tag\":null},\"success\":true}', 58, 1, '2022-01-30 17:43:27', NULL, NULL);

-- ----------------------------
-- Table structure for sys_log_request
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_request`;
CREATE TABLE `sys_log_request`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '请求用户ID',
  `ip` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求IP',
  `user_agent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器Agent',
  `req_uri` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求的URI',
  `req_method` varchar(31) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法：GET | POST | DELETE',
  `req_function` varchar(511) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求的函数',
  `req_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求的数据',
  `res_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '返回的数据',
  `time_cost` int(11) NULL DEFAULT NULL COMMENT '耗时',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '请求状态：0-失败 | 1-成功',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 162 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作请求日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_request
-- ----------------------------
INSERT INTO `sys_log_request` VALUES (161, 10000, '60.233.1.102', 'Windows 10 or Windows Server 2016;Chrome 97.0.4692.99', '/system/role/update', 'POST', 'SysRoleController.updateRole(..)', '[{\"id\":101,\"name\":\"围观角色\",\"permission\":\"visit\",\"remark\":\"演示用户拥有的权限\",\"sort\":50,\"status\":null,\"createTime\":null,\"updateTime\":null,\"deleteTime\":null,\"checkArr\":\"0\",\"parentId\":\"-1\",\"LAY_CHECKED\":false},[1,2,3,6,7,9,12,4,17,18,20,5,28,25,26,35,36,24,32,34,38,39,40,33,41,42]]', '{\"msg\":\"更新角色成功\",\"code\":200,\"success\":true}', 174, 1, '2022-01-30 17:43:50', NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单/权限ID',
  `name` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单/权限名称',
  `icon` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `path` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单/权限路由：对于后台控制类定义，示例：system/user/get/{id}',
  `permission` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识符：对于后台控制类定义。示例：system:user:list',
  `component` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径：对于前端框架定义。示例：system/user/index',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '上级菜单ID',
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单类型：C - 目录 | M - 菜单 | B - 按钮',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序：数值越大越靠前',
  `editable` tinyint(4) NULL DEFAULT NULL COMMENT '可编辑：0-禁止编辑 | 1-可编辑',
  `removable` tinyint(4) NULL DEFAULT NULL COMMENT '可删除：0-禁止删除 | 1-可删除',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '角色状态：0-封禁 | 1-正常 | 2-正常且禁止封禁',
  `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除：0-未删除 | 1-已删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单/权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统功能', 'fa fa-gears', '', 'system:system', NULL, -1, 'N', 100, 0, 0, 2, NULL, '2021-09-16 20:10:10', '2021-12-04 18:53:50', NULL);
INSERT INTO `sys_menu` VALUES (2, '系统管理', 'fa fa-cog', '', 'system:index', NULL, 1, 'C', 80, 0, 0, 2, NULL, '2021-09-16 20:10:10', '2021-12-04 18:53:46', NULL);
INSERT INTO `sys_menu` VALUES (3, '系统用户', 'fa fa-user', '/system/user/index', 'system:user:index', NULL, 2, 'M', 75, 1, 0, 1, NULL, '2021-09-16 20:10:10', '2021-12-09 10:48:48', NULL);
INSERT INTO `sys_menu` VALUES (4, '角色管理', 'fa fa-users', '/system/role/index', 'system:role:index', NULL, 2, 'M', 70, 1, 0, 1, NULL, '2021-09-16 20:10:10', '2021-12-02 21:27:29', NULL);
INSERT INTO `sys_menu` VALUES (5, '菜单管理', 'fa fa-th-list', '/system/menu/index', 'system:menu:index', NULL, 2, 'M', 65, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-02 21:01:06', NULL);
INSERT INTO `sys_menu` VALUES (6, '查询用户列表', 'fa fa fa-mouse-pointer', '/system/user/list/{type}', 'system:user:list', NULL, 3, 'B', 50, 1, 0, 1, NULL, '2021-10-12 20:50:33', '2021-12-04 17:13:17', NULL);
INSERT INTO `sys_menu` VALUES (7, '访问添加用户页面', 'fa fa-address-card', '/system/user/goAdd', 'system:user:goAdd', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-02 21:45:36', NULL);
INSERT INTO `sys_menu` VALUES (8, '添加用户', 'fa fa-500px', '/system/user/add', 'system:user:add', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-02 21:46:11', NULL);
INSERT INTO `sys_menu` VALUES (9, '访问用户编辑页面', 'fa fa-500px', '/system/user/goEdit', 'system:user:goEdit', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-02 21:47:21', NULL);
INSERT INTO `sys_menu` VALUES (10, '更新用户信息', 'fa fa-address-book', '/system/user/update', 'system:user:update', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-04 17:43:00', NULL);
INSERT INTO `sys_menu` VALUES (11, '删除用户', 'fa fa-address-book', '/system/user/delete', 'system:user:delete', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2022-01-30 15:21:14', NULL);
INSERT INTO `sys_menu` VALUES (12, '访问分配角色页面', 'fa fa-address-book', '/system/user/role/{id}', 'system:user:role', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2021-12-04 17:25:52', NULL);
INSERT INTO `sys_menu` VALUES (13, '分配角色', 'fa fa-address-book', '/system/user/authRole', 'system:user:authRole', NULL, 3, 'B', 50, 1, 0, 1, NULL, '2021-09-16 20:10:10', '2022-01-30 15:22:43', NULL);
INSERT INTO `sys_menu` VALUES (14, '改变用户状态', 'fa fa-address-book', '/system/user/changeStatus/{status}', 'system:user:changeStatus', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-09-16 20:10:10', '2022-01-30 15:19:20', NULL);
INSERT INTO `sys_menu` VALUES (16, '重置密码', 'fa fa-compass', '/system/user/resetPassword', 'system:user:resetPassword', NULL, 3, 'B', 50, 1, 1, 1, NULL, '2021-12-02 17:32:40', '2022-01-30 15:23:33', NULL);
INSERT INTO `sys_menu` VALUES (17, '查询用户列表', 'fa fa-address-book', '/system/role/list', 'system:role:list', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-02 21:20:41', '2021-12-04 19:01:55', NULL);
INSERT INTO `sys_menu` VALUES (18, '访问添加角色页面', 'fa fa-fonticons', '/system/role/goAdd', 'system:role:goAdd', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 19:03:32', NULL, NULL);
INSERT INTO `sys_menu` VALUES (19, '添加角色', 'fa fa-fonticons', '/system/role/add', 'system:role:add', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 19:03:54', NULL, NULL);
INSERT INTO `sys_menu` VALUES (20, '访问修改角色页面', 'fa fa-fonticons', '/system/role/goEdit/{id}', 'system:role:goEdit', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 19:04:10', '2021-12-04 21:09:28', NULL);
INSERT INTO `sys_menu` VALUES (21, '修改角色', 'fa fa-fonticons', '/system/role/update', 'system:role:update', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 19:04:35', NULL, NULL);
INSERT INTO `sys_menu` VALUES (22, '删除角色', 'fa fa-fonticons', '/system/role/delete', 'system:role:delete', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 21:21:13', '2021-12-04 21:21:23', NULL);
INSERT INTO `sys_menu` VALUES (23, '改变角色状态', 'fa fa-fonticons', '/system/role/changeStatus/{status}', 'system:role:changeStatus', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2021-12-04 21:35:37', '2021-12-08 20:33:05', NULL);
INSERT INTO `sys_menu` VALUES (24, '日志管理', 'fa fa-history', '', 'system:log', NULL, 1, 'C', 50, 1, 1, 1, NULL, '2021-12-06 15:48:11', '2021-12-26 16:30:38', NULL);
INSERT INTO `sys_menu` VALUES (25, '查询菜单列表', 'fa fa-fonticons', '/system/menu/list/{type}', 'system:menu:list', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:31:13', NULL, NULL);
INSERT INTO `sys_menu` VALUES (26, '访问添加菜单页面', 'fa fa-fonticons', '/system/menu/goAdd', 'system:menu:goAdd', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:31:42', NULL, NULL);
INSERT INTO `sys_menu` VALUES (27, '添加菜单', 'fa fa-fonticons', '/system/menu/add', 'system:menu:add', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:31:52', NULL, NULL);
INSERT INTO `sys_menu` VALUES (28, '访问编辑菜单页面', 'fa fa-fonticons', '/system/menu/goEdit/{id}', 'system:menu:goEdit', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:34:04', '2021-12-07 20:41:21', NULL);
INSERT INTO `sys_menu` VALUES (29, '编辑菜单', 'fa fa-fonticons', '/system/menu/update', 'system:menu:update', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:34:11', '2021-12-07 19:46:47', NULL);
INSERT INTO `sys_menu` VALUES (30, '修改菜单状态', 'fa fa-fonticons', '/system/menu/changeStatus/{status}', 'system:menu:changeStatus', NULL, 5, 'B', 50, 1, 1, 2, NULL, '2021-12-07 11:34:35', '2022-01-30 16:41:24', NULL);
INSERT INTO `sys_menu` VALUES (31, '删除菜单', 'fa fa-fonticons', '/system/menu/remove', 'system:menu:remove', NULL, 5, 'B', 50, 1, 1, 1, NULL, '2021-12-07 11:34:46', '2022-01-30 17:29:12', NULL);
INSERT INTO `sys_menu` VALUES (32, '操作日志', 'fa fa-sliders', '/system/log/request', 'system:log:request', NULL, 24, 'M', 50, 1, 1, 1, NULL, '2021-12-13 19:07:47', '2021-12-26 16:31:38', NULL);
INSERT INTO `sys_menu` VALUES (33, '第三方组件', 'fa fa-fonticons', '', 'component', NULL, -1, 'N', 50, 1, 1, 1, NULL, '2021-12-23 18:23:07', '2022-01-11 20:46:52', NULL);
INSERT INTO `sys_menu` VALUES (34, '登录日志', 'fa fa-street-view', '/system/log/login', 'system:log:login', NULL, 24, 'M', 50, 1, 1, 1, NULL, '2021-12-26 16:27:13', '2021-12-28 20:21:06', NULL);
INSERT INTO `sys_menu` VALUES (35, '开发管理', 'fa fa-gears', '', 'controller:develop', NULL, 1, 'C', 50, 1, 1, 1, NULL, '2021-12-26 16:28:55', '2021-12-26 16:35:59', NULL);
INSERT INTO `sys_menu` VALUES (36, '代码生成', 'fa fa-file-code-o', '/system/develop/generator', 'system:develop:generator', NULL, 35, 'M', 50, 1, 1, 1, NULL, '2021-12-26 16:37:46', '2021-12-26 16:39:26', NULL);
INSERT INTO `sys_menu` VALUES (37, '接口文档', 'fa fa-external-link-square', '/swagger', 'swagger', NULL, 35, 'M', 50, 1, 1, 1, NULL, '2021-12-26 16:39:13', '2022-01-03 14:00:48', NULL);
INSERT INTO `sys_menu` VALUES (38, '系统监控', 'fa fa-dashboard', '', 'controller:monitor', NULL, 1, 'C', 25, 1, 1, 1, NULL, '2021-12-26 16:52:26', '2021-12-26 16:55:27', NULL);
INSERT INTO `sys_menu` VALUES (39, '服务器监控', 'fa fa-line-chart', '/system/monitor/server', 'system:monitor:server', NULL, 38, 'M', 50, 1, 1, 1, NULL, '2021-12-26 16:54:23', '2022-01-30 17:29:18', NULL);
INSERT INTO `sys_menu` VALUES (40, '数据库监控', 'fa fa-database', '/druid/login.html', 'druid:login.html', NULL, 38, 'M', 50, 1, 1, 1, NULL, '2021-12-26 16:54:47', '2022-01-23 21:24:32', NULL);
INSERT INTO `sys_menu` VALUES (41, '微信支付演示', 'fa fa-wechat', '/component/wechatPay/index', 'component:wechatPay:index', NULL, 33, 'M', 50, 1, 1, 1, NULL, '2022-01-11 20:45:02', '2022-01-12 20:01:22', NULL);
INSERT INTO `sys_menu` VALUES (42, '支付宝支付演示', 'fa fa-cc-paypal', '/component/alipay/index', 'component:alipay:index', NULL, 33, 'M', 50, 1, 1, 1, NULL, '2022-01-11 22:57:18', '2022-01-24 21:36:19', NULL);
INSERT INTO `sys_menu` VALUES (43, '阿里云短信服务', 'fa fa-commenting-o', '/component/aliyunSms/index', 'component:aliyunSms:index', NULL, 33, 'M', 50, 1, 1, 1, NULL, '2022-01-12 16:31:08', '2022-01-24 21:35:56', NULL);
INSERT INTO `sys_menu` VALUES (44, '七牛云文件存储', 'fa fa-file', '/component/qiniuOss/index', 'component:qiniuOss:index', NULL, 33, 'M', 50, 1, 1, 1, NULL, '2022-01-24 21:38:10', '2022-01-24 21:53:54', NULL);
INSERT INTO `sys_menu` VALUES (45, '给用户授权/撤销授权', 'fa fa-fonticons', '/system/role/authUser/{type}', 'system:role:authUser', NULL, 4, 'B', 50, 1, 1, 1, NULL, '2022-01-30 16:36:10', '2022-01-30 16:37:46', NULL);
INSERT INTO `sys_menu` VALUES (46, '删除日志', 'fa fa-fonticons', '/system/log/request/delete', 'system:log:request:delete', NULL, 32, 'B', 0, 1, 1, 1, NULL, '2022-01-30 16:39:00', '2022-01-30 16:53:30', NULL);
INSERT INTO `sys_menu` VALUES (47, '生成指定表的代码', 'fa fa-fonticons', '/system/develop/generator/create', 'system:develop:generator:create', NULL, 36, 'B', 50, 1, 1, 1, NULL, '2022-01-30 16:51:03', NULL, NULL);
INSERT INTO `sys_menu` VALUES (48, '删除日志', 'fa fa-fonticons', '/system/log/login/delete', 'system:log:login:delete', NULL, 34, 'B', 50, 1, 1, 1, NULL, '2022-01-30 16:54:05', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `permission` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色权限标识符，如：admin、guest',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序：数值越大越靠前',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '角色状态：0-封禁 | 1-正常 | 2-正常且禁止封禁',
  `is_delete` tinyint(4) NULL DEFAULT NULL COMMENT '是否删除：0-未删除 | 1-已删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (100, '超级管理员', 'admin', '超级管理员，禁止禁用和删除！', 100, 2, 0, NULL, '2022-01-30 17:15:50', NULL);
INSERT INTO `sys_role` VALUES (101, '围观角色', 'visit', '演示用户拥有的权限', 50, 1, 0, '2021-09-23 19:36:08', '2022-01-30 17:43:50', NULL);
INSERT INTO `sys_role` VALUES (102, 'test2', 'test2', '', 50, 1, 0, NULL, '2022-01-30 16:47:12', NULL);
INSERT INTO `sys_role` VALUES (103, '系统管理员', 'system', '', 50, 0, 0, '2021-12-04 20:55:13', '2021-12-06 20:23:26', NULL);
INSERT INTO `sys_role` VALUES (104, '系统管理员', 'system', '', 50, 0, 0, '2021-12-04 20:56:45', '2021-12-06 20:19:40', NULL);
INSERT INTO `sys_role` VALUES (105, '测试', '1111', '', 50, 0, 0, '2021-12-08 20:55:59', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色菜单关联ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单/权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 844 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色和菜单/权限关联表' ROW_FORMAT = Dynamic;

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
INSERT INTO `sys_role_menu` VALUES (720, 100, 1);
INSERT INTO `sys_role_menu` VALUES (721, 100, 2);
INSERT INTO `sys_role_menu` VALUES (722, 100, 3);
INSERT INTO `sys_role_menu` VALUES (724, 100, 6);
INSERT INTO `sys_role_menu` VALUES (725, 100, 7);
INSERT INTO `sys_role_menu` VALUES (726, 100, 8);
INSERT INTO `sys_role_menu` VALUES (727, 100, 9);
INSERT INTO `sys_role_menu` VALUES (728, 100, 10);
INSERT INTO `sys_role_menu` VALUES (729, 100, 11);
INSERT INTO `sys_role_menu` VALUES (730, 100, 12);
INSERT INTO `sys_role_menu` VALUES (731, 100, 13);
INSERT INTO `sys_role_menu` VALUES (732, 100, 14);
INSERT INTO `sys_role_menu` VALUES (733, 100, 16);
INSERT INTO `sys_role_menu` VALUES (734, 100, 4);
INSERT INTO `sys_role_menu` VALUES (735, 100, 45);
INSERT INTO `sys_role_menu` VALUES (736, 100, 17);
INSERT INTO `sys_role_menu` VALUES (737, 100, 18);
INSERT INTO `sys_role_menu` VALUES (738, 100, 19);
INSERT INTO `sys_role_menu` VALUES (739, 100, 20);
INSERT INTO `sys_role_menu` VALUES (740, 100, 21);
INSERT INTO `sys_role_menu` VALUES (741, 100, 22);
INSERT INTO `sys_role_menu` VALUES (742, 100, 23);
INSERT INTO `sys_role_menu` VALUES (743, 100, 5);
INSERT INTO `sys_role_menu` VALUES (744, 100, 27);
INSERT INTO `sys_role_menu` VALUES (745, 100, 28);
INSERT INTO `sys_role_menu` VALUES (746, 100, 29);
INSERT INTO `sys_role_menu` VALUES (747, 100, 30);
INSERT INTO `sys_role_menu` VALUES (748, 100, 31);
INSERT INTO `sys_role_menu` VALUES (749, 100, 26);
INSERT INTO `sys_role_menu` VALUES (750, 100, 25);
INSERT INTO `sys_role_menu` VALUES (751, 100, 35);
INSERT INTO `sys_role_menu` VALUES (752, 100, 36);
INSERT INTO `sys_role_menu` VALUES (753, 100, 47);
INSERT INTO `sys_role_menu` VALUES (754, 100, 37);
INSERT INTO `sys_role_menu` VALUES (755, 100, 24);
INSERT INTO `sys_role_menu` VALUES (756, 100, 32);
INSERT INTO `sys_role_menu` VALUES (757, 100, 46);
INSERT INTO `sys_role_menu` VALUES (758, 100, 34);
INSERT INTO `sys_role_menu` VALUES (759, 100, 48);
INSERT INTO `sys_role_menu` VALUES (760, 100, 38);
INSERT INTO `sys_role_menu` VALUES (761, 100, 39);
INSERT INTO `sys_role_menu` VALUES (762, 100, 40);
INSERT INTO `sys_role_menu` VALUES (763, 100, 33);
INSERT INTO `sys_role_menu` VALUES (764, 100, 41);
INSERT INTO `sys_role_menu` VALUES (765, 100, 42);
INSERT INTO `sys_role_menu` VALUES (818, 101, 1);
INSERT INTO `sys_role_menu` VALUES (819, 101, 2);
INSERT INTO `sys_role_menu` VALUES (820, 101, 3);
INSERT INTO `sys_role_menu` VALUES (821, 101, 6);
INSERT INTO `sys_role_menu` VALUES (822, 101, 7);
INSERT INTO `sys_role_menu` VALUES (823, 101, 9);
INSERT INTO `sys_role_menu` VALUES (824, 101, 12);
INSERT INTO `sys_role_menu` VALUES (825, 101, 4);
INSERT INTO `sys_role_menu` VALUES (826, 101, 17);
INSERT INTO `sys_role_menu` VALUES (827, 101, 18);
INSERT INTO `sys_role_menu` VALUES (828, 101, 20);
INSERT INTO `sys_role_menu` VALUES (829, 101, 5);
INSERT INTO `sys_role_menu` VALUES (830, 101, 28);
INSERT INTO `sys_role_menu` VALUES (831, 101, 25);
INSERT INTO `sys_role_menu` VALUES (832, 101, 26);
INSERT INTO `sys_role_menu` VALUES (833, 101, 35);
INSERT INTO `sys_role_menu` VALUES (834, 101, 36);
INSERT INTO `sys_role_menu` VALUES (835, 101, 24);
INSERT INTO `sys_role_menu` VALUES (836, 101, 32);
INSERT INTO `sys_role_menu` VALUES (837, 101, 34);
INSERT INTO `sys_role_menu` VALUES (838, 101, 38);
INSERT INTO `sys_role_menu` VALUES (839, 101, 39);
INSERT INTO `sys_role_menu` VALUES (840, 101, 40);
INSERT INTO `sys_role_menu` VALUES (841, 101, 33);
INSERT INTO `sys_role_menu` VALUES (842, 101, 41);
INSERT INTO `sys_role_menu` VALUES (843, 101, 42);

-- ----------------------------
-- Table structure for sys_test
-- ----------------------------
DROP TABLE IF EXISTS `sys_test`;
CREATE TABLE `sys_test`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_test
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '用户性别（0-未知 | 1-男 | 2-女）',
  `email` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(31) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `password` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '用户状态',
  `login_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '登录时间',
  `is_delete` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除（0-未删除 | 1-已删除）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10020 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (10000, '张三', NULL, 1, '10000@zxdmy.com', '18811111111', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', 1, NULL, NULL, 0, NULL, '2021-12-30 15:18:34', NULL);
INSERT INTO `sys_user` VALUES (10006, '李四', NULL, 1, 'demo@zxdmy.com', '18866666666', 'e10adc3949ba59abbe56e057f20f883e', '演示用户', 1, NULL, NULL, 0, NULL, '2022-01-30 15:15:13', NULL);
INSERT INTO `sys_user` VALUES (10007, '用户2', NULL, 1, '10007@zxdmy.com', '18877777777', 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10008, '用户3', NULL, 1, '10008@zxdmy.com', '18888888888', 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, NULL, '2022-01-30 16:30:59', NULL);
INSERT INTO `sys_user` VALUES (10009, '用户4', NULL, 1, '10009@zxdmy.com', '18899999999', 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, NULL, '2022-01-30 15:23:53', NULL);
INSERT INTO `sys_user` VALUES (10010, '用户5', NULL, 1, '10010@zxdmy.com', '18810101010', 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10011, '10000@zxdmy.com', NULL, 0, '10011@qq.com', '18811001100', 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, '2021-12-22 21:53:48', '2022-01-30 16:30:33', NULL);
INSERT INTO `sys_user` VALUES (10012, '用户1', NULL, NULL, '10012@zxdmy.com', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10013, '用户2', NULL, NULL, '10013@zxdmy.com', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10014, '用户3', NULL, NULL, '10014@zxdmy.com', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, NULL, '2021-12-29 11:04:48', NULL);
INSERT INTO `sys_user` VALUES (10015, '用户4', NULL, NULL, '10015@zxdmy.com', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10016, '用户5', NULL, NULL, '10016@zxdmy.com', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10017, '张三', NULL, NULL, '10017@zxdmy.com', NULL, 'e10adc3949ba59abbe56e057f20f883e', NULL, 0, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (10018, '王二麻子', NULL, 1, 'hahah@zxdmy.com', '18811001122', 'e10adc3949ba59abbe56e057f20f883e', '', 0, NULL, NULL, 0, '2021-12-29 10:09:43', '2021-12-29 11:05:31', NULL);
INSERT INTO `sys_user` VALUES (10019, '哈哈哈', NULL, 0, 'hahaaaa@zxdmy.com', '18811001111', 'e10adc3949ba59abbe56e057f20f883e', '', 0, NULL, NULL, 0, '2021-12-29 10:12:55', '2021-12-29 10:36:17', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色关联ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 10000, 100);
INSERT INTO `sys_user_role` VALUES (2, 10000, 101);
INSERT INTO `sys_user_role` VALUES (4, 10006, 101);
INSERT INTO `sys_user_role` VALUES (7, 10018, 101);
INSERT INTO `sys_user_role` VALUES (9, 10011, 101);
INSERT INTO `sys_user_role` VALUES (10, 10006, 102);

SET FOREIGN_KEY_CHECKS = 1;
