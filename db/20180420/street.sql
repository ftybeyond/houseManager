/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-20 21:09:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for street
-- ----------------------------
DROP TABLE IF EXISTS `street`;
CREATE TABLE `street` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `region` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of street
-- ----------------------------
INSERT INTO `street` VALUES ('1', '文昌街', '3');
INSERT INTO `street` VALUES ('3', '123123', '3');
INSERT INTO `street` VALUES ('4', '1111', '2');
INSERT INTO `street` VALUES ('5', '阿斯蒂芬', '2');

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
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('1', '联通', '啊哈哈', '123001230120', '1');
INSERT INTO `company` VALUES ('2', '渣渣辉', '古天乐', '123123123123', '2');
INSERT INTO `company` VALUES ('3', '软件研究院', '付天有', '23010419881019061X', '3');

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

-- ----------------------------
-- Table structure for proprietor
-- ----------------------------
DROP TABLE IF EXISTS `proprietor`;
CREATE TABLE `proprietor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `license` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `residential_area` int(255) DEFAULT NULL COMMENT '小区',
  `unit` int(255) DEFAULT NULL,
  `building` int(255) DEFAULT NULL COMMENT '楼栋',
  `floor` int(255) DEFAULT NULL COMMENT '楼层',
  `house` int(255) DEFAULT NULL COMMENT '房间',
  `elevator_sign` int(255) DEFAULT NULL COMMENT '电梯标识，是否有电梯',
  `floor_type` int(255) DEFAULT NULL COMMENT '高层、多层',
  `unit_price` decimal(11,2) DEFAULT NULL COMMENT '单价',
  PRIMARY KEY (`id`),
  KEY `proprietor_name` (`name`),
  KEY `proprietor_residential_area` (`residential_area`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of proprietor
-- ----------------------------

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `house_code` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of house
-- ----------------------------

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '编码唯一',
  `residential_area` int(11) DEFAULT NULL COMMENT '所属小区',
  `floors` int(11) DEFAULT NULL COMMENT '楼层数',
  `units` int(11) DEFAULT NULL COMMENT '单元数',
  `houses` int(11) DEFAULT NULL COMMENT '单元户数',
  `elevator_sign` int(11) DEFAULT NULL COMMENT '电梯标志',
  `basement` int(11) DEFAULT NULL COMMENT '地下室层数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `buliding_code_index` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of building
-- ----------------------------

-- ----------------------------
-- Table structure for unit
-- ----------------------------
DROP TABLE IF EXISTS `unit`;
CREATE TABLE `unit` (
  `id` int(11) NOT NULL,
  `residential_area` int(255) DEFAULT NULL COMMENT '所属小区',
  `code` varchar(10) DEFAULT NULL COMMENT '唯一编码',
  `floors` int(255) DEFAULT NULL COMMENT '楼层数',
  `houses` int(255) DEFAULT NULL COMMENT '楼层户数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of unit
-- ----------------------------

-- ----------------------------
-- Table structure for residential_area
-- ----------------------------
DROP TABLE IF EXISTS `residential_area`;
CREATE TABLE `residential_area` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '小区名称',
  `company` int(11) DEFAULT NULL,
  `street` int(11) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `area_elevator` decimal(10,0) DEFAULT NULL,
  `area_noelevator` decimal(10,0) DEFAULT NULL,
  `nature` int(10) NOT NULL COMMENT '房屋性质 商品房、已购公用住房、旧有已购住房、旧有住房',
  PRIMARY KEY (`id`),
  KEY `residential_area_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of residential_area
-- ----------------------------
