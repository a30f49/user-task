DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`(
                          `id` bigint NOT NULL  AUTO_INCREMENT,
                          `title` varchar(255) NOT NULL COMMENT '消息标题',
                          `desc` text DEFAULT NULL COMMENT '消息描述',
                          `message_type` varchar(20) NOT NULL COMMENT '消息类型',
                          `reference_id` bigint(20) NOT NULL COMMENT '关联表里的id',
                          `notice_date` datetime NOT NULL COMMENT '消息提醒时间',
                          `noticeStaffId` bigint(20) NOT NULL COMMENT '被提醒人',
                          `noticeEndUserId` bigint(20) NOT NULL COMMENT '被提醒人',
                          UNIQUE KEY (`message_type`,`reference_id`),
                          PRIMARY KEY (`id`)
)ENGINE=InnoDB;