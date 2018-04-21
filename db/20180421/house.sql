/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-21 10:45:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '小区编号+楼栋编号+单元编号+层号+房号 唯一',
  `num` varchar(255) DEFAULT NULL COMMENT '编码',
  `residential_area` int(255) DEFAULT NULL COMMENT '所属小区',
  `building` int(255) DEFAULT NULL COMMENT '楼栋',
  `unit` int(255) DEFAULT NULL COMMENT '单元',
  `floor` int(255) DEFAULT NULL COMMENT '楼层',
  `area` decimal(11,2) DEFAULT NULL COMMENT '面积',
  `elevator_sign` int(255) DEFAULT NULL COMMENT '电梯标志 无电梯、有电梯',
  `nature` int(255) DEFAULT NULL COMMENT '房屋性质 商品房、已购公用住房、旧有已购住房、旧有住房',
  `type` int(255) DEFAULT NULL COMMENT '住宅、商服、车库',
  `owner_name` varchar(255) DEFAULT NULL,
  `owner_license` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `house_code` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='房屋信息表';
