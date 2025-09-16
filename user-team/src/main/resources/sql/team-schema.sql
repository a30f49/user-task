
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`teamName` varchar(255) NOT NULL COMMENT '团队名称',
`teamDesc` text DEFAULT NULL COMMENT '团队描述',
`pid` bigint DEFAULT NULL COMMENT '父级ID',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
UNIQUE(`teamName`),
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`firstName` varchar(255) NOT NULL COMMENT '名字',
`lastName` varchar(255) NOT NULL COMMENT '姓',
`DOB` datetime DEFAULT NULL COMMENT '出身年月',
`sex` varchar(26) NOT NULL COMMENT '性别(F/M)',
`postPosition` varchar(255) DEFAULT NULL COMMENT '职位',
`phone` varchar(255) DEFAULT NULL COMMENT '电话',
`email` varchar(255) DEFAULT NULL COMMENT '邮箱',
`weChat` varchar(255) DEFAULT NULL COMMENT '微信',
`qq` varchar(255) DEFAULT NULL COMMENT '企鹅',
`microBlog` varchar(255) DEFAULT NULL COMMENT '微博',
`twitter` varchar(255) DEFAULT NULL COMMENT '推特',
`note` text DEFAULT NULL COMMENT '备注',
`avatar` varchar(255) DEFAULT NULL COMMENT '头像',
`field2` varchar(255) DEFAULT NULL COMMENT '保留字段',
`userId` bigint NOT NULL COMMENT 'UserId',
`isValid` smallint(5) NOT NULL COMMENT '是否启用 0不启用 1启用',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`roleName` varchar(255) NOT NULL COMMENT '角色名称',
`roleDesc` text DEFAULT NULL COMMENT '角色描述',
`pid` bigint DEFAULT NULL COMMENT '父级ID',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
UNIQUE(`roleName`),
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `staff_team`;
CREATE TABLE `staff_team` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`teamId` bigint NOT NULL COMMENT '团队Id',
`staffId` bigint NOT NULL COMMENT '员工ID',
`isLeader` smallint NOT NULL COMMENT '领导者(0-N 1-Y)',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `staff_role`;
CREATE TABLE `staff_role` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`roleId` bigint NOT NULL COMMENT '角色ID',
`staffId` bigint NOT NULL COMMENT '员工ID',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
