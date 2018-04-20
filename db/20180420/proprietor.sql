/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-20 21:10:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for proprietor
-- ----------------------------
DROP TABLE IF EXISTS `proprietor`;
CREATE TABLE `proprietor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `license` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `residential_area` int(255) DEFAULT NULL COMMENT '小区',
  `unit` int(255) DEFAULT NULL,
  `building` int(255) DEFAULT NULL COMMENT '楼栋',
  `floor` int(255) DEFAULT NULL COMMENT '楼层',
  `house` int(255) DEFAULT NULL COMMENT '房间',
  `elevator_sign` int(255) DEFAULT NULL COMMENT '电梯标识，是否有电梯',
  `floor_type` int(255) DEFAULT NULL COMMENT '高层、多层',
  `unit_price` decimal(11,2) DEFAULT NULL COMMENT '单价',
  PRIMARY KEY (`id`),
  KEY `proprietor_name` (`name`),
  KEY `proprietor_residential_area` (`residential_area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of proprietor
-- ----------------------------
