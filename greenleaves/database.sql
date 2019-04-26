/*
Navicat MySQL Data Transfer

Source Server         : sansir
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : greenleaves

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2019-04-21 00:56:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `gl_group`
-- ----------------------------
DROP TABLE IF EXISTS `gl_group`;
CREATE TABLE `gl_group` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of gl_group
-- ----------------------------
INSERT INTO `gl_group` VALUES ('1', 'Coordinator', 'coordinator');
INSERT INTO `gl_group` VALUES ('2', 'Supervisor', 'supervisor');
INSERT INTO `gl_group` VALUES ('3', 'Student', 'student');
INSERT INTO `gl_group` VALUES ('4', 'Reader', 'reader');

-- ----------------------------
-- Table structure for `gl_member`
-- ----------------------------
DROP TABLE IF EXISTS `gl_member`;
CREATE TABLE `gl_member` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `membername` varchar(18) NOT NULL,
  `password` varchar(32) NOT NULL,
  `group` int(3) NOT NULL,
  `realName` varchar(20) NOT NULL,
  `loginTime` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of gl_member
-- ----------------------------
INSERT INTO `gl_member` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', 'God', '1555777605');
INSERT INTO `gl_member` VALUES ('2', 'supervisor1', 'e10adc3949ba59abbe56e057f20f883e', '2', 'Supervisor1', '0');
INSERT INTO `gl_member` VALUES ('3', 'supervisor2', 'e10adc3949ba59abbe56e057f20f883e', '2', 'Supervisor2', '0');
INSERT INTO `gl_member` VALUES ('4', 'supervisor3', 'e10adc3949ba59abbe56e057f20f883e', '2', 'Supervisor2', '0');
INSERT INTO `gl_member` VALUES ('5', 'reader1', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader1', '0');
INSERT INTO `gl_member` VALUES ('6', 'reader2', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader2', '0');
INSERT INTO `gl_member` VALUES ('7', 'reader3', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader3', '0');
INSERT INTO `gl_member` VALUES ('8', 'reader4', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader4', '0');
INSERT INTO `gl_member` VALUES ('9', 'jz222cv', 'e10adc3949ba59abbe56e057f20f883e', '3', 'Jinzhe Zhao', '1555779179');
INSERT INTO `gl_member` VALUES ('10', 'andy-zhong', 'e10adc3949ba59abbe56e057f20f883e', '3', 'Shishengxiong Zhong', '1555777539');
INSERT INTO `gl_member` VALUES ('11', 'sz222cu', 'e10adc3949ba59abbe56e057f20f883e', '3', 'Hailing Zhang', '1555777597');

-- ----------------------------
-- Table structure for `gl_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `gl_schedule`;
CREATE TABLE `gl_schedule` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `startTime` int(11) NOT NULL,
  `endTime` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of gl_schedule
-- ----------------------------
