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
