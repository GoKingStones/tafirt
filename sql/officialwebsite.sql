/*
Navicat MySQL Data Transfer

Source Server         : yjf
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : tafirt

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2017-08-17 09:57:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for officialwebsite
-- ----------------------------
DROP TABLE IF EXISTS `officialwebsite`;
CREATE TABLE `officialwebsite` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `cooperation_name` varchar(255) NOT NULL,
  `cooperation_url` varchar(255) DEFAULT NULL,
  `news_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of officialwebsite
-- ----------------------------
INSERT INTO `officialwebsite` VALUES ('1', '埃及中非泰达产业园', 'http://www.setc-zone.com/', 'http://www.setc-zone.com/xwzx/');
INSERT INTO `officialwebsite` VALUES ('2', '赞比亚中国经贸合作区', 'http://zccz.cnmc.com.cn/', 'http://zccz.cnmc.com.cn/outlinetem.jsp?outlinetype=1&column_no=070401');
INSERT INTO `officialwebsite` VALUES ('3', '埃塞俄比亚东方工业园', 'http://www.e-eiz.com/', 'http://www.e-eiz.com/news.asp?pagetitle=%E5%9B%AD%E5%8C%BA%E6%96%B0%E9%97%BB');
INSERT INTO `officialwebsite` VALUES ('4', '埃塞俄比亚中非现代畜牧业循环经济工业区', null, null);
INSERT INTO `officialwebsite` VALUES ('5', '尼日利亚莱基自由贸易区－中尼经贸合作区', 'http://www.calekki.com/index.html', 'http://www.calekki.com/xwzx/newsCategoryId=9.html');
INSERT INTO `officialwebsite` VALUES ('6', '尼日利亚广东经贸合作区', 'http://www.zfnig.com/cn/Default.aspx', 'http://www.zfnig.com/cn/News.aspx?page=1');
INSERT INTO `officialwebsite` VALUES ('7', '毛里求斯晋非经贸合作区', 'http://www.mlqsydy.com/', 'http://www.mlqsydy.com/newslist/1923');
INSERT INTO `officialwebsite` VALUES ('8', '塞拉利昂国基工贸园区', null, null);
INSERT INTO `officialwebsite` VALUES ('9', '柬埔寨西哈努克港经济特区', 'http://www.ssez.com/index.asp', 'http://www.ssez.com/News.asp?None=3');
INSERT INTO `officialwebsite` VALUES ('10', '泰中罗勇工业园', 'http://www.sinothaizone.com/index.php', 'http://www.sinothaizone.com/news.php?cid=17,http://www.sinothaizone.com/news.php?cid=19,http://www.sinothaizone.com/news.php?cid=6');
INSERT INTO `officialwebsite` VALUES ('11', '越南龙江工业园', 'http://ljip.vn/web/zh/', 'http://ljip.vn/web/zh/news-center/');
INSERT INTO `officialwebsite` VALUES ('12', '越南中国（深圳-海防）经贸合作区', null, null);
INSERT INTO `officialwebsite` VALUES ('13', '巴基斯坦海尔－鲁巴经济区', null, null);
INSERT INTO `officialwebsite` VALUES ('14', '韩国韩中工业园', null, null);
INSERT INTO `officialwebsite` VALUES ('15', '中国•印度尼西亚经贸合作区', null, null);
INSERT INTO `officialwebsite` VALUES ('16', '中国印度尼西亚聚龙农业产业合作区', 'http://www.jlaicz.com/', 'http://www.jlaicz.com/news.html');
INSERT INTO `officialwebsite` VALUES ('17', '中国印尼综合产业园区青山园区', 'http://www.decent-china.com/', 'http://www.decent-china.com/index.php/Channel/Index/id/179.html');
INSERT INTO `officialwebsite` VALUES ('18', '中俄托木斯克木材工贸合作区', null, null);
INSERT INTO `officialwebsite` VALUES ('19', '俄罗斯乌苏里斯克经贸合作区', null, null);
INSERT INTO `officialwebsite` VALUES ('20', '俄罗斯龙跃林业经贸合作区', 'http://www.mdjly.cn/', 'http://www.mdjly.cn/index.php?p=news_list&lanmu=4');
INSERT INTO `officialwebsite` VALUES ('21', '中俄现代农业产业合作区', 'http://www.huaxin-agri.com/cn/index.Asp', 'http://www.huaxin-agri.com/cn/News.Asp');
INSERT INTO `officialwebsite` VALUES ('22', '乌兹别克斯坦鹏盛工业园', 'http://www.pengshenguz.com/', 'http://www.pengshenguz.com/news/CompanyNews/,http://www.pengshenguz.com/news/IndustryNews/');
INSERT INTO `officialwebsite` VALUES ('23', '塔吉克斯坦中塔（河南）农业产业科级示范园区', null, null);
INSERT INTO `officialwebsite` VALUES ('24', '万象赛色塔综合开发区', null, null);
INSERT INTO `officialwebsite` VALUES ('25', '吉尔吉斯斯坦亚洲之星农业产业合作区', null, null);
INSERT INTO `officialwebsite` VALUES ('26', '中国—沙特吉赞产业园', null, null);
INSERT INTO `officialwebsite` VALUES ('27', '委内瑞拉库阿科技工贸区', null, null);
INSERT INTO `officialwebsite` VALUES ('28', '中匈宝思德经贸合作区', null, null);
INSERT INTO `officialwebsite` VALUES ('29', '匈牙利中欧商贸物流合作园区', 'http://www.cecz.org/', 'http://www.cecz.org/list_1.html');
INSERT INTO `officialwebsite` VALUES ('30', '德国帕西姆中欧空港产业园', null, null);
INSERT INTO `officialwebsite` VALUES ('31', '乌克兰中乌农业科技示范园区', null, null);
