/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-20 21:10:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for unit
-- ----------------------------
DROP TABLE IF EXISTS `unit`;
CREATE TABLE `unit` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '单元编号',
  `building` int(11) DEFAULT NULL COMMENT '所属楼栋',
  `code` varchar(10) DEFAULT NULL COMMENT '唯一编码',
  `total_floor` int(4) DEFAULT 0 COMMENT '楼层数(不准)',
  `house_per_floor` int(4) DEFAULT 0 COMMENT '每楼层户数(不准)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unit_unique_index` (`name`,`building`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of unit
-- ----------------------------
