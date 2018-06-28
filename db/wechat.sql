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

 Date: 06/18/2018 10:32:22 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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

SET FOREIGN_KEY_CHECKS = 1;

#按钮
insert into `sys_menu` ( `id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `type`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) values ( REPLACE(UUID(),"-",""), 'bca06746db9d4c5ebf41b9a6c63927ae', '0,bca06746db9d4c5ebf41b9a6c63927ae,', '修改', '2', null, null, '2', null, '1', 'wechat:userinfo:update', '1', now(), '1', now(), null);
insert into `sys_menu` ( `id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `type`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) values ( REPLACE(UUID(),"-",""), 'bca06746db9d4c5ebf41b9a6c63927ae', '0,bca06746db9d4c5ebf41b9a6c63927ae,', '删除', '10', null, null, '2', null, '1', 'wechat:userinfo:delete', '1', now(), '1', now(), null);
insert into `sys_menu` ( `id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `type`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) values ( REPLACE(UUID(),"-",""), 'bca06746db9d4c5ebf41b9a6c63927ae', '0,bca06746db9d4c5ebf41b9a6c63927ae,', '添加', '1', null, null, '2', null, '1', 'wechat:userinfo:save', '1', now(), '1', now(), null);
insert into `sys_menu` ( `id`, `parent_id`, `parent_ids`, `name`, `sort`, `href`, `target`, `type`, `icon`, `is_show`, `permission`, `create_by`, `create_date`, `update_by`, `update_date`, `remarks`) values ( 'bca06746db9d4c5ebf41b9a6c63927ae', '0', '0,', '用户信息', '1', '/pages/wechat/userinfo.html', 'main', '1', 'fa fa-fw fa-user', '1', 'wechat:userinfo:qry', '1', now(), '1', now(), null);
commit;

