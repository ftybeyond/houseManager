/*
Navicat MySQL Data Transfer

Source Server         : localhost whf
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-04-21 11:14:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '楼栋编码',
  `residential_area` int(11) DEFAULT NULL COMMENT '所属小区',
  `units` int(11) DEFAULT NULL COMMENT '单元数(不准)',
  `has_elevator` int(1) DEFAULT NULL COMMENT '有电梯1,无电梯0(不准)',
  `has_underground` int(1) DEFAULT '0' COMMENT '有1,无0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `building_unique_index` (`name`,`residential_area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
