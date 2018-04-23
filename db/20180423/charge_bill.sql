/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : qth_house

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-23 09:41:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for charge_bill
-- ----------------------------
DROP TABLE IF EXISTS `charge_bill`;
CREATE TABLE `charge_bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `building_code` int(11) DEFAULT NULL COMMENT '楼栋号',
  `house_code` varchar(255) DEFAULT NULL COMMENT '产业编码',
  `house_unit` varchar(255) DEFAULT NULL COMMENT '所在单元',
  `house_floor` varchar(255) DEFAULT NULL COMMENT '楼层',
  `house_num` varchar(255) DEFAULT NULL COMMENT '房号',
  `house_area` varchar(255) DEFAULT NULL COMMENT '面积',
  `house_owner` varchar(50) DEFAULT NULL COMMENT '业主名',
  `user_type` int(255) DEFAULT NULL COMMENT '使用方式',
  `create_time` datetime DEFAULT NULL COMMENT '收缴日期',
  `handler` varchar(255) DEFAULT NULL COMMENT '处理人,姓名',
  `actual_sum` decimal(11,2) DEFAULT NULL COMMENT '实际缴费金额',
  `invoice_num` varchar(50) DEFAULT NULL COMMENT '发票单号',
  `state` int(255) DEFAULT NULL COMMENT '收缴状态,-1 回退 0未收缴，1收缴未登帐 2收缴已登帐',
  PRIMARY KEY (`id`),
  UNIQUE KEY `charge_bill_invoice_num` (`invoice_num`),
  KEY `charge_bill_house_code` (`house_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收缴单';
