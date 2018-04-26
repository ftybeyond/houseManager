/*
Navicat MySQL Data Transfer

Source Server         : localhost whf
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-04-25 20:36:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for param_config
-- ----------------------------
DROP TABLE IF EXISTS `param_config`;
CREATE TABLE `param_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL COMMENT '表名.字段名',
  `type_id` int(11) DEFAULT '0',
  `type_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1013 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of param_config
-- ----------------------------
INSERT INTO `param_config` VALUES ('1000', 'residential_area.nature', '1', '商品房');
INSERT INTO `param_config` VALUES ('1001', 'residential_area.nature', '2', '已购公用住房');
INSERT INTO `param_config` VALUES ('1002', 'residential_area.nature', '3', '旧有已购住房');
INSERT INTO `param_config` VALUES ('1003', 'residential_area.nature', '4', '旧有住房');
INSERT INTO `param_config` VALUES ('1004', 'hasornot', '1', '有');
INSERT INTO `param_config` VALUES ('1005', 'hasornot', '0', '无');
INSERT INTO `param_config` VALUES ('1006', 'house.nature', '1', '商品房');
INSERT INTO `param_config` VALUES ('1007', 'house.nature', '2', '已购公用住房');
INSERT INTO `param_config` VALUES ('1008', 'house.nature', '3', '旧有已购住房');
INSERT INTO `param_config` VALUES ('1009', 'house.nature', '4', '旧有住房');
INSERT INTO `param_config` VALUES ('1010', 'house.type', '1', '住宅');
INSERT INTO `param_config` VALUES ('1011', 'house.type', '2', '商服');
INSERT INTO `param_config` VALUES ('1012', 'house.type', '3', '车库');
