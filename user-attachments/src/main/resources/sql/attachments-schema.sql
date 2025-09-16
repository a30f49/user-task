DROP TABLE IF EXISTS `t_task_attachments`;
CREATE TABLE `t_task_attachments`(
`id` bigint(20) NOT NULL  AUTO_INCREMENT,
`title` varchar(60) NOT NULL COMMENT '附件标题',
`url` varchar(150) NOT NULL COMMENT '附件链接',
`table_name` varchar(50) NOT NULL COMMENT '所属的表',
`reference_id` bigint(20) NOT NULL COMMENT '所属表里的id',
`create_date` datetime NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
UNIQUE KEY (`url`),
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;