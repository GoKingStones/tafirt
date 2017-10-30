/*
Navicat MySQL Data Transfer

Source Server         : yjf
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : tafirt

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2017-08-17 09:57:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `url` text NOT NULL,
  `name` varchar(32) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('53', 'http://www.setc-zone.com/xwzx/', '埃及中非泰达产业园', '2017-08-16 16:40:59');
INSERT INTO `task` VALUES ('55', 'http://zccz.cnmc.com.cn/outlinetem.jsp?outlinetype=1&column_no=070401', '赞比亚中国经贸合作区', '2017-08-16 16:41:01');
INSERT INTO `task` VALUES ('57', 'http://www.e-eiz.com/news.asp?pagetitle=%E5%9B%AD%E5%8C%BA%E6%96%B0%E9%97%BB', '埃塞俄比亚东方工业园', '2017-08-16 16:41:03');
INSERT INTO `task` VALUES ('59', 'http://www.calekki.com/xwzx/newsCategoryId=9.html', '尼日利亚莱基自由贸易区－中尼经贸合作区', '2017-08-16 16:41:04');
INSERT INTO `task` VALUES ('61', 'http://www.zfnig.com/cn/News.aspx?page=1', '尼日利亚广东经贸合作区', '2017-08-16 16:41:44');
INSERT INTO `task` VALUES ('65', 'http://www.mlqsydy.com/newslist_1/1923/1942', '毛里求斯晋非经贸合作区', '2017-08-08 11:35:26');
INSERT INTO `task` VALUES ('67', 'http://www.ssez.com/News.asp?None=3', '柬埔寨西哈努克港经济特区', '2017-08-16 16:41:57');
INSERT INTO `task` VALUES ('69', 'http://www.sinothaizone.com/news.php?cid=17', '泰中罗勇工业园', '2017-08-16 16:41:58');
INSERT INTO `task` VALUES ('70', 'http://www.sinothaizone.com/news.php?cid=19', '泰中罗勇工业园', '2017-08-08 15:09:31');
INSERT INTO `task` VALUES ('71', 'http://www.sinothaizone.com/news.php?cid=6', '泰中罗勇工业园', '2017-08-08 15:09:36');
INSERT INTO `task` VALUES ('72', 'http://ljip.vn/web/zh/news-center/', '越南龙江工业园', '2017-08-08 15:26:59');
INSERT INTO `task` VALUES ('73', 'http://www.jlaicz.com/news.html', '中国印度尼西亚聚龙农业产业合作区', '2017-08-16 16:42:17');
INSERT INTO `task` VALUES ('74', 'http://www.decent-china.com/index.php/Channel/Index/id/179.html', '中国印尼综合产业园区青山园区', '2017-08-16 16:42:17');
INSERT INTO `task` VALUES ('75', 'http://www.mdjly.cn/index.php?p=news_list&lanmu=4', '俄罗斯龙跃林业经贸合作区', '2017-08-16 16:42:21');
INSERT INTO `task` VALUES ('76', 'http://www.huaxin-agri.com/cn/News.Asp', '中俄现代农业产业合作区', '2017-08-16 16:42:22');
INSERT INTO `task` VALUES ('77', 'http://www.pengshenguz.com/news/CompanyNews/', '乌兹别克斯坦鹏盛工业园', '2017-08-16 16:42:23');
INSERT INTO `task` VALUES ('78', 'http://www.pengshenguz.com/news/IndustryNews/', '乌兹别克斯坦鹏盛工业园', '2017-08-08 17:15:35');
INSERT INTO `task` VALUES ('79', 'http://www.cecz.org/list_1.html', '匈牙利中欧商贸物流合作园区', '2017-08-16 16:42:24');
INSERT INTO `task` VALUES ('80', 'http://www.mlqsydy.com/newslist/1923', '毛里求斯晋非经贸合作区', '2017-08-16 16:41:48');
INSERT INTO `task` VALUES ('82', 'http://www.baidu.com.cn/s?wd=埃及中非泰达产业园&tn=22073068_3_oem_dg', '埃及中非泰达产业园', '2017-08-16 16:34:32');
INSERT INTO `task` VALUES ('83', 'http://www.baidu.com.cn/s?wd=赞比亚中国经贸合作区&tn=22073068_3_oem_dg', '赞比亚中国经贸合作区', '2017-08-16 16:35:32');
INSERT INTO `task` VALUES ('84', 'http://www.baidu.com.cn/s?wd=埃塞俄比亚东方工业园&tn=22073068_3_oem_dg', '埃塞俄比亚东方工业园', '2017-08-16 16:35:34');
INSERT INTO `task` VALUES ('85', 'http://www.baidu.com.cn/s?wd=埃塞俄比亚中非现代畜牧业循环经济工业区&tn=22073068_3_oem_dg', '埃塞俄比亚中非现代畜牧业循环经济工业区', '2017-08-16 16:35:38');
INSERT INTO `task` VALUES ('86', 'http://www.baidu.com.cn/s?wd=尼日利亚莱基自由贸易区－中尼经贸合作区&tn=22073068_3_oem_dg', '尼日利亚莱基自由贸易区－中尼经贸合作区', '2017-08-16 16:35:47');
INSERT INTO `task` VALUES ('87', 'http://www.baidu.com.cn/s?wd=尼日利亚广东经贸合作区&tn=22073068_3_oem_dg', '尼日利亚广东经贸合作区', '2017-08-16 16:35:54');
INSERT INTO `task` VALUES ('88', 'http://www.baidu.com.cn/s?wd=毛里求斯晋非经贸合作区&tn=22073068_3_oem_dg', '毛里求斯晋非经贸合作区', '2017-08-16 16:35:58');
INSERT INTO `task` VALUES ('89', 'http://www.baidu.com.cn/s?wd=塞拉利昂国基工贸园区&tn=22073068_3_oem_dg', '塞拉利昂国基工贸园区', '2017-08-16 16:36:00');
INSERT INTO `task` VALUES ('90', 'http://www.baidu.com.cn/s?wd=柬埔寨西哈努克港经济特区&tn=22073068_3_oem_dg', '柬埔寨西哈努克港经济特区', '2017-08-16 16:36:05');
INSERT INTO `task` VALUES ('91', 'http://www.baidu.com.cn/s?wd=泰中罗勇工业园&tn=22073068_3_oem_dg', '泰中罗勇工业园', '2017-08-16 16:36:14');
INSERT INTO `task` VALUES ('92', 'http://www.baidu.com.cn/s?wd=越南龙江工业园&tn=22073068_3_oem_dg', '越南龙江工业园', '2017-08-16 16:36:21');
INSERT INTO `task` VALUES ('93', 'http://www.baidu.com.cn/s?wd=越南中国（深圳-海防）经贸合作区&tn=22073068_3_oem_dg', '越南中国（深圳-海防）经贸合作区', '2017-08-16 16:36:32');
INSERT INTO `task` VALUES ('94', 'http://www.baidu.com.cn/s?wd=巴基斯坦海尔－鲁巴经济区&tn=22073068_3_oem_dg', '巴基斯坦海尔－鲁巴经济区', '2017-08-16 16:36:35');
INSERT INTO `task` VALUES ('95', 'http://www.baidu.com.cn/s?wd=韩国韩中工业园&tn=22073068_3_oem_dg', '韩国韩中工业园', '2017-08-16 16:36:57');
INSERT INTO `task` VALUES ('96', 'http://www.baidu.com.cn/s?wd=中国•印度尼西亚经贸合作区&tn=22073068_3_oem_dg', '中国•印度尼西亚经贸合作区', '2017-08-16 16:37:04');
INSERT INTO `task` VALUES ('97', 'http://www.baidu.com.cn/s?wd=中国印度尼西亚聚龙农业产业合作区&tn=22073068_3_oem_dg', '中国印度尼西亚聚龙农业产业合作区', '2017-08-16 16:37:15');
INSERT INTO `task` VALUES ('98', 'http://www.baidu.com.cn/s?wd=中国印尼综合产业园区青山园区&tn=22073068_3_oem_dg', '中国印尼综合产业园区青山园区', '2017-08-16 16:37:21');
INSERT INTO `task` VALUES ('99', 'http://www.baidu.com.cn/s?wd=中俄托木斯克木材工贸合作区&tn=22073068_3_oem_dg', '中俄托木斯克木材工贸合作区', '2017-08-16 16:37:36');
INSERT INTO `task` VALUES ('100', 'http://www.baidu.com.cn/s?wd=俄罗斯乌苏里斯克经贸合作区&tn=22073068_3_oem_dg', '俄罗斯乌苏里斯克经贸合作区', '2017-08-16 16:37:58');
INSERT INTO `task` VALUES ('101', 'http://www.baidu.com.cn/s?wd=俄罗斯龙跃林业经贸合作区&tn=22073068_3_oem_dg', '俄罗斯龙跃林业经贸合作区', '2017-08-16 16:38:05');
INSERT INTO `task` VALUES ('102', 'http://www.baidu.com.cn/s?wd=中俄现代农业产业合作区&tn=22073068_3_oem_dg', '中俄现代农业产业合作区', '2017-08-16 16:38:07');
INSERT INTO `task` VALUES ('103', 'http://www.baidu.com.cn/s?wd=乌兹别克斯坦鹏盛工业园&tn=22073068_3_oem_dg', '乌兹别克斯坦鹏盛工业园', '2017-08-16 16:38:14');
INSERT INTO `task` VALUES ('104', 'http://www.baidu.com.cn/s?wd=塔吉克斯坦中塔（河南）农业产业科级示范园区&tn=22073068_3_oem_dg', '塔吉克斯坦中塔（河南）农业产业科级示范园区', '2017-08-16 16:38:24');
INSERT INTO `task` VALUES ('105', 'http://www.baidu.com.cn/s?wd=万象赛色塔综合开发区&tn=22073068_3_oem_dg', '万象赛色塔综合开发区', '2017-08-16 16:38:28');
INSERT INTO `task` VALUES ('106', 'http://www.baidu.com.cn/s?wd=吉尔吉斯斯坦亚洲之星农业产业合作区&tn=22073068_3_oem_dg', '吉尔吉斯斯坦亚洲之星农业产业合作区', '2017-08-16 16:38:31');
INSERT INTO `task` VALUES ('107', 'http://www.baidu.com.cn/s?wd=中国—沙特吉赞产业园&tn=22073068_3_oem_dg', '中国—沙特吉赞产业园', '2017-08-16 16:38:35');
INSERT INTO `task` VALUES ('108', 'http://www.baidu.com.cn/s?wd=委内瑞拉库阿科技工贸区&tn=22073068_3_oem_dg', '委内瑞拉库阿科技工贸区', '2017-08-16 16:38:43');
INSERT INTO `task` VALUES ('109', 'http://www.baidu.com.cn/s?wd=中匈宝思德经贸合作区&tn=22073068_3_oem_dg', '中匈宝思德经贸合作区', '2017-08-16 16:40:15');
INSERT INTO `task` VALUES ('110', 'http://www.baidu.com.cn/s?wd=匈牙利中欧商贸物流合作园区&tn=22073068_3_oem_dg', '匈牙利中欧商贸物流合作园区', '2017-08-16 16:40:19');
INSERT INTO `task` VALUES ('111', 'http://www.baidu.com.cn/s?wd=德国帕西姆中欧空港产业园&tn=22073068_3_oem_dg', '德国帕西姆中欧空港产业园', '2017-08-16 16:40:24');
INSERT INTO `task` VALUES ('112', 'http://www.baidu.com.cn/s?wd=乌克兰中乌农业科技示范园区&tn=22073068_3_oem_dg', '乌克兰中乌农业科技示范园区', '2017-08-16 16:40:50');
