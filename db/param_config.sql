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
-- ----------------------------
-- Records of param_config
-- ----------------------------
INSERT INTO `param_config` VALUES ('1000', 'residential_area.nature', '1', '商品房');
INSERT INTO `param_config` VALUES ('1001', 'residential_area.nature', '2', '已购公用住房');
INSERT INTO `param_config` VALUES ('1002', 'residential_area.nature', '3', '旧有已购住房');
INSERT INTO `param_config` VALUES ('1003', 'residential_area.nature', '4', '旧有住房');
