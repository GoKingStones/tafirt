/*
Navicat MySQL Data Transfer

Source Server         : Joanthon
Source Server Version : 50619
Source Host           : localhost:3306
Source Database       : tafirt

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2017-06-24 18:35:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zone_basic_info_table
-- ----------------------------
DROP TABLE IF EXISTS `zone_basic_info_table`;
CREATE TABLE `zone_basic_info_table` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `nation` varchar(100) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `zone_level` varchar(100) DEFAULT NULL,
  `zone_type` varchar(100) DEFAULT NULL,
  `exe_enterprise` varchar(1000) DEFAULT NULL,
  `build_enterprise` varchar(1000) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `latitude` float(255,5) DEFAULT NULL,
  `longtitude` float(255,5) DEFAULT NULL,
  `square` float(255,2) DEFAULT NULL,
  `square_detail` varchar(255) DEFAULT NULL,
  `invest` float(255,2) DEFAULT NULL,
  `invest_detail` varchar(255) DEFAULT NULL,
  `main_industry` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `mod_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zone_basic_info_table
-- ----------------------------
INSERT INTO `zone_basic_info_table` VALUES ('1', '埃及中非泰达产业园', '埃及', '天津', '国家级', '加工制造', '中非泰达投资股份有限公司', '埃及泰达投资公司（由天津泰达投资控股有限公司、天津开发区苏伊士国际合作有限公司和埃及埃中合营公司合资组建）', '埃及苏伊士湾西北经济区，紧邻苏伊士运河，距离埃及第三大港口——因苏哈那港2公里', '0.00000', '0.00000', '9.12', '总体规划面积9.12平方公里，合作区起步区面积1.34平方公里，已基本开发完成；扩展区面积6平方公里，即将开展一期项目建设招商工作', '5.00', '计划投资4.6亿美元，累计投资9066万美元；扩展区将分三期开发，预计开发建设总投资约5亿美元', '纺织服装、石油装备、高低压电器、新型建材及精细化工', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('2', '埃塞俄比亚东方工业园', '埃塞俄比亚', '江苏', '国家级', '加工制造', '江苏永元投资有限公司', '埃塞俄比亚东方工业园私营有限公司', '埃塞俄比亚奥罗米亚州杜卡姆市', '7.40258', '40.55360', '5.00', '规划面积5平方公里，已开发面积2.33平方公里。', '4.00', '计划投资4亿美元，实际投资5752万美元。', '冶金、建材、机电等', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('3', '塞拉利昂国基工贸园区', '塞拉利昂', '河南', '国家级', '加工制造', '河南国基实业集团有限公司', '塞拉利昂国基投资发展有限公司', '塞拉利昂', '8.45867', '-13.20555', '2.11', '规划面积2.11平方公里（土地租赁年限为99年）合作区建区企业投资：计划投资约 6000万美元，实际完成投资3831万美元。', '0.60', '0.6亿元人民币 ', '彩板厂、床垫厂、丝网厂、汽修厂', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('4', '尼日利亚广东经贸合作区', '尼日利亚', '广东', '国家级', '加工制造', '广东新广国际集团中非投资有限公司 ', '广东新广国际集团中非投资有限公司', '尼日利亚奥贡州伊格贝萨地区 ', '6.90021', '3.25921', '20.00', '首期规划20平方公里，起步区2.5平方公里 ', '25.00', '25亿元人民币 ', '家具、建材、陶瓷、五金、医药、电子 ', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('5', '尼日利亚莱基自由贸易区－中尼经贸合作区', '尼日利亚', '南京', '国家级', '加工制造', '中非莱基投资有限公司', '莱基自贸区开发公司', '尼日利亚拉各斯州莱基半岛地区', '6.38696', '3.40550', '30.00', '总体规划面积为30平方公里，其中一期11.79平方公里，起步区面积4.2平方公里，已开发3.12平方公里。', '7.90', '一期计划投资7.9亿美元 ，实际投资1.26亿美元。', '以装备制造、通信产品为主的高端制造业，以交通运输车辆和工程机械为主的产品装配业，以商贸物流为主的现代物流业，以旅游、宾馆酒店、商业等为主的城市服务业与房地产业。 未来的莱基自贸区规划建成一座“综合新城”，不仅有工业制造加工区块，还有商贸物流园、房地产区、创意产业园、油气仓储区和城市配套区，可涵盖社会发展的各个门类。', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('6', '巴基斯坦海尔－鲁巴经济区', '巴基斯坦', '山东', '国家级', '加工制造', '青岛海尔集团电器产业有限公司', '青岛海尔集团电器产业有限公司', '巴基斯坦拉合尔市', '31.52000', '74.46461', '2.33', '8平方公里 ', '1.29', '1.29亿美元 ', '家电、汽车、纺织、建材、化工等', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('7', '柬埔寨西哈努克港经济特区', '柬埔寨', '江苏', '国家级', '加工制造', '江苏太湖柬埔寨国际经济合作区投资有限公司', '西哈努克港经济特区有限公司', '柬埔寨西哈努克省波雷诺区4号国道212公里处，距柬埔寨唯一的国际深水港——西哈努克港12公里，距西哈努克机场3公里。', '10.16304', '104.02407', '11.13', '规划面积2.11平方公里（土地租赁年限为99年）合作区建区企业投资：计划投资约 6000万美元，实际完成投资3831万美元。', '1.51', '截止2014年5月，实际投资1.51亿美元', '纺织服装、五金机械、轻工家电等。', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('8', '毛里求斯晋非经贸合作区', '毛里求斯', '山西', '国家级', '加工制造', '山西晋非投资有限公司', '毛里求斯晋非经济贸易合作区有限公司', '毛里求斯西北部的Baie du Tombeau地区，距离毛里求斯首都路易港3.5公里，距离港口2公里。', '-20.17194', '57.51998', '2.11', '规划面积2.11平方公里（土地租赁年限为99年）合作区建区企业投资：计划投资约 6000万美元，实际完成投资3831万美元。', '0.60', '0.6亿元人民币 ', '产品加工及物流仓储、商务商贸、教育培训、房地产、旅游餐饮、绿色能源等板块。', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('9', '泰中罗勇工业园', '泰国', '浙江', '国家级', '加工制造', '华方医药科技有限公司', '泰中罗勇工业园开发有限公司', '泰国罗勇府（东部海岸安美德工业城），距曼谷114 公里，距芭堤雅市36 公里，距曼谷新国际机场99 公里，距廉差邦深海港 27 公里。', '13.70643', '100.48358', '12.00', '规划面积12平方公里，已开发4平方公里。', '2.00', '计划投资2亿美元 ，实际投资1.96 亿美元。', '汽摩配、五金、机械、电子等', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('10', '赞比亚中国经贸合作区', '赞比亚', '北京', '国家级', '加工制造', '中国有色矿业集团有限公司', '赞比亚中国经济贸易合作区发展有限公司', '合作区分为谦比希园区和卢萨卡园区，谦比希园区位于赞比亚铜带省中部，距赞比亚首都卢萨卡360公里，距赞比亚第二大城市恩多拉70公里，距赞比亚第三大城市基特韦28公里；卢萨卡园区位于赞比亚首都卢萨卡市东北部，距离市中心25公里，南侧紧邻卢萨卡国际机场', '-13.17106', '27.77638', '17.00', '总规划面积17.28平方公里，其中，谦比希园区11.58平方公里；卢萨卡园区5.7平方公里，其中一期1.63平方公里', '4.10', '谦比希园区计划投资4.1亿美元，卢萨卡园区一期计划投资9919.53万美元；实际投资1.69亿美元', '谦比希园区以铜钴开采为基础，以铜钴冶炼为核心，形成以有色金属矿冶产业群为主的主导产业；卢萨卡园区以发展现代物流业、商贸服务业、加工制造业、房地产业、配套服务业和新技术产业为主导产业', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('11', '越南中国（深圳-海防）经贸合作区', '越南', '广东', '国家级', '加工制造', '深越联合投资有限公司（由深圳中航技集团、中深国际、海王集团等7家企业合资成立）', '深越联合投资有限公司（由深圳中航技集团、中深国际、海王集团等7家企业合资成立）', '越南海防市安阳县', '20.82786', '106.69918', '8.00', '8平方公里 ', '2.00', '2亿美元 ', '纺织轻工、机械电子，医药生物等', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('12', '越南龙江工业园', '越南', '浙江', '国家级', '加工制造', '前江投资管理有限责任公司', '龙江工业园发展有限责任公司', '越南南部前江省新福县新立第一社，距胡志明市中心、国际机场及西贡港均约为50公里。', '10.79831', '106.66243', '6.00', '总体规划面积为600公顷，其中包括工业区540公顷和住宅服务区60公顷,目前工业区已开发183公顷。', '1.00', '计划总投资1亿美元，目前实际投资4169万美元', '电子、机械、轻工、建材、生物制药业、农林产品加工、橡胶、纸业、新材料、人造纤维等。', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('13', '韩国韩中工业园', '韩国', '重庆', '国家级', '加工制造', '重庆东泰华安国际投资有限公司（由重庆市地产集团、东兆长泰投资集团有限公司合资成立', '重庆东泰华安国际投资有限公司（由重庆市地产集团、东兆长泰投资集团有限公司合资成立', '韩国全罗南道务安郡', '34.83669', '126.98784', '3.96', '3.96平方公里，分三期建设，其中一期规划1.98平方公里。', '3.60', '3.6亿美元 ', '汽车、摩托车、船舶零部件，以及生物技术、物流及批发业等。', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('14', '埃塞俄比亚中非现代畜牧业循环经济工业区', '埃塞俄比亚', '河南', '国家级', '农牧业', '江苏永元投资有限公司', '新乡市币港皮业有限公司', '新乡市币港皮业有限公司', '8.01598', '38.20141', '10.00', '10', '4.40', '计划投资4.4亿美元', '畜牧业', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('15', '中国•印度尼西亚经贸合作区', '印度尼西亚', '广西', '国家级', '加工制造', '江苏永元投资有限公司', '广西农垦集团有限责任公司 ', '印度尼西亚贝卡西县绿壤国际工业中心 ', '-0.50084', '113.51614', '2.00', '规划面积2平方公里 ', '0.93', '9300万美元 ', '家用电器、精细化工、生物制药、农产品精深加工、机械制造及新材料相关产业 ', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('16', '中国印度尼西亚聚龙农业产业合作区', '印度尼西亚', '天津', '国家级', '农业制造', '江苏永元投资天津聚龙嘉华投资集团有限公司全资子公司天津市邦柱贸易有限责任公司投资开发建设公司', '印尼格拉哈公司，邦柱公司持有95%的股份', '印度尼西亚加里曼丹岛上的中加里曼丹园区、南加里曼丹园区、西加里曼丹园区、北加里曼丹园区，以及印度尼西亚苏门答腊岛楠榜省的楠榜港园区加里曼丹园区占地1.26平方公里，北加里曼丹园区占地0.31平方公里，楠榜港园区占地0.29平方公里（共计五大园区）', '0.73114', '0.73114', '4.21', '总体规划面积4.21平方公里，其中中加里曼丹园区占地1.68平方公里，南加里曼丹园区占地0.67平方公里，西', '1.15', '合作区共计完成投资11517.88万美元', '油棕种植开发、棕榈油初加工、精炼与分提、品牌油包装生产、油脂化工（脂肪酸、甘油及衍生品生产）及生物柴油提炼', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('17', '中国印尼综合产业园区青山园区', '印度尼西亚', '上海', '国家级', '工业制造', '印尼经贸合作区青山园区开发有限公司', '印尼经贸合作区青山园区开发有限公司', '印度尼西亚中苏拉威西省摩罗瓦里县', '-1.62808', '121.47153', '20.00', '2000公顷', '8.00', '8亿美元', '镍铁+不锈钢', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('18', '中俄托木斯克木材工贸合作区', '俄罗斯', '山东', '国家级', '工业制造', '中航林业有限公司，由中国航空技术国际控股有限公司、烟台西北林业有限公司和烟台经济技术开发区经销中心合资组建。', '中航林业有限公司，由中国航空技术国际控股有限公司、烟台西北林业有限公司和烟台经济技术开发区经销中心合资组建。 ', '俄罗斯托木斯克州阿西诺地区和捷古里杰特地区及克麦罗沃州马林斯克地区。 ', '58.73513', '82.01498', '6.95', '规划面积6.95平方公里，已开发1.68平方公里。', '5.30', '计划一期投资5.3亿美元，实际已投资1.77亿美元。', '森林抚育采伐业、木材深加工业、商贸物流业。', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('19', '俄罗斯乌苏里斯克经贸合作区', '俄罗斯', '黑龙江', '国家级', '加工制造', '康吉国际投资有限公司，由黑龙江省吉信工贸集团、浙江省康奈集团、温州华润公司共同组建。康吉国际投资有限公司，由黑龙江省吉信工贸集团、浙江省康奈集团、温州华润公司共同组建', '康吉国际工贸有限公司', '俄罗斯滨海边疆区乌苏里斯克市', '43.79265', '131.97285', '2.28', '规划占地面积2.28平方公里', '20.00', '规划总投资20亿元人民币，其中，基础设施及配套设施投资7亿元人民币。截至2014年5月底，实际投资1.66亿美元。', '轻工、机电（家电、电子）、木业等产业', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('20', '中俄托木斯克木材工贸合作区', '俄罗斯', '黑龙江', '国家级', '工业制造', '中航林业有限公司，由中国航空技术国际控股有限公司、烟台西北林业有限公司和烟台经济技术开发区经销中心合资组建。', '中航林业有限公司，由中国航空技术国际控股有限公司、烟台西北林业有限公司和烟台经济技术开发区经销中心合资组建。 ', '俄罗斯托木斯克州阿西诺地区和捷古里杰特地区及克麦罗沃州马林斯克地区。 ', '58.73513', '82.01498', '6.95', '规划面积6.95平方公里，已开发1.68平方公里。', '5.30', '计划一期投资5.3亿美元，实际已投资1.77亿美元。', '森林抚育采伐业、木材深加工业、商贸物流业。', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('21', '中俄现代农业产业合作区', '俄罗斯', '黑龙江', '国家级', '农业制造', '东宁华信经济贸易有限责任公司', '牡丹江市龙跃经贸有限公司，于2013年3月6日在黑龙江省牡丹江市注册登记。注册资本金6亿元人民币，由黑龙江耐力集团（黑龙江森工持股10%）、伊春新春集团和绥芬河三峡公司各持40%、30%、30%的股份合作兴建', '俄罗斯犹太自治州比罗比詹市', '0.00000', '0.00000', '0.00', '', '0.00', '', '粮食处理、仓储、养殖、加工园区，养猪场、肉牛养殖场、奶牛养殖', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('22', '乌兹别克斯坦鹏盛工业园', '乌兹别克斯', '浙江', '国家级', '加工制造', '温州市金盛贸易有限公司', '温州市金盛贸易有限公司', '乌兹别克斯坦共和国锡尔河州', '40.42880', '68.79600', '1.02', '园区占地102公顷', '0.99', '投资约9940万美元', '瓷砖、制革、制鞋、水龙头阀门、卫浴、宠物食品和肠衣制品', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('23', '塔吉克斯坦中塔（河南）农业产业科级示范园区', '塔吉克斯坦', '河南', '国家级', '农业制造', '塔吉克斯坦经研银海黄泛区农业科技有限公司和塔吉克斯坦经研银海黄泛区农业开发有限公司', '塔吉克斯坦经研银海黄泛区农业科技有限公司和塔吉克斯坦经研银海黄泛区农业开发有限公司合作区位置：乌兹别克斯坦共和国锡尔河州', '', '38.49790', '68.81000', '0.00', '', '0.00', '', '', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('24', '万象赛色塔综合开发区', '老挝', '云南', '国家级', '加工制造，商贸物流', '云南省建设投资控股集团有限公司的海外投资平台—云南省海外投资有限公司与老挝万象市政府共同出资组建', '云南省建设投资控股集团有限公司的海外投资平台—云南省海外投资有限公司与老挝万象市政府共同出资组建', '老挝首都万象市主城区东北方17公里处', '17.96650', '102.60097', '11.49', '占地11.49平方公里', '1.28', '1.28亿美元', '农副产品加工、纺织服装、五金建材、机械制造、清洁能源生产、物流商贸以及支持科技创新和中小企业创业。开发区二期及三期重点发展商贸和努力建成万象新城', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('25', '吉尔吉斯斯坦亚洲之星农业产业合作区', '吉尔吉斯斯坦', '河南', '国家级', '农业制造', '亚洲之星股份有限公司，河南贵友实业集团有限公司', '亚洲之星股份有限公司，河南贵友实业集团有限公司', '吉尔吉斯斯坦共和国楚河州楚河区伊斯克拉镇北纬42°49′42.01″、东经75°26′2.16″', '42.49000', '42.01000', '5.67', '5.67平方公里', '2.07', '', '畜禽养殖屠宰加工、食品深加工、国际贸易物流', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('26', '中国—沙特吉赞产业园', '沙特', '宁夏', '国际产能合作区', '钢铁，石油', '阿美石油公司', '阿美石油公司', '吉赞', '17.28310', '42.69424', '102.00', '', '80.00', '', '能源站、海水净化站、燃油储藏厂、发电站。 炼铝业 原材料、工业流、服务、垃圾处理、工业部件、硅处理、生物技术、农业技术', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('27', '委内瑞拉库阿科技工贸区', '委内瑞拉', '山东', '国家级', '加工制造', '山东浪潮集团有限公司', '山东浪潮集团有限公司', '委内瑞拉库阿市 ', '10.22529', '-66.78088', '5.00', '5平方公里，启动面积2.67平方公里 ', '1.00', '', '电子、家电和农业机械等', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('28', '中匈宝思德经贸合作区', '匈牙利', '山东', '国家级', '加工制造', '万华实业集团有限公司', '万华收购的匈牙利最大的化工公司宝思德化学（BorsodChem，以下简称“BC”）公司', '匈牙利东北部包尔绍德州卡辛茨巴茨卡市', '48.24638', '20.53873', '6015.00', '合作区总体规划面积6.15平方公里', '30.00', '合作区规划总投资约30亿美元，其中基础设施投资8亿美元，实现销售收入约30亿美元/年', '化工为主导产业、配套轻工和机械加工、节能环保产业等绿色产业', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('29', '匈牙利中欧商贸物流合作园区', '匈牙利', '山东', '国家级', '商贸物流', '山东帝豪国际投资有限公司', '山东帝豪国际投资有限公司', '匈牙利布达佩斯市', '47.48160', '19.04574', '9.87', '“切佩尔港物流园”和“德国不莱梅港物流园”，开发面积9.87万平方米', '2.00', '规划总投资2亿欧元', '集商品展览、展示、交易、体验、仓储、集散、物流、配送、信息处理、流通加工、办公、生活于一体', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('30', '德国帕西姆中欧空港产业园', '德国', '河南', '国家级', '商贸物流', '河南林德国际物流有限公司', '河南林德国际物流有限公司', '梅克伦堡-前波美拉尼亚州', '53.72415', '12.72943', '8.00', '园区总用地800公顷', '30.00', '规划总投资25亿欧元', '空俱乐部及休疗养度假区、商贸旅游接待服务区、机场及相关产业拓展区、机场作业区、临空物流园区、生态工业园区、综合商务核心区。其中商贸旅游接待服务区考虑将来作为免签滞留区建设。', null, null);
INSERT INTO `zone_basic_info_table` VALUES ('31', '乌克兰中乌农业科技示范园区', '乌克兰', '河南', '国家级', '农业制造', '河南黄泛区实业集团有限公司', '河南黄泛区实业集团有限公司', '', '46.40695', '31.47284', '0.00', '', '0.00', '', '农业 ', null, null);
