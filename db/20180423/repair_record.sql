/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-23 18:25:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for repair_record
-- ----------------------------
DROP TABLE IF EXISTS `repair_record`;
CREATE TABLE `repair_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resideential_area` int(255) DEFAULT NULL COMMENT '小区名称',
  `address` varchar(255) DEFAULT NULL,
  `developer` varchar(255) DEFAULT NULL COMMENT '开发商',
  `property_company` varchar(50) DEFAULT NULL COMMENT '物业公司',
  `property_company_tel` varchar(255) DEFAULT NULL,
  `owners` varchar(255) DEFAULT NULL COMMENT '业委会',
  `owners_tel` varchar(255) DEFAULT NULL,
  `money_sum` decimal(11,2) DEFAULT NULL,
  `share_type` int(11) DEFAULT NULL COMMENT '分摊方式 1：按面积 2 按价格',
  `handler` varchar(50) DEFAULT NULL,
  `state` int(255) DEFAULT NULL COMMENT '登帐状态 0 未登账，1 已登帐',
  `stamp` datetime DEFAULT NULL COMMENT '受理时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维修记录';
