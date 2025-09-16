SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `workTask`;
CREATE TABLE `workTask` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`owner_by_end_user_id` bigint default NULL  ,
`create_by_end_user_id` bigint default NULL  ,
`task_type` varchar(255) NOT NULL COMMENT '事件类型',
`create_by_staff_id` bigint NOT NULL comment '创建者ID',
`task_number` varchar(26) not null comment '事件编号',
`owner_by_staff_id` bigint not NULL COMMENT 'ownerId',
`owner_by_team_id` bigint default NULL COMMENT 'ownByTeamId',
`task_name` varchar(255) NOT NULL COMMENT '事件名称',
`create_time` datetime NOT NULL COMMENT '创建时间',
`close_time` datetime default NULL COMMENT '关闭时间',
`status` VARCHAR(20) default NULL COMMENT '状态',
`priority` INT NOT NULL comment  '优先级 0/1/2 低 中 高',
`desc` text default NULL COMMENT '描述',
`start_time` datetime default NULL COMMENT '开始时间',
`notice_time` datetime default NULL COMMENT '提醒时间',
`deadline` datetime default NULL COMMENT '到期时间',
`communication_record_id` bigint default NULL COMMENT '客服记录',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
 unique(`taskNumber`),
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `task_reference`;
CREATE TABLE `task_reference` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`task_id` bigint NOT NULL COMMENT '事件ID',
`reference_name` varchar(255) NOT NULL COMMENT '参考名称',
`reference_id` varchar(26)  NOT NULL COMMENT '参考事件ID',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
UNIQUE KEY (`taskId`,`referenceName`,`referenceId`),
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `task_update`;
CREATE TABLE `task_update` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`task_id` bigint NOT NULL COMMENT '事件ID',
`staff_id` bigint NOT NULL COMMENT '员工ID',
`end_user_id` bigint NOT NULL COMMENT '终端用户id',
`note` text default NULL COMMENT '备注',
`attachment` varchar(255) default NULL COMMENT '附件',
`updateTime` datetime default NULL COMMENT '更新时间',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `task_follower`;
CREATE TABLE `task_follower` (
`id` bigint NOT NULL  AUTO_INCREMENT,
`team_id` bigint NOT NULL COMMENT '团队Id',
`task_id` bigint NOT NULL COMMENT '事件ID',
`staff_id` bigint default NULL COMMENT '员工ID',
`end_user_id` bigint NOT NULL COMMENT '终端用户id',
`org_id` bigint(20) DEFAULT NULL COMMENT '组织(部门)ID',
`version` int(11) NOT NULL DEFAULT 1 COMMENT '版本控制',
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Table structure for task_queue
-- ----------------------------
DROP TABLE IF EXISTS `task_queue`;
CREATE TABLE `task_queue`  (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
                               `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
                               `org_id` bigint(20) NULL DEFAULT NULL COMMENT '社区隔离',
                               `email_operation` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否自动发送email',
                               `sms_operation` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否自动发送短信',
                               `wechat_operation` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否自动发送公众号',
                               `pid` bigint(20) NOT NULL DEFAULT -1 COMMENT '父id',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
