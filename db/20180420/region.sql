/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-20 21:10:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '区域表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of region
-- ----------------------------
INSERT INTO `region` VALUES ('2', '测试1区1');
INSERT INTO `region` VALUES ('3', '测试2区');
INSERT INTO `region` VALUES ('7', '非官方的');
INSERT INTO `region` VALUES ('8', '香坊');
INSERT INTO `region` VALUES ('9', '暗杠');
INSERT INTO `region` VALUES ('10', '道理');
INSERT INTO `region` VALUES ('11', '阿飞');
INSERT INTO `region` VALUES ('12', '道外');
INSERT INTO `region` VALUES ('13', '南岗');
INSERT INTO `region` VALUES ('14', '松北');
INSERT INTO `region` VALUES ('15', '呼兰');
INSERT INTO `region` VALUES ('16', '阿城');
INSERT INTO `region` VALUES ('17', '嘿嘿');
INSERT INTO `region` VALUES ('18', '123');
INSERT INTO `region` VALUES ('19', 'ffaaaf');
INSERT INTO `region` VALUES ('20', '222');
INSERT INTO `region` VALUES ('22', '333');
INSERT INTO `region` VALUES ('23', '555');
INSERT INTO `region` VALUES ('26', '333111222');
INSERT INTO `region` VALUES ('27', '222');
