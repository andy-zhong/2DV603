/*
Navicat MySQL Data Transfer

Source Server         : sansir
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : greenleaves

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2019-05-31 22:11:29
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of gl_feedback
-- ----------------------------
INSERT INTO `gl_feedback` VALUES ('10', '1', '29', 'Goode.', '4', '1559310725');
INSERT INTO `gl_feedback` VALUES ('11', '3', '30', 'Good.', '4', '1559311377');
INSERT INTO `gl_feedback` VALUES ('12', '1', '30', 'I think B is better.', '5', '1559311412');
INSERT INTO `gl_feedback` VALUES ('13', '5', '31', 'First reader come here.', '3', '1559311459');
INSERT INTO `gl_feedback` VALUES ('14', '6', '31', 'The second reader come here, and I think you can get C nor B.', '4', '1559311500');
INSERT INTO `gl_feedback` VALUES ('15', '3', '31', 'No No No, I think should be B.', '5', '1559311539');
INSERT INTO `gl_feedback` VALUES ('16', '1', '31', '?????\nC', '4', '1559311565');

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
INSERT INTO `gl_member` VALUES ('1', '0', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '1', 'God', '111@qq.com', '1559311549');
INSERT INTO `gl_member` VALUES ('2', '0', 'supervisor1', 'e10adc3949ba59abbe56e057f20f883e', '2', 'Supervisor1', '', '0');
INSERT INTO `gl_member` VALUES ('3', '0', 'supervisor2', 'e10adc3949ba59abbe56e057f20f883e', '2', 'Supervisor2', '', '1559311515');
INSERT INTO `gl_member` VALUES ('4', '0', 'supervisor3', 'e10adc3949ba59abbe56e057f20f883e', '2', 'Supervisor2', '', '1559311095');
INSERT INTO `gl_member` VALUES ('5', '0', 'reader1', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader1', '', '1559311443');
INSERT INTO `gl_member` VALUES ('6', '0', 'reader2', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader2', '', '1559311469');
INSERT INTO `gl_member` VALUES ('7', '0', 'reader3', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader3', '', '0');
INSERT INTO `gl_member` VALUES ('8', '0', 'reader4', 'e10adc3949ba59abbe56e057f20f883e', '4', 'Reader4', '', '0');
INSERT INTO `gl_member` VALUES ('9', '3', 'jz222cv', 'ab71ee15ff881e571defbaffb2b9b0fa', '3', 'Jinzhe Zhao', 'jz222cv@student.lnu.se', '1559310144');
INSERT INTO `gl_member` VALUES ('10', '0', 'andy-zhong', 'e10adc3949ba59abbe56e057f20f883e', '3', 'Shishengxiong Zhong', '1111@qq.com', '1559310682');
INSERT INTO `gl_member` VALUES ('11', '0', 'sz222cu', 'e10adc3949ba59abbe56e057f20f883e', '3', 'Hailing Zhang', '', '1555777597');
INSERT INTO `gl_member` VALUES ('12', '0', 'jz222cv2', 'ab71ee15ff881e571defbaffb2b9b0fa', '3', 'Jinzhe Zhao', 'jz222cv2@student.lnu.se', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of gl_submission
-- ----------------------------
INSERT INTO `gl_submission` VALUES ('29', '9', '0', '1', 'upload/125/1559310229-Assignment2.pdf', '1559310229', '1559310725', '4');
INSERT INTO `gl_submission` VALUES ('30', '9', '3', '2', 'upload/125/1559310746-AutomataTheory1.pdf', '1559310746', '1559311412', '5');
INSERT INTO `gl_submission` VALUES ('31', '9', '3', '3', 'upload/125/1559311428-CFG.pdf', '1559311428', '1559311565', '4');

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
