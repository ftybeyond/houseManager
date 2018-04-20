/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-20 21:10:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '编码唯一',
  `residential_area` int(11) DEFAULT NULL COMMENT '所属小区',
  `floors` int(11) DEFAULT NULL COMMENT '楼层数',
  `units` int(11) DEFAULT NULL COMMENT '单元数',
  `houses` int(11) DEFAULT NULL COMMENT '单元户数',
  `elevator_sign` int(11) DEFAULT NULL COMMENT '电梯标志',
  `basement` int(11) DEFAULT NULL COMMENT '地下室层数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `buliding_code_index` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of building
-- ----------------------------
