/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-23 09:42:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for invoice_log
-- ----------------------------
DROP TABLE IF EXISTS `invoice_log`;
CREATE TABLE `invoice_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `invoice_num` varchar(255) DEFAULT NULL COMMENT '发票号码',
  `event_type` int(255) DEFAULT NULL COMMENT '事件类型，1.新增,2修改。3作废(预留）',
  `stamp` datetime DEFAULT NULL COMMENT ' 时间戳',
  `bill` int(255) DEFAULT NULL COMMENT ' 收缴单标识',
  `handler` varchar(255) DEFAULT NULL COMMENT '处理人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发票记录，包括新增，作废，修改';
