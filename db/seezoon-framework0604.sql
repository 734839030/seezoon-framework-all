/*
 Navicat Premium Data Transfer

 Source Server         : 本机mysql
 Source Server Type    : MySQL
 Source Server Version : 50633
 Source Host           : localhost
 Source Database       : seezoon-framework

 Target Server Type    : MySQL
 Target Server Version : 50633
 File Encoding         : utf-8

 Date: 06/03/2018 16:43:53 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `demo_gen`
-- ----------------------------
DROP TABLE IF EXISTS `demo_gen`;
CREATE TABLE `demo_gen` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `input_text` varchar(50) NOT NULL COMMENT '文本',
  `input_select` varchar(10) NOT NULL COMMENT '下拉',
  `input_radio` varchar(10) NOT NULL COMMENT '单选',
  `input_checkbox` varchar(10) NOT NULL COMMENT '复选',
  `input_textarea` text COMMENT '文本域',
  `input_date` datetime NOT NULL COMMENT '日期',
  `input_zhengshu` int(11) DEFAULT NULL COMMENT '整数',
  `input_xiaoshu` double DEFAULT NULL COMMENT '小数',
  `rich_text` mediumtext COMMENT '富文本',
  `image` varchar(255) DEFAULT NULL COMMENT '图片',
  `file` varchar(255) DEFAULT NULL COMMENT '文件',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `create_by` (`create_by`),
  KEY `create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生成案例';

-- ----------------------------
--  Records of `demo_gen`
-- ----------------------------
BEGIN;
INSERT INTO `demo_gen` VALUES ('a9d96391b9e143f59a4e1fd0c5ba184a', '1', '0', '1', '1', '文本域', '2018-05-20 00:00:00', '1112', '-11.12', '&lt;h1&gt;\r\n	54543534\r\n&lt;/h1&gt;\r\n&lt;p&gt;\r\n	&lt;br /&gt;\r\n&lt;/p&gt;\r\n&lt;p&gt;\r\n	543543&lt;img src=&quot;http://127.0.0.1:8888/plugins/kindeditor/plugins/emoticons/images/32.gif&quot; border=&quot;0&quot; alt=&quot;&quot; /&gt;[委屈]&lt;img src=&quot;http://127.0.0.1:8888/images/2018/05/21/d23f8b27e3274f30b8c5ba18c6bab241.jpeg&quot; alt=&quot;&quot; /&gt; \r\n&lt;/p&gt;', '/2018/06/02/6095addfc29e431ebe3ade245ae8b327.jpg,/2018/06/02/a9ba98fbcc4b4ed685416751f0f61c8b.jpeg,/2018/06/02/97857c02f83c44f58a6f32f4eaa53d37.jpeg,/2018/06/02/2ec295b097dd4d9294a5ab84d6a5368b.jpeg', '/2018/05/23/65e2934dfae04057946020fbdbab2828.png,/2018/06/02/193ab3d32a384a798b3e09240311bf9b.jpg', '1', '2018-05-14 21:00:41', '1', '2018-06-02 11:14:56', '备注1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `parent_id` varchar(32) NOT NULL COMMENT '父部门',
  `parent_ids` varchar(500) NOT NULL COMMENT '父ids，按层级逗号分隔',
  `name` varchar(100) NOT NULL COMMENT '部门名称',
  `sort` int(10) NOT NULL COMMENT '排序',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `create_date` (`create_date`),
  KEY `create_by` (`create_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='组织机构';

-- ----------------------------
--  Records of `sys_dept`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES ('0a7f12c90f1a4875987265e00589e408', 'fd3df3592662424ebbea1c4f92bbf4a0', '0,fd3df3592662424ebbea1c4f92bbf4a0', '总经办', '10', '1', '2018-05-26 16:52:35', '1', '2018-05-26 16:52:35', ''), ('fd3df3592662424ebbea1c4f92bbf4a0', '0', '0,', '上征科技有限公司', '10', '1', '2018-04-17 20:50:54', '07702dfdee1343b783153529790099ba', '2018-06-03 16:37:35', '');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(50) NOT NULL COMMENT '字典类型',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `sort` int(11) NOT NULL COMMENT '排序',
  `status` char(1) NOT NULL COMMENT '是否启用1：是，0：否',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `type` (`type`) USING BTREE,
  KEY `create_date` (`create_date`),
  KEY `create_by` (`create_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='字典';

-- ----------------------------
--  Records of `sys_dict`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES ('305454e07215411a8019998f49f6574d', 'yes_no', '1', '是', '12', '1', '1', '2018-04-05 18:30:36', '1', '2018-04-07 17:38:00', ''), ('7696b22fec6548bbaf034a2d91889c07', 'yes_no', '0', '否', '11', '1', '1', '2018-04-06 08:43:50', '1', '2018-04-06 08:43:50', ''), ('9320f1dabe1948c3af76d0ae89bb6a1f', 'sys_user_type', '0', '系统管理员', '10', '1', '1', '2018-04-07 16:48:51', '1', '2018-04-15 00:13:37', ''), ('9463a9fe58ee4f9cb80147f84523c7e6', 'sex', '1', '男', '10', '1', '1', '2018-06-02 11:02:55', '1', '2018-06-02 11:02:55', ''), ('fc737eb43e674748b6c0180767001487', 'sex', '2', '女', '10', '1', '1', '2018-06-02 11:03:04', '1', '2018-06-02 11:03:04', ''), ('fd59350df7314c3d94cfd686d8fa1032', 'sys_user_type', '1', '普通用户', '10', '1', '1', '2018-04-07 16:49:04', '1', '2018-04-15 00:13:41', '');
COMMIT;

-- ----------------------------
--  Table structure for `sys_file`
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(200) NOT NULL COMMENT '名称',
  `content_type` varchar(100) NOT NULL COMMENT '文件类型',
  `file_size` int(11) NOT NULL,
  `relative_path` varchar(200) NOT NULL COMMENT '地址',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `create_date` (`create_date`) USING BTREE,
  KEY `create_by` (`create_by`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件';

-- ----------------------------
--  Table structure for `sys_gen`
-- ----------------------------
DROP TABLE IF EXISTS `sys_gen`;
CREATE TABLE `sys_gen` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `table_name` varchar(32) NOT NULL COMMENT '表名',
  `pk_type` varchar(10) NOT NULL COMMENT '主键类型',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名',
  `module_name` varchar(10) NOT NULL COMMENT '模块名',
  `function_name` varchar(20) NOT NULL COMMENT '功能模块',
  `template` varchar(1) NOT NULL COMMENT '生成模板',
  `class_name` varchar(50) NOT NULL COMMENT '类名',
  `columns` text NOT NULL COMMENT '字段信息',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `create_date` (`create_date`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `table_name` (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码生成';

-- ----------------------------
--  Records of `sys_gen`
-- ----------------------------
BEGIN;
INSERT INTO `sys_gen` VALUES ('17ef33c1d93d4775b122680cf9d0d069', 'sys_file', 'String', '文件管理', 'sys', 'file', '1', 'SysFile', '[{\"columnComment\":\"编号\",\"columnKey\":\"PRI\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"id\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"hidden\",\"insert\":\"1\",\"javaFieldName\":\"id\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":10},{\"columnComment\":\"名称\",\"columnKey\":\"\",\"columnType\":\"varchar(200)\",\"dataType\":\"varchar\",\"dbColumnName\":\"name\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"name\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":200,\"queryType\":\"=\",\"search\":\"1\",\"sort\":20,\"update\":\"1\"},{\"columnComment\":\"文件类型\",\"columnKey\":\"\",\"columnType\":\"varchar(100)\",\"dataType\":\"varchar\",\"dbColumnName\":\"content_type\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"contentType\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":100,\"queryType\":\"\",\"sort\":30,\"update\":\"1\"},{\"columnComment\":\"文件大小\",\"columnKey\":\"\",\"columnType\":\"int(11)\",\"dataType\":\"int\",\"dbColumnName\":\"file_size\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"zhengshu\",\"insert\":\"1\",\"javaFieldName\":\"fileSize\",\"javaType\":\"Integer\",\"jdbcType\":\"INTEGER\",\"list\":\"1\",\"queryType\":\"\",\"sort\":40,\"update\":\"1\"},{\"columnComment\":\"地址\",\"columnKey\":\"\",\"columnType\":\"varchar(200)\",\"dataType\":\"varchar\",\"dbColumnName\":\"relative_path\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"relativePath\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":200,\"queryType\":\"\",\"sort\":50,\"update\":\"1\"},{\"columnComment\":\"创建者\",\"columnKey\":\"MUL\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"create_by\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"createBy\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":60},{\"columnComment\":\"创建时间\",\"columnKey\":\"MUL\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"create_date\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"createDate\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"queryType\":\"\",\"sort\":70},{\"columnComment\":\"更新者\",\"columnKey\":\"\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"update_by\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"updateBy\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":80,\"update\":\"1\"},{\"columnComment\":\"更新时间\",\"columnKey\":\"\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"update_date\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"updateDate\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"list\":\"1\",\"queryType\":\"\",\"sort\":90,\"update\":\"1\"},{\"columnComment\":\"备注信息\",\"columnKey\":\"\",\"columnType\":\"varchar(255)\",\"dataType\":\"varchar\",\"dbColumnName\":\"remarks\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"textarea\",\"insert\":\"1\",\"javaFieldName\":\"remarks\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":255,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":100,\"update\":\"1\"}]', '1', '2018-06-02 10:53:46', '1', '2018-06-02 10:53:46', null), ('4426a306855647fd9fc495037334be84', 'sys_login_log', 'String', '登录日志', 'sys', 'loginlog', '1', 'SysLoginLog', '[{\"columnComment\":\"编号\",\"columnKey\":\"PRI\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"id\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"hidden\",\"insert\":\"1\",\"javaFieldName\":\"id\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":10},{\"columnComment\":\"用户ID\",\"columnKey\":\"\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"user_id\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"hidden\",\"insert\":\"1\",\"javaFieldName\":\"userId\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":32,\"queryType\":\"\",\"sort\":20,\"update\":\"1\"},{\"columnComment\":\"登录时间\",\"columnKey\":\"\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"login_time\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"loginTime\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"list\":\"1\",\"queryType\":\"between\",\"search\":\"1\",\"sort\":30,\"sortable\":\"1\",\"update\":\"1\"},{\"columnComment\":\"IP地址\",\"columnKey\":\"\",\"columnType\":\"varchar(16)\",\"dataType\":\"varchar\",\"dbColumnName\":\"ip\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"ip\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":16,\"nullable\":\"1\",\"queryType\":\"=\",\"search\":\"1\",\"sort\":40,\"update\":\"1\"},{\"columnComment\":\"登录地区\",\"columnKey\":\"\",\"columnType\":\"varchar(20)\",\"dataType\":\"varchar\",\"dbColumnName\":\"area\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"area\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":20,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":50,\"update\":\"1\"},{\"columnComment\":\"用户代理\",\"columnKey\":\"\",\"columnType\":\"varchar(1000)\",\"dataType\":\"varchar\",\"dbColumnName\":\"user_agent\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"userAgent\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":1000,\"queryType\":\"\",\"sort\":60,\"update\":\"1\"},{\"columnComment\":\"设备名称\",\"columnKey\":\"\",\"columnType\":\"varchar(100)\",\"dataType\":\"varchar\",\"dbColumnName\":\"device_name\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"deviceName\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":100,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":70,\"update\":\"1\"},{\"columnComment\":\"浏览器名称\",\"columnKey\":\"\",\"columnType\":\"varchar(100)\",\"dataType\":\"varchar\",\"dbColumnName\":\"browser_name\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"browserName\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":100,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":80,\"update\":\"1\"},{\"columnComment\":\"创建者\",\"columnKey\":\"\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"create_by\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"createBy\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":90},{\"columnComment\":\"创建时间\",\"columnKey\":\"\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"create_date\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"createDate\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"queryType\":\"\",\"sort\":100},{\"columnComment\":\"更新者\",\"columnKey\":\"\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"update_by\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"updateBy\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":110,\"update\":\"1\"},{\"columnComment\":\"更新时间\",\"columnKey\":\"\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"update_date\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"updateDate\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"list\":\"1\",\"queryType\":\"\",\"sort\":120,\"update\":\"1\"},{\"columnComment\":\"备注信息\",\"columnKey\":\"\",\"columnType\":\"varchar(255)\",\"dataType\":\"varchar\",\"dbColumnName\":\"remarks\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"textarea\",\"insert\":\"1\",\"javaFieldName\":\"remarks\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":255,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":130,\"update\":\"1\"}]', '1', '2018-05-31 21:34:13', '1', '2018-05-31 21:34:13', null), ('89611fbac5c543ea87f923eadf769bc0', 'demo_user', 'String', '测试用户信息', 'demo', 'user', '1', 'DemoUser', '[{\"columnComment\":\"编号\",\"columnKey\":\"PRI\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"id\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"hidden\",\"insert\":\"1\",\"javaFieldName\":\"id\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":10},{\"columnComment\":\"姓名\",\"columnKey\":\"\",\"columnType\":\"varchar(100)\",\"dataType\":\"varchar\",\"dbColumnName\":\"name\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"name\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":100,\"nullable\":\"1\",\"queryType\":\"like\",\"search\":\"1\",\"sort\":20,\"update\":\"1\"},{\"columnComment\":\"年纪\",\"columnKey\":\"\",\"columnType\":\"int(11)\",\"dataType\":\"int\",\"dbColumnName\":\"age\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"zhengshu\",\"insert\":\"1\",\"javaFieldName\":\"age\",\"javaType\":\"Integer\",\"jdbcType\":\"INTEGER\",\"list\":\"1\",\"queryType\":\"\",\"sort\":30,\"sortable\":\"1\",\"update\":\"1\"},{\"columnComment\":\"生日\",\"columnKey\":\"\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"birthday\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"birthday\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"list\":\"1\",\"queryType\":\"\",\"sort\":40,\"sortable\":\"1\",\"update\":\"1\"},{\"columnComment\":\"图像\",\"columnKey\":\"\",\"columnType\":\"varchar(100)\",\"dataType\":\"varchar\",\"dbColumnName\":\"photo\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"photo\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":100,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":50,\"update\":\"1\"},{\"columnComment\":\"性别\",\"columnKey\":\"\",\"columnType\":\"varchar(1)\",\"dataType\":\"varchar\",\"dbColumnName\":\"sex\",\"dictType\":\"sex\",\"extra\":\"\",\"inputType\":\"select\",\"insert\":\"1\",\"javaFieldName\":\"sex\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":1,\"queryType\":\"=\",\"search\":\"1\",\"sort\":60,\"update\":\"1\"},{\"columnComment\":\"介绍\",\"columnKey\":\"\",\"columnType\":\"text\",\"dataType\":\"text\",\"dbColumnName\":\"info\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"richtext\",\"insert\":\"1\",\"javaFieldName\":\"info\",\"javaType\":\"String\",\"jdbcType\":\"LONGVARCHAR\",\"maxLength\":65535,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":70,\"update\":\"1\"},{\"columnComment\":\"创建者\",\"columnKey\":\"MUL\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"create_by\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"createBy\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":80},{\"columnComment\":\"创建时间\",\"columnKey\":\"MUL\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"create_date\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"createDate\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"queryType\":\"\",\"sort\":90},{\"columnComment\":\"更新者\",\"columnKey\":\"\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"update_by\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"updateBy\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":100,\"update\":\"1\"},{\"columnComment\":\"更新时间\",\"columnKey\":\"\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"update_date\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"updateDate\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"list\":\"1\",\"queryType\":\"\",\"sort\":110,\"update\":\"1\"},{\"columnComment\":\"备注信息\",\"columnKey\":\"\",\"columnType\":\"varchar(255)\",\"dataType\":\"varchar\",\"dbColumnName\":\"remarks\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"textarea\",\"insert\":\"1\",\"javaFieldName\":\"remarks\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":255,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":120,\"update\":\"1\"}]', '1', '2018-06-02 11:04:35', '1', '2018-06-02 11:04:35', null), ('d6db25ccff874e049c81afd2d09dfc21', 'demo_gen', 'String', '生成案例', 'demo', 'gen', '1', 'DemoGen', '[{\"columnComment\":\"编号\",\"columnKey\":\"PRI\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"id\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"hidden\",\"insert\":\"1\",\"javaFieldName\":\"id\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":10},{\"columnComment\":\"文本\",\"columnKey\":\"\",\"columnType\":\"varchar(50)\",\"dataType\":\"varchar\",\"dbColumnName\":\"input_text\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"inputText\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":50,\"queryType\":\"like\",\"search\":\"1\",\"sort\":20,\"sortable\":\"1\",\"update\":\"1\"},{\"columnComment\":\"下拉\",\"columnKey\":\"\",\"columnType\":\"varchar(10)\",\"dataType\":\"varchar\",\"dbColumnName\":\"input_select\",\"dictType\":\"yes_no\",\"extra\":\"\",\"inputType\":\"select\",\"insert\":\"1\",\"javaFieldName\":\"inputSelect\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":10,\"queryType\":\"=\",\"search\":\"1\",\"sort\":30,\"sortable\":\"1\",\"update\":\"1\"},{\"columnComment\":\"单选\",\"columnKey\":\"\",\"columnType\":\"varchar(10)\",\"dataType\":\"varchar\",\"dbColumnName\":\"input_radio\",\"dictType\":\"yes_no\",\"extra\":\"\",\"inputType\":\"radio\",\"insert\":\"1\",\"javaFieldName\":\"inputRadio\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":10,\"queryType\":\"=\",\"sort\":40,\"sortable\":\"1\",\"update\":\"1\"},{\"columnComment\":\"复选\",\"columnKey\":\"\",\"columnType\":\"varchar(10)\",\"dataType\":\"varchar\",\"dbColumnName\":\"input_checkbox\",\"dictType\":\"yes_no\",\"extra\":\"\",\"inputType\":\"checkbox\",\"insert\":\"1\",\"javaFieldName\":\"inputCheckbox\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"list\":\"1\",\"maxLength\":10,\"queryType\":\"=\",\"sort\":50,\"sortable\":\"1\",\"update\":\"1\"},{\"columnComment\":\"文本域\",\"columnKey\":\"\",\"columnType\":\"text\",\"dataType\":\"text\",\"dbColumnName\":\"input_textarea\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"textarea\",\"insert\":\"1\",\"javaFieldName\":\"inputTextarea\",\"javaType\":\"String\",\"jdbcType\":\"LONGVARCHAR\",\"list\":\"1\",\"maxLength\":65535,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":60,\"update\":\"1\"},{\"columnComment\":\"时间\",\"columnKey\":\"\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"input_date\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"inputDate\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"list\":\"1\",\"queryType\":\"between\",\"search\":\"1\",\"sort\":70,\"sortable\":\"1\",\"update\":\"1\"},{\"columnComment\":\"整数\",\"columnKey\":\"\",\"columnType\":\"int(11)\",\"dataType\":\"int\",\"dbColumnName\":\"input_zhengshu\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"zhengshu\",\"insert\":\"1\",\"javaFieldName\":\"inputZhengshu\",\"javaType\":\"Integer\",\"jdbcType\":\"INTEGER\",\"list\":\"1\",\"nullable\":\"1\",\"queryType\":\"\",\"sort\":80,\"sortable\":\"1\",\"update\":\"1\"},{\"columnComment\":\"小数\",\"columnKey\":\"\",\"columnType\":\"double\",\"dataType\":\"double\",\"dbColumnName\":\"input_xiaoshu\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"xiaoshu\",\"insert\":\"1\",\"javaFieldName\":\"inputXiaoshu\",\"javaType\":\"Double\",\"jdbcType\":\"DOUBLE\",\"list\":\"1\",\"nullable\":\"1\",\"queryType\":\"\",\"sort\":90,\"sortable\":\"1\",\"update\":\"1\"},{\"columnComment\":\"富文本\",\"columnKey\":\"\",\"columnType\":\"mediumtext\",\"dataType\":\"mediumtext\",\"dbColumnName\":\"rich_text\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"richtext\",\"insert\":\"1\",\"javaFieldName\":\"richText\",\"javaType\":\"String\",\"jdbcType\":\"LONGVARCHAR\",\"list\":\"1\",\"maxLength\":16777215,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":100,\"update\":\"1\"},{\"columnComment\":\"图片\",\"columnKey\":\"\",\"columnType\":\"varchar(255)\",\"dataType\":\"varchar\",\"dbColumnName\":\"image\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"picture\",\"insert\":\"1\",\"javaFieldName\":\"image\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":255,\"queryType\":\"\",\"sort\":110,\"update\":\"1\"},{\"columnComment\":\"文件\",\"columnKey\":\"\",\"columnType\":\"varchar(255)\",\"dataType\":\"varchar\",\"dbColumnName\":\"file\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"file\",\"insert\":\"1\",\"javaFieldName\":\"file\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":255,\"queryType\":\"\",\"sort\":120,\"update\":\"1\"},{\"columnComment\":\"创建者\",\"columnKey\":\"MUL\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"create_by\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"createBy\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":130},{\"columnComment\":\"创建时间\",\"columnKey\":\"MUL\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"create_date\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"createDate\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"queryType\":\"\",\"sort\":140,\"sortable\":\"1\"},{\"columnComment\":\"更新者\",\"columnKey\":\"\",\"columnType\":\"varchar(32)\",\"dataType\":\"varchar\",\"dbColumnName\":\"update_by\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"text\",\"insert\":\"1\",\"javaFieldName\":\"updateBy\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":32,\"queryType\":\"\",\"sort\":150,\"update\":\"1\"},{\"columnComment\":\"更新时间\",\"columnKey\":\"\",\"columnType\":\"datetime\",\"dataType\":\"datetime\",\"dbColumnName\":\"update_date\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"date\",\"insert\":\"1\",\"javaFieldName\":\"updateDate\",\"javaType\":\"Date\",\"jdbcType\":\"TIMESTAMP\",\"list\":\"1\",\"queryType\":\"\",\"sort\":160,\"update\":\"1\"},{\"columnComment\":\"备注信息\",\"columnKey\":\"\",\"columnType\":\"varchar(255)\",\"dataType\":\"varchar\",\"dbColumnName\":\"remarks\",\"dictType\":\"\",\"extra\":\"\",\"inputType\":\"textarea\",\"insert\":\"1\",\"javaFieldName\":\"remarks\",\"javaType\":\"String\",\"jdbcType\":\"VARCHAR\",\"maxLength\":255,\"nullable\":\"1\",\"queryType\":\"\",\"sort\":170,\"update\":\"1\"}]', '1', '2018-05-13 20:48:13', '1', '2018-05-23 21:26:22', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_login_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `status` varchar(1) NOT NULL COMMENT '登录状态0:成功;1.密码错误；2.禁用;3.锁定24小时',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `ip` varchar(16) DEFAULT NULL COMMENT 'IP地址',
  `area` varchar(20) DEFAULT NULL COMMENT '登录地区',
  `user_agent` varchar(1000) NOT NULL COMMENT '用户代理',
  `device_name` varchar(100) DEFAULT NULL COMMENT '设备名称',
  `browser_name` varchar(100) DEFAULT NULL COMMENT '浏览器名称',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `ip` (`ip`) USING BTREE,
  KEY `login_time` (`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志';

-- ----------------------------
--  Records of `sys_login_log`
-- ----------------------------
BEGIN;
INSERT INTO `sys_login_log` VALUES ('4a5565e042a24912988b7e36691c8c19', '1', '0', '2018-06-03 16:40:23', '127.0.0.1', 'XX内网IP', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/602.2.14 (KHTML, like Gecko) Version/10.0.1 Safari/602.2.14', 'Mac OS X COMPUTER', 'Safari 10 10.0.1', '1', '2018-06-03 16:40:23', '1', '2018-06-03 16:40:23', null), ('8fa6c443814748b0806f5803a694e6ce', '1', '0', '2018-06-03 16:40:23', '127.0.0.1', 'XX内网IP', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/602.2.14 (KHTML, like Gecko) Version/10.0.1 Safari/602.2.14', 'Mac OS X COMPUTER', 'Safari 10 10.0.1', '1', '2018-06-03 16:40:23', '1', '2018-06-03 16:40:23', null), ('ccfaba032e0d4728bb09ac897e97889e', '1', '0', '2018-06-03 16:42:06', '127.0.0.1', 'XX内网IP', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/602.2.14 (KHTML, like Gecko) Version/10.0.1 Safari/602.2.14', 'Mac OS X COMPUTER', 'Safari 10 10.0.1', '1', '2018-06-03 16:42:06', '1', '2018-06-03 16:42:06', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `parent_id` varchar(32) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `sort` int(10) NOT NULL COMMENT '排序',
  `href` varchar(200) DEFAULT NULL COMMENT '链接',
  `target` varchar(10) DEFAULT NULL COMMENT '目标',
  `type` varchar(2) NOT NULL COMMENT '0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `is_show` char(1) NOT NULL COMMENT '是否在菜单中显示',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`) USING BTREE,
  KEY `parent_ids` (`parent_ids`(255)),
  KEY `name` (`name`),
  KEY `create_by` (`create_by`),
  KEY `create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单';

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('0cbc3253fc1148c894cc954acfebdd92', '2112b91ae0ce4f8db8af99d304bca35c', '0,2b3712ac51b3440589a64bd6b12440832112b91ae0ce4f8db8af99d304bca35c', '删除', '10', null, null, '2', null, '1', 'sys:menu:delete', '1', '2018-04-19 14:47:47', '1', '2018-04-19 14:47:47', null), ('0f030bf647b146e6873a3bb177d6fa90', '2b3712ac51b3440589a64bd6b1244083', '0,2b3712ac51b3440589a64bd6b1244083', '角色管理', '2', '/pages/sys/role.html', 'main', '1', 'fa fa-fw fa-user-secret', '1', 'sys:role:qry', '1', '2018-04-15 00:07:38', '1', '2018-04-19 14:32:29', null), ('0f42afb2182f423f8f203276058d1fe2', '11691595d4324f2f88d1f883d18f4e04', '0,2b3712ac51b3440589a64bd6b124408311691595d4324f2f88d1f883d18f4e04', '删除', '10', null, null, '2', null, '1', 'sys:dict:delete', '1', '2018-04-19 15:00:12', '1', '2018-04-19 15:14:36', null), ('11691595d4324f2f88d1f883d18f4e04', '2b3712ac51b3440589a64bd6b1244083', '0,2b3712ac51b3440589a64bd6b1244083', '字典管理', '5', '/pages/sys/dict.html', 'main', '1', 'fa fa-fw fa-database', '1', 'sys:dict:qry', '1', '2018-04-15 00:09:12', '1', '2018-04-19 14:56:03', null), ('2112b91ae0ce4f8db8af99d304bca35c', '2b3712ac51b3440589a64bd6b1244083', '0,2b3712ac51b3440589a64bd6b1244083', '菜单管理', '4', '/pages/sys/menu.html', 'main', '1', 'fa fa-fw fa-tasks', '1', 'sys:menu:qry', '1', '2018-04-15 00:08:16', '1', '2018-04-19 14:46:59', null), ('2b3712ac51b3440589a64bd6b1244083', '0', '0,', '系统管理', '2', null, null, '0', 'fa fa-fw fa-cogs', '1', '', '1', '2018-04-15 00:05:08', '1', '2018-04-16 20:41:55', null), ('3266e16218e94fb28d8c6342c4ddbc61', '0f030bf647b146e6873a3bb177d6fa90', '0,2b3712ac51b3440589a64bd6b12440830f030bf647b146e6873a3bb177d6fa90', '添加', '10', null, null, '2', null, '1', 'sys:role:save', '1', '2018-04-19 14:36:23', '1', '2018-04-19 14:36:23', null), ('3693fa53ff7c4146abc444230e5eb4b4', 'b916f2a5c9b84b98be54f829010ab5c8', '0,2b3712ac51b3440589a64bd6b1244083b916f2a5c9b84b98be54f829010ab5c8', '修改', '2', null, null, '2', null, '1', 'sys:user:update', '1', '2018-04-16 21:48:05', '1', '2018-04-16 21:48:05', null), ('4426a306855647fd9fc495037334be84', '2b3712ac51b3440589a64bd6b1244083', '0,2b3712ac51b3440589a64bd6b1244083', '登录日志', '11', '/pages/sys/loginlog.html', 'main', '1', 'fa fa-fw fa-hourglass-half', '1', 'sys:loginlog:qry', '1', '2018-05-31 23:04:50', '1', '2018-05-31 23:22:30', null), ('4845ecff299945679383569e97f863c4', '11691595d4324f2f88d1f883d18f4e04', '0,2b3712ac51b3440589a64bd6b124408311691595d4324f2f88d1f883d18f4e04', '修改', '10', null, null, '2', null, '1', 'sys:dict:update', '1', '2018-04-19 15:00:01', '1', '2018-04-19 15:00:01', null), ('50646379e4ec4c299cde89f5eee3ca16', 'b916f2a5c9b84b98be54f829010ab5c8', '0,2b3712ac51b3440589a64bd6b1244083b916f2a5c9b84b98be54f829010ab5c8', '删除', '10', null, null, '2', null, '1', 'sys:user:delete', '1', '2018-04-16 21:51:02', '1', '2018-04-16 21:51:02', null), ('5333b205d4044feab756c6ac6561ab05', '2112b91ae0ce4f8db8af99d304bca35c', '0,2b3712ac51b3440589a64bd6b12440832112b91ae0ce4f8db8af99d304bca35c', '修改', '10', null, null, '2', null, '1', 'sys:menu:update', '1', '2018-04-19 14:47:34', '1', '2018-04-19 14:47:34', null), ('69baa5b46caf433093ff69ed533a8c0e', '0f030bf647b146e6873a3bb177d6fa90', '0,2b3712ac51b3440589a64bd6b12440830f030bf647b146e6873a3bb177d6fa90', '删除', '10', null, null, '2', null, '1', 'sys:role:delete', '1', '2018-04-19 14:36:48', '1', '2018-04-19 14:36:48', null), ('7ba079aaa4054300aeabd0e4c3055bf8', '87bfa91d5d78468695adea15b6e20b38', '0,2b3712ac51b3440589a64bd6b124408387bfa91d5d78468695adea15b6e20b38', '修改', '10', null, null, '2', null, '1', 'sys:param:update', '1', '2018-04-19 15:06:27', '1', '2018-04-19 15:06:27', null), ('7e644586cd144c9bb2f9819ad9a64718', '2112b91ae0ce4f8db8af99d304bca35c', '0,2b3712ac51b3440589a64bd6b12440832112b91ae0ce4f8db8af99d304bca35c', '添加', '10', null, null, '2', null, '1', 'sys:menu:save', '1', '2018-04-19 14:47:12', '1', '2018-04-19 14:47:12', null), ('8059bd699a4444a0aaa2e103b0daada2', '87bfa91d5d78468695adea15b6e20b38', '0,2b3712ac51b3440589a64bd6b124408387bfa91d5d78468695adea15b6e20b38', '添加', '10', null, null, '2', null, '1', 'sys:param:save', '1', '2018-04-19 15:05:58', '1', '2018-04-19 15:05:58', null), ('87bfa91d5d78468695adea15b6e20b38', '2b3712ac51b3440589a64bd6b1244083', '0,2b3712ac51b3440589a64bd6b1244083', '系统参数', '10', '/pages/sys/param.html', 'main', '1', 'fa fa-fw fa-houzz', '1', 'sys:param:qry', '1', '2018-04-15 00:10:05', '1', '2018-04-19 14:56:03', null), ('880aeae856ae11e8a97925ef3f6b5e93', 'd6db25ccff874e049c81afd2d09dfc21', '0,d6db25ccff874e049c81afd2d09dfc21,', '修改', '2', null, null, '2', null, '1', 'demo:gen:update', '1', '2018-05-13 21:07:04', '1', '2018-05-13 21:07:04', null), ('880b5dd456ae11e8a97925ef3f6b5e93', 'd6db25ccff874e049c81afd2d09dfc21', '0,d6db25ccff874e049c81afd2d09dfc21,', '删除', '10', null, null, '2', null, '1', 'demo:gen:delete', '1', '2018-05-13 21:07:04', '1', '2018-05-13 21:07:04', null), ('8e175bbb35494b6e9c74b64ab6ec0ce0', 'b916f2a5c9b84b98be54f829010ab5c8', '0,2b3712ac51b3440589a64bd6b1244083b916f2a5c9b84b98be54f829010ab5c8', '添加', '1', null, null, '2', null, '1', 'sys:user:save', '1', '2018-04-16 21:47:42', '1', '2018-04-16 21:47:42', null), ('a25c853c31db4361914c824a0773cc07', '0f030bf647b146e6873a3bb177d6fa90', '0,2b3712ac51b3440589a64bd6b12440830f030bf647b146e6873a3bb177d6fa90', '修改', '10', null, null, '2', null, '1', 'sys:role:update', '1', '2018-04-19 14:36:37', '1', '2018-04-19 14:36:37', null), ('a2a24773907f4f43856f7bbd2a9df767', '87bfa91d5d78468695adea15b6e20b38', '0,2b3712ac51b3440589a64bd6b124408387bfa91d5d78468695adea15b6e20b38', '删除', '10', null, null, '2', null, '1', 'sys:param:delete', '1', '2018-04-19 15:06:39', '1', '2018-05-10 09:57:23', null), ('a33f081dcb7a439b96dad4e7a1e89e27', '11691595d4324f2f88d1f883d18f4e04', '0,2b3712ac51b3440589a64bd6b124408311691595d4324f2f88d1f883d18f4e04', '添加', '10', null, null, '2', null, '1', 'sys:dict:save', '1', '2018-04-19 14:59:49', '1', '2018-04-19 14:59:49', null), ('ad58220256ae11e8a97925ef3f6b5e93', 'd6db25ccff874e049c81afd2d09dfc21', '0,d6db25ccff874e049c81afd2d09dfc21,', '添加', '1', null, null, '2', null, '1', 'demo:gen:save', '1', '2018-05-13 21:08:06', '1', '2018-05-13 21:08:06', null), ('b916f2a5c9b84b98be54f829010ab5c8', '2b3712ac51b3440589a64bd6b1244083', '0,2b3712ac51b3440589a64bd6b1244083', '用户管理', '1', '/pages/sys/user.html', 'main', '1', 'fa fa-fw fa-user', '1', 'sys:user:qry', '1', '2018-04-15 00:05:46', '1', '2018-04-16 21:54:12', null), ('b9f8b111a6c34a2a8ce42e280f7e3a33', '2b3712ac51b3440589a64bd6b1244083', '0,2b3712ac51b3440589a64bd6b1244083', '文件管理', '10', '/pages/sys/file.html', 'main', '1', 'fa fa-fw fa-file', '1', 'sys:file:qry', '1', '2018-04-15 21:44:01', '1', '2018-04-19 14:56:03', null), ('c27147c97283488f8787aea546e55f30', 'ec9045a36aab42bf9d33a0587ab956a7', '0,2b3712ac51b3440589a64bd6b1244083ec9045a36aab42bf9d33a0587ab956a7', '添加', '10', null, null, '2', null, '1', 'sys:dept:save', '1', '2018-04-19 14:40:21', '1', '2018-04-19 14:40:21', null), ('c9f77f40397e4908ba8b580647275f27', '2b3712ac51b3440589a64bd6b1244083', '0,2b3712ac51b3440589a64bd6b1244083', '代码生成', '12', '/pages/sys/gen.html', 'main', '1', 'fa fa-fw fa-optin-monster', '1', 'sys:gen', '1', '2018-04-26 22:19:45', '1', '2018-06-01 00:47:31', null), ('d6db25ccff874e049c81afd2d09dfc21', '0', '0,', '生成案例', '100', '/pages/demo/gen.html', 'main', '1', 'fa fa-fw fa-user', '1', 'demo:gen:qry', '1', '2018-05-13 21:07:04', '1', '2018-05-21 22:06:20', null), ('e3b5184f60534bd4a3843e916fd361e8', 'ec9045a36aab42bf9d33a0587ab956a7', '0,2b3712ac51b3440589a64bd6b1244083ec9045a36aab42bf9d33a0587ab956a7', '删除', '10', null, null, '2', null, '1', 'sys:dept:delete', '1', '2018-04-19 14:40:54', '1', '2018-04-19 14:40:54', null), ('eb4d200652b44122ad10fb98dbbc4cf5', 'b9f8b111a6c34a2a8ce42e280f7e3a33', '0,2b3712ac51b3440589a64bd6b1244083b9f8b111a6c34a2a8ce42e280f7e3a33', '上传', '10', null, null, '2', null, '1', 'sys:file:upload', '1', '2018-04-19 15:09:07', '1', '2018-04-19 15:09:07', null), ('ec9045a36aab42bf9d33a0587ab956a7', '2b3712ac51b3440589a64bd6b1244083', '0,2b3712ac51b3440589a64bd6b1244083', '部门管理', '3', '/pages/sys/dept.html', 'main', '1', 'fa fa-fw fa-cubes', '1', 'sys:dept:qry', '1', '2018-04-15 00:06:37', '1', '2018-04-20 19:06:11', null), ('f6c0677f7e004d75aba2984c70122640', 'ec9045a36aab42bf9d33a0587ab956a7', '0,2b3712ac51b3440589a64bd6b1244083ec9045a36aab42bf9d33a0587ab956a7', '修改', '10', null, null, '2', null, '1', 'sys:dept:update', '1', '2018-04-19 14:40:36', '1', '2018-04-19 14:40:36', null), ('f7a1fd0464e311e88559ce72ceb3185c', '4426a306855647fd9fc495037334be84', '0,4426a306855647fd9fc495037334be84,', '删除', '10', null, null, '2', null, '1', 'sys:loginlog:delete', '1', '2018-05-31 23:04:50', '1', '2018-05-31 23:04:50', null), ('f95bcb9b445549d5910e904476185ebf', 'b9f8b111a6c34a2a8ce42e280f7e3a33', '0,2b3712ac51b3440589a64bd6b1244083b9f8b111a6c34a2a8ce42e280f7e3a33', '删除', '10', null, null, '2', null, '1', 'sys:file:delete', '1', '2018-04-19 15:07:51', '1', '2018-04-19 15:07:51', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_param`
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `param_key` varchar(50) NOT NULL COMMENT '键',
  `param_value` varchar(100) NOT NULL COMMENT '值',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统参数';

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `data_scope` char(1) NOT NULL COMMENT '数据范围,0:全部，1：本部门，2：本部门及以下，3.本人',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `name` (`name`),
  KEY `create_by` (`create_by`),
  KEY `create_date` (`create_date`),
  KEY `data_scope` (`data_scope`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
--  Table structure for `sys_role_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `dept_id` varchar(32) NOT NULL COMMENT '部门id',
  PRIMARY KEY (`role_id`,`dept_id`),
  KEY `role_id` (`role_id`),
  KEY `dept_id` (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色部门';

-- ----------------------------
--  Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` varchar(32) NOT NULL COMMENT '角色编号',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-菜单';

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门',
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机',
  `photo` varchar(100) DEFAULT NULL COMMENT '用户头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮件',
  `user_type` varchar(2) DEFAULT NULL COMMENT '用户类型，业务扩展用',
  `status` varchar(1) DEFAULT NULL COMMENT '状态1：正常，0：禁用',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`) USING BTREE,
  KEY `dept_id` (`dept_id`) USING BTREE,
  KEY `create_by` (`create_by`),
  KEY `create_date` (`create_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', '0a7f12c90f1a4875987265e00589e408', 'admin', '2a576f34b098c24b4406bf0d5fa7cd31493f94df58ebe5b978435d1d5e5eab7a', 'rNBdNtjuefmwLGzXjHoN', '管理员', '121212', '/2018/06/02/60d2e10d1c714076ac972c492ddeb822.png', '21221@qq.com', '0', '1', '1', '2017-12-29 14:22:04', '1', '2018-06-02 11:37:59', '');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_bak`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_bak`;
CREATE TABLE `sys_user_bak` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '部门',
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机',
  `photo` varchar(100) DEFAULT NULL COMMENT '用户头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮件',
  `user_type` varchar(2) DEFAULT NULL COMMENT '用户类型，业务扩展用',
  `status` varchar(1) DEFAULT NULL COMMENT '状态1：正常，0：禁用',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `role_id` varchar(32) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色';

-- ----------------------------
--  Table structure for `wechat_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `wechat_user_info`;
CREATE TABLE `wechat_user_info` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别(值为1时是男性，值为2时是女性，值为0时是未知)',
  `country` varchar(64) DEFAULT NULL COMMENT '国家',
  `province` varchar(64) DEFAULT NULL COMMENT '省份',
  `city` varchar(64) DEFAULT NULL COMMENT '城市',
  `head_img_url` varchar(500) DEFAULT NULL COMMENT '图像',
  `subscribe` varchar(2) DEFAULT NULL COMMENT '是否关注',
  `subscribe_time` datetime DEFAULT NULL COMMENT '关注时间',
  `subscribe_scene` varchar(100) DEFAULT NULL COMMENT '关注场景',
  `openid` varchar(64) NOT NULL COMMENT 'openid',
  `unionid` varchar(100) DEFAULT NULL COMMENT 'unionid',
  `create_by` varchar(32) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `create_by` (`create_by`),
  KEY `create_date` (`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

-- ----------------------------
--  Records of `wechat_user_info`
-- ----------------------------
BEGIN;
INSERT INTO `wechat_user_info` VALUES ('91aca8587a40482883e0f51a53c1c2ea', 'value', null, null, null, null, null, null, null, null, '', null, '1', '2018-05-10 09:50:27', '1', '2018-05-10 09:50:27', '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
