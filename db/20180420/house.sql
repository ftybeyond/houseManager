/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-20 21:10:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '房号',
  `unit` int(11) DEFAULT NULL COMMENT '单元',
  `floor` varchar(255) DEFAULT NULL COMMENT '层号',
  `code` varchar(255) DEFAULT NULL COMMENT '产业代码，小区id+楼栋name+单元name+层号name+房号name 唯一',
  `area` decimal(11,2) DEFAULT NULL COMMENT '面积',
  `has_elevator` int(1) DEFAULT NULL COMMENT '电梯标志,有1无0',
  `nature` int(1) DEFAULT 0 COMMENT '房屋性质 商品房、已购公用住房、旧有已购住房、旧有住房',
  `type` int(1) DEFAULT 0 COMMENT '住宅、商服、车库',
  `building_rise` int(1) DEFAULT NULL COMMENT '多层0高层1',
  `unit_price` decimal(24,12) DEFAULT NULL COMMENT '单价',
  `owner_name` varchar(255) DEFAULT NULL COMMENT '业主名称',
  `owner_psptid` int(10) DEFAULT NULL COMMENT '业主身份证号码',
  `account_balance` decimal(24,12) DEFAULT 0 COMMENT '账户余额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `house_unique_index` (`unit`,`floor`,`name`),
  UNIQUE KEY `house_unique_index2` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of house
-- ----------------------------
