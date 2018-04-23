/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-23 09:41:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account_log
-- ----------------------------
DROP TABLE IF EXISTS `account_log`;
CREATE TABLE `account_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `house_code` varchar(255) DEFAULT NULL COMMENT '房屋产业编号',
  `house_owner` varchar(255) DEFAULT NULL COMMENT '业主姓名',
  `balance` decimal(11,2) DEFAULT NULL COMMENT '业务发生时余额',
  `trade_money` decimal(11,2) DEFAULT NULL COMMENT '变化额',
  `trade_time` datetime DEFAULT NULL COMMENT '入帐时间',
  `trade_type` int(255) DEFAULT NULL COMMENT '交易类型，1：收缴 2：返还，3：计息，4：分摊',
  `handler` varchar(50) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`),
  KEY `accuunt_log_house_code` (`house_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户变更记录表，涉及收缴登帐，基金返还，基金分摊，基金计息';
