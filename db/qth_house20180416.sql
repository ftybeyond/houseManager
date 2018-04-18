/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-16 13:54:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '单位编码',
  `name` varchar(50) DEFAULT NULL COMMENT '单位名称',
  `legal_person_name` varchar(50) DEFAULT NULL COMMENT '法人姓名',
  `legal_person_license` varchar(18) DEFAULT NULL COMMENT '法人身份证号',
  `nature` int(255) DEFAULT NULL COMMENT '单位性质 房产局、建设单位、售房单位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------

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
INSERT INTO `region` VALUES ('23', '444444');
INSERT INTO `region` VALUES ('24', '123');
INSERT INTO `region` VALUES ('25', '');

-- ----------------------------
-- Table structure for street
-- ----------------------------
DROP TABLE IF EXISTS `street`;
CREATE TABLE `street` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `region` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of street
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(32) NOT NULL COMMENT '密码MD5',
  `org` int(255) DEFAULT NULL COMMENT '单位',
  `role` int(255) DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '管理员', 'E10ADC3949BA59ABBE56E057F20F883E', null, null);
