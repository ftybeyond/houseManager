-- ----------------------------
-- Table structure for residential_area
-- ----------------------------
DROP TABLE IF EXISTS `residentialarea`;
CREATE TABLE `residentialarea` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '小区名称',
  `company` int(11) DEFAULT NULL,
  `street` int(11) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `areaelevator` double(10,0) DEFAULT 0,
  `areanoelevator` double(10,0) DEFAULT 0,
  `nature` int(10) NOT NULL COMMENT '房屋性质 商品房、已购公用住房、旧有已购住房、旧有住房',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
