/*
Navicat MySQL Data Transfer

Source Server         : 测试
Source Server Version : 50173
Source Host           : 192.168.1.11:3306
Source Database       : gbook

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-10-17 10:32:41
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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of child
-- ----------------------------
INSERT INTO `child` VALUES ('3', '1', '啊啊', '2017-10-15 17:39:33', '192.168.1.4', '52');
INSERT INTO `child` VALUES ('4', '1', '啊啊啊啊啊', '2017-10-15 17:41:25', '192.168.1.4', '51');
INSERT INTO `child` VALUES ('5', '1', '啊啊啊', '2017-10-15 18:34:37', '192.168.1.4', '52');
INSERT INTO `child` VALUES ('6', '2', '啊啊啊啊啊啊啊啊啊', '2017-10-15 19:07:17', '192.168.1.4', '52');
INSERT INTO `child` VALUES ('7', '2', '啊啊', '2017-10-15 19:23:30', '192.168.1.4', '51');
INSERT INTO `child` VALUES ('8', '2', '啊啊啊啊啊', '2017-10-15 19:23:53', '192.168.1.4', '50');
INSERT INTO `child` VALUES ('9', '1', '啊啊', '2017-10-15 19:25:13', '192.168.1.4', '50');
INSERT INTO `child` VALUES ('10', '2', '啊啊啊啊', '2017-10-15 20:56:45', '192.168.1.4', '52');
INSERT INTO `child` VALUES ('12', '1', '啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '2017-10-15 21:30:33', '192.168.1.4', '48');
INSERT INTO `child` VALUES ('15', '1', '你好有什么问题？', '2017-10-15 21:49:05', '192.168.1.4', '52');
INSERT INTO `child` VALUES ('32', '2', '老铁得劲吗？', '2017-10-16 16:45:03', '192.168.1.4', '50');
INSERT INTO `child` VALUES ('33', '1', '老得劲了', '2017-10-16 16:45:17', '192.168.1.4', '50');
INSERT INTO `child` VALUES ('38', '8', '双击666', '2017-10-17 10:11:28', '192.168.1.4', '52');
INSERT INTO `child` VALUES ('39', '8', '啊啊啊', '2017-10-17 10:11:41', '192.168.1.4', '52');
INSERT INTO `child` VALUES ('40', '1', '没毛病', '2017-10-17 10:11:58', '192.168.1.4', '52');

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
) ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('1', '1', '故人西辞黄鹤楼，烟花三月下扬州。', '2016-10-12 12:33:06', '0:0:0:0:0:0:0:1', '0');
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
INSERT INTO `message` VALUES ('51', '1', '相看两不厌，只有敬亭山。', '2016-10-12 12:42:36', '0:0:0:0:0:0:0:1', '0');
INSERT INTO `message` VALUES ('52', '8', '我寄愁心与明月，随风直到夜郎西。', '2016-10-12 12:42:57', '0:0:0:0:0:0:0:1', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL,
  `name` char(200) DEFAULT NULL,
  `email` char(200) DEFAULT NULL,
  `passwd` char(200) DEFAULT NULL,
  `imgurl` char(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '2', '项越兄弟', '1327342025@qq.com', '12345678', 'nopic.jpg');
INSERT INTO `user` VALUES ('2', '1', '林声声', 'wangzj@outlook.com', '12345678', 'nopic.jpg');
INSERT INTO `user` VALUES ('8', '1', '是以彤啊', 'wangzj@gmail.com', '12345678', 'nopic.jpg');
INSERT INTO `user` VALUES ('9', '1', '浑沌七日死', 'wangzj@163.com', '12345678', 'nopic.jpg');
INSERT INTO `user` VALUES ('10', '1', '喵了个咪', 'wangzj@qq.com', '12345678', 'nopic.jpg');
INSERT INTO `user` VALUES ('11', '1', '是朕', 'wangzj@admin.com', '12345678', 'nopic.jpg');
