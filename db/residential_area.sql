-- ----------------------------
-- Table structure for residential_area
-- ----------------------------
DROP TABLE IF EXISTS `residential_area`;
CREATE TABLE `residential_area` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '小区名称',
  `company` int(11) DEFAULT NULL,
  `street` int(11) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `area_elevator` double(24,12) DEFAULT 0,
  `area_noelevator` double(24,12) DEFAULT 0,
  `nature` int(10) NOT NULL COMMENT '房屋性质 商品房、已购公用住房、旧有已购住房、旧有住房',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;
