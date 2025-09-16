DROP TABLE IF EXISTS `attachments`;
CREATE TABLE `attachments`(
`id` bigint(20) NOT NULL  AUTO_INCREMENT,
`name` varchar(60) NOT NULL COMMENT '附件名',
`url` varchar(150) NOT NULL COMMENT '附件url',
`table_name` varchar(50) NOT NULL COMMENT '所属的表',
`reference_id` bigint(20) NOT NULL COMMENT '所属表里的id',
`create_date` datetime NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
UNIQUE KEY (`url`),
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;