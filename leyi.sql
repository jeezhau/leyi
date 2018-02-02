/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50709
Source Host           : localhost:3306
Source Database       : leyi

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2016-05-10 16:39:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_article_brief
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_brief`;
CREATE TABLE `tb_article_brief` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '文章标题名称',
  `keywords` varchar(255) DEFAULT NULL COMMENT '文章关键词',
  `brief` varchar(600) DEFAULT NULL COMMENT '文章简介',
  `source` char(1) NOT NULL,
  `theme_id` int(11) NOT NULL COMMENT '主题ID，引用tb_theme_class表ID',
  `type` char(1) NOT NULL COMMENT '文章类型',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_opr` int(11) DEFAULT NULL COMMENT '更新人,引用tb_user_full_info表ID',
  `enabled` char(1) NOT NULL COMMENT '启用状态',
  PRIMARY KEY (`id`),
  KEY `fk_articleBrief_themeId` (`theme_id`),
  KEY `fk_articleBrief_updateOpr` (`update_opr`),
  CONSTRAINT `fk_articleBrief_themeId` FOREIGN KEY (`theme_id`) REFERENCES `tb_theme_class` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_articleBrief_updateOpr` FOREIGN KEY (`update_opr`) REFERENCES `tb_user_full_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章标题概要';

-- ----------------------------
-- Table structure for tb_article_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_article_content`;
CREATE TABLE `tb_article_content` (
  `article_id` int(11) NOT NULL COMMENT '文章ID',
  `content` text NOT NULL COMMENT '文章内容',
  PRIMARY KEY (`article_id`),
  CONSTRAINT `fk_articleContent_articleId` FOREIGN KEY (`article_id`) REFERENCES `tb_article_brief` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章详细信息';

-- ----------------------------
-- Table structure for tb_comments
-- ----------------------------
DROP TABLE IF EXISTS `tb_comments`;
CREATE TABLE `tb_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论Id',
  `article_id` int(11) NOT NULL COMMENT '文章ID，引用tb_artile_brief表ID',
  `content` text NOT NULL COMMENT '评论内容',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_opr` int(11) DEFAULT NULL COMMENT '更新人,引用tb_user_full_info表ID',
  `enabled` char(1) DEFAULT NULL COMMENT '启用状态',
  PRIMARY KEY (`id`),
  KEY `fk_comments_articleId` (`article_id`),
  KEY `fk_comments_updateOpr` (`update_opr`),
  CONSTRAINT `fk_comments_articleId` FOREIGN KEY (`article_id`) REFERENCES `tb_article_brief` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comments_updateOpr` FOREIGN KEY (`update_opr`) REFERENCES `tb_user_full_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章评论信息';

-- ----------------------------
-- Table structure for tb_review_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_review_info`;
CREATE TABLE `tb_review_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `obj_name` varchar(50) NOT NULL COMMENT '相关表',
  `key_id` int(11) NOT NULL COMMENT '相关表记录ID',
  `original_info` text NOT NULL COMMENT '待审批信息',
  `review_info` varchar(600) DEFAULT NULL COMMENT '审批信息',
  `review_opr` int(11) DEFAULT NULL COMMENT '审批人',
  `review_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '审批时间',
  `result` char(1) NOT NULL COMMENT '审批结果(0：通过，1：不通过)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批信息记录';

-- ----------------------------
-- Table structure for tb_theme_class
-- ----------------------------
DROP TABLE IF EXISTS `tb_theme_class`;
CREATE TABLE `tb_theme_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级主题ID',
  `name` varchar(50) NOT NULL COMMENT '主题名称',
  `keywords` varchar(255) DEFAULT NULL COMMENT '主题关键词',
  `desc_info` varchar(600) DEFAULT NULL COMMENT '主题描述',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_opr` int(11) DEFAULT NULL COMMENT '更新人,引用用户表ID',
  `enabled` char(1) NOT NULL COMMENT '启用状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_themeClass_name` (`name`),
  KEY `fk_themeClass_updateOpr` (`update_opr`),
  KEY `fk_themeClass_parentId` (`parent_id`),
  CONSTRAINT `fk_themeClass_parentId` FOREIGN KEY (`parent_id`) REFERENCES `tb_theme_class` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
  CONSTRAINT `fk_themeClass_updateOpr` FOREIGN KEY (`update_opr`) REFERENCES `tb_user_full_info` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类主题';

-- ----------------------------
-- Table structure for tb_user_base_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_base_info`;
CREATE TABLE `tb_user_base_info` (
  `user_id` int(11) NOT NULL COMMENT '用户ID，引用tb_user_full_info表主键',
  `username` varchar(50) NOT NULL COMMENT '用户名（唯一）',
  `email` varchar(100) NOT NULL COMMENT '邮箱',
  `passwd` varchar(100) NOT NULL COMMENT '密码，再次加密后的密文',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_userBaseInfo_userId` FOREIGN KEY (`user_id`) REFERENCES `tb_user_full_info` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='该表用于用户登录验证，只保存当前已启用用户的信息；通过定时服务定期更新该表数据';

-- ----------------------------
-- Table structure for tb_user_full_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_full_info`;
CREATE TABLE `tb_user_full_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名（唯一）',
  `email` varchar(100) NOT NULL,
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `passwd` varchar(100) NOT NULL COMMENT '密文',
  `city` varchar(50) DEFAULT NULL COMMENT '所在城市',
  `favourite` varchar(100) DEFAULT NULL COMMENT '兴趣爱好',
  `profession` varchar(100) DEFAULT NULL COMMENT '职业',
  `introduce` varchar(600) DEFAULT NULL COMMENT '个人简介',
  `picture` varchar(250) COMMENT '照片',
  `regist_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `enabled` char(1) NOT NULL COMMENT '启用状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_userFullInfo_username` (`username`),
  UNIQUE KEY `uq_userFullInfo_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户详细信息表';
