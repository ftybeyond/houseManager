-- ----------------------------
-- Table structure for residential_area
-- ----------------------------
DROP TABLE IF EXISTS `unit`;
CREATE TABLE `unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '单元编号',
  `building` int(11) DEFAULT NULL,
  `total_floor` int(4) DEFAULT 0,
  `rooms_per_floor` int(2) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;
