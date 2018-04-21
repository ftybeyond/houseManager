/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-21 10:45:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for charge_criterion
-- ----------------------------
DROP TABLE IF EXISTS `charge_criterion`;
CREATE TABLE `charge_criterion` (
  `id` int(11) NOT NULL,
  `term` date DEFAULT NULL COMMENT '规则有效期',
  `house_type` int(255) DEFAULT NULL COMMENT '房屋类型',
  `charge_type` int(255) DEFAULT NULL COMMENT '缴费类型 业务、建设单位、售房单位',
  `elevator_sign` int(255) DEFAULT NULL COMMENT '电梯标识',
  `price_ratio` int(255) DEFAULT NULL COMMENT '价格系数，算法是按价格时应用此列',
  `area_ratio` int(255) DEFAULT NULL COMMENT '面积系数，当算法是以面积时，应用此系数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='缴费规则';
