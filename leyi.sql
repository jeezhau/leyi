SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `tb_user_full_info`;
CREATE TABLE `tb_user_full_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名（唯一）',
  `email` varchar(100) NOT NULL COMMENT '邮箱（唯一）',
  `birthday` date NOT NULL COMMENT '生日',
  `sex` char(1) NOT NULL DEFAULT 'N' COMMENT '性别：N-未知／M-男／F-女',
  `passwd` varchar(255) NOT NULL COMMENT '密文',
  `city` varchar(50) DEFAULT NULL COMMENT '所在城市',
  `favourite` varchar(100) DEFAULT NULL COMMENT '兴趣爱好',
  `profession` varchar(100) DEFAULT NULL COMMENT '职业',
  `introduce` varchar(600) DEFAULT NULL COMMENT '个人简介',
  `picture` varchar(255) COMMENT '头像照片',
  `regist_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `invite_code` int(50) NOT NULL COMMENT '邀请码',
  `status` char(1) NOT NULL COMMENT '启用状态（0：待审核，A：审核通过，F：审核拒绝）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_userFullInfo_username` (`username`),
  UNIQUE KEY `uq_userFullInfo_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户详细信息表';


DROP TABLE IF EXISTS `tb_theme_class`;
CREATE TABLE `tb_theme_class` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_seq` varchar(50) NOT NULL COMMENT '上级主题ID序列（/一级ID/二级ID/...），顶级主题的上级主题为 /',
  `name` varchar(50) NOT NULL COMMENT '主题名称',
  `owner_id` int(11) NOT NULL COMMENT '主题的拥有者ID',
  `keywords` varchar(255) DEFAULT NULL COMMENT '主题关键词',
  `content` varchar(600) DEFAULT NULL COMMENT '主题描述',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` char(1) NOT NULL COMMENT '启用状态（A：正常，D-已删除）',
  CONSTRAINT `pk_themeClass_id` PRIMARY KEY (`id`),
  CONSTRAINT `fk_themeClass_ownerId` FOREIGN KEY (`owner_id`) REFERENCES `tb_user_full_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主题信息表';


