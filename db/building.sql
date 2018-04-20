-- ----------------------------
-- Table structure for residential_area
-- ----------------------------
DROP TABLE IF EXISTS `residential_area`;
CREATE TABLE `residential_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '楼栋名称',
  `residential_area` int(11) DEFAULT NULL COMMENT '小区',
  `total_unit` int(4) DEFAULT 0,
  `haveElevator` int(1) DEFAULT 0,
  `haveUnderground` int(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;
