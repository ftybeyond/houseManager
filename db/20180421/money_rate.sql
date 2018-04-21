/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-21 10:46:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for money_rate
-- ----------------------------
DROP TABLE IF EXISTS `money_rate`;
CREATE TABLE `money_rate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `term` date DEFAULT NULL COMMENT '利率期间',
  `rate` decimal(14,12) DEFAULT NULL COMMENT '利率值 保留12位有效数字',
  PRIMARY KEY (`id`),
  UNIQUE KEY `money_rate_term` (`term`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='利率表';
