/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : cart

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-08-29 11:36:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for malfunction
-- ----------------------------
DROP TABLE IF EXISTS `malfunction`;
CREATE TABLE `malfunction` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `module` varchar(255) DEFAULT NULL,
  `type` int(11) NOT NULL COMMENT '1.故障 2.BUG 3.优化',
  `report_by` varchar(255) DEFAULT NULL,
  `follow_by` varchar(255) DEFAULT NULL,
  `solve_start_time` datetime DEFAULT NULL,
  `solve_end_time` datetime DEFAULT NULL,
  `solve_state` int(11) DEFAULT NULL,
  `solution` varchar(255) DEFAULT NULL,
  `system` int(11) NOT NULL COMMENT '1.渠道 2.POS',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of malfunction
-- ----------------------------
INSERT INTO `malfunction` VALUES ('1', 'MPOS无法支付', '用户使用MPOS刷卡无法支付', 'MPOS', '1', '陈剑', '邹超超', '2018-08-22 11:19:05', '2018-08-22 11:19:16', '2', 'USB 插口松动', '2');
INSERT INTO `malfunction` VALUES ('2', '门店账号导出', '门店账号批量导出结果空白', '数据管理', '2', '分公司', '王富金', '2018-08-20 11:20:46', '2018-08-22 11:20:50', '2', '发版，修改导出字段', '1');
INSERT INTO `malfunction` VALUES ('3', '制成品上架通知', '制成品按分公司导入，只通知了一个门店', '门店商品范围管理', '2', '商品运营部', '杨林', '2018-08-17 11:21:57', '2018-08-22 11:22:02', '2', '发版，查询分公司下所有门店，循环组装通知', '1');
INSERT INTO `malfunction` VALUES ('4', '商品销售渠道批量导入', '新加了特殊渠道编码，包含“-”，检验失败', '商品销售渠道分配', '3', '商品运营部', '杨林', '2018-08-16 11:25:27', '2018-08-22 11:25:30', '2', '发版，修改正则校验', '1');
