SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM staff WHERE id=1;
-- INSERT INTO `staff` VALUES ('1', 'Leader', 'Supper', null, 'Male', null, null, null, null, null, null, null, null, null, null, null, '876708082437197827', null);
INSERT INTO `staff` (`id`, `firstName`, `lastName`, `DOB`, `sex`, `postPosition`, `phone`, `email`, `weChat`, `qq`, `microBlog`, `twitter`, `note`, `avatar`, `field2`, `userId`, `org_id`, `isValid`) VALUES ('1', 'Leader', 'Supper', '2008-04-03 00:00:00', 'Male', NULL, '13192268816', '263463764@qq.com', 'afef', '3242', NULL, NULL, '', '', NULL, '876708082437197827', NULL, '1');

