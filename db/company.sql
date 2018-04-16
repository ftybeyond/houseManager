-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '单位编码',
  `name` varchar(50) DEFAULT NULL COMMENT '单位名称',
  `legal_person_name` varchar(50) DEFAULT NULL COMMENT '法人姓名',
  `legal_person_license` varchar(18) DEFAULT NULL COMMENT '法人身份证号',
  `nature` int(255) DEFAULT NULL COMMENT '单位性质 房产局、建设单位、售房单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
