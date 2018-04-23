/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-23 09:42:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for repair_item
-- ----------------------------
DROP TABLE IF EXISTS `repair_item`;
CREATE TABLE `repair_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `repair_record` int(255) DEFAULT NULL COMMENT '维修记录',
  `remark` varchar(255) DEFAULT NULL,
  `price` decimal(11,2) DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='维修事项，一次维修记录关联多个维修事项';
