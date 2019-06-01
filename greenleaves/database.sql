/*
Navicat MySQL Data Transfer

Source Server         : sansir
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : greenleaves

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2019-06-01 21:25:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `gl_feedback`
-- ----------------------------
DROP TABLE IF EXISTS `gl_feedback`;
CREATE TABLE `gl_feedback` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `mid` int(9) NOT NULL,
  `sid` int(9) NOT NULL,
  `content` text NOT NULL,
  `score` int(1) NOT NULL,
  `createTime` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of gl_feedback
-- ----------------------------

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
  `sid` int(9) NOT NULL DEFAULT '0',
  `membername` varchar(18) NOT NULL,
  `password` varchar(32) NOT NULL,
  `group` int(3) NOT NULL,
  `realName` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `loginTime` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of gl_member
-- ----------------------------
INSERT INTO `gl_member` VALUES ('1', '0', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', 'God', '111@qq.com', '1559388955');
INSERT INTO `gl_member` VALUES ('2', '0', 'supervisor1', 'e10adc3949ba59abbe56e057f20f883e', '2', 'Supervisor1', '', '0');
INSERT INTO `gl_member` VALUES ('3', '0', 'supervisor2', 'e10adc3949ba59abbe56e057f20f883e', '2', 'Supervisor2', '', '1559311515');
INSERT INTO `gl_member` VALUES ('4', '0', 'supervisor3', 'e10adc3949ba59abbe56e057f20f883e', '2', 'Supervisor2', '', '1559311095');
INSERT INTO `gl_member` VALUES ('5', '0', 'reader1', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader1', '', '1559311443');
INSERT INTO `gl_member` VALUES ('6', '0', 'reader2', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader2', '', '1559311469');
INSERT INTO `gl_member` VALUES ('7', '0', 'reader3', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader3', '', '0');
INSERT INTO `gl_member` VALUES ('8', '0', 'reader4', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader4', '', '0');
INSERT INTO `gl_member` VALUES ('9', '4', 'jz222cv', 'ab71ee15ff881e571defbaffb2b9b0fa', '3', 'Jinzhe Zhao', 'jz222cv@student.lnu.se', '1559389727');
INSERT INTO `gl_member` VALUES ('10', '0', 'andy-zhong', 'e10adc3949ba59abbe56e057f20f883e', '3', 'Shishengxiong Zhong', '1111@qq.com', '1559310682');
INSERT INTO `gl_member` VALUES ('11', '0', 'sz222cu', 'e10adc3949ba59abbe56e057f20f883e', '3', 'Hailing Zhang', '', '1555777597');
INSERT INTO `gl_member` VALUES ('12', '0', 'jz222cv2', 'ab71ee15ff881e571defbaffb2b9b0fa', '3', 'Jinzhe Zhao', 'jz222cv2@student.lnu.se', '1559393735');

-- ----------------------------
-- Table structure for `gl_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `gl_schedule`;
CREATE TABLE `gl_schedule` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `type` varchar(30) NOT NULL,
  `startTime` int(11) NOT NULL,
  `endTime` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of gl_schedule
-- ----------------------------
INSERT INTO `gl_schedule` VALUES ('1', 'Project description\r\n2', 'SubmitDescription', '1559222961', '1559522961');
INSERT INTO `gl_schedule` VALUES ('2', 'Project Plan', 'SubmitPlan', '1556717361', '1561783961');
INSERT INTO `gl_schedule` VALUES ('3', 'Project Report', 'SubmitReport', '1559222960', '1559623999');

-- ----------------------------
-- Table structure for `gl_submission`
-- ----------------------------
DROP TABLE IF EXISTS `gl_submission`;
CREATE TABLE `gl_submission` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `mid` int(9) NOT NULL,
  `sid` int(9) NOT NULL,
  `type` int(3) NOT NULL,
  `attachPath` varchar(100) NOT NULL,
  `submitTime` int(11) NOT NULL,
  `gradeTime` int(11) NOT NULL,
  `score` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of gl_submission
-- ----------------------------

-- ----------------------------
-- Table structure for `gl_submission_type`
-- ----------------------------
DROP TABLE IF EXISTS `gl_submission_type`;
CREATE TABLE `gl_submission_type` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of gl_submission_type
-- ----------------------------
INSERT INTO `gl_submission_type` VALUES ('1', 'Project Description', 'description');
INSERT INTO `gl_submission_type` VALUES ('2', 'Project Plan', 'plan');
INSERT INTO `gl_submission_type` VALUES ('3', 'Project Report', 'report');
