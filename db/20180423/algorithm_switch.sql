/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-23 09:41:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for algorithm_switch
-- ----------------------------
DROP TABLE IF EXISTS `algorithm_switch`;
CREATE TABLE `algorithm_switch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '房产局名称',
  `charge_switch` int(255) DEFAULT NULL COMMENT '缴费开关',
  `pay_switch` int(255) DEFAULT NULL COMMENT '支付开关',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='缴费算法开关（按面积、按房价）';
