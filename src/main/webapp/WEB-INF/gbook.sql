/*
Navicat MySQL Data Transfer

Source Server         : 测试
Source Server Version : 50173
Source Host           : 192.168.1.11:3306
Source Database       : gbook

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-10-23 15:12:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for child
-- ----------------------------
DROP TABLE IF EXISTS `child`;
CREATE TABLE `child` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '子内容id',
  `userid` int(11) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL COMMENT '父内容id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of child
-- ----------------------------
INSERT INTO `child` VALUES ('6', '2', '啊啊啊啊啊啊啊啊啊', '2017-10-15 19:07:17', '192.168.1.4', '52');
INSERT INTO `child` VALUES ('7', '2', '啊啊', '2017-10-15 19:23:30', '192.168.1.4', '51');
INSERT INTO `child` VALUES ('8', '2', '啊啊啊啊啊', '2017-10-15 19:23:53', '192.168.1.4', '50');
INSERT INTO `child` VALUES ('91', '43', '你疯了？', '2017-10-23 15:09:25', '127.0.0.1', '338');
INSERT INTO `child` VALUES ('92', '43', '(๑•॒̀ ູ॒•́๑)', '2017-10-23 15:10:30', '127.0.0.1', '52');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=339 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('2', '9', '孤帆远影碧空尽，唯见长江天际流。', '2016-10-12 12:34:40', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('3', '2', '床前明月光，疑是地上霜。', '2016-10-12 12:33:27', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('4', '8', '行路难！行路难！多歧路，今安在？长风破浪会有时，直挂云帆济沧海。', '2016-10-12 12:34:10', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('42', '10', '飞流直下三千尺，疑是银河落九天。', '2016-10-12 12:36:13', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('43', '10', '弃我去者，昨日之日不可留', '2016-10-12 12:37:04', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('44', '10', '朝辞白帝彩云间，千里江陵一日还。', '2016-10-12 12:37:27', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('45', '10', '两岸猿声啼不住，轻舟已过万重山。', '2016-10-12 12:41:03', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('46', '10', '日照香炉生紫烟，遥看瀑布挂前川。', '2016-10-12 12:41:11', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('47', '10', '举杯邀明月，对影成三人。', '2016-10-12 12:41:32', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('48', '10', '举头望明月，低头思故乡。', '2016-10-12 12:41:43', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('49', '10', '抽刀断水水更流，举杯消愁愁更愁。', '2016-10-12 12:41:58', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('50', '2', '两岸青山相对出，孤帆一片日边来。', '2016-10-12 12:42:18', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('52', '8', '我寄愁心与明月，随风直到夜郎西。', '2016-10-12 12:42:57', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('338', '43', '啊啊啊', '2017-10-23 14:59:15', '127.0.0.1', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT '1' COMMENT '管理员2，普通默认1',
  `name` char(200) DEFAULT NULL,
  `passwd` char(200) DEFAULT NULL,
  `imgurl` char(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', '1', '林声声', '12345678', 'upload/nopic.jpg');
INSERT INTO `user` VALUES ('8', '1', '是以彤啊', '12345678', 'upload/nopic.jpg');
INSERT INTO `user` VALUES ('9', '1', '浑沌七日死', '12345678', 'upload/nopic.jpg');
INSERT INTO `user` VALUES ('10', '1', '喵了个咪', '12345678', 'upload/nopic.jpg');
INSERT INTO `user` VALUES ('43', '2', '默会', 'o6j4w0wSwgMknXSBJ6-atD6ihxho', 'http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLZ1diafCBQt2zFBvj4EfmsFEDwEwHzbBAyWSrH7wnKlz1tnr3h2Hh1EdMegqF2SOLB07icysQFqLqA/0');