DROP TABLE IF EXISTS `tb_article_brief`;
CREATE TABLE `tb_article_brief` (
  `id` int(25) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '文章标题名称',
  `keywords` varchar(255) NOT NULL COMMENT '文章关键词',
  `brief` varchar(600) NOT NULL COMMENT '文章简介（小文章内容，大文章简介概要）',
  `source` char(1) NOT NULL default '0' COMMENT '来源类型：0-自创，1-转摘，2-其他',
  `theme_id` int(20) NOT NULL COMMENT '主题ID，引用tb_theme_class表ID',
  `type` char(1) NOT NULL default '0' COMMENT '文章类型：0-文本，1-图册，2-多媒体，3-混合，4-文件资料',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `owner_id` int(11) NOT NULL COMMENT '更新人,引用tb_user_full_info表ID',
  `status` char(1) NOT NULL COMMENT '启用状态(0-待审核，A-审核通过，F-审核拒绝，D-已删除)',
  CONSTRAINT `pk_articleBrief_Id` PRIMARY KEY (`id`),
  CONSTRAINT `fk_articleBrief_themeId` FOREIGN KEY (`theme_id`) REFERENCES `tb_theme_class` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_articleBrief_ownerId` FOREIGN KEY (`owner_id`) REFERENCES `tb_user_full_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章标题概要';


DROP TABLE IF EXISTS `tb_article_detail`;
CREATE TABLE `tb_article_detail` (
  `article_id` int(25) NOT NULL COMMENT '文章ID',
  `content` text NULL COMMENT '文章内容',
  CONSTRAINT `pk_articleDetail_articleId` PRIMARY KEY (`article_id`),
  CONSTRAINT `fk_articleDetail_articleId` FOREIGN KEY (`article_id`) REFERENCES `tb_article_brief` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章详细信息';


DROP TABLE IF EXISTS `tb_comments`;
CREATE TABLE `tb_comments` (
  `id` int(30) NOT NULL AUTO_INCREMENT COMMENT '评论Id',
  `article_id` int(25) NOT NULL COMMENT '文章ID，引用tb_artile_brief表ID',
  `content` varchar(600) NOT NULL COMMENT '评论内容',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_opr` int(11) NOT NULL COMMENT '更新人,引用tb_user_full_info表ID',
  `status` char(1) NOT NULL COMMENT '启用状态(0-待审核，A-审核通过，F-审核拒绝)',
  CONSTRAINT `pk_comments_id` PRIMARY KEY (`id`),
  CONSTRAINT `fk_comments_articleId` FOREIGN KEY (`article_id`) REFERENCES `tb_article_brief` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comments_updateOpr` FOREIGN KEY (`update_opr`) REFERENCES `tb_user_full_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章评论留言表';


DROP TABLE IF EXISTS `tb_review_apply`;
CREATE TABLE `tb_review_apply` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `obj_name` varchar(50) NOT NULL COMMENT '相关表',
  `key_id` int(30) NOT NULL COMMENT '相关表记录ID',
  `apply_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `status` varchar(2) NOT NULL COMMENT '审批结果(0：待审核，A：通过，R：不通过，CC：复核中，CR：复核不通过，CA：复核通过，CT：复核终止) ',
  CONSTRAINT `pk_reviewApply_id` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批申请表';

DROP TABLE IF EXISTS `tb_review_log`;
CREATE TABLE `tb_review_log` (
    `id` INT(30) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `apply_id` INT(20) NOT NULL COMMENT '申请ID，关联tb_review_apply.id',
    `original_info` TEXT NOT NULL COMMENT '原始待审批信息（json）',
    `review_info` VARCHAR(600) DEFAULT NULL COMMENT '审批意见',
    `review_opr` INT(11) NOT NULL COMMENT '审批人',
    `review_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '审批时间',
    `status` CHAR(1) NOT NULL COMMENT '审批结果(R：不通过,A：通过)',
    CONSTRAINT `pk_reviewLog_id` PRIMARY KEY (`id`),
    CONSTRAINT `fk_reviewLog_applyId` FOREIGN KEY (`apply_id`)
        REFERENCES `tb_review_apply` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_reviewLog_reviewOpr` FOREIGN KEY (`review_opr`)
        REFERENCES `tb_user_full_info` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='审批日志表';


DROP TABLE IF EXISTS `tb_check_log`;
CREATE TABLE `tb_check_log` (
    `id` INT(30) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `apply_id` INT(20) NOT NULL COMMENT '申请ID，关联tb_review_apply.id',
    `check_info` VARCHAR(600) DEFAULT NULL COMMENT '复核意见',
    `check_opr` INT(11) NOT NULL COMMENT '复核人',
    `check_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '复核时间',
    `status` CHAR(1) NOT NULL COMMENT '复核结果(R：不通过,A：通过)',
    CONSTRAINT `pk_checkLog_id` PRIMARY KEY (`id`),
    CONSTRAINT `fk_checkLog_applyId` FOREIGN KEY (`apply_id`)
        REFERENCES `tb_review_apply` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_checkLog_checkOpr` FOREIGN KEY (`check_opr`)
        REFERENCES `tb_user_full_info` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='复核日志表';

DROP TABLE IF EXISTS `tb_invite_code`;
CREATE TABLE `tb_invite_code` (
  `code` integer(50) NOT NULL AUTO_INCREMENT COMMENT '邀请码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_opr` int(11) NOT NULL COMMENT '创建人,引用tb_user_full_info表ID',
  `used_cnt` int(2) NOT NULL DEFAULT 0 COMMENT '已使用次数',
  CONSTRAINT `pk_inviteCode_code` PRIMARY KEY (`code`),
  CONSTRAINT `fk_inviteCode_createOpr` FOREIGN KEY (`create_opr`) REFERENCES `tb_user_full_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=100001 COMMENT='邀请码信息表';


DROP TABLE IF EXISTS `tb_role_info`;
CREATE TABLE `tb_role_info` (
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(30) NOT NULL COMMENT '角色名称',
  `desc` varchar(600)  NULL COMMENT '角色描述',
  `update_opr` int(11)  NOT NULL  COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` char(1) NOT NULL COMMENT '状态（A：正常，D：注销） ',
  CONSTRAINT `pk_roleInfo_id` PRIMARY KEY (`id`),
  CONSTRAINT `fk_roleInfo_updateOpr` FOREIGN KEY (`update_opr`) REFERENCES `tb_user_full_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息表';

DROP TABLE IF EXISTS `tb_func_info`;
CREATE TABLE `tb_func_info` (
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(30) NOT NULL COMMENT '功能名称',
  `url` varchar(100) NOT NULL COMMENT '功能URL',
  `desc` varchar(600)  NULL COMMENT '功能描述',
  `update_opr` int(11)  NOT NULL  COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` char(1) NOT NULL COMMENT '状态（A：正常，D：注销） ',
  CONSTRAINT `pk_funcInfo_id` PRIMARY KEY (`id`),
  CONSTRAINT `fk_funcInfo_updateOpr` FOREIGN KEY (`update_opr`) REFERENCES `tb_user_full_info` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用功能表';

DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
    `id` INT(15) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` int(11) NOT NULL COMMENT '用户ID',
    `role_id` int(2) NOT NULL COMMENT '角色ID',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `status` CHAR(1) NOT NULL COMMENT '状态(0-待审核，A-已审核通过，F-审核拒绝，D-已收回)） ',
    CONSTRAINT `pk_userRole_id` PRIMARY KEY (`id`),
    CONSTRAINT `fk_userRole_userId` FOREIGN KEY (`user_id`)
        REFERENCES `tb_user_full_info` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_userRole_roleId` FOREIGN KEY (`role_id`)
        REFERENCES `tb_role_info` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    UNIQUE KEY `uq_userRole` (`user_id`,`role_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='用户角色管理表';

DROP TABLE IF EXISTS `tb_func_role`;
CREATE TABLE `tb_func_role` (
    `id` INT(15) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `func_id` int(3) NOT NULL COMMENT '功能ID',
    `role_id` int(2) NOT NULL COMMENT '角色ID',
    `update_opr` INT(11) NOT NULL COMMENT '更新人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `status` CHAR(1) NOT NULL COMMENT '状态（A：正常，D：注销） ',
    CONSTRAINT `pk_funcRole_id` PRIMARY KEY (`id`),
    CONSTRAINT `fk_funcRole_funcId` FOREIGN KEY (`func_id`)
        REFERENCES `tb_funcl_info` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_funcRole_roleId` FOREIGN KEY (`role_id`)
        REFERENCES `tb_role_info` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_funcRole_updateOpr` FOREIGN KEY (`update_opr`)
        REFERENCES `tb_user_full_info` (`id`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    UNIQUE KEY `uq_funcRole` (`func_id`,`role_id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='功能角色管理表';

