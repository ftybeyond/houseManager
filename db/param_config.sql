-- ----------------------------
-- Table structure for param_config
-- ----------------------------
DROP TABLE IF EXISTS `param_config`;
CREATE TABLE `param_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL COMMENT '表名.字段名',
  `type_id` int(11) DEFAULT 0,
  `type_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;
