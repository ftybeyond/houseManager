/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-20 21:10:41
*/

SET FOREIGN_KEY_CHECKS=0;

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
  `area_elevator` decimal(10,0) DEFAULT NULL,
  `area_noelevator` decimal(10,0) DEFAULT NULL,
  `nature` int(10) DEFAULT NULL COMMENT '房屋性质 商品房、已购公用住房、旧有已购住房、旧有住房',
  PRIMARY KEY (`id`),
  KEY `residential_area_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of residential_area
-- ----------------------------
