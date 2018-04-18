-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(32) NOT NULL COMMENT '密码MD5',
  `org` int(255) DEFAULT NULL COMMENT '单位',
  `role` int(255) DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;
