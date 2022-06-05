/*
 Navicat Premium Data Transfer

 Source Server         : JavaEEdemo
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : actionmall

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 05/06/2022 10:23:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for action_address
-- ----------------------------
DROP TABLE IF EXISTS `action_address`;
CREATE TABLE `action_address`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自动增长列',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '收件人姓名',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '收件人固定电话号码',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '收件人手机号码',
  `province` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '省份',
  `city` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '城市',
  `district` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '区/县',
  `addr` varchar(300) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '详细地址',
  `zip` varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '邮编',
  `default_addr` tinyint(1) NULL DEFAULT NULL COMMENT '是否是默认地址，0-非默认；1-默认',
  `del_state` tinyint(1) NOT NULL COMMENT '是否删除，0-正常，1-已删除',
  `created` date NULL DEFAULT NULL COMMENT '创建时间',
  `updated` date NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of action_address
-- ----------------------------
INSERT INTO `action_address` VALUES (1, 1, 'testname1', '123', '123', '1', '1', '1', '1', '1', 1, 1, '2022-06-05', '2022-06-05');
INSERT INTO `action_address` VALUES (2, 2, 'testname2', '123', '123', '2', '2', '2', '2', '2', 2, 2, '2022-06-05', '2022-06-05');
INSERT INTO `action_address` VALUES (3, 3, 'testname3', '123', '123', '3', '3', '3', '3', '3', 3, 3, '2022-06-05', '2022-06-05');

-- ----------------------------
-- Table structure for action_carts
-- ----------------------------
DROP TABLE IF EXISTS `action_carts`;
CREATE TABLE `action_carts`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号，自动增长列',
  `user_id` int(11) NOT NULL COMMENT '用户Id',
  `product_id` int(11) NOT NULL COMMENT '商品Id',
  `quantity` int(20) NOT NULL COMMENT '商品数量',
  `created` date NULL DEFAULT NULL COMMENT '创建时间',
  `updated` date NULL DEFAULT NULL COMMENT '更新时间',
  `checked` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of action_carts
-- ----------------------------
INSERT INTO `action_carts` VALUES (1, 1, 10, 10, '2022-06-05', '2022-06-05', 1);
INSERT INTO `action_carts` VALUES (2, 2, 19, 5, '2022-06-05', '2022-06-05', 1);

-- ----------------------------
-- Table structure for action_order_items
-- ----------------------------
DROP TABLE IF EXISTS `action_order_items`;
CREATE TABLE `action_order_items`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单项id',
  `uid` int(11) NOT NULL COMMENT '用户编号',
  `order_no` bigint(20) NOT NULL COMMENT '所属订单编号',
  `goods_id` int(11) NOT NULL COMMENT '商品编号',
  `goods_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '商品名称',
  `icon_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '商品主图',
  `price` decimal(20, 2) NOT NULL COMMENT '商品单价',
  `quantity` int(11) NOT NULL COMMENT '购买的商品数量',
  `total_price` decimal(20, 2) NOT NULL COMMENT '订单项总价格',
  `created` date NULL DEFAULT NULL,
  `updated` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of action_order_items
-- ----------------------------
INSERT INTO `action_order_items` VALUES (19, 2, 1519095514229, 6, '锂基脂 00#', 'dizhi1', 215.80, 2, 431.78, '2022-06-05', '2022-06-05');
INSERT INTO `action_order_items` VALUES (20, 2, 1519095514229, 7, '锂基脂 00#', 'dizhi1', 215.80, 2, 431.78, '2022-06-05', '2022-06-05');
INSERT INTO `action_order_items` VALUES (21, 2, 1519099875344, 7, '锂基脂 00#', 'dizhi1', 215.80, 2, 431.78, '2022-06-05', '2022-06-05');
INSERT INTO `action_order_items` VALUES (22, 2, 1519099875344, 6, '锂基脂 00#', 'dizhi1', 215.80, 2, 431.78, '2022-06-05', '2022-06-05');
INSERT INTO `action_order_items` VALUES (23, 5, 1552995071508, 6, '锂基脂 00#', 'dizhi2', 215.80, 3, 647.67, '2022-06-05', '2022-06-05');
INSERT INTO `action_order_items` VALUES (24, 5, 1553226067154, 10, '壁挂臂级起重机', 'dizhi3', 1500000.00, 1, 1500000.00, '2022-06-05', '2022-06-05');

-- ----------------------------
-- Table structure for action_orders
-- ----------------------------
DROP TABLE IF EXISTS `action_orders`;
CREATE TABLE `action_orders`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_no` bigint(20) NOT NULL COMMENT '订单编号',
  `uid` int(11) NOT NULL COMMENT '用户编号',
  `addr_id` int(11) NOT NULL COMMENT '收货地址编号',
  `amount` decimal(20, 2) NOT NULL COMMENT '订单付款金额',
  `type` int(11) NOT NULL COMMENT '付款类型，1-在线支付；2-货到付款',
  `freight` int(11) NOT NULL COMMENT '运费',
  `status` int(11) NOT NULL COMMENT '订单状态，1-未付款；2-已付款；3-已发货；4-交易成功',
  `payment_time` date NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` date NULL DEFAULT NULL COMMENT '发货时间',
  `finish_time` date NULL DEFAULT NULL COMMENT '交易完成时间',
  `close_time` date NULL DEFAULT NULL COMMENT '交易关闭时间',
  `updated` date NULL DEFAULT NULL COMMENT '更新时间',
  `created` date NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of action_orders
-- ----------------------------
INSERT INTO `action_orders` VALUES (8, 1519095514229, 2, 4, 863.56, 1, 0, 1, '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04');
INSERT INTO `action_orders` VALUES (9, 1519099875344, 2, 4, 863.56, 2, 0, 2, '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04');
INSERT INTO `action_orders` VALUES (10, 1552995071508, 2, 4, 647.67, 1, 0, 3, '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04');
INSERT INTO `action_orders` VALUES (11, 155322606715, 5, 5, 1500000.00, 2, 0, 4, '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04');
INSERT INTO `action_orders` VALUES (12, 5, 5, 5, 5.00, 1, 0, 1, '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04');
INSERT INTO `action_orders` VALUES (13, 6, 5, 5, 6.00, 2, 0, 2, '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04', '2022-06-04');

-- ----------------------------
-- Table structure for action_params
-- ----------------------------
DROP TABLE IF EXISTS `action_params`;
CREATE TABLE `action_params`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别ID',
  `parent_id` int(11) NOT NULL COMMENT '父类ID，id为0时为根节点，为一类节点',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '父类名称',
  `sort_order` int(11) NULL DEFAULT NULL COMMENT '同类战士顺序',
  `status` tinyint(1) NOT NULL COMMENT '状态编码，1有效，0无效',
  `level` int(11) NULL DEFAULT NULL COMMENT '节点级别，顶层节点为0，依次类推',
  `created` date NULL DEFAULT NULL,
  `updated` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 10065 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of action_params
-- ----------------------------
INSERT INTO `action_params` VALUES (10023, 0, '混凝土机械', 1, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10024, 0, '建筑起重机械', 2, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10025, 0, '路面机械', 3, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10026, 0, '土方机械', 4, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10027, 0, '环卫机械', 5, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10028, 0, '工业车辆', 6, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10029, 0, '模型专区', 7, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10030, 0, '特辑专区', 8, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10031, 0, '运费-01', 9, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10032, 10023, '泵送搅拌系统', 1, 1, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10033, 10023, '油品', 2, 1, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10034, 10023, '电器元件', 3, 1, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10035, 10023, '地盘配件', 4, 1, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10036, 10023, '发动机件', 5, 1, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10037, 10023, '轮胎', 6, 1, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10038, 10032, '管阀类', 1, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10039, 10032, '易损类', 2, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10040, 10033, '防冻液', 1, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10041, 10033, '齿轮油', 2, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10042, 10033, '润滑油', 3, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10043, 10033, '液压油', 4, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10044, 10033, '锂基油', 5, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10045, 10034, '接触器', 1, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10046, 10034, '开关', 2, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10047, 10034, '继电器', 3, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10048, 10034, '遥控器', 4, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10049, 10034, '断路器', 5, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10050, 10034, '控制器', 6, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10051, 10024, '轮胎起重机', 1, 1, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10052, 10028, '内燃平衡重叉车', 1, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10053, 10052, 'Z系列', 1, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10054, 10052, 'E系列', 2, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10059, 10031, '免运费', 1, 1, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10060, 10038, 'pvc管阀', 1, 1, 3, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10061, 0, 'test', NULL, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10062, 10061, 'updatetest', NULL, 1, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10063, 0, 'test2', NULL, 1, 0, '2022-06-04', '2022-06-04');
INSERT INTO `action_params` VALUES (10064, 10063, 'updatetest', NULL, 1, 1, '2022-06-04', '2022-06-04');

-- ----------------------------
-- Table structure for action_products
-- ----------------------------
DROP TABLE IF EXISTS `action_products`;
CREATE TABLE `action_products`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '商品名称',
  `product_id` int(11) NOT NULL COMMENT '产品类型编号，对应action_params表parent_id为0',
  `parts_id` int(11) NOT NULL COMMENT '配件分类，对应action_params表parent_id非0',
  `icon_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '商品主图片',
  `sub_images` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '图片地址，一组小图',
  `detail` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '商品详情',
  `spec_param` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '规格参数',
  `price` decimal(20, 2) NOT NULL COMMENT '价格：单位元，保留两位小数',
  `stock` int(11) NOT NULL COMMENT '库存',
  `status` int(11) NOT NULL COMMENT '商品的状态：1：代售，刚保存；2：上架，在售；3：下架，未售',
  `is_hot` int(11) NOT NULL COMMENT '是否热销 1-是，2-否',
  `created` date NULL DEFAULT NULL COMMENT '创建时间',
  `updated` date NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of action_products
-- ----------------------------
INSERT INTO `action_products` VALUES (6, '锂基脂 00#', 10023, 10044, NULL, NULL, '<p>商品详情</p>', NULL, 215.89, 93, 2, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (7, '锂基脂 01#', 10023, 10044, NULL, NULL, '<p>商品详情</p>', NULL, 215.89, 96, 2, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (8, '钢架 10#', 10023, 10038, NULL, NULL, '<p>商品详情</p>', NULL, 11.00, 21, 3, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (9, '水果运费', 100031, 10059, NULL, NULL, '<p>免运费</p>', NULL, 0.00, 1000, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (10, '起重机', 10024, 10051, NULL, NULL, '<p>起重机</p>', '规格参数', 500.00, 10, 2, 1, '2022-06-03', '2022-06-04');
INSERT INTO `action_products` VALUES (11, '壁挂臂级起重机', 10024, 10051, NULL, NULL, '<p>起重机</p>', '规格参数', 500.00, 10, 1, 2, '2022-06-02', '2022-06-04');
INSERT INTO `action_products` VALUES (12, '锂基脂 00#', 10023, 10044, NULL, NULL, '<p>商品详情</p>', NULL, 215.89, 93, 2, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (13, '锂基脂 01#', 10023, 10044, NULL, NULL, '<p>商品详情</p>', NULL, 215.89, 96, 2, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (14, '钢架 10#', 10023, 10038, NULL, NULL, '<p>商品详情</p>', NULL, 11.00, 21, 3, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (15, '水果运费', 100031, 10059, NULL, NULL, '<p>免运费</p>', NULL, 0.00, 1000, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (16, '起重机', 10024, 10051, NULL, NULL, '<p>起重机</p>', '规格参数', 500.00, 10, 2, 1, '2022-06-03', '2022-06-04');
INSERT INTO `action_products` VALUES (17, '壁挂臂级起重机', 10024, 10051, NULL, NULL, '<p>起重机</p>', '规格参数', 500.00, 10, 1, 2, '2022-06-02', '2022-06-04');
INSERT INTO `action_products` VALUES (20, '锂基脂 01#', 10023, 10044, NULL, NULL, '<p>商品详情</p>', NULL, 215.89, 96, 2, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (21, '钢架 10#', 10023, 10038, NULL, NULL, '<p>商品详情</p>', NULL, 11.00, 21, 3, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (22, '水果运费', 100031, 10059, NULL, NULL, '<p>免运费</p>', NULL, 0.00, 1000, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (23, '壁挂臂级起重机', 10024, 10051, NULL, NULL, '<p>起重机</p>', '规格参数', 500.00, 10, 1, 2, '2022-06-02', '2022-06-04');
INSERT INTO `action_products` VALUES (24, '锂基脂 00#', 10023, 10044, NULL, NULL, '<p>商品详情</p>', NULL, 215.89, 93, 2, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (25, '锂基脂 01#', 10023, 10044, NULL, NULL, '<p>商品详情</p>', NULL, 215.89, 96, 2, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (26, '钢架 10#', 10023, 10038, NULL, NULL, '<p>商品详情</p>', NULL, 11.00, 21, 3, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (27, '水果运费', 100031, 10059, NULL, NULL, '<p>免运费</p>', NULL, 0.00, 1000, 1, 2, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (28, '起重机', 10024, 10051, NULL, NULL, '<p>起重机</p>', '规格参数', 500.00, 10, 2, 1, '2022-06-03', '2022-06-04');
INSERT INTO `action_products` VALUES (29, '壁挂臂级起重机', 10024, 10051, NULL, NULL, '<p>起重机</p>', '规格参数', 500.00, 10, 1, 2, '2022-06-02', '2022-06-04');
INSERT INTO `action_products` VALUES (30, '锂基脂 00#', 10023, 10044, NULL, NULL, '<p>商品详情</>', NULL, 215.89, 93, 2, 1, '2022-06-04', '2022-06-04');
INSERT INTO `action_products` VALUES (31, '水果运费', 100031, 10059, '123', NULL, '<p>免运费</p>', '规格参数', 500.00, 1, 1, 2, '2022-06-04', '2022-06-04');

-- ----------------------------
-- Table structure for action_users
-- ----------------------------
DROP TABLE IF EXISTS `action_users`;
CREATE TABLE `action_users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '密码，MD5加密',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `question` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '密码问题',
  `asw` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '找回密码答案',
  `role` int(11) NOT NULL COMMENT '角色2-管理员，1-普通用户',
  `create_time` date NOT NULL COMMENT '创建时间',
  `update_time` date NOT NULL COMMENT '更新时间',
  `age` int(11) NOT NULL COMMENT '年龄，0-120',
  `sex` tinyint(1) NOT NULL COMMENT '性别1：男，2：女',
  `del` tinyint(1) NOT NULL COMMENT '正常0、删除1',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of action_users
-- ----------------------------
INSERT INTO `action_users` VALUES (1, 'admin01', '1234', 'admin01@qq.com', '123456789', '1', '1', 2, '2022-05-25', '2022-05-29', 21, 1, 0, 'testname1');
INSERT INTO `action_users` VALUES (2, 'admin02', '1234', 'admin02@qq.com', '123456789', '2', '2', 2, '2022-05-29', '2022-05-29', 22, 2, 0, 'testname2');
INSERT INTO `action_users` VALUES (3, 'action033', '1234', 'zhangsan@souhu.com', '11112222', '3', '3', 2, '2022-05-29', '2022-06-04', 30, 0, 0, 'zhangsan');
INSERT INTO `action_users` VALUES (4, 'admin04', '1234', 'admin04@qq.com', '123456789', '4', '4', 2, '2022-05-29', '2022-06-04', 24, 1, 0, 'testname4');
INSERT INTO `action_users` VALUES (5, 'admin05', '1234', 'admin05@qq.com', '123456789', '5', '5', 2, '2022-05-29', '2022-06-04', 25, 1, 0, 'testname5');
INSERT INTO `action_users` VALUES (6, 'admin06', '1234', 'admin04@qq.com', '123456789', '4', '4', 2, '2022-05-29', '2022-06-04', 24, 1, 1, 'testname4');

SET FOREIGN_KEY_CHECKS = 1;
