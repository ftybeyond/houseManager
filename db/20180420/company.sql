/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-20 21:10:12
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
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('1', '联通', '啊哈哈', '123001230120', '1');
INSERT INTO `company` VALUES ('2', '渣渣辉', '古天乐', '123123123123', '2');
INSERT INTO `company` VALUES ('3', '软件研究院', '付天有', '23010419881019061X', '3');
