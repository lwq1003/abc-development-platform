-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.36 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.4.0.6659
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 abc 的数据库结构
CREATE
DATABASE IF NOT EXISTS `abc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE
`abc`;

-- 导出  表 abc.cfg_entity 结构
CREATE TABLE IF NOT EXISTS `cfg_entity`
(
    `module` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '模块',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `author` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '作者',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `remark` varchar
(
    400
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='实体';

-- 正在导出表  abc.cfg_entity 的数据：~10 rows (大约)
INSERT INTO `cfg_entity` (`module`, `name`, `code`, `author`, `order_no`, `remark`, `id`, `create_id`, `create_time`, `update_id`, `update_time`,
                          `version`, `delete_flag`)
VALUES ('1640314868674338817', '系统参数', 'Param', 'wqliu', '09', NULL, '1640515371261796353', '', '2023-03-28 08:45:18', '', '2023-04-19 15:13:27',
        31, 'NO'),
       ('1640315164112723970', '模块', 'Module', 'wqliu', '01', NULL, '1643422382840434689', '', '2023-04-05 09:16:44', '', '2023-04-10 09:29:12', 4,
        'NO'),
       ('1640315164112723970', '实体', 'Entity', 'wqliu', '02', NULL, '1644974663587110913', '', '2023-04-09 16:04:57', '', '2023-04-10 21:14:59', 3,
        'NO'),
       ('1640315164112723970', '实体模型', 'EntityModel', 'wqliu', '03', NULL, '1645621429420425217', '', '2023-04-11 10:54:58', '',
        '2023-04-11 10:54:58', 1, 'NO'),
       ('1640315164112723970', '实体模型属性', 'EntityModelProperty', 'wqliu', '04', NULL, '1645676041900941314', '', '2023-04-11 14:31:58', '',
        '2023-04-11 14:31:58', 1, 'NO'),
       ('1640315164112723970', '实体视图', 'EntityView', 'wqliu', '05', NULL, '1645707891298033666', '', '2023-04-11 16:38:32', '',
        '2023-04-11 16:38:32', 1, 'NO'),
       ('1640315164112723970', '视图属性', 'ViewProperty', 'wqliu', '06', NULL, '1646742577256448001', '', '2023-04-14 13:10:00', '',
        '2023-04-14 13:10:14', 2, 'NO'),
       ('1640315164112723970', '视图查询条件', 'ViewQueryCondition', 'wqliu', '07', NULL, '1647124533710229506', '', '2023-04-15 14:27:46', '',
        '2023-04-15 14:27:46', 1, 'NO'),
       ('1640315164112723970', '视图查询结果', 'ViewQueryResult', 'wqliu', '08', NULL, '1647391978438057985', '', '2023-04-16 08:10:29', '',
        '2023-04-16 08:10:29', 1, 'NO'),
       ('1640315164112723970', '视图按钮', 'ViewButton', 'wqliu', '09', NULL, '1647395514416668674', '', '2023-04-16 08:24:32', '',
        '2023-04-16 14:04:53', 2, 'NO'),
       ('1640315164112723970', '视图按钮模板', 'ViewButtonTemplate', 'wqliu', '10', NULL, '1647421061372891138', '', '2023-04-16 10:06:03', '',
        '2023-04-16 10:06:03', 1, 'NO'),
       ('1640314868674338817', '组织机构', 'Organization', 'wqliu', '01', NULL, '1648585539498070017', '', '2023-04-19 15:13:17', '',
        '2023-04-20 09:54:30', 3, 'NO'),
       ('1640314868674338817', '用户', 'User', 'wqliu', '02', NULL, '1649609916222042114', '', '2023-04-22 11:03:47', '', '2023-04-22 11:03:47', 1,
        'NO'),
       ('1640314868674338817', '用户组', 'UserGroup', 'wqliu', '03', NULL, '1650322968168935425', '', '2023-04-24 10:17:12', '',
        '2023-04-24 10:17:12', 1, 'NO'),
       ('1640314868674338817', '权限项', 'PermissionItem', 'wqliu', '05', NULL, '1650661248861237250', '', '2023-04-25 08:41:24', '',
        '2023-04-25 08:41:24', 1, 'NO'),
       ('1640314868674338817', '字典类型', 'DictionaryType', 'wqliu', '05', NULL, '1651032312736960513', '', '2023-04-26 09:15:53', '',
        '2023-04-26 15:02:31', 3, 'NO'),
       ('1640314868674338817', '字典项', 'DictionaryItem', 'wqliu', '07', NULL, '1651119964337250306', '', '2023-04-26 15:04:10', '',
        '2023-04-26 15:05:20', 2, 'NO'),
       ('1640314868674338817', '日志', 'Log', 'wqliu', '99', NULL, '1651194819556454401', '', '2023-04-26 20:01:37', '', '2023-04-26 20:01:37', 1,
        'NO');

-- 导出  表 abc.cfg_entity_model 结构
CREATE TABLE IF NOT EXISTS `cfg_entity_model`
(
    `parent_model` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'BUSINESS_MODEL' COMMENT '父级模型',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `main_model_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '是否主模型',
    `self_reference_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'NO' COMMENT '是否自关联',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `remark` varchar
(
    400
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
    `entity` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '实体',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='实体模型';

-- 正在导出表  abc.cfg_entity_model 的数据：~13 rows (大约)
INSERT INTO `cfg_entity_model` (`parent_model`, `name`, `code`, `main_model_flag`, `self_reference_flag`, `order_no`, `remark`, `entity`, `id`,
                                `create_id`, `create_time`, `update_id`, `update_time`, `version`, `delete_flag`)
VALUES ('-1', '标识模型', 'ID_MODEL', 'YES', 'NO', '02', NULL, '1589874537867948033', '1589885161452777473', '1', '2022-11-08 15:38:56', '1',
        '2022-11-08 16:12:37', 4, 'NO'),
       ('-1', '业务模型', 'BUSINESS_MODEL', 'NO', 'NO', '01', NULL, '1589874537867948033', '1589885252062326786', '1', '2022-11-08 15:39:17', '1',
        '2022-11-08 16:12:15', 3, 'NO'),
       ('BUSINESS_MODEL', '系统参数', 'Param', 'YES', 'NO', '01', '', '1640515371261796353', '1641252670496907265', '', '2023-03-30 09:35:04', '',
        '2023-04-02 19:27:27', 5, 'NO'),
       ('BUSINESS_MODEL', '模块', 'Module', 'YES', 'NO', '01', NULL, '1643422382840434689', '1643422467041087489', '', '2023-04-05 09:17:04', '',
        '2023-04-05 09:17:04', 1, 'NO'),
       ('BUSINESS_MODEL', '实体', 'Entity', 'YES', 'NO', '01', NULL, '1644974663587110913', '1644974897687994370', '', '2023-04-09 16:05:52', '',
        '2023-04-09 16:05:52', 1, 'NO'),
       ('BUSINESS_MODEL', '实体模型', 'EntityModel', 'YES', 'NO', '01', NULL, '1645621429420425217', '1645621529257443330', '', '2023-04-11 10:55:21',
        '', '2023-04-11 10:55:21', 1, 'NO'),
       ('BUSINESS_MODEL', '实体模型属性', 'EntityModelProperty', 'YES', 'NO', '01', '', '1645676041900941314', '1645681655993622529', '',
        '2023-04-11 14:54:17', '', '2023-04-11 14:54:52', 2, 'NO'),
       ('BUSINESS_MODEL', '实体视图', 'EntityView', 'YES', 'NO', '01', NULL, '1645707891298033666', '1645708089986408450', '', '2023-04-11 16:39:19',
        '', '2023-04-11 16:39:19', 1, 'NO'),
       ('BUSINESS_MODEL', '视图属性', 'ViewProperty', 'YES', 'NO', '01', NULL, '1646742577256448001', '1646742761914875905', '',
        '2023-04-14 13:10:44', '', '2023-04-14 13:10:44', 1, 'NO'),
       ('BUSINESS_MODEL', '视图查询条件', 'ViewQueryCondition', 'YES', 'NO', '01', NULL, '1647124533710229506', '1647124616879083521', '',
        '2023-04-15 14:28:05', '', '2023-04-15 14:28:05', 1, 'NO'),
       ('BUSINESS_MODEL', '视图按钮', 'ViewButton', 'YES', 'NO', '01', NULL, '1647395514416668674', '1647395514450223106', '', '2023-04-16 08:24:32',
        '', '2023-04-16 14:05:04', 2, 'NO'),
       ('BUSINESS_MODEL', '视图查询结果', 'ViewQueryResult', 'YES', 'NO', '01', NULL, '1647391978438057985', '1647398864835092482', '',
        '2023-04-16 08:37:51', '', '2023-04-16 08:37:51', 1, 'NO'),
       ('BUSINESS_MODEL', '视图按钮模板', 'ViewButtonTemplate', 'YES', 'NO', '01', NULL, '1647421061372891138', '1647421061372891139', '',
        '2023-04-16 10:06:03', '', '2023-04-16 10:06:03', 1, 'NO'),
       ('BUSINESS_MODEL', '组织机构', 'Organization', 'YES', 'YES', '01', NULL, '1648585539498070017', '1648585539498070018', '',
        '2023-04-19 15:13:17', '', '2023-04-19 15:13:55', 2, 'NO'),
       ('BUSINESS_MODEL', '用户', 'User', 'YES', 'NO', '01', NULL, '1649609916222042114', '1649609916289150977', '', '2023-04-22 11:03:47', '',
        '2023-04-22 11:03:47', 1, 'NO'),
       ('BUSINESS_MODEL', '用户组', 'UserGroup', 'YES', 'YES', '01', NULL, '1650322968168935425', '1650322968231849985', '', '2023-04-24 10:17:12',
        '', '2023-04-24 10:23:35', 2, 'NO'),
       ('BUSINESS_MODEL', '权限项', 'PermissionItem', 'YES', 'YES', '01', NULL, '1650661248861237250', '1650661248886403073', '',
        '2023-04-25 08:41:24', '', '2023-04-25 08:41:46', 2, 'NO'),
       ('BUSINESS_MODEL', '字典类型', 'DictionaryType', 'YES', 'YES', '06', NULL, '1651032312736960513', '1651032312774709250', '',
        '2023-04-26 09:15:53', '', '2023-04-26 15:05:04', 3, 'NO'),
       ('BUSINESS_MODEL', '字典项', 'DictionaryItem', 'YES', 'NO', '01', NULL, '1651119964337250306', '1651119964337250307', '',
        '2023-04-26 15:04:10', '', '2023-04-26 15:04:10', 1, 'NO'),
       ('BUSINESS_MODEL', '日志', 'Log', 'YES', 'NO', '01', NULL, '1651194819556454401', '1651194819623563266', '', '2023-04-26 20:01:37', '',
        '2023-04-26 20:01:37', 1, 'NO'),
       ('BUSINESS_MODEL', '2134', '123', 'NO', 'NO', '23', NULL, '1649609916222042114', '1652498009371467778', '', '2023-04-30 10:20:02', '',
        '2023-04-30 10:20:08', 1, 'YES');

-- 导出  表 abc.cfg_entity_model_property 结构
CREATE TABLE IF NOT EXISTS `cfg_entity_model_property`
(
    `entity_model` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '实体模型',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `data_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'STRING' COMMENT '数据类型',
    `dictionary_type` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字典类型',
    `widget_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '控件类型',
    `format_pattern` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '显示格式',
    `null_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT 'YES' COMMENT '是否可为空',
    `max_length` int
(
    11
) DEFAULT NULL COMMENT '最大长度',
    `decimal_length` int
(
    11
) DEFAULT NULL COMMENT '小数位数',
    `default_value` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '默认值',
    `unique_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'NO' COMMENT '是否唯一',
    `entity_model_property` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '唯一性参照',
    `main_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '是否主属性',
    `parent_property_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '是否上级属性',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `remark` varchar
(
    400
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='实体模型属性';

-- 正在导出表  abc.cfg_entity_model_property 的数据：~194 rows (大约)
INSERT INTO `cfg_entity_model_property` (`entity_model`, `name`, `code`, `data_type`, `dictionary_type`, `widget_type`, `format_pattern`, `null_flag`,
                                         `max_length`, `decimal_length`, `default_value`, `unique_flag`, `entity_model_property`, `main_flag`,
                                         `parent_property_flag`, `order_no`, `remark`, `id`, `create_id`, `create_time`, `update_id`, `update_time`,
                                         `version`, `delete_flag`)
VALUES ('1586180626507087873', '订单号', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', 40, NULL, NULL, 'YES', NULL, 'NO', 'NO', '01', NULL,
        '1586913225110175746', '1', '2022-10-31 10:49:31', '1', '2023-03-01 10:51:27', 6, 'NO'),
       ('1586871888444899329', '数量', 'count', 'INTEGER', NULL, '', NULL, 'YES', NULL, NULL, '10', 'NO', NULL, 'NO', 'NO', '01', NULL,
        '1586913225110175747', NULL, NULL, NULL, NULL, 1, 'NO'),
       ('1586180626507087873', '创建时间', 'orderCreateTime', 'DATETIME', NULL, 'DATETIME', 'SECOND', 'NO', NULL, NULL, NULL, 'NO', NULL, 'NO', 'NO',
        '02', NULL, '1586915332580163585', '1', '2022-10-31 10:57:53', '1', '2023-02-07 14:08:34', 3, 'NO'),
       ('1589885252062326786', '标识', 'id', 'STRING', NULL, '', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '01', NULL,
        '1589885718330519554', '1', '2022-11-08 15:41:09', '1', '2022-11-08 15:58:03', 3, 'NO'),
       ('1589885161452777473', '标识', 'id', 'STRING', NULL, '', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '01', NULL,
        '1589889432931971073', '1', '2022-11-08 15:55:54', '1', '2022-11-08 15:57:51', 2, 'NO'),
       ('1589885252062326786', '创建人', 'createId', 'STRING', NULL, '', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '02', NULL,
        '1589890137843478530', '1', '2022-11-08 15:58:42', '1', '2022-11-08 15:58:42', 1, 'NO'),
       ('1589885252062326786', '创建时间', 'createTime', 'DATETIME', NULL, '', NULL, 'YES', NULL, NULL, NULL, 'NO', NULL, 'NO', 'NO', '03', NULL,
        '1589890236006969346', '1', '2022-11-08 15:59:06', '1', '2022-11-08 15:59:06', 1, 'NO'),
       ('1589885252062326786', '更新人', 'updateId', 'STRING', NULL, '', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1589890406597701634', '1', '2022-11-08 15:59:46', '1', '2022-11-08 15:59:46', 1, 'NO'),
       ('1589885252062326786', '更新时间', 'updateTime', 'DATETIME', NULL, '', NULL, 'YES', NULL, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05', NULL,
        '1589890520162676737', '1', '2022-11-08 16:00:13', '1', '2022-11-08 16:00:13', 1, 'NO'),
       ('1589885252062326786', '版本号', 'version', 'INTEGER', NULL, '', NULL, 'YES', NULL, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1589890644460875778', '1', '2022-11-08 16:00:43', '1', '2022-11-08 16:00:43', 1, 'NO'),
       ('1589885252062326786', '删除标识', 'deleteFlag', 'STRING', 'YesOrNo', '', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '07', NULL,
        '1589890780712841217', '1', '2022-11-08 16:01:16', '1', '2022-11-08 16:01:16', 1, 'NO'),
       ('1586180626507087873', '总价', 'sumMoney', 'DECIMAL', NULL, 'TEXT', NULL, 'YES', 8, 2, NULL, 'NO', NULL, 'NO', 'NO', '03', NULL,
        '1619159882895675394', '1', '2023-01-28 10:26:13', '1', '2023-02-07 14:08:42', 4, 'NO'),
       ('1586180626507087873', '状态', 'status', 'DATA_DICTIONARY', 'OrganizationType', 'DROP_DOWN_LIST', NULL, 'YES', 32, NULL, 'GROUP', 'NO',
        'paidFlag', 'NO', 'NO', '04', NULL, '1622479932016795650', '1', '2023-02-06 14:18:55', '1', '2023-03-02 09:17:52', 22, 'NO'),
       ('1586180626507087873', '是否已支付', 'paidFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'YES', 32, NULL, 'NO', 'NO', NULL,
        'NO', 'NO', '06', NULL, '1622487205371944961', '1', '2023-02-06 14:47:49', '1', '2023-03-02 08:46:38', 9, 'NO'),
       ('1641252670496907265', '213', '123', 'STRING', 'LeaveType', 'TEXT', NULL, 'YES', 123, 123, '123123', 'NO', 'createId', 'NO', 'NO', '21', NULL,
        '1641602605134151681', '', '2023-03-31 08:45:35', '', '2023-04-02 19:27:39', 8, 'YES'),
       ('1641252670496907265', '参数名称', 'paramName', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, '', 'YES', NULL, 'YES', 'NO', '01', NULL,
        '1642489599813009410', '', '2023-04-02 19:30:11', '', '2023-04-17 20:55:28', 5, 'NO'),
       ('1641252670496907265', '参数编码', 'paramKey', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', NULL, 'NO', 'NO', '02', NULL,
        '1642489832533966849', '', '2023-04-02 19:31:07', '', '2023-04-02 19:31:07', 1, 'NO'),
       ('1641252670496907265', '参数值', 'paramValue', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '03', NULL,
        '1642490004357824513', '', '2023-04-02 19:31:48', '', '2023-04-02 19:31:48', 1, 'NO'),
       ('1641252670496907265', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1642490207102091266', '', '2023-04-02 19:32:36', '', '2023-04-17 20:46:19', 2, 'NO'),
       ('1643422467041087489', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'app', 'YES', 'NO', '01', NULL,
        '1643423090310467585', '', '2023-04-05 09:19:33', '', '2023-04-15 14:59:37', 9, 'NO'),
       ('1643422467041087489', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'app', 'NO', 'NO', '02', NULL,
        '1643423206220058625', '', '2023-04-05 09:20:00', '', '2023-04-15 14:07:06', 4, 'NO'),
       ('1643422467041087489', '应用', 'app', 'DATA_DICTIONARY', 'AppCode', 'DROP_DOWN_LIST', NULL, 'NO', 32, NULL, '', 'NO', NULL, 'NO', 'NO', '00',
        NULL, '1643423490958774273', '', '2023-04-05 09:21:08', '', '2023-04-09 20:36:41', 4, 'NO'),
       ('1643422467041087489', '缩略码', 'abbreviation', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'app', 'NO', 'NO', '04', NULL,
        '1643424185774592001', '', '2023-04-05 09:23:54', '', '2023-04-15 14:07:25', 4, 'NO'),
       ('1643422467041087489', '包路径', 'packagePath', 'STRING', NULL, 'TEXT', NULL, 'NO', 400, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05', NULL,
        '1643424499596611586', '', '2023-04-05 09:25:09', '', '2023-04-05 21:15:34', 2, 'NO'),
       ('1643422467041087489', '排序号', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1643424729658380289', '', '2023-04-05 09:26:04', '', '2023-04-05 21:30:48', 2, 'NO'),
       ('1643422467041087489', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'YES', 400, NULL, '', 'NO', NULL, 'NO', 'NO', '07', NULL,
        '1643424943832125442', '', '2023-04-05 09:26:55', '', '2023-04-05 09:26:55', 1, 'NO'),
       ('1644974897687994370', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', NULL, 'YES', 'NO', '01', NULL,
        '1644975372596453378', '', '2023-04-09 16:07:46', '', '2023-04-09 16:07:46', 1, 'NO'),
       ('1644974897687994370', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', NULL, 'NO', 'NO', '02', NULL,
        '1644975606256934914', '', '2023-04-09 16:08:41', '', '2023-04-09 16:08:41', 1, 'NO'),
       ('1644974897687994370', '模块', 'module', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '00', NULL,
        '1644975921492434945', '', '2023-04-09 16:09:57', '', '2023-04-10 08:28:45', 2, 'NO'),
       ('1644974897687994370', '作者', 'author', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '03', NULL,
        '1644976304180731906', '', '2023-04-09 16:11:28', '', '2023-04-09 16:12:08', 2, 'NO'),
       ('1644974897687994370', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1644976450247368706', '', '2023-04-09 16:12:03', '', '2023-04-09 16:12:03', 1, 'NO'),
       ('1644974897687994370', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'YES', 400, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05', NULL,
        '1644976588357410817', '', '2023-04-09 16:12:36', '', '2023-04-09 16:12:36', 1, 'NO'),
       ('1645621529257443330', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'entity', 'YES', 'NO', '01', NULL,
        '1645622150295453698', '', '2023-04-11 10:57:49', '', '2023-04-12 13:42:33', 2, 'NO'),
       ('1645708089986408450', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'entityModel', 'YES', 'NO', '01', NULL,
        '1645622150295453699', '', '2023-04-11 10:57:49', '', '2023-04-12 19:49:41', 2, 'NO'),
       ('1645621529257443330', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'entity', 'NO', 'NO', '02', NULL,
        '1645622456970379265', '', '2023-04-11 10:59:03', '', '2023-04-12 13:42:44', 2, 'NO'),
       ('1645708089986408450', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'entityModel', 'NO', 'NO', '02', NULL,
        '1645622456970379269', '', '2023-04-11 10:59:03', '', '2023-04-12 19:49:50', 2, 'NO'),
       ('1645621529257443330', '父级模型', 'parentModel', 'DATA_DICTIONARY', 'BaseModel', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL,
        'BUSINESS_MODEL', 'NO', NULL, 'NO', 'NO', '00', NULL, '1645622999063199746', '', '2023-04-11 11:01:12', '', '2023-04-12 09:59:29', 2, 'NO'),
       ('1645708089986408450', '实体模型', 'entityModel', 'ENTITY', 'BaseModel', 'ENTITY_SELECT', NULL, 'NO', 32, NULL, '', 'NO', NULL, 'NO', 'NO',
        '001', NULL, '1645622999063199749', '', '2023-04-11 11:01:12', '', '2023-04-17 13:34:07', 5, 'NO'),
       ('1645621529257443330', '实体', 'entity', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '99', NULL,
        '1645623606536830978', '', '2023-04-11 11:03:37', '', '2023-04-11 11:03:37', 1, 'NO'),
       ('1645708089986408450', '实体', 'entity', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '99', NULL,
        '1645623606536830979', '', '2023-04-11 11:03:37', '', '2023-04-11 11:03:37', 1, 'NO'),
       ('1645621529257443330', '是否主模型', 'mainModelFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'YES', 'NO',
        NULL, 'NO', 'NO', '03', NULL, '1645623866491404290', '', '2023-04-11 11:04:39', '', '2023-04-12 09:59:00', 2, 'NO'),
       ('1645708089986408450', '视图类型', 'entityViewType', 'DATA_DICTIONARY', 'EntityViewType', 'DROP_DOWN_LIST', NULL, 'NO', 32, NULL, '', 'NO',
        NULL, 'NO', 'NO', '00', NULL, '1645623866491404299', '', '2023-04-11 11:04:39', '', '2023-04-13 08:08:06', 4, 'NO'),
       ('1645621529257443330', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1645624036293607425', '', '2023-04-11 11:05:19', '', '2023-04-11 11:05:19', 1, 'NO'),
       ('1645708089986408450', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1645624036293607429', '', '2023-04-11 11:05:19', '', '2023-04-11 11:05:19', 1, 'NO'),
       ('1645621529257443330', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'YES', 400, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05', NULL,
        '1645624138655596545', '', '2023-04-11 11:05:44', '', '2023-04-11 11:05:44', 1, 'NO'),
       ('1645708089986408450', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'YES', 400, NULL, NULL, 'NO', NULL, 'NO', 'NO', '95', NULL,
        '1645624138655596549', '', '2023-04-11 11:05:44', '', '2023-04-11 20:19:33', 2, 'NO'),
       ('1646742761914875905', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'view', 'YES', 'NO', '01', NULL,
        '1645688020975771641', '', '2023-04-11 15:19:34', '', '2023-04-14 19:53:21', 3, 'NO'),
       ('1645681655993622529', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'entityModel', 'YES', 'NO', '01', NULL,
        '1645688020975771649', '', '2023-04-11 15:19:34', '', '2023-04-15 15:06:01', 5, 'NO'),
       ('1646742761914875905', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '00', '',
        '1645688370042527741', '', '2023-04-11 15:20:57', '', '2023-04-14 19:53:00', 2, 'NO'),
       ('1645681655993622529', '实体模型', 'entityModel', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '00', '',
        '1645688370042527745', '', '2023-04-11 15:20:57', '', '2023-04-11 15:20:57', 1, 'NO'),
       ('1646742761914875905', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'view', 'NO', 'NO', '02', NULL,
        '1645688630668189691', '', '2023-04-11 15:22:00', '', '2023-04-14 19:53:27', 4, 'NO'),
       ('1645681655993622529', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'entityModel', 'NO', 'NO', '02', NULL,
        '1645688630668189698', '', '2023-04-11 15:22:00', '', '2023-04-11 15:24:27', 3, 'NO'),
       ('1647398864835092482', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 32, NULL, 'STRING',
        'NO', NULL, 'NO', 'NO', '03', NULL, '1645689167123865112', '', '2023-04-11 15:24:08', '', '2023-04-11 15:24:08', 1, 'NO'),
       ('1645681655993622529', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 32, NULL, 'STRING',
        'NO', NULL, 'NO', 'NO', '03', NULL, '1645689167123865601', '', '2023-04-11 15:24:08', '', '2023-04-15 15:00:18', 2, 'NO'),
       ('1646742761914875905', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 32, NULL, 'STRING',
        'NO', NULL, 'NO', 'NO', '03', NULL, '1645689167123865602', '', '2023-04-11 15:24:08', '', '2023-04-11 15:24:08', 1, 'NO'),
       ('1647124616879083521', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 32, NULL, 'STRING',
        'NO', NULL, 'NO', 'NO', '03', NULL, '1645689167123865612', '', '2023-04-11 15:24:08', '', '2023-04-11 15:24:08', 1, 'NO'),
       ('1647398864835092482', '是否显示', 'showFlag', 'DATA_DICTIONARY', 'ShowControl', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'YES', 'NO',
        NULL, 'NO', 'NO', '09', NULL, '1645689568124493111', '', '2023-04-11 15:25:43', '', '2023-04-14 19:58:01', 2, 'NO'),
       ('1647398864835092482', '是否支持排序', 'sortableFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'YES', 'NO',
        NULL, 'NO', 'NO', '06', NULL, '1645689568124493112', '', '2023-04-11 15:25:43', '', '2023-04-14 19:58:01', 2, 'NO'),
       ('1647124616879083521', '是否显示', 'showFlag', 'DATA_DICTIONARY', 'ShowControl', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'YES', 'NO',
        NULL, 'NO', 'NO', '10', NULL, '1645689568124493811', '', '2023-04-11 15:25:43', '', '2023-04-14 19:58:01', 2, 'NO'),
       ('1646742761914875905', '是否显示', 'showFlag', 'DATA_DICTIONARY', 'ShowControl', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'YES', 'NO',
        NULL, 'NO', 'NO', '11', NULL, '1645689568124493821', '', '2023-04-11 15:25:43', '', '2023-04-14 19:58:01', 2, 'NO'),
       ('1645681655993622529', '是否唯一', 'uniqueFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO', 'NO', NULL,
        'NO', 'NO', '11', NULL, '1645689568124493826', '', '2023-04-11 15:25:43', '', '2023-04-11 15:25:43', 1, 'NO'),
       ('1647124616879083521', '匹配规则', 'matchRule', 'DATA_DICTIONARY', 'TextPatternRule', 'DROP_DOWN_LIST', NULL, 'YES', 32, NULL, 'LK', 'NO',
        NULL, 'NO', 'NO', '06', NULL, '1645689757509902311', '', '2023-04-11 15:26:28', '', '2023-04-15 17:30:36', 5, 'NO'),
       ('1646742761914875905', '是否必填', 'requireFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO', 'NO', NULL,
        'NO', 'NO', '13', NULL, '1645689757509902331', '', '2023-04-11 15:26:28', '', '2023-04-14 19:58:59', 3, 'NO'),
       ('1645681655993622529', '是否主属性', 'mainFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO', 'NO', NULL,
        'NO', 'NO', '13', NULL, '1645689757509902337', '', '2023-04-11 15:26:28', '', '2023-04-15 08:21:13', 3, 'NO'),
       ('1646742761914875905', '唯一性参照', 'uniqueReference', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, '', 'NO', NULL, 'NO', 'NO', '12', NULL,
        '1645690203184062461', '', '2023-04-11 15:28:15', '', '2023-04-14 20:00:27', 2, 'YES'),
       ('1645681655993622529', '唯一性参照', 'entityModelProperty', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, '', 'NO', NULL, 'NO', 'NO', '12',
        NULL, '1645690203184062465', '', '2023-04-11 15:28:15', '', '2023-04-15 14:39:37', 5, 'NO'),
       ('1647398864835092482', '宽度', 'width', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, '120', 'NO', NULL, 'NO', 'NO', '05', NULL,
        '1645690601441615111', '', '2023-04-11 15:29:49', '', '2023-04-24 08:38:10', 4, 'NO'),
       ('1647124616879083521', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05', NULL,
        '1645690601441615811', '', '2023-04-11 15:29:49', '', '2023-04-11 15:29:49', 1, 'NO'),
       ('1646742761914875905', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05', NULL,
        '1645690601441615871', '', '2023-04-11 15:29:49', '', '2023-04-11 15:29:49', 1, 'NO'),
       ('1645681655993622529', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05', NULL,
        '1645690601441615874', '', '2023-04-11 15:29:49', '', '2023-04-11 15:29:49', 1, 'NO'),
       ('1647398864835092482', '格式化方法', 'formatFunction', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '07',
        NULL, '1645690804215242111', '', '2023-04-11 15:30:38', '', '2023-04-11 15:30:38', 1, 'NO'),
       ('1647124616879083521', '显示格式', 'formatPattern', 'DATA_DICTIONARY', 'DatetimeFormat', 'DROP_DOWN_LIST', NULL, 'YES', 32, NULL, '', 'NO',
        NULL, 'NO', 'NO', '07', NULL, '1645690804215242711', '', '2023-04-11 15:30:38', '', '2023-04-24 09:53:11', 2, 'NO'),
       ('1646742761914875905', '显示格式', 'formatPattern', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1645690804215242751', '', '2023-04-11 15:30:38', '', '2023-04-11 15:30:38', 1, 'NO'),
       ('1645681655993622529', '显示格式', 'formatPattern', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1645690804215242754', '', '2023-04-11 15:30:38', '', '2023-04-11 15:30:38', 1, 'NO'),
       ('1647398864835092482', '是否悬浮显示', 'showOverflowTooltipFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL,
        'YES', 'NO', NULL, 'NO', 'NO', '08', NULL, '1645691084067594112', '', '2023-04-11 15:31:45', '', '2023-04-14 19:56:57', 2, 'NO'),
       ('1647124616879083521', '是否只读', 'readonlyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'YES', 'NO',
        NULL, 'NO', 'NO', '09', NULL, '1645691084067594212', '', '2023-04-11 15:31:45', '', '2023-04-14 19:56:57', 2, 'NO'),
       ('1645681655993622529', '是否可为空', 'nullFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'YES', 32, NULL, 'YES', 'NO', NULL,
        'NO', 'NO', '10', NULL, '1645691084067594241', '', '2023-04-11 15:31:45', '', '2023-04-15 08:18:39', 2, 'NO'),
       ('1646742761914875905', '是否只读', 'readonlyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO', 'NO', NULL,
        'NO', 'NO', '07', NULL, '1645691084067594242', '', '2023-04-11 15:31:45', '', '2023-04-15 21:03:35', 3, 'NO'),
       ('1646742761914875905', '最大长度', 'maxLength', 'INTEGER', NULL, 'TEXT', NULL, 'YES', 10, NULL, NULL, 'NO', NULL, 'NO', 'NO', '08', NULL,
        '1645691432207409151', '', '2023-04-11 15:33:08', '', '2023-04-14 20:00:10', 1, 'YES'),
       ('1645681655993622529', '最大长度', 'maxLength', 'INTEGER', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '07', NULL,
        '1645691432207409153', '', '2023-04-11 15:33:08', '', '2023-04-15 08:17:31', 4, 'NO'),
       ('1645681655993622529', '小数位数', 'decimalLength', 'INTEGER', NULL, 'TEXT', NULL, 'YES', 10, NULL, NULL, 'NO', NULL, 'NO', 'NO', '08', NULL,
        '1645691589271511041', '', '2023-04-11 15:33:45', '', '2023-04-15 08:18:14', 2, 'NO'),
       ('1646742761914875905', '小数位数', 'decimalLength', 'INTEGER', NULL, 'TEXT', NULL, 'YES', 10, NULL, NULL, 'NO', NULL, 'NO', 'NO', '09', NULL,
        '1645691589271511042', '', '2023-04-11 15:33:45', '', '2023-04-14 20:00:10', 1, 'YES'),
       ('1647398864835092482', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1645691900321095512', '', '2023-04-11 15:34:59', '', '2023-04-11 15:34:59', 1, 'NO'),
       ('1647124616879083521', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1645691900321095612', '', '2023-04-11 15:34:59', '', '2023-04-11 15:34:59', 1, 'NO'),
       ('1645681655993622529', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1645691900321095681', '', '2023-04-11 15:34:59', '', '2023-04-11 15:34:59', 1, 'NO'),
       ('1646742761914875905', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1645691900321095682', '', '2023-04-11 15:34:59', '', '2023-04-11 15:34:59', 1, 'NO'),
       ('1646742761914875905', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '10', NULL,
        '1645692101890957312', '', '2023-04-11 15:35:47', '', '2023-04-11 15:35:47', 1, 'NO'),
       ('1645681655993622529', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '09', NULL,
        '1645692101890957313', '', '2023-04-11 15:35:47', '', '2023-04-15 08:18:04', 3, 'NO'),
       ('1647124616879083521', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '08', NULL,
        '1645692101890957322', '', '2023-04-11 15:35:47', '', '2023-04-11 15:35:47', 1, 'NO'),
       ('1647398864835092482', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '10', NULL,
        '1645692208115900121', '', '2023-04-11 15:36:13', '', '2023-04-11 15:36:13', 1, 'NO'),
       ('1646742761914875905', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '14', NULL,
        '1645692208115900411', '', '2023-04-11 15:36:13', '', '2023-04-11 15:36:13', 1, 'NO'),
       ('1645681655993622529', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '14', NULL,
        '1645692208115900418', '', '2023-04-11 15:36:13', '', '2023-04-11 15:36:13', 1, 'NO'),
       ('1647124616879083521', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '11', NULL,
        '1645692208115900421', '', '2023-04-11 15:36:13', '', '2023-04-11 15:36:13', 1, 'NO'),
       ('1646742761914875905', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'YES', 400, NULL, NULL, 'NO', NULL, 'NO', 'NO', '15', NULL,
        '1645692325359280121', '', '2023-04-11 15:36:41', '', '2023-04-14 20:00:19', 1, 'YES'),
       ('1645681655993622529', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'YES', 400, NULL, NULL, 'NO', NULL, 'NO', 'NO', '15', NULL,
        '1645692325359280129', '', '2023-04-11 15:36:41', '', '2023-04-11 15:36:41', 1, 'NO'),
       ('1645708089986408450', '初始化前', 'beforeInit', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 2000, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05',
        NULL, '1645763817140932609', '', '2023-04-11 20:20:45', '', '2023-04-11 20:20:45', 1, 'NO'),
       ('1645708089986408450', '初始化后', 'afterInit', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 2000, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1645763955628462082', '', '2023-04-11 20:21:19', '', '2023-04-11 20:21:19', 1, 'NO'),
       ('1647124616879083521', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '00', NULL,
        '1647125184968200193', '', '2023-04-15 14:30:21', '', '2023-04-15 14:30:21', 1, 'NO'),
       ('1647398864835092482', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '00', NULL,
        '1647125184968200293', '', '2023-04-15 14:30:21', '', '2023-04-15 14:30:21', 1, 'NO'),
       ('1647398864835092482', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'view', 'YES', 'NO', '01', NULL,
        '1647125321656373149', '', '2023-04-15 14:30:53', '', '2023-04-15 15:54:07', 3, 'NO'),
       ('1647124616879083521', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'view', 'YES', 'NO', '01', NULL,
        '1647125321656373249', '', '2023-04-15 14:30:53', '', '2023-04-15 15:54:07', 3, 'NO'),
       ('1645681655993622529', '123', '213', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, '33', 'NO', NULL, 'NO', 'NO', '34', NULL,
        '1647134235382845442', '', '2023-04-15 15:06:19', '', '2023-04-15 15:06:25', 1, 'YES'),
       ('1647398864835092482', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'view', 'NO', 'NO', '02', NULL,
        '1647146023394549161', '', '2023-04-15 15:53:09', '', '2023-04-15 15:53:57', 2, 'NO'),
       ('1647124616879083521', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'view', 'NO', 'NO', '02', NULL,
        '1647146023394549761', '', '2023-04-15 15:53:09', '', '2023-04-15 15:53:57', 2, 'NO'),
       ('1647421061372891139', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'buttonType', 'YES', 'NO', '01', NULL,
        '1647424303293059071', '', '2023-04-16 10:18:56', '', '2023-04-16 10:20:43', 2, 'NO'),
       ('1647395514450223106', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'buttonType', 'YES', 'NO', '01', NULL,
        '1647424303293059074', '', '2023-04-16 10:18:56', '', '2023-04-16 10:20:43', 2, 'NO'),
       ('1647421061372891139', '按钮类型', 'buttonType', 'DATA_DICTIONARY', 'ViewButtonType', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'LIST_PAGE',
        'NO', NULL, 'NO', 'NO', '00', NULL, '1647424672144347131', '', '2023-04-16 10:20:24', '', '2023-04-16 10:20:24', 1, 'NO'),
       ('1647395514450223106', '按钮类型', 'buttonType', 'DATA_DICTIONARY', 'ViewButtonType', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'LIST_PAGE',
        'NO', NULL, 'NO', 'NO', '00', NULL, '1647424672144347137', '', '2023-04-16 10:20:24', '', '2023-04-16 10:39:21', 3, 'NO'),
       ('1647395514450223106', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'buttonType', 'NO', 'NO', '02',
        '同时作为方法名称', '1647425338371788801', '', '2023-04-16 10:23:03', '', '2023-04-16 10:23:03', 1, 'NO'),
       ('1647421061372891139', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'buttonType', 'NO', 'NO', '02',
        '同时作为方法名称', '1647425338371788802', '', '2023-04-16 10:23:03', '', '2023-04-16 10:23:03', 1, 'NO'),
       ('1647421061372891139', '内容', 'content', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 2000, NULL, NULL, 'NO', NULL, 'NO', 'NO', '03', NULL,
        '1647425543620055041', '', '2023-04-16 10:23:52', '', '2023-04-16 10:23:52', 1, 'NO'),
       ('1647395514450223106', '内容', 'content', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 2000, NULL, NULL, 'NO', NULL, 'NO', 'NO', '03', NULL,
        '1647425543620055042', '', '2023-04-16 10:23:52', '', '2023-04-16 10:23:52', 1, 'NO'),
       ('1647421061372891139', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1647425796259762171', '', '2023-04-16 10:24:52', '', '2023-04-16 10:24:52', 1, 'NO'),
       ('1647395514450223106', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1647425796259762178', '', '2023-04-16 10:24:52', '', '2023-04-16 10:24:52', 1, 'NO'),
       ('1647421061372891139', '是否需确认', 'confirmFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO', 'NO',
        NULL, 'NO', 'NO', '05', NULL, '1647426049113378811', '', '2023-04-16 10:25:52', '', '2023-04-16 10:25:52', 1, 'NO'),
       ('1647395514450223106', '是否需确认', 'confirmFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO', 'NO',
        NULL, 'NO', 'NO', '05', NULL, '1647426049113378818', '', '2023-04-16 10:25:52', '', '2023-04-16 10:25:52', 1, 'NO'),
       ('1647421061372891139', '确认信息', 'confirmMessage', 'STRING', NULL, 'TEXT', NULL, 'YES', 256, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1647426280521519101', '', '2023-04-16 10:26:48', '', '2023-04-16 10:26:48', 1, 'NO'),
       ('1647395514450223106', '确认信息', 'confirmMessage', 'STRING', NULL, 'TEXT', NULL, 'YES', 256, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1647426280521519106', '', '2023-04-16 10:26:48', '', '2023-04-16 10:26:48', 1, 'NO'),
       ('1647421061372891139', '是否控制权限', 'permissionFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'YES',
        'NO', NULL, 'NO', 'NO', '07', NULL, '1647426507907321851', '', '2023-04-16 10:27:42', '', '2023-04-16 10:27:42', 1, 'NO'),
       ('1647395514450223106', '是否控制权限', 'permissionFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'YES',
        'NO', NULL, 'NO', 'NO', '07', NULL, '1647426507907321857', '', '2023-04-16 10:27:42', '', '2023-04-16 10:27:42', 1, 'NO'),
       ('1647421061372891139', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '08', NULL,
        '1647426662136074241', '', '2023-04-16 10:28:19', '', '2023-04-16 10:28:19', 1, 'NO'),
       ('1647395514450223106', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '08', NULL,
        '1647426662136074242', '', '2023-04-16 10:28:19', '', '2023-04-16 10:28:19', 1, 'NO'),
       ('1647421061372891139', '是否用于更多', 'moreFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO', 'NO', NULL,
        'NO', 'NO', '09', NULL, '1647426944911855611', '', '2023-04-16 10:29:26', '', '2023-04-16 10:29:26', 1, 'NO'),
       ('1647395514450223106', '是否用于更多', 'moreFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO', 'NO', NULL,
        'NO', 'NO', '09', NULL, '1647426944911855617', '', '2023-04-16 10:29:26', '', '2023-04-16 10:29:26', 1, 'NO'),
       ('1647421061372891139', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '10', NULL,
        '1647427215809368061', '', '2023-04-16 10:30:31', '', '2023-04-16 10:30:31', 1, 'NO'),
       ('1647395514450223106', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '10', NULL,
        '1647427215809368066', '', '2023-04-16 10:30:31', '', '2023-04-16 10:30:31', 1, 'NO'),
       ('1647395514450223106', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '0', NULL,
        '1647429218618888194', '', '2023-04-16 10:38:28', '', '2023-04-16 10:39:29', 3, 'NO'),
       ('1645708089986408450', '保存前', 'beforeSave', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 2000, NULL, NULL, 'NO', NULL, 'NO', 'NO', '08', NULL,
        '1647797519014440961', '', '2023-04-17 11:01:58', '', '2023-04-17 11:08:55', 3, 'NO'),
       ('1645708089986408450', '保存后', 'afterSave', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 2000, NULL, NULL, 'NO', NULL, 'NO', 'NO', '09', NULL,
        '1647797519085744130', '', '2023-04-17 11:01:58', '', '2023-04-17 11:08:44', 2, 'NO'),
       ('1645708089986408450', '验证数据', 'validateData', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 2000, NULL, NULL, 'NO', NULL, 'NO', 'NO', '07',
        NULL, '1647799334942654465', '', '2023-04-17 11:09:11', '', '2023-04-17 11:09:51', 2, 'NO'),
       ('1646742761914875905', '显示表达式', 'showExpression', 'STRING', NULL, 'TEXT', NULL, 'YES', 1000, NULL, NULL, 'NO', NULL, 'NO', 'NO', '111',
        NULL, '1647922461773250561', '', '2023-04-17 19:18:26', '', '2023-04-17 19:18:26', 1, 'NO'),
       ('1645621529257443330', '是否自关联', 'selfReferenceFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO',
        'NO', NULL, 'NO', 'NO', '031', NULL, '1648524099051327489', '', '2023-04-19 11:09:08', '', '2023-04-19 13:15:56', 2, 'NO'),
       ('1645681655993622529', '是否上级属性', 'parentPropertyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO',
        'NO', NULL, 'NO', 'NO', '131', NULL, '1648524391859884034', '', '2023-04-19 11:10:18', '', '2023-04-19 13:37:41', 3, 'NO'),
       ('1648585539498070018', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'organization', 'YES', 'NO', '01', NULL,
        '1648586520407363586', '', '2023-04-19 15:17:10', '', '2023-04-19 16:49:59', 2, 'NO'),
       ('1648585539498070018', '上级组织', 'organization', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'YES', '00',
        NULL, '1648587463316262913', '', '2023-04-19 15:20:55', '', '2023-04-20 09:54:48', 4, 'NO'),
       ('1648585539498070018', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'YES', NULL, 'NO', 'NO', '02', NULL,
        '1648587613325545474', '', '2023-04-19 15:21:31', '', '2023-04-19 16:50:32', 3, 'NO'),
       ('1648585539498070018', '类型', 'type', 'DATA_DICTIONARY', 'OrganizationType', 'DROP_DOWN_LIST', NULL, 'NO', 32, NULL, 'DEPARTMENT', 'NO',
        NULL, 'NO', 'NO', '03', NULL, '1648588308422451201', '', '2023-04-19 15:24:17', '', '2023-04-19 15:24:17', 1, 'NO'),
       ('1648585539498070018', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'DROP_DOWN_LIST', NULL, 'NO', 32, NULL, 'NORMAL', 'NO', NULL, 'NO',
        'NO', '04', NULL, '1648588401405976577', '', '2023-04-19 15:24:39', '', '2023-04-19 15:25:05', 2, 'NO'),
       ('1648585539498070018', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1648588641177559042', '', '2023-04-19 15:25:36', '', '2023-04-19 16:41:57', 3, 'NO'),
       ('1648585539498070018', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '08', NULL,
        '1648588661377327105', '', '2023-04-19 15:25:41', '', '2023-04-19 16:41:36', 3, 'NO'),
       ('1645708089986408450', '通用参数变更', 'commonParamChange', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 2000, NULL, NULL, 'NO', NULL, 'NO', 'NO',
        '10', NULL, '1648921779992846338', '', '2023-04-20 13:29:23', '', '2023-04-20 13:31:51', 2, 'NO'),
       ('1649609916289150977', '组织机构', 'organization', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'YES', '00',
        NULL, '1649610435753701377', '', '2023-04-22 11:05:51', '', '2023-04-22 11:05:51', 1, 'NO'),
       ('1649609916289150977', '姓名', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'YES', 'NO', '01', NULL,
        '1649610545443139585', '', '2023-04-22 11:06:17', '', '2023-04-22 11:06:17', 1, 'NO'),
       ('1649609916289150977', '账号', 'account', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', NULL, 'NO', 'NO', '02', NULL,
        '1649610684324933633', '', '2023-04-22 11:06:50', '', '2023-04-22 11:06:50', 1, 'NO'),
       ('1649609916289150977', '密码', 'password', 'STRING', NULL, 'TEXT', NULL, 'YES', 256, NULL, NULL, 'NO', NULL, 'NO', 'NO', '03', NULL,
        '1649610853263110146', '', '2023-04-22 11:07:30', '', '2023-04-23 20:44:57', 3, 'NO'),
       ('1649609916289150977', '性别', 'gender', 'DATA_DICTIONARY', 'Gender', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, '', 'NO', NULL, 'NO', 'NO',
        '04', NULL, '1649610920032235522', '', '2023-04-22 11:07:46', '', '2023-04-22 11:08:38', 2, 'NO'),
       ('1649609916289150977', '出生日期', 'birthday', 'DATETIME', NULL, 'DATETIME', 'DAY', 'YES', NULL, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05',
        NULL, '1649612349723668481', '', '2023-04-22 11:13:27', '', '2023-04-24 08:14:46', 2, 'NO'),
       ('1649609916289150977', '手机号', 'telephone', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1649677559281012737', '', '2023-04-22 15:32:34', '', '2023-04-22 15:32:34', 1, 'NO'),
       ('1649609916289150977', '邮箱', 'email', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '07', NULL,
        '1649677650888806402', '', '2023-04-22 15:32:56', '', '2023-04-22 15:32:56', 1, 'NO'),
       ('1649609916289150977', '状态', 'status', 'DATA_DICTIONARY', 'UserStatus', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NORMAL', 'NO', NULL,
        'NO', 'NO', '08', NULL, '1649677899292266497', '', '2023-04-22 15:33:55', '', '2023-04-22 15:33:55', 1, 'NO'),
       ('1649609916289150977', '强制修改密码', 'forceChangePasswordFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL,
        'YES', 'NO', NULL, 'NO', 'NO', '09', NULL, '1649678284044161026', '', '2023-04-22 15:35:27', '', '2023-04-22 15:50:26', 3, 'NO'),
       ('1649609916289150977', '失败登录次数', 'failLoginCount', 'INTEGER', NULL, 'TEXT', NULL, 'YES', NULL, NULL, '0', 'NO', NULL, 'NO', 'NO', '10',
        NULL, '1649679375603388418', '', '2023-04-22 15:39:47', '', '2023-04-23 13:39:46', 19, 'NO'),
       ('1649609916289150977', '锁定时间', 'lockTime', 'DATETIME', NULL, 'DATETIME', 'SECOND', 'YES', NULL, NULL, NULL, 'NO', NULL, 'NO', 'NO', '12',
        NULL, '1649743254933008385', '', '2023-04-22 19:53:37', '', '2023-04-22 19:53:58', 2, 'NO'),
       ('1649609916289150977', '职位', 'position', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '071', NULL,
        '1649743892781789185', '', '2023-04-22 19:56:09', '', '2023-04-22 19:57:01', 3, 'NO'),
       ('1649609916289150977', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '13', NULL,
        '1649744234793725953', '', '2023-04-22 19:57:31', '', '2023-04-22 19:57:31', 1, 'NO'),
       ('1645708089986408450', '主参照视图', 'mainReferenceViewFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NO',
        'NO', NULL, 'NO', 'NO', '021', NULL, '1649932040396677122', '', '2023-04-23 08:23:47', '', '2023-04-23 08:23:47', 1, 'NO'),
       ('1650322968231849985', '上级', 'userGroup', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'YES', '00', NULL,
        '1650324541225873409', '', '2023-04-24 10:23:27', '', '2023-04-24 10:23:27', 1, 'NO'),
       ('1650322968231849985', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'userGroup', 'YES', 'NO', '01', NULL,
        '1650332755292106754', '', '2023-04-24 10:56:05', '', '2023-04-24 10:56:05', 1, 'NO'),
       ('1650322968231849985', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'YES', NULL, 'NO', 'NO', '02', NULL,
        '1650332952499892225', '', '2023-04-24 10:56:52', '', '2023-04-24 14:50:50', 2, 'NO'),
       ('1650322968231849985', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NORMAL', 'NO', NULL, 'NO',
        'NO', '03', NULL, '1650334887042920450', '', '2023-04-24 11:04:33', '', '2023-04-24 11:04:33', 1, 'NO'),
       ('1650322968231849985', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1650336007735783426', '', '2023-04-24 11:09:01', '', '2023-04-24 11:09:01', 1, 'NO'),
       ('1650322968231849985', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'YES', 256, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05', NULL,
        '1650336421550010370', '', '2023-04-24 11:10:39', '', '2023-04-24 11:10:39', 1, 'NO'),
       ('1650661248886403073', '上级', 'permissionItem', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'YES', '00',
        NULL, '1650662204160110594', '', '2023-04-25 08:45:12', '', '2023-04-25 08:45:12', 1, 'NO'),
       ('1650661248886403073', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'permissionItem', 'YES', 'NO', '01', NULL,
        '1650662693555695617', '', '2023-04-25 08:47:09', '', '2023-04-25 08:47:09', 1, 'NO'),
       ('1650661248886403073', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'permissionItem', 'NO', 'NO', '02', NULL,
        '1650662897361121282', '', '2023-04-25 08:47:57', '', '2023-04-25 08:47:57', 1, 'NO'),
       ('1650661248886403073', '类型', 'type', 'DATA_DICTIONARY', 'PermissionType', 'DROP_DOWN_LIST', NULL, 'NO', 32, NULL, '', 'NO', NULL, 'NO',
        'NO', '03', NULL, '1650663344075468802', '', '2023-04-25 08:49:44', '', '2023-04-25 08:49:44', 1, 'NO'),
       ('1650661248886403073', '组件', 'component', 'STRING', NULL, 'TEXT', NULL, 'YES', 256, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1650663641778778113', '', '2023-04-25 08:50:55', '', '2023-04-25 13:25:30', 5, 'NO'),
       ('1650661248886403073', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '07', NULL,
        '1650663796783476737', '', '2023-04-25 08:51:32', '', '2023-04-25 13:25:37', 3, 'NO'),
       ('1650661248886403073', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'YES', 256, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1650664259385847809', '', '2023-04-25 08:53:22', '', '2023-04-25 13:25:20', 4, 'NO'),
       ('1650661248886403073', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'YES', 32, NULL, 'NORMAL', 'NO', NULL, 'NO',
        'NO', '08', NULL, '1650664776916824066', '', '2023-04-25 08:55:25', '', '2023-04-25 13:25:43', 3, 'NO'),
       ('1650661248886403073', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '09', NULL,
        '1650664950456152066', '', '2023-04-25 08:56:07', '', '2023-04-25 13:25:50', 2, 'NO'),
       ('1650661248886403073', '视图编码', 'viewCode', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05', NULL,
        '1650731471526195201', '', '2023-04-25 13:20:27', '', '2023-04-25 13:25:14', 3, 'NO'),
       ('1651032312774709250', '上级', 'dictionaryType', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'YES', '00',
        NULL, '1651032757303824385', '', '2023-04-26 09:17:39', '', '2023-04-26 13:52:11', 3, 'NO'),
       ('1651032312774709250', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', '', 'YES', 'NO', '01', NULL,
        '1651032968331841538', '', '2023-04-26 09:18:29', '', '2023-04-26 09:19:38', 2, 'NO'),
       ('1651032312774709250', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', NULL, 'NO', 'NO', '02', NULL,
        '1651033204693454849', '', '2023-04-26 09:19:25', '', '2023-04-26 09:19:25', 1, 'NO'),
       ('1651032312774709250', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '03', NULL,
        '1651033614082691073', '', '2023-04-26 09:21:03', '', '2023-04-26 09:21:03', 1, 'NO'),
       ('1651119964337250307', '字典类型', 'dictionaryType', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', 32, NULL, NULL, 'NO', NULL, 'NO', 'YES',
        '00', NULL, '1651125953451036674', '', '2023-04-26 15:27:58', '', '2023-04-26 15:27:58', 1, 'NO'),
       ('1651119964337250307', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'dictionaryType', 'YES', 'NO', '01', NULL,
        '1651126062750404609', '', '2023-04-26 15:28:24', '', '2023-04-26 15:28:24', 1, 'NO'),
       ('1651119964337250307', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', 32, NULL, NULL, 'YES', 'dictionaryType', 'NO', 'NO', '02', NULL,
        '1651126168451059714', '', '2023-04-26 15:28:50', '', '2023-04-26 15:28:50', 1, 'NO'),
       ('1651119964337250307', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04', NULL,
        '1651129534535208961', '', '2023-04-26 15:42:12', '', '2023-04-26 15:42:12', 1, 'NO'),
       ('1651119964337250307', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', 32, NULL, 'NORMAL', 'NO', NULL, 'NO',
        'NO', '03', NULL, '1651133850163654658', '', '2023-04-26 15:59:21', '', '2023-04-26 19:26:57', 2, 'NO'),
       ('1651194819623563266', '内容', 'content', 'STRING', NULL, 'TEXT', NULL, 'YES', 256, NULL, NULL, 'NO', NULL, 'YES', 'NO', '01', NULL,
        '1651195361158541313', '', '2023-04-26 20:03:46', '', '2023-04-26 20:24:44', 2, 'NO'),
       ('1651194819623563266', '日志类型', 'logType', 'DATA_DICTIONARY', 'LogType', 'DROP_DOWN_LIST', NULL, 'YES', 32, NULL, '', 'NO', NULL, 'NO',
        'NO', '02', NULL, '1651195689744510977', '', '2023-04-26 20:05:05', '', '2023-04-26 20:05:05', 1, 'NO'),
       ('1651194819623563266', '请求时间', 'requestTime', 'DATETIME', NULL, 'DATETIME', 'SECOND', 'YES', NULL, NULL, NULL, 'NO', NULL, 'NO', 'NO',
        '03', NULL, '1651195923140751362', '', '2023-04-26 20:06:00', '', '2023-04-26 20:06:00', 1, 'NO'),
       ('1651194819623563266', '请求参数', 'requestParam', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 5120, NULL, NULL, 'NO', NULL, 'NO', 'NO', '04',
        NULL, '1651196147913502721', '', '2023-04-26 20:06:54', '', '2023-04-26 20:06:54', 1, 'NO'),
       ('1651194819623563266', '请求路径', 'requestPath', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 1024, NULL, NULL, 'NO', NULL, 'NO', 'NO', '05',
        NULL, '1651196324275597313', '', '2023-04-26 20:07:36', '', '2023-04-26 20:07:36', 1, 'NO'),
       ('1651194819623563266', '请求方法', 'requestMethod', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '06', NULL,
        '1651196735250280450', '', '2023-04-26 20:09:14', '', '2023-04-26 20:09:14', 1, 'NO'),
       ('1651194819623563266', '操作人标识', 'operatorId', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '07', NULL,
        '1651196952066437121', '', '2023-04-26 20:10:06', '', '2023-04-26 20:10:06', 1, 'NO'),
       ('1651194819623563266', '操作人账号', 'operatorAccount', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '08',
        NULL, '1651196982047322114', '', '2023-04-26 20:10:13', '', '2023-04-26 20:11:40', 3, 'NO'),
       ('1651194819623563266', '操作人姓名', 'operatorName', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '09', NULL,
        '1651197116009197570', '', '2023-04-26 20:10:45', '', '2023-04-26 20:11:34', 2, 'NO'),
       ('1651194819623563266', '操作人ip', 'operatorIp', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '11', NULL,
        '1651197578636734466', '', '2023-04-26 20:12:35', '', '2023-04-26 20:12:35', 1, 'NO'),
       ('1651194819623563266', '响应结果', 'responseCode', 'STRING', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '12', NULL,
        '1651197724359438338', '', '2023-04-26 20:13:10', '', '2023-04-26 20:13:10', 1, 'NO'),
       ('1651194819623563266', '响应数据', 'responseData', 'STRING', NULL, 'TEXTAREA', NULL, 'YES', 5120, NULL, NULL, 'NO', NULL, 'NO', 'NO', '13',
        NULL, '1651197809361203202', '', '2023-04-26 20:13:30', '', '2023-04-26 20:13:58', 3, 'NO'),
       ('1651194819623563266', '执行耗时ms', 'timeConsuming', 'INTEGER', NULL, 'TEXT', NULL, 'YES', 32, NULL, NULL, 'NO', NULL, 'NO', 'NO', '14',
        NULL, '1651198191755898881', '', '2023-04-26 20:15:01', '', '2023-04-26 20:15:01', 1, 'NO'),
       ('1648585539498070018', '测试', 'test', 'DATA_DICTIONARY', 'SerialNoResetStrategy', 'DROP_DOWN_LIST', NULL, 'YES', 234, NULL, 'RUNNING', 'NO',
        NULL, 'NO', 'NO', NULL, NULL, '1652566450501607425', '', '2023-04-30 14:52:00', '', '2023-04-30 14:58:27', 3, 'YES');

-- 导出  表 abc.cfg_entity_view 结构
CREATE TABLE IF NOT EXISTS `cfg_entity_view`
(
    `entity_view_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '视图类型',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `main_reference_view_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '主参照视图',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `before_init` varchar
(
    2000
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '初始化前',
    `after_init` varchar
(
    2000
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '初始化后',
    `validateData` varchar
(
    2000
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '验证数据',
    `before_save` varchar
(
    2000
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '保存前',
    `after_save` varchar
(
    2000
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '保存后',
    `common_param_change` varchar
(
    2000
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通用参数变更',
    `tree_path` varchar
(
    400
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '树路径',
    `remark` varchar
(
    400
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
    `entity_model` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '实体模型',
    `entity` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '实体',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='实体视图';

-- 正在导出表  abc.cfg_entity_view 的数据：~88 rows (大约)
INSERT INTO `cfg_entity_view` (`entity_view_type`, `name`, `code`, `main_reference_view_flag`, `order_no`, `before_init`, `after_init`,
                               `validateData`, `before_save`, `after_save`, `common_param_change`, `tree_path`, `remark`, `entity_model`, `entity`,
                               `id`, `create_id`, `create_time`, `update_id`, `update_time`, `version`, `delete_flag`)
VALUES ('LIST', '列表', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '1641252670496907265', '1640515371261796353',
        '1641975231912390658', '', '2023-04-01 09:26:16', '', '2023-04-17 20:53:37', 4, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1643422467041087489', '1643422382840434689',
        '1643425456518680578', '', '2023-04-05 09:28:57', '', '2023-04-05 13:55:07', 2, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1643422467041087489', '1643422382840434689',
        '1643492368195858433', '', '2023-04-05 13:54:50', '', '2023-04-05 13:54:50', 1, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1643422467041087489', '1643422382840434689',
        '1644491506374955010', '', '2023-04-08 08:05:03', '', '2023-04-08 08:05:51', 2, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1643422467041087489', '1643422382840434689',
        '1644498007630041090', '', '2023-04-08 08:30:53', '', '2023-04-14 19:32:44', 3, 'NO'),
       ('REFERENCE', '参照视图', 'reference', 'YES', '05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1643422467041087489',
        '1643422382840434689', '1644957439652581378', '', '2023-04-09 14:56:30', '', '2023-04-23 13:07:31', 6, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1644974897687994370', '1644974663587110913',
        '1644978322668560386', '', '2023-04-09 16:19:29', '', '2023-04-09 16:19:29', 1, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1644974897687994370', '1644974663587110913',
        '1644978953978421250', '', '2023-04-09 16:22:00', '', '2023-04-09 16:22:00', 1, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '1644974897687994370', '1644974663587110913',
        '1644979089785790466', '', '2023-04-09 16:22:32', '', '2023-04-13 20:18:06', 7, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1644974897687994370', '1644974663587110913',
        '1644979154025750529', '', '2023-04-09 16:22:47', '', '2023-04-09 16:23:38', 3, 'NO'),
       ('REFERENCE', '参照视图', 'reference', 'NO', '05', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1644974897687994370', '1644974663587110913',
        '1644979290651009026', '', '2023-04-09 16:23:20', '', '2023-04-13 17:06:44', 4, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01',
        'const entity = this.$route.query.id\nthis.queryCondition = Object.assign(this.queryCondition, { entity })', NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, '1645621529257443330', '1645621429420425217', '1645624558534787074', '', '2023-04-11 11:07:24', '', '2023-04-13 20:40:18', 2,
        'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', '', ' this.entityData.entity = param.id', NULL, NULL, NULL, NULL, NULL, NULL, '1645621529257443330',
        '1645621429420425217', '1645661443412058113', '', '2023-04-11 13:33:58', '', '2023-04-13 20:38:13', 2, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1645621529257443330', '1645621429420425217',
        '1645661989745319938', '', '2023-04-11 13:36:08', '', '2023-04-11 13:36:27', 2, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1645621529257443330', '1645621429420425217',
        '1645662010179969026', '', '2023-04-11 13:36:13', '', '2023-04-11 13:36:49', 3, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', 'this.queryCondition.entityModel = this.$route.query.id', NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        '1645681655993622529', '1645676041900941314', '1645685382146187265', '', '2023-04-11 15:09:05', '', '2023-04-15 08:30:05', 2, 'NO'),
       ('REFERENCE', '参照视图', 'reference', 'NO', '05', 'this.queryCondition.entity=this.entityModelParam.entity', NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, '1645621529257443330', '1645621429420425217', '1646053022073143297', '', '2023-04-12 15:29:57', '', '2023-04-13 17:14:12', 3,
        'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', '', NULL, NULL, NULL, NULL, '', NULL, NULL, '1645708089986408450', '1645707891298033666',
        '1646070570890080257', '', '2023-04-12 16:39:41', '', '2023-04-20 13:47:02', 7, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '01', '', ' this.entityData.entity = param.id\nthis.entityModelParam = { entity: param.id }', NULL, NULL,
        NULL, NULL, NULL, NULL, '1645708089986408450', '1645707891298033666', '1646114040468049921', '', '2023-04-12 19:32:25', '',
        '2023-04-14 19:40:12', 4, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '02', NULL, 'this.entityModelParam = { entity: this.entityData.entity }', NULL, NULL, NULL, NULL, NULL,
        NULL, '1645708089986408450', '1645707891298033666', '1646114409386446849', '', '2023-04-12 19:33:53', '', '2023-04-13 21:48:39', 4, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '03', NULL, 'this.entityModelParam = { entity: this.entityData.entity }', NULL, NULL, NULL, NULL, NULL,
        NULL, '1645708089986408450', '1645707891298033666', '1646114523928694785', '', '2023-04-12 19:34:20', '', '2023-04-13 21:49:21', 3, 'NO'),
       ('CUSTOM', '自定义视图', 'custom', 'NO', '123', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1645708089986408450', '1645707891298033666',
        '1646841016484945921', '', '2023-04-14 19:41:10', '', '2023-04-14 19:41:14', 1, 'YES'),
       ('ADD', '新增视图', 'add', 'NO', '01', '', 'this.entityData.view = param.id', NULL, NULL, NULL, NULL, NULL, NULL, '1646742761914875905',
        '1646742577256448001', '1646859594433093634', '', '2023-04-14 20:54:59', '', '2023-04-15 21:08:34', 3, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1646742761914875905', '1646742577256448001',
        '1646862452985487361', '', '2023-04-14 21:06:21', '', '2023-04-14 21:06:33', 2, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, 'this.entityData.entityModel=param.id', NULL, NULL, NULL, NULL, NULL, NULL, '1645681655993622529',
        '1645676041900941314', '1647034718641836034', '', '2023-04-15 08:30:52', '', '2023-04-15 08:38:42', 3, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '1645681655993622529', '1645676041900941314',
        '1647036417238487041', '', '2023-04-15 08:37:37', '', '2023-04-15 08:38:46', 3, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '1645681655993622529', '1645676041900941314',
        '1647036588785520642', '', '2023-04-15 08:38:18', '', '2023-04-15 08:38:31', 2, 'NO'),
       ('REFERENCE', '参照视图', 'reference', 'NO', '05', 'this.queryCondition.entityModel = this.entityModelPropertyParam.entityModel', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, '1645681655993622529', '1645676041900941314', '1647036734806020097', '', '2023-04-15 08:38:53', '',
        '2023-04-15 08:40:28', 2, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1647124616879083521', '1647124533710229506',
        '1647160512705724417', '', '2023-04-15 16:50:44', '', '2023-04-15 16:50:44', 1, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, ' this.entityData.view = param.id', NULL, NULL, NULL, NULL, NULL, NULL, '1647124616879083521',
        '1647124533710229506', '1647160664963153921', '', '2023-04-15 16:51:20', '', '2023-04-15 17:07:18', 2, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1647124616879083521', '1647124533710229506',
        '1647160941803995138', '', '2023-04-15 16:52:26', '', '2023-04-15 16:52:47', 2, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1646742761914875905', '1646742577256448001',
        '1647205704750100482', '', '2023-04-15 19:50:18', '', '2023-04-15 20:06:42', 2, 'YES'),
       ('ADD', '新增视图', 'add', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1647398864835092482', '1647391978438057985',
        '1647404556346236930', '', '2023-04-16 09:00:28', '', '2023-04-16 09:00:28', 1, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1647398864835092482', '1647391978438057985',
        '1647404577783324674', '', '2023-04-16 09:00:33', '', '2023-04-16 09:00:43', 2, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1647421061372891139', '1647421061372891138',
        '1647470434446266370', '', '2023-04-16 13:22:15', '', '2023-04-16 13:22:15', 1, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1647421061372891139', '1647421061372891138',
        '1647471028607176706', '', '2023-04-16 13:24:36', '', '2023-04-16 13:25:29', 2, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1647421061372891139', '1647421061372891138',
        '1647471167728046082', '', '2023-04-16 13:25:10', '', '2023-04-16 13:25:22', 2, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1647421061372891139', '1647421061372891138',
        '1647471292642807809', '', '2023-04-16 13:25:39', '', '2023-04-16 13:25:58', 2, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1647395514450223106', '1647395514416668674',
        '1647480604186402818', '', '2023-04-16 14:02:39', '', '2023-04-16 14:02:39', 1, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1647395514450223106', '1647395514416668674',
        '1647480841919553537', '', '2023-04-16 14:03:36', '', '2023-04-16 14:03:49', 2, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1641252670496907265', '1640515371261796353',
        '1647946485639266306', '', '2023-04-17 20:53:54', '', '2023-04-17 20:53:54', 1, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1641252670496907265', '1640515371261796353',
        '1647946510154973186', '', '2023-04-17 20:54:00', '', '2023-04-17 20:54:09', 2, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1641252670496907265', '1640515371261796353',
        '1647946675444105218', '', '2023-04-17 20:54:39', '', '2023-04-17 20:54:49', 2, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, '1648585539498070018', '1648585539498070017',
        '1648589167218778113', '', '2023-04-19 15:27:41', '', '2023-04-21 20:29:39', 4, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, '1648585539498070018', '1648585539498070017',
        '1648589615623430146', '', '2023-04-19 15:29:28', '', '2023-04-21 20:29:46', 3, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1648585539498070018', '1648585539498070017',
        '1648589714055356417', '', '2023-04-19 15:29:52', '', '2023-04-19 15:30:17', 2, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1648585539498070018', '1648585539498070017',
        '1648589837569220609', '', '2023-04-19 15:30:21', '', '2023-04-19 15:30:43', 2, 'NO'),
       ('TREE', '树视图', 'tree', 'NO', '05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1648585539498070018', '1648585539498070017',
        '1648590042138009601', '', '2023-04-19 15:31:10', '', '2023-04-19 15:31:10', 1, 'NO'),
       ('TREE_LIST', '树表视图', 'treeList', 'NO', '06', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1648585539498070018', '1648585539498070017',
        '1648591399939072001', '', '2023-04-19 15:36:34', '', '2023-04-19 15:36:59', 2, 'NO'),
       ('REFERENCE', '参照视图', 'reference', 'NO', '99', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1648585539498070018',
        '1648585539498070017', '1648664997567430657', '', '2023-04-19 20:29:01', '', '2023-04-22 10:04:05', 2, 'YES'),
       ('TREE_REFERENCE', '树参照视图', 'treeReference', 'YES', '07', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1648585539498070018',
        '1648585539498070017', '1649583520334520321', '', '2023-04-22 09:18:54', '', '2023-04-23 11:12:12', 4, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1649609916289150977', '1649609916222042114',
        '1649751408416714753', '', '2023-04-22 20:26:01', '', '2023-04-22 20:26:01', 1, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1649609916289150977', '1649609916222042114',
        '1649753938068566017', '', '2023-04-22 20:36:04', '', '2023-04-22 20:36:04', 1, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1649609916289150977', '1649609916222042114',
        '1649753955013554177', '', '2023-04-22 20:36:08', '', '2023-04-22 20:37:53', 2, 'YES'),
       ('ADD', '新增视图 副本 副本', 'add', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1649609916289150977', '1649609916222042114',
        '1649753972096954369', '', '2023-04-22 20:36:13', '', '2023-04-22 20:37:53', 1, 'YES'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1649609916289150977', '1649609916222042114',
        '1649754414826713090', '', '2023-04-22 20:37:58', '', '2023-04-22 20:38:08', 2, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1649609916289150977', '1649609916222042114',
        '1649754722730569730', '', '2023-04-22 20:39:12', '', '2023-04-22 20:40:03', 2, 'NO'),
       ('TREE_LIST', '树表视图', 'treeList', 'NO', '05', NULL, NULL, NULL, NULL, NULL, NULL, 'system/view/organization/tree.vue', NULL,
        '1649609916289150977', '1649609916222042114', '1649755540301082625', '', '2023-04-22 20:42:26', '', '2023-04-22 20:44:51', 2, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650322968231849985', '1650322968168935425',
        '1650336635631480834', '', '2023-04-24 11:11:30', '', '2023-04-24 20:45:10', 2, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650322968231849985', '1650322968168935425',
        '1650337116860755969', '', '2023-04-24 11:13:25', '', '2023-04-24 11:13:25', 1, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650322968231849985', '1650322968168935425',
        '1650337274377842689', '', '2023-04-24 11:14:03', '', '2023-04-24 11:14:13', 2, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650322968231849985', '1650322968168935425',
        '1650337350240219137', '', '2023-04-24 11:14:21', '', '2023-04-24 11:14:35', 2, 'NO'),
       ('TREE', '树视图', 'tree', 'NO', '05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650322968231849985', '1650322968168935425',
        '1650365631190577153', '', '2023-04-24 13:06:43', '', '2023-04-24 13:06:43', 1, 'NO'),
       ('TREE_LIST', '树表视图', 'treeList', 'NO', '06', NULL, NULL, NULL, NULL, NULL, NULL, 'system/view/userGroup/tree.vue', NULL,
        '1650322968231849985', '1650322968168935425', '1650365911009374209', '', '2023-04-24 13:07:50', '', '2023-04-24 13:07:50', 1, 'NO'),
       ('TREE_REFERENCE', '树参照视图', 'treeReference', 'YES', '07', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650322968231849985',
        '1650322968168935425', '1650385951385493505', '', '2023-04-24 14:27:28', '', '2023-04-24 14:27:28', 1, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650661248886403073', '1650661248861237250',
        '1650671993913110530', '', '2023-04-25 09:24:06', '', '2023-04-25 09:24:06', 1, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, NULL,
        ' if (\n        (this.entityData.type == \'MENU\' || this.entityData.type == \'PAGE\') &&\n        !this.entityData.viewCode\n      ) {\n        this.$message.warning(\'【视图编码】不能为空\')\n        return false\n      }\nreturn true',
        NULL, NULL, NULL, NULL, NULL, '1650661248886403073', '1650661248861237250', '1650672680004775937', '', '2023-04-25 09:26:50', '',
        '2023-04-25 20:36:31', 3, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL,
        ' if (\n        (this.entityData.type == \'MENU\' || this.entityData.type == \'PAGE\') &&\n        !this.entityData.viewCode\n      ) {\n        this.$message.warning(\'【视图编码】不能为空\')\n        return false\n      }\nreturn true',
        NULL, NULL, NULL, NULL, NULL, '1650661248886403073', '1650661248861237250', '1650673931077578754', '', '2023-04-25 09:31:48', '',
        '2023-04-25 20:36:40', 4, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650661248886403073', '1650661248861237250',
        '1650674030222536706', '', '2023-04-25 09:32:12', '', '2023-04-25 09:32:25', 2, 'NO'),
       ('TREE', '树视图', 'tree', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650661248886403073', '1650661248861237250',
        '1650674148468355074', '', '2023-04-25 09:32:40', '', '2023-04-25 09:32:40', 1, 'NO'),
       ('TREE_LIST', '树表视图', 'treeList', 'NO', '05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650661248886403073', '1650661248861237250',
        '1650674245365166081', '', '2023-04-25 09:33:03', '', '2023-04-25 09:33:20', 2, 'NO'),
       ('TREE_REFERENCE', '树参照视图', 'treeReference', 'YES', '06', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1650661248886403073',
        '1650661248861237250', '1650675719503638530', '', '2023-04-25 09:38:54', '', '2023-04-25 13:40:58', 2, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651032312774709250', '1651032312736960513',
        '1651033706957164546', '', '2023-04-26 09:21:25', '', '2023-04-26 09:21:25', 1, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651032312774709250', '1651032312736960513',
        '1651033967490551809', '', '2023-04-26 09:22:27', '', '2023-04-26 09:22:27', 1, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651032312774709250', '1651032312736960513',
        '1651034297791991809', '', '2023-04-26 09:23:46', '', '2023-04-26 09:23:55', 2, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651032312774709250', '1651032312736960513',
        '1651034515786747906', '', '2023-04-26 09:24:38', '', '2023-04-26 09:24:49', 2, 'NO'),
       ('TREE', '树视图', 'tree', 'NO', '05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651032312774709250', '1651032312736960513',
        '1651034642710581250', '', '2023-04-26 09:25:08', '', '2023-04-26 09:25:08', 1, 'NO'),
       ('TREE_LIST', '树表视图', 'treeList', 'NO', '06', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651032312774709250', '1651032312736960513',
        '1651034743466151937', '', '2023-04-26 09:25:32', '', '2023-04-26 09:25:32', 1, 'NO'),
       ('TREE_REFERENCE', '树参照视图', 'treeReference', 'YES', '07', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651032312774709250',
        '1651032312736960513', '1651034838496497665', '', '2023-04-26 09:25:55', '', '2023-04-26 09:25:55', 1, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651119964337250307', '1651119964337250306',
        '1651184645340971009', '', '2023-04-26 19:21:12', '', '2023-04-26 19:21:12', 1, 'NO'),
       ('ADD', '新增视图', 'add', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651119964337250307', '1651119964337250306',
        '1651185031766392833', '', '2023-04-26 19:22:44', '', '2023-04-26 19:22:44', 1, 'NO'),
       ('MODIFY', '修改视图', 'modify', 'NO', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651119964337250307', '1651119964337250306',
        '1651185359832268802', '', '2023-04-26 19:24:02', '', '2023-04-26 19:24:11', 2, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651119964337250307', '1651119964337250306',
        '1651185414270140418', '', '2023-04-26 19:24:15', '', '2023-04-26 19:24:24', 2, 'NO'),
       ('TREE_LIST', '树表视图', 'treeList', 'NO', '00', NULL, NULL, NULL, NULL, NULL, NULL, 'system/view/dictionaryType/tree.vue', NULL,
        '1651119964337250307', '1651119964337250306', '1651185839958441986', '', '2023-04-26 19:25:56', '', '2023-04-26 19:25:56', 1, 'NO'),
       ('LIST', '列表视图', 'list', 'NO', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651194819623563266', '1651194819556454401',
        '1651198471625027585', '', '2023-04-26 20:16:08', '', '2023-04-26 20:16:08', 1, 'NO'),
       ('VIEW', '查看视图', 'view', 'NO', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1651194819623563266', '1651194819556454401',
        '1651199009330606081', '', '2023-04-26 20:18:16', '', '2023-04-26 20:18:16', 1, 'NO'),
       ('TREE_MULTIPLE_REFERENCE', '树多选参照视图', 'treeMultipleReference', 'NO', '08', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        '1650322968231849985', '1650322968168935425', '1651743212364140546', '', '2023-04-28 08:20:44', '', '2023-04-28 08:20:44', 1, 'NO'),
       ('TREE_MULTIPLE_REFERENCE', '树多选参照视图', 'treeMultipleReference', 'NO', '08', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,
        '1650661248886403073', '1650661248861237250', '1651784013878255618', '', '2023-04-28 11:02:52', '', '2023-04-28 11:02:52', 1, 'NO');

-- 导出  表 abc.cfg_entity_view_button 结构
CREATE TABLE IF NOT EXISTS `cfg_entity_view_button`
(
    `id` varchar
(
    32
) NOT NULL COMMENT '标识',
    `name` varchar
(
    32
) DEFAULT NULL COMMENT '名称',
    `code` varchar
(
    32
) DEFAULT NULL COMMENT '编码',
    `function_call` varchar
(
    256
) DEFAULT NULL COMMENT '方法调用',
    `function_content` varchar
(
    1048
) DEFAULT NULL COMMENT '方法内容',
    `view_id` varchar
(
    32
) DEFAULT NULL COMMENT '视图标识',
    `button_type` varchar
(
    32
) DEFAULT NULL COMMENT '按钮类型',
    `template_flag` varchar
(
    32
) DEFAULT NULL COMMENT '模板标识',
    `operation_type` varchar
(
    32
) DEFAULT NULL COMMENT '操作类型',
    `operation_view` varchar
(
    32
) DEFAULT NULL COMMENT '操作视图',
    `icon` varchar
(
    32
) DEFAULT NULL COMMENT '图标',
    `confirm_flag` varchar
(
    32
) DEFAULT NULL COMMENT '是否需确认',
    `confirm_message` varchar
(
    128
) DEFAULT NULL COMMENT '确认提示信息',
    `permission_flag` varchar
(
    32
) DEFAULT NULL COMMENT '是否控制权限',
    `permission_code` varchar
(
    128
) DEFAULT NULL COMMENT '权限编码',
    `more_flag` varchar
(
    32
) DEFAULT NULL COMMENT '是否用于更多',
    `refresh_flag` varchar
(
    32
) DEFAULT NULL COMMENT '是否回刷',
    `order_no` varchar
(
    10
) DEFAULT NULL COMMENT '排序号',
    `create_id` varchar
(
    32
) DEFAULT NULL COMMENT '创建人标识',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) DEFAULT NULL COMMENT '更新人标识',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本',
    `delete_flag` varchar
(
    32
) DEFAULT NULL COMMENT '删除标志',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实体视图按钮';

-- 正在导出表  abc.cfg_entity_view_button 的数据：~63 rows (大约)
INSERT INTO `cfg_entity_view_button` (`id`, `name`, `code`, `function_call`, `function_content`, `view_id`, `button_type`, `template_flag`,
                                      `operation_type`, `operation_view`, `icon`, `confirm_flag`, `confirm_message`, `permission_flag`,
                                      `permission_code`, `more_flag`, `refresh_flag`, `order_no`, `create_id`, `create_time`, `update_id`,
                                      `update_time`, `version`, `delete_flag`)
VALUES ('1597515665647853570', '新增', 'add', 'add', NULL, '-1', 'LIST_PAGE', 'YES', 'ADD', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', 'NO', '02',
        '1', '2022-11-29 16:59:50', '', '2023-04-13 09:12:15', 13, 'NO'),
       ('1597515800729608194', '刷新', 'refresh', 'refresh', NULL, '-1', 'LIST_PAGE', 'YES', 'REFRESH', NULL, 'refresh', 'NO', '111', 'NO', NULL,
        'NO', 'NO', '01', '1', '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1598503607128162306', '删除', 'batchRemove', 'batchRemove', NULL, '-1', 'LIST_PAGE', 'YES', 'REMOVE', NULL, 'delete', 'NO',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'NO', '03', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1598939938052603905', '修改', 'modify', 'modify', NULL, '-1', 'LIST_ROW', 'YES', 'MODIFY', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO',
        'NO', '01', '1', '2022-12-03 15:19:23', '', '2023-04-13 09:13:16', 5, 'NO'),
       ('1631117040853028865', '保存', 'save', 'save', NULL, '-1', 'MODIFY_PAGE', 'YES', 'SAVE', NULL, NULL, 'NO', NULL, 'NO', NULL, 'NO', 'YES',
        '02', '1', '2023-03-02 10:19:42', '', '2023-04-05 20:22:51', 3, 'NO'),
       ('1631117807261421570', '关闭', 'close', 'close', NULL, '-1', 'MODIFY_PAGE', 'YES', 'CLOSE', NULL, NULL, 'NO', NULL, 'NO', NULL, 'NO', 'NO',
        '01', '1', '2023-03-02 10:22:45', '', '2023-04-05 20:22:59', 4, 'NO'),
       ('1642810905850851330', '删除', 'remove', 'remove', NULL, '-1', 'LIST_ROW', 'YES', 'REMOVE', NULL, 'Delete', 'YES',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'NO', '02', '', '2023-04-03 16:46:57', '', '2023-04-13 09:16:01', 4, 'NO'),
       ('1643227088882290690', '编辑', 'modify', 'modify', NULL, '1641975231912390658', 'LIST_ROW', 'NO', 'MODIFY', NULL, 'edit', 'NO', NULL, 'NO',
        NULL, 'NO', 'YES', '01', '1', '2022-12-03 15:19:23', '', '2023-04-03 16:48:06', 2, 'NO'),
       ('1643227088924233729', '删除', 'remove', 'remove', NULL, '1641975231912390658', 'LIST_ROW', 'NO', 'REMOVE', NULL, 'Delete', 'YES',
        '此操作将删除数据, 是否继续？', 'NO', NULL, 'NO', 'YES', '02', '', '2023-04-03 16:46:57', '', '2023-04-03 16:48:40', 2, 'NO'),
       ('1643230059015753731', '新增', 'add', 'add', NULL, '1641975231912390658', 'LIST_PAGE', 'NO', 'ADD', NULL, 'plus', 'NO', NULL, 'YES', 'add',
        'NO', 'YES', '02', '1', '2022-11-29 16:59:50', '', '2023-04-03 16:41:31', 12, 'NO'),
       ('1643230059015753732', '删除', 'batchRemove', 'batchRemove', NULL, '1641975231912390658', 'LIST_PAGE', 'NO', 'REMOVE', NULL, 'delete', 'NO',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'YES', '03', '1', '2022-12-02 10:25:33', '1', '2023-02-16 17:06:20', 4, 'NO'),
       ('1643241505191677954', '刷新', 'refresh', 'refresh', NULL, '1641975231912390658', 'LIST_PAGE', 'NO', 'REFRESH', NULL, 'refresh', 'NO', '111',
        'NO', NULL, 'NO', 'NO', '01', '1', '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1643425494347108354', '刷新', 'refresh', 'refresh', NULL, '1643425456518680578', 'LIST_PAGE', 'NO', 'REFRESH', NULL, 'refresh', 'NO', '111',
        'NO', NULL, 'NO', 'NO', '01', '1', '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1643425494414217218', '新增', 'add', 'add', NULL, '1643425456518680578', 'LIST_PAGE', 'NO', 'ADD', NULL, 'plus', 'NO', NULL, 'YES', 'add',
        'NO', 'YES', '02', '1', '2022-11-29 16:59:50', '', '2023-04-03 16:41:31', 12, 'NO'),
       ('1643425494414217219', '删除', 'batchRemove', 'batchRemove', NULL, '1643425456518680578', 'LIST_PAGE', 'NO', 'REMOVE', NULL, 'delete', 'NO',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'YES', '03', '1', '2022-12-02 10:25:33', '1', '2023-02-16 17:06:20', 4, 'NO'),
       ('1643425835708928002', '编辑', 'modify', 'modify', NULL, '1643425456518680578', 'LIST_ROW', 'NO', 'MODIFY', NULL, 'edit', 'NO', NULL, 'NO',
        NULL, 'NO', 'YES', '01', '1', '2022-12-03 15:19:23', '', '2023-04-03 16:48:06', 2, 'NO'),
       ('1643425835776036866', '删除', 'remove', 'remove', NULL, '1643425456518680578', 'LIST_ROW', 'NO', 'REMOVE', NULL, 'Delete', 'YES',
        '此操作将删除数据, 是否继续？', 'NO', NULL, 'NO', 'YES', '02', '', '2023-04-03 16:46:57', '', '2023-04-03 16:48:40', 2, 'NO'),
       ('1643590265264484354', '关闭', 'close', 'close', NULL, '1643492368195858433', 'MODIFY_PAGE', 'NO', 'CLOSE', NULL, NULL, 'NO', NULL, 'NO',
        NULL, 'NO', 'NO', '01', '1', '2023-03-02 10:22:45', '', '2023-04-05 20:22:59', 4, 'NO'),
       ('1643590265264484355', '保存', 'save', 'save', '', '1643492368195858433', 'MODIFY_PAGE', 'NO', 'SAVE', NULL, NULL, 'NO', NULL, 'NO', NULL,
        'NO', 'YES', '02', '1', '2023-03-02 10:19:42', '', '2023-04-05 20:22:51', 3, 'NO'),
       ('1644491506555310083', '关闭', 'close', 'close', NULL, '1644491506374955010', 'MODIFY_PAGE', 'NO', 'CLOSE', NULL, NULL, 'NO', NULL, 'NO',
        NULL, 'NO', 'NO', '01', '', '2023-04-08 08:05:03', '', '2023-04-08 08:05:03', 1, 'NO'),
       ('1644491506618224641', '保存', 'save', 'save', '', '1644491506374955010', 'MODIFY_PAGE', 'NO', 'SAVE', NULL, NULL, 'NO', NULL, 'NO', NULL,
        'NO', 'YES', '02', '', '2023-04-08 08:05:03', '', '2023-04-08 08:05:03', 1, 'NO'),
       ('1644498007781036033', '关闭', 'close', 'close', NULL, '1644498007630041090', 'MODIFY_PAGE', 'NO', 'CLOSE', NULL, NULL, 'NO', NULL, 'NO',
        NULL, 'NO', 'NO', '01', '', '2023-04-08 08:30:53', '', '2023-04-08 08:30:53', 1, 'NO'),
       ('1644498007781036034', '保存', 'save', 'save', '', '1644498007630041090', 'MODIFY_PAGE', 'NO', 'SAVE', NULL, NULL, 'NO', NULL, 'NO', NULL,
        'NO', 'YES', '02', '', '2023-04-08 08:30:53', '', '2023-04-08 08:30:53', 1, 'NO'),
       ('1644978354620768257', '刷新', 'refresh', 'refresh', NULL, '1644978322668560386', 'LIST_PAGE', 'NO', 'REFRESH', NULL, 'refresh', 'NO', '111',
        'NO', NULL, 'NO', 'NO', '01', '1', '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1644978354687877122', '新增', 'add', 'add', NULL, '1644978322668560386', 'LIST_PAGE', 'NO', 'ADD', NULL, 'plus', 'NO', NULL, 'YES', 'add',
        'NO', 'YES', '02', '1', '2022-11-29 16:59:50', '', '2023-04-03 16:41:31', 12, 'NO'),
       ('1644978354687877123', '删除', 'batchRemove', 'batchRemove', NULL, '1644978322668560386', 'LIST_PAGE', 'NO', 'REMOVE', NULL, 'delete', 'NO',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'YES', '03', '1', '2022-12-02 10:25:33', '1', '2023-02-16 17:06:20', 4, 'NO'),
       ('1644979290713923591', '刷新', 'refresh', 'refresh', NULL, '1644979290651009026', 'LIST_PAGE', 'NO', 'REFRESH', NULL, 'refresh', 'NO', '111',
        'NO', NULL, 'NO', 'NO', '01', '', '2023-04-09 16:23:20', '', '2023-04-09 16:23:20', 1, 'NO'),
       ('1644979290713923592', '新增', 'add', 'add', NULL, '1644979290651009026', 'LIST_PAGE', 'NO', 'ADD', NULL, 'plus', 'NO', NULL, 'YES', 'add',
        'NO', 'YES', '02', '', '2023-04-09 16:23:20', '', '2023-04-09 16:23:20', 1, 'NO'),
       ('1644979290713923593', '删除', 'batchRemove', 'batchRemove', NULL, '1644979290651009026', 'LIST_PAGE', 'NO', 'REMOVE', NULL, 'delete', 'NO',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'YES', '03', '', '2023-04-09 16:23:20', '', '2023-04-09 16:23:20', 1, 'NO'),
       ('1644979290781032450', '编辑', 'modify', 'modify', NULL, '1644979290651009026', 'LIST_ROW', 'NO', 'MODIFY', NULL, 'edit', 'NO', NULL, 'NO',
        NULL, 'NO', 'YES', '01', '', '2023-04-09 16:23:20', '', '2023-04-09 16:23:20', 1, 'NO'),
       ('1644979290781032451', '删除', 'remove', 'remove', NULL, '1644979290651009026', 'LIST_ROW', 'NO', 'REMOVE', NULL, 'Delete', 'YES',
        '此操作将删除数据, 是否继续？', 'NO', NULL, 'NO', 'YES', '02', '', '2023-04-09 16:23:20', '', '2023-04-09 16:23:20', 1, 'NO'),
       ('1645576631883972610', '生成库表', 'generateTable', 'generateTable',
        'if (!this.checkSelected()) {\n        return\n}\nconst ids = this.getCheckedId()\nthis.api.generateTable(ids)', '1644978322668560386',
        'LIST_PAGE', 'NO', 'CUSTOM', NULL, 'Setting', 'YES', '此操作将批量生成库表, 是否继续?', 'YES', 'generateTable', 'NO', 'NO', '04', '',
        '2023-04-11 07:56:57', '', '2023-04-11 09:28:36', 5, 'NO'),
       ('1645577050836221953', '生成代码', 'generateCode', 'generateCode',
        'if (!this.checkSelected()) {\n        return\n}\nconst ids = this.getCheckedId()\nthis.api.generateCode(ids)', '1644978322668560386',
        'LIST_PAGE', 'NO', 'CUSTOM', NULL, 'Setting', 'YES', '此操作将批量生成代码, 是否继续?', 'YES', 'generateCode', 'NO', 'NO', '05', '',
        '2023-04-11 07:58:37', '', '2023-04-11 09:28:50', 5, 'NO'),
       ('1645577359394390018', '修改', 'modify', 'modify', NULL, '1644978322668560386', 'LIST_ROW', 'NO', 'MODIFY', NULL, 'edit', 'NO', NULL, 'NO',
        NULL, 'YES', 'YES', '02', '1', '2022-12-03 15:19:23', '', '2023-04-11 08:03:08', 6, 'NO'),
       ('1645577359394390019', '删除', 'remove', 'remove', NULL, '1644978322668560386', 'LIST_ROW', 'NO', 'REMOVE', NULL, 'Delete', 'YES',
        '此操作将删除数据, 是否继续？', 'NO', NULL, 'YES', 'YES', '03', '', '2023-04-03 16:46:57', '', '2023-04-11 08:03:08', 5, 'NO'),
       ('1645577930666983425', '配置模型', 'configModel', 'configModel',
        ' this.$router.push({ path: \' / entityconfig / entityModel\', query: { id: row.id } })', '1644978322668560386', 'LIST_ROW', 'NO', 'CUSTOM',
        NULL, NULL, 'NO', NULL, 'YES', 'configModel', 'NO', 'NO', '00', '', '2023-04-11 08:02:07', '', '2023-04-13 16:44:11', 9, 'NO'),
       ('1645578174469292033', '配置视图', 'configView', 'configView',
        'this.$router.push({ path: \' / entityconfig / entityView\', query: { id: row.id } })', '1644978322668560386', 'LIST_ROW', 'NO', 'CUSTOM',
        NULL, NULL, 'NO', NULL, 'YES', 'configView', 'NO', 'NO', '01', '', '2023-04-11 08:03:05', '', '2023-04-13 16:44:23', 8, 'NO'),
       ('1645624783479504897', '刷新', 'refresh', 'refresh', NULL, '1645624558534787074', 'LIST_PAGE', 'NO', 'REFRESH', NULL, 'refresh', 'NO', '111',
        'NO', NULL, 'NO', 'NO', '01', '1', '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1645624783479504898', '新增', 'add', 'addWithId', 'this.$refs.addPage.init({ id: this.$route.query.id })', '1645624558534787074',
        'LIST_PAGE', 'NO', 'CUSTOM', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', 'NO', '02', '1', '2022-11-29 16:59:50', '', '2023-04-13 20:41:14',
        13, 'NO'),
       ('1645624783542419458', '删除', 'batchRemove', 'batchRemove', NULL, '1645624558534787074', 'LIST_PAGE', 'NO', 'REMOVE', NULL, 'delete', 'NO',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'YES', '03', '1', '2022-12-02 10:25:33', '1', '2023-02-16 17:06:20', 4, 'NO'),
       ('1645625541587369985', '修改', 'modify', 'modify', NULL, '1645624558534787074', 'LIST_ROW', 'NO', 'MODIFY', NULL, 'edit', 'NO', NULL, 'NO',
        NULL, 'NO', 'NO', '01', '1', '2022-12-03 15:19:23', '', '2023-04-13 20:49:15', 4, 'NO'),
       ('1645625541587369986', '删除', 'remove', 'remove', NULL, '1645624558534787074', 'LIST_ROW', 'NO', 'REMOVE', NULL, 'Delete', 'YES',
        '此操作将删除数据, 是否继续？', 'NO', NULL, 'NO', 'NO', '02', '', '2023-04-03 16:46:57', '', '2023-04-13 20:49:20', 3, 'NO'),
       ('1645668487854579713', '配置属性', 'configModelProperty', 'configModelProperty',
        'this.$router.push({ path: \' / entityconfig / entityModelProperty\', query: { id: row.id } })', '1645624558534787074', 'LIST_ROW', 'NO',
        'CUSTOM', NULL, NULL, 'NO', NULL, 'YES', 'configModelProperty', 'NO', 'NO', '00', '', '2023-04-11 14:01:57', '', '2023-04-13 20:49:11', 2,
        'NO'),
       ('1645687219872096258', '刷新', 'refresh', 'refresh', NULL, '1645685382146187265', 'LIST_PAGE', 'NO', 'REFRESH', NULL, 'refresh', 'NO', '111',
        'NO', NULL, 'NO', 'NO', '01', '1', '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1645687219901456385', '新增', 'add', 'addWithId', 'this.$refs.addPage.init({ id: this.$route.query.id })', '1645685382146187265',
        'LIST_PAGE', 'NO', 'CUSTOM', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', 'NO', '02', '1', '2022-11-29 16:59:50', '', '2023-04-15 08:35:41',
        14, 'NO'),
       ('1645687219901456386', '删除', 'batchRemove', 'batchRemove', NULL, '1645685382146187265', 'LIST_PAGE', 'NO', 'REMOVE', NULL, 'delete', 'NO',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'NO', '03', '1', '2022-12-02 10:25:33', '', '2023-04-13 20:52:56', 5, 'NO'),
       ('1645698796318908418', '修改', 'modify', 'modify', NULL, '1645685382146187265', 'LIST_ROW', 'NO', 'MODIFY', NULL, 'edit', 'NO', NULL, 'NO',
        NULL, 'NO', 'NO', '01', '1', '2022-12-03 15:19:23', '', '2023-04-13 20:52:40', 4, 'NO'),
       ('1645698796386017281', '删除', 'remove', 'remove', NULL, '1645685382146187265', 'LIST_ROW', 'NO', 'REMOVE', NULL, 'Delete', 'YES',
        '此操作将删除数据, 是否继续？', 'NO', NULL, 'NO', 'NO', '02', '', '2023-04-03 16:46:57', '', '2023-04-13 20:52:45', 3, 'NO'),
       ('1646073000742662146', '刷新', 'refresh', 'refresh', NULL, '1646070570890080257', 'LIST_PAGE', 'NO', 'REFRESH', NULL, 'refresh', 'NO', '111',
        'NO', NULL, 'NO', 'NO', '01', '1', '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1646073000809771010', '新增', 'addWithId', 'addWithId', 'this.$refs.addPage.init({ id: this.$route.query.id })', '1646070570890080257',
        'LIST_PAGE', 'NO', 'CUSTOM', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', 'NO', '02', '1', '2022-11-29 16:59:50', '', '2023-04-13 09:11:51',
        15, 'NO'),
       ('1646073000809771011', '删除', 'batchRemove', 'batchRemove', NULL, '1646070570890080257', 'LIST_PAGE', 'NO', 'REMOVE', NULL, 'delete', 'NO',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'YES', '03', '1', '2022-12-02 10:25:33', '1', '2023-02-16 17:06:20', 4, 'NO'),
       ('1646074961286180865', '复制新增', 'addByCopy', 'addByCopy', NULL, '-1', 'LIST_PAGE', 'YES', 'ADD_BY_COPY', NULL, 'CopyDocument', 'NO', NULL,
        'YES', 'addByCopy', 'NO', 'NO', '04', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1646076210454769666', '复制新增', 'addByCopy', 'addByCopy', NULL, '1646070570890080257', 'LIST_PAGE', 'NO', 'ADD_BY_COPY', NULL,
        'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', 'YES', '04', '', '2023-04-12 16:57:08', '', '2023-04-12 17:01:28', 1, 'NO'),
       ('1646076900610715649', '配置', 'configView', 'configView',
        ' if (row.entityViewType === \'LIST\') {\n        this.$router.push({ path: \'/entityconfig/listViewConfig\', query: { id: row.id } })\n      } else if (row.entityViewType === \'ADD\') {\n        this.$router.push({ path: \'/entityconfig/addViewConfig\', query: { id: row.id } })\n      } else if (row.entityViewType === \'MODIFY\') {\n        this.$router.push({ path: \'/entityconfig/modifyViewConfig\', query: { id: row.id } })\n      } else if (row.entityViewType === \'VIEW\') {\n        this.$router.push({ path: \'/entityconfig/viewViewConfig\', query: { id: row.id } })\n      } else if (row.entityViewType === \'REFERENCE\') {\n        this.$router.push({ path: \'/entityconfig/referenceViewConfig\', query: { id: row.id } })\n      }',
        '1646070570890080257', 'LIST_ROW', 'NO', 'CUSTOM', NULL, NULL, 'NO', NULL, 'YES', 'configView', 'NO', 'NO', '00', '', '2023-04-12 17:04:50',
        '', '2023-04-12 20:13:38', 2, 'NO'),
       ('1646124457403617282', '修改', 'modify', 'modify', NULL, '1646070570890080257', 'LIST_ROW', 'NO', 'MODIFY', NULL, 'edit', 'NO', NULL, 'YES',
        'modify', 'NO', 'NO', '01', '1', '2022-12-03 15:19:23', '', '2023-04-13 09:20:49', 5, 'NO'),
       ('1646124457403617283', '删除', 'remove', 'remove', NULL, '1646070570890080257', 'LIST_ROW', 'NO', 'REMOVE', NULL, 'Delete', 'YES',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'NO', '02', '', '2023-04-03 16:46:57', '', '2023-04-13 09:20:43', 4, 'NO'),
       ('1647037429437288450', '复制新增', 'addByCopy', 'addByCopy', NULL, '1645685382146187265', 'LIST_PAGE', 'NO', 'ADD_BY_COPY', NULL,
        'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', 'NO', '04', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1647470522505678850', '刷新', 'refresh', 'refresh', NULL, '1647470434446266370', 'LIST_PAGE', 'NO', 'REFRESH', NULL, 'refresh', 'NO', '111',
        'NO', NULL, 'NO', 'NO', '01', '1', '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1647470522581176321', '新增', 'add', 'add', NULL, '1647470434446266370', 'LIST_PAGE', 'NO', 'ADD', NULL, 'plus', 'NO', NULL, 'YES', 'add',
        'NO', 'NO', '02', '1', '2022-11-29 16:59:50', '', '2023-04-13 09:12:15', 13, 'NO'),
       ('1647470522581176322', '删除', 'batchRemove', 'batchRemove', NULL, '1647470434446266370', 'LIST_PAGE', 'NO', 'REMOVE', NULL, 'delete', 'NO',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'NO', '03', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1647470522644090882', '复制新增', 'addByCopy', 'addByCopy', NULL, '1647470434446266370', 'LIST_PAGE', 'NO', 'ADD_BY_COPY', NULL,
        'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', 'NO', '04', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1647470932901548034', '修改', 'modify', 'modify', NULL, '1647470434446266370', 'LIST_ROW', 'NO', 'MODIFY', NULL, 'edit', 'NO', NULL, 'YES',
        'modify', 'NO', 'NO', '01', '1', '2022-12-03 15:19:23', '', '2023-04-13 09:13:16', 5, 'NO'),
       ('1647470932968656898', '删除', 'remove', 'remove', NULL, '1647470434446266370', 'LIST_ROW', 'NO', 'REMOVE', NULL, 'Delete', 'YES',
        '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', 'NO', '02', '', '2023-04-03 16:46:57', '', '2023-04-13 09:16:01', 4, 'NO');

-- 导出  表 abc.cfg_module 结构
CREATE TABLE IF NOT EXISTS `cfg_module`
(
    `id` varchar
(
    32
) NOT NULL COMMENT '标识',
    `name` varchar
(
    32
) NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) NOT NULL COMMENT '编码',
    `app` varchar
(
    32
) NOT NULL COMMENT '应用',
    `abbreviation` varchar
(
    32
) NOT NULL COMMENT '缩略码',
    `package_path` varchar
(
    400
) NOT NULL COMMENT '包路径',
    `remark` varchar
(
    400
) DEFAULT NULL COMMENT '备注',
    `order_no` varchar
(
    10
) DEFAULT NULL COMMENT '排序号',
    `create_id` varchar
(
    32
) DEFAULT NULL COMMENT '创建人标识',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) DEFAULT NULL COMMENT '更新人标识',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本',
    `delete_flag` varchar
(
    32
) DEFAULT NULL COMMENT '删除标志',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块';

-- 正在导出表  abc.cfg_module 的数据：~2 rows (大约)
INSERT INTO `cfg_module` (`id`, `name`, `code`, `app`, `abbreviation`, `package_path`, `remark`, `order_no`, `create_id`, `create_time`, `update_id`,
                          `update_time`, `version`, `delete_flag`)
VALUES ('1640314868674338817', '系统管理', 'system', 'PLATFORM', 'sys', 'tech.abc.platform', '', '01', '', '2023-03-27 19:28:35', '',
        '2023-04-05 11:11:27', 10, 'NO'),
       ('1640315164112723970', '实体配置', 'entityconfig', 'PLATFORM', 'cfg', 'tech.abc.platform', NULL, '02', '', '2023-03-27 19:29:45', '',
        '2023-04-08 09:25:53', 5, 'NO');

-- 导出  表 abc.cfg_view_button 结构
CREATE TABLE IF NOT EXISTS `cfg_view_button`
(
    `view` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '视图',
    `button_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'LIST_PAGE' COMMENT '按钮类型',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `content` varchar
(
    2000
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '内容',
    `icon` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图标',
    `confirm_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'NO' COMMENT '是否需确认',
    `confirm_message` varchar
(
    256
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '确认信息',
    `permission_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '是否控制权限',
    `permission_code` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限编码',
    `more_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'NO' COMMENT '是否用于更多',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='视图按钮';

-- 正在导出表  abc.cfg_view_button 的数据：~92 rows (大约)
INSERT INTO `cfg_view_button` (`view`, `button_type`, `name`, `code`, `content`, `icon`, `confirm_flag`, `confirm_message`, `permission_flag`,
                               `permission_code`, `more_flag`, `order_no`, `id`, `create_id`, `create_time`, `update_id`, `update_time`, `version`,
                               `delete_flag`)
VALUES ('-1', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1597515665647853570', '1', '2022-11-29 16:59:50', '',
        '2023-04-13 09:12:15', 13, 'NO'),
       ('-1', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1597515800729608194', '1', '2022-11-29 17:00:22',
        '', '2023-04-04 21:17:51', 10, 'NO'),
       ('-1', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1598503607128162306', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('-1', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1598939938052603905', '1', '2022-12-03 15:19:23',
        '', '2023-04-13 09:13:16', 5, 'NO'),
       ('-1', 'MODIFY_PAGE', '保存', 'save', NULL, NULL, 'NO', NULL, 'NO', NULL, 'NO', '02', '1631117040853028865', '1', '2023-03-02 10:19:42', '',
        '2023-04-05 20:22:51', 3, 'NO'),
       ('-1', 'MODIFY_PAGE', '关闭', 'close', NULL, NULL, 'NO', NULL, 'NO', NULL, 'NO', '01', '1631117807261421570', '1', '2023-03-02 10:22:45', '',
        '2023-04-05 20:22:59', 4, 'NO'),
       ('-1', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '02', '1642810905850851330',
        '', '2023-04-03 16:46:57', '', '2023-04-13 09:16:01', 4, 'NO'),
       ('1641975231912390658', 'LIST_ROW', '编辑', 'modify', NULL, 'edit', 'NO', NULL, 'NO', NULL, 'NO', '01', '1643227088882290690', '1',
        '2022-12-03 15:19:23', '', '2023-04-03 16:48:06', 2, 'YES'),
       ('1641975231912390658', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'NO', NULL, 'NO', '02',
        '1643227088924233729', '', '2023-04-03 16:46:57', '', '2023-04-03 16:48:40', 2, 'YES'),
       ('1641975231912390658', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1643230059015753731', '1',
        '2022-11-29 16:59:50', '', '2023-04-03 16:41:31', 12, 'YES'),
       ('1641975231912390658', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1643230059015753732', '1', '2022-12-02 10:25:33', '1', '2023-02-16 17:06:20', 4, 'YES'),
       ('1641975231912390658', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1643241505191677954', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'YES'),
       ('1643425456518680578', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1643425494347108354', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1643425456518680578', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1643425494414217218', '1',
        '2022-11-29 16:59:50', '', '2023-04-03 16:41:31', 12, 'NO'),
       ('1643425456518680578', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1643425494414217219', '1', '2022-12-02 10:25:33', '1', '2023-02-16 17:06:20', 4, 'NO'),
       ('1643425456518680578', 'LIST_ROW', '编辑', 'modify', NULL, 'edit', 'NO', NULL, 'NO', NULL, 'NO', '01', '1643425835708928002', '1',
        '2022-12-03 15:19:23', '', '2023-04-03 16:48:06', 2, 'NO'),
       ('1643425456518680578', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'NO', NULL, 'NO', '02',
        '1643425835776036866', '', '2023-04-03 16:46:57', '', '2023-04-03 16:48:40', 2, 'NO'),
       ('1643492368195858433', 'MODIFY_PAGE', '关闭', 'close', NULL, NULL, 'NO', NULL, 'NO', NULL, 'NO', '01', '1643590265264484354', '1',
        '2023-03-02 10:22:45', '', '2023-04-05 20:22:59', 4, 'NO'),
       ('1643492368195858433', 'MODIFY_PAGE', '保存', 'save', '', NULL, 'NO', NULL, 'NO', NULL, 'NO', '02', '1643590265264484355', '1',
        '2023-03-02 10:19:42', '', '2023-04-05 20:22:51', 3, 'NO'),
       ('1644491506374955010', 'MODIFY_PAGE', '关闭', 'close', NULL, NULL, 'NO', NULL, 'NO', NULL, 'NO', '01', '1644491506555310083', '',
        '2023-04-08 08:05:03', '', '2023-04-08 08:05:03', 1, 'NO'),
       ('1644491506374955010', 'MODIFY_PAGE', '保存', 'save', '', NULL, 'NO', NULL, 'NO', NULL, 'NO', '02', '1644491506618224641', '',
        '2023-04-08 08:05:03', '', '2023-04-08 08:05:03', 1, 'NO'),
       ('1644498007630041090', 'MODIFY_PAGE', '关闭', 'close', NULL, NULL, 'NO', NULL, 'NO', NULL, 'NO', '01', '1644498007781036033', '',
        '2023-04-08 08:30:53', '', '2023-04-08 08:30:53', 1, 'NO'),
       ('1644498007630041090', 'MODIFY_PAGE', '保存', 'save', '', NULL, 'NO', NULL, 'NO', NULL, 'NO', '02', '1644498007781036034', '',
        '2023-04-08 08:30:53', '', '2023-04-08 08:30:53', 1, 'NO'),
       ('1644978322668560386', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1644978354620768257', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1644978322668560386', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1644978354687877122', '1',
        '2022-11-29 16:59:50', '', '2023-04-03 16:41:31', 12, 'NO'),
       ('1644978322668560386', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1644978354687877123', '1', '2022-12-02 10:25:33', '1', '2023-02-16 17:06:20', 4, 'NO'),
       ('1644979290651009026', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1644979290713923591', '',
        '2023-04-09 16:23:20', '', '2023-04-09 16:23:20', 1, 'NO'),
       ('1644979290651009026', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1644979290713923592', '',
        '2023-04-09 16:23:20', '', '2023-04-09 16:23:20', 1, 'NO'),
       ('1644979290651009026', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1644979290713923593', '', '2023-04-09 16:23:20', '', '2023-04-09 16:23:20', 1, 'NO'),
       ('1644979290651009026', 'LIST_ROW', '编辑', 'modify', NULL, 'edit', 'NO', NULL, 'NO', NULL, 'NO', '01', '1644979290781032450', '',
        '2023-04-09 16:23:20', '', '2023-04-09 16:23:20', 1, 'NO'),
       ('1644979290651009026', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'NO', NULL, 'NO', '02',
        '1644979290781032451', '', '2023-04-09 16:23:20', '', '2023-04-09 16:23:20', 1, 'NO'),
       ('1644978322668560386', 'LIST_PAGE', '生成库表', 'generateTable',
        'if (!this.checkSelected()) {\n        return\n}\nconst ids = this.getCheckedId()\nthis.api.generateTable(ids)', 'Setting', 'YES',
        '此操作将批量生成库表, 是否继续?', 'YES', 'generateTable', 'NO', '04', '1645576631883972610', '', '2023-04-11 07:56:57', '',
        '2023-04-11 09:28:36', 5, 'NO'),
       ('1644978322668560386', 'LIST_PAGE', '生成代码', 'generateCode',
        'if (!this.checkSelected()) {\n        return\n}\nconst ids = this.getCheckedId()\nthis.api.generateCode(ids)', 'Setting', 'YES',
        '此操作将批量生成代码, 是否继续?', 'YES', 'generateCode', 'NO', '05', '1645577050836221953', '', '2023-04-11 07:58:37', '',
        '2023-04-11 09:28:50', 5, 'NO'),
       ('1644978322668560386', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'NO', NULL, 'YES', '02', '1645577359394390018', '1',
        '2022-12-03 15:19:23', '', '2023-04-11 08:03:08', 6, 'NO'),
       ('1644978322668560386', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'NO', NULL, 'YES', '03',
        '1645577359394390019', '', '2023-04-03 16:46:57', '', '2023-04-11 08:03:08', 5, 'NO'),
       ('1644978322668560386', 'LIST_ROW', '配置模型', 'configModel',
        ' this.$router.push({ path: \' / entityconfig / entityModel\', query: { id: row.id } })', NULL, 'NO', NULL, 'YES', 'configModel', 'NO', '00',
        '1645577930666983425', '', '2023-04-11 08:02:07', '', '2023-04-13 16:44:11', 9, 'NO'),
       ('1644978322668560386', 'LIST_ROW', '配置视图', 'configView',
        'this.$router.push({ path: \' / entityconfig / entityView\', query: { id: row.id } })', NULL, 'NO', NULL, 'YES', 'configView', 'NO', '01',
        '1645578174469292033', '', '2023-04-11 08:03:05', '', '2023-04-13 16:44:23', 8, 'NO'),
       ('1645624558534787074', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1645624783479504897', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1645624558534787074', 'LIST_PAGE', '新增', 'addWithId', 'this.$refs.addPage.init({ id: this.$route.query.id })', 'plus', 'NO', NULL, 'YES',
        'add', 'NO', '02', '1645624783479504898', '1', '2022-11-29 16:59:50', '', '2023-04-19 13:28:38', 14, 'NO'),
       ('1645624558534787074', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1645624783542419458', '1', '2022-12-02 10:25:33', '1', '2023-02-16 17:06:20', 4, 'NO'),
       ('1645624558534787074', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'NO', NULL, 'NO', '01', '1645625541587369985', '1',
        '2022-12-03 15:19:23', '', '2023-04-13 20:49:15', 4, 'NO'),
       ('1645624558534787074', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'NO', NULL, 'NO', '02',
        '1645625541587369986', '', '2023-04-03 16:46:57', '', '2023-04-13 20:49:20', 3, 'NO'),
       ('1645624558534787074', 'LIST_ROW', '配置属性', 'configModelProperty',
        'this.$router.push({ path: \' / entityconfig / entityModelProperty\', query: { id: row.id } })', NULL, 'NO', NULL, 'YES',
        'configModelProperty', 'NO', '00', '1645668487854579713', '', '2023-04-11 14:01:57', '', '2023-04-13 20:49:11', 2, 'NO'),
       ('1645685382146187265', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1645687219872096258', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1645685382146187265', 'LIST_PAGE', '新增', 'add', 'this.$refs.addPage.init({ id: this.$route.query.id })', 'plus', 'NO', NULL, 'YES', 'add',
        'NO', '02', '1645687219901456385', '1', '2022-11-29 16:59:50', '', '2023-04-15 08:35:41', 14, 'NO'),
       ('1645685382146187265', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1645687219901456386', '1', '2022-12-02 10:25:33', '', '2023-04-13 20:52:56', 5, 'NO'),
       ('1645685382146187265', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1645698796318908418', '1',
        '2022-12-03 15:19:23', '', '2023-04-19 14:41:06', 5, 'NO'),
       ('1645685382146187265', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '02',
        '1645698796386017281', '', '2023-04-03 16:46:57', '', '2023-04-19 14:41:15', 4, 'NO'),
       ('1646070570890080257', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1646073000742662146', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1646070570890080257', 'LIST_PAGE', '新增', 'addWithId', 'this.$refs.addPage.init({ id: this.$route.query.id })', 'plus', 'NO', NULL, 'YES',
        'add', 'NO', '02', '1646073000809771010', '1', '2022-11-29 16:59:50', '', '2023-04-13 09:11:51', 15, 'NO'),
       ('1646070570890080257', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1646073000809771011', '1', '2022-12-02 10:25:33', '1', '2023-02-16 17:06:20', 4, 'NO'),
       ('-1', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04', '1646074961286180865', '',
        '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1646070570890080257', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1646076210454769666', '', '2023-04-12 16:57:08', '', '2023-04-12 17:01:28', 1, 'NO'),
       ('1646070570890080257', 'LIST_ROW', '配置', 'configView', '', NULL, 'NO', NULL, 'YES', 'configView', 'NO', '00', '1646076900610715649', '',
        '2023-04-12 17:04:50', '', '2023-04-23 08:33:56', 3, 'NO'),
       ('1646070570890080257', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1646124457403617282', '1',
        '2022-12-03 15:19:23', '', '2023-04-13 09:20:49', 5, 'NO'),
       ('1646070570890080257', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '02',
        '1646124457403617283', '', '2023-04-03 16:46:57', '', '2023-04-13 09:20:43', 4, 'NO'),
       ('1645685382146187265', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1647037429437288450', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1647470434446266370', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1647470522505678850', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1647470434446266370', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1647470522581176321', '1',
        '2022-11-29 16:59:50', '', '2023-04-13 09:12:15', 13, 'NO'),
       ('1647470434446266370', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1647470522581176322', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1647470434446266370', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1647470522644090882', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1647470434446266370', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1647470932901548034', '1',
        '2022-12-03 15:19:23', '', '2023-04-13 09:13:16', 5, 'NO'),
       ('1647470434446266370', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '02',
        '1647470932968656898', '', '2023-04-03 16:46:57', '', '2023-04-13 09:16:01', 4, 'NO'),
       ('1641975231912390658', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1647946258417041410', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1641975231912390658', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1647946258417041411', '1',
        '2022-11-29 16:59:50', '', '2023-04-13 09:12:15', 13, 'NO'),
       ('1641975231912390658', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1647946258417041412', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1641975231912390658', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1647946258417041413', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1641975231912390658', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1647946302994104322', '1',
        '2022-12-03 15:19:23', '', '2023-04-13 09:13:16', 5, 'NO'),
       ('1641975231912390658', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '02',
        '1647946302994104323', '', '2023-04-03 16:46:57', '', '2023-04-13 09:16:01', 4, 'NO'),
       ('1645624558534787074', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1648559105261965314', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1648589167218778113', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1648589247799746561', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1648589167218778113', 'LIST_PAGE', '新增', 'add', '', 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1648589247799746562', '1',
        '2022-11-29 16:59:50', '', '2023-04-21 20:30:49', 15, 'NO'),
       ('1648589167218778113', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1648589247799746563', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1648589167218778113', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1648589247866855426', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1648589167218778113', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'YES', '02', '1648589452553084930', '1',
        '2022-12-03 15:19:23', '', '2023-04-22 10:52:00', 8, 'NO'),
       ('1648589167218778113', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'YES', '03',
        '1648589452553084931', '', '2023-04-03 16:46:57', '', '2023-04-22 10:52:07', 7, 'NO'),
       ('1648664997567430657', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1648664997844254722', '',
        '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1648664997844254723', '',
        '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1648664997844254724', '', '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1648664997844254725', '', '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1648664997911363585', '',
        '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '02',
        '1648664997911363586', '', '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648589167218778113', 'LIST_ROW', '启用', 'enable', NULL, NULL, 'NO', NULL, 'YES', 'enable', 'NO', '00', '1649606801506725889', '',
        '2023-04-19 20:00:09', '', '2023-04-22 10:51:39', 3, 'NO'),
       ('1648589167218778113', 'LIST_ROW', '停用', 'disable', NULL, NULL, 'NO', NULL, 'YES', 'disable', 'NO', '01', '1649606801569640449', '',
        '2023-04-19 20:00:37', '', '2023-04-22 10:51:39', 3, 'NO'),
       ('1649751408416714753', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1649751455162232834', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1649751408416714753', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1649751455162232835', '1',
        '2022-11-29 16:59:50', '', '2023-04-13 09:12:15', 13, 'NO'),
       ('1649751408416714753', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1649751455162232836', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1649751408416714753', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1649751455225147394', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1649751408416714753', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1649751513177845762', '1',
        '2022-12-03 15:19:23', '', '2023-04-24 08:22:26', 15, 'NO'),
       ('1649751408416714753', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'YES', '02',
        '1649751513240760322', '', '2023-04-03 16:46:57', '', '2023-04-24 08:22:26', 12, 'NO'),
       ('1649751408416714753', 'LIST_ROW', '启用', 'enable', NULL, NULL, 'NO', NULL, 'YES', 'enable', 'YES', '03', '1649751513240760323', '',
        '2023-04-19 20:00:09', '', '2023-04-24 08:22:26', 9, 'NO'),
       ('1649751408416714753', 'LIST_ROW', '停用', 'disable', NULL, NULL, 'NO', NULL, 'YES', 'disable', 'YES', '04', '1649751513240760324', '',
        '2023-04-19 20:00:37', '', '2023-04-24 08:22:26', 9, 'NO'),
       ('1649751408416714753', 'LIST_ROW', '用户组', 'configUserGroup',
        ' this.currentId = row.id\n      this.api.getUserGroup(row.id).then((res) => {\n        this.$refs.userGroup.init({ data: res.data })\n      })',
        NULL, 'NO', NULL, 'YES', 'configUserGroup', 'NO', '00', '1650289534033760258', '', '2023-04-24 08:04:20', '', '2023-04-28 13:18:59', 6, 'NO'),
       ('1649751408416714753', 'LIST_ROW', '重置密码', 'resetPassword', ' this.api.resetPassword(row.id)', NULL, 'YES', '是否重置密码？', 'YES',
        'resetPassword', 'YES', '05', '1650290247346458625', '', '2023-04-24 08:07:11', '', '2023-04-24 08:22:26', 4, 'NO'),
       ('1649751408416714753', 'LIST_ROW', '手工解锁', 'unlock',
        ' this.api.unlock(row.id)\n          .then(() => {\n            this.refresh()\n          })', NULL, 'YES', '是否解锁账号？', 'YES', 'unlock',
        'YES', '06', '1650290763879190529', '', '2023-04-24 08:09:14', '', '2023-04-24 08:22:26', 3, 'NO'),
       ('1650336635631480834', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1650336687552770049', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1650336635631480834', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1650336687619878913', '1',
        '2022-11-29 16:59:50', '', '2023-04-13 09:12:15', 13, 'NO'),
       ('1650336635631480834', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1650336687619878914', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1650336635631480834', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1650336687619878915', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1650336635631480834', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'YES', '01', '1650336889617559554', '1',
        '2022-12-03 15:19:23', '', '2023-04-28 13:44:46', 11, 'NO'),
       ('1650336635631480834', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'YES', '02',
        '1650336889617559555', '', '2023-04-03 16:46:57', '', '2023-04-28 13:44:51', 10, 'NO'),
       ('1650336635631480834', 'LIST_ROW', '启用', 'enable', NULL, NULL, 'NO', NULL, 'YES', 'enable', 'YES', '03', '1650336889680474113', '',
        '2023-04-19 20:00:09', '', '2023-04-28 13:14:15', 7, 'NO'),
       ('1650336635631480834', 'LIST_ROW', '停用', 'disable', NULL, NULL, 'NO', NULL, 'YES', 'disable', 'YES', '04', '1650336889680474114', '',
        '2023-04-19 20:00:37', '', '2023-04-28 13:14:15', 7, 'NO'),
       ('1650671993913110530', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1650672039362588673', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1650671993913110530', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1650672039362588674', '1',
        '2022-11-29 16:59:50', '', '2023-04-13 09:12:15', 13, 'NO'),
       ('1650671993913110530', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1650672039362588675', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1650671993913110530', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1650672039425503234', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1650671993913110530', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1650672563931607042', '1',
        '2022-12-03 15:19:23', '', '2023-04-13 09:13:16', 5, 'NO'),
       ('1650671993913110530', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '02',
        '1650672563973550082', '', '2023-04-03 16:46:57', '', '2023-04-13 09:16:01', 4, 'NO'),
       ('1650671993913110530', 'LIST_ROW', '启用', 'enable', NULL, NULL, 'NO', NULL, 'YES', 'enable', 'YES', '03', '1650672563973550083', '',
        '2023-04-19 20:00:09', '', '2023-04-25 09:26:28', 2, 'NO'),
       ('1650671993913110530', 'LIST_ROW', '停用', 'disable', NULL, NULL, 'NO', NULL, 'YES', 'disable', 'YES', '04', '1650672563973550084', '',
        '2023-04-19 20:00:37', '', '2023-04-25 09:26:32', 2, 'NO'),
       ('1651033706957164546', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1651033773839536129', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1651033706957164546', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1651033773839536130', '1',
        '2022-11-29 16:59:50', '', '2023-04-13 09:12:15', 13, 'NO'),
       ('1651033706957164546', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1651033773906644994', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1651033706957164546', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1651033773906644995', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1651033706957164546', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1651033900725620738', '1',
        '2022-12-03 15:19:23', '', '2023-04-13 09:13:16', 5, 'NO'),
       ('1651033706957164546', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '02',
        '1651033900792729602', '', '2023-04-03 16:46:57', '', '2023-04-13 09:16:01', 4, 'NO'),
       ('1651184645340971009', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1651184694594682881', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1651184645340971009', 'LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1651184694594682882', '1',
        '2022-11-29 16:59:50', '', '2023-04-13 09:12:15', 13, 'NO'),
       ('1651184645340971009', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1651184694661791745', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1651184645340971009', 'LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04',
        '1651184694661791746', '', '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('1651184645340971009', 'LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1651184861532176386', '1',
        '2022-12-03 15:19:23', '', '2023-04-13 09:13:16', 5, 'NO'),
       ('1651184645340971009', 'LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '02',
        '1651184861595090945', '', '2023-04-03 16:46:57', '', '2023-04-13 09:16:01', 4, 'NO'),
       ('1651184645340971009', 'LIST_ROW', '启用', 'enable', NULL, NULL, 'NO', NULL, 'YES', 'enable', 'YES', '03', '1651184861595090946', '',
        '2023-04-19 20:00:09', '', '2023-04-26 19:22:13', 2, 'NO'),
       ('1651184645340971009', 'LIST_ROW', '停用', 'disable', NULL, NULL, 'NO', NULL, 'YES', 'disable', 'YES', '04', '1651184861595090947', '',
        '2023-04-19 20:00:37', '', '2023-04-26 19:22:17', 2, 'NO'),
       ('1651198471625027585', 'LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1651198505913462786', '1',
        '2022-11-29 17:00:22', '', '2023-04-04 21:17:51', 10, 'NO'),
       ('1651198471625027585', 'LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03',
        '1651198535776907266', '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('1650336635631480834', 'LIST_ROW', '权限', 'configPermission',
        'this.currentId = row.id\n      this.api.getPermission(row.id).then((res) => {\n        this.$refs.permissionItem.init({ data: res.data })\n      })',
        NULL, 'NO', NULL, 'YES', 'configPermission', 'NO', '00', '1651817053539131394', '', '2023-04-28 13:14:10', '', '2023-04-28 13:43:42', 4,
        'NO'),
       ('1650336635631480834', 'LIST_ROW', '人员', 'configUser', NULL, NULL, 'NO', NULL, 'YES', 'configUser', 'NO', NULL, '1651817779933863938', '',
        '2023-04-28 13:17:03', '', '2023-04-28 13:17:03', 1, 'NO');

-- 导出  表 abc.cfg_view_button_template 结构
CREATE TABLE IF NOT EXISTS `cfg_view_button_template`
(
    `button_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'LIST_PAGE' COMMENT '按钮类型',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `content` varchar
(
    2000
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '内容',
    `icon` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图标',
    `confirm_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'NO' COMMENT '是否需确认',
    `confirm_message` varchar
(
    256
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '确认信息',
    `permission_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '是否控制权限',
    `permission_code` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限编码',
    `more_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'NO' COMMENT '是否用于更多',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='视图按钮模板';

-- 正在导出表  abc.cfg_view_button_template 的数据：~8 rows (大约)
INSERT INTO `cfg_view_button_template` (`button_type`, `name`, `code`, `content`, `icon`, `confirm_flag`, `confirm_message`, `permission_flag`,
                                        `permission_code`, `more_flag`, `order_no`, `id`, `create_id`, `create_time`, `update_id`, `update_time`,
                                        `version`, `delete_flag`)
VALUES ('LIST_PAGE', '新增', 'add', NULL, 'plus', 'NO', NULL, 'YES', 'add', 'NO', '02', '1597515665647853571', '1', '2022-11-29 16:59:50', '',
        '2023-04-13 09:12:15', 13, 'NO'),
       ('LIST_PAGE', '刷新', 'refresh', NULL, 'refresh', 'NO', '111', 'NO', NULL, 'NO', '01', '1597515800729608191', '1', '2022-11-29 17:00:22', '',
        '2023-04-04 21:17:51', 10, 'NO'),
       ('LIST_PAGE', '删除', 'batchRemove', NULL, 'delete', 'NO', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '03', '1598503607128162301',
        '1', '2022-12-02 10:25:33', '', '2023-04-13 09:16:17', 5, 'NO'),
       ('LIST_ROW', '修改', 'modify', NULL, 'edit', 'NO', NULL, 'YES', 'modify', 'NO', '01', '1598939938052603901', '1', '2022-12-03 15:19:23', '',
        '2023-04-13 09:13:16', 5, 'NO'),
       ('MODIFY_PAGE', '保存', 'save', NULL, NULL, 'NO', NULL, 'NO', NULL, 'NO', '02', '1631117040853028861', '1', '2023-03-02 10:19:42', '',
        '2023-04-05 20:22:51', 3, 'NO'),
       ('MODIFY_PAGE', '关闭', 'close', NULL, NULL, 'NO', NULL, 'NO', NULL, 'NO', '01', '1631117807261421571', '1', '2023-03-02 10:22:45', '',
        '2023-04-05 20:22:59', 4, 'NO'),
       ('LIST_ROW', '删除', 'remove', NULL, 'Delete', 'YES', '此操作将删除数据, 是否继续？', 'YES', 'remove', 'NO', '02', '1642810905850851331', '',
        '2023-04-03 16:46:57', '', '2023-04-13 09:16:01', 4, 'NO'),
       ('LIST_PAGE', '复制新增', 'addByCopy', NULL, 'CopyDocument', 'NO', NULL, 'YES', 'addByCopy', 'NO', '04', '1646074961286180861', '',
        '2023-04-12 16:57:08', '', '2023-04-13 09:16:36', 2, 'NO'),
       ('LIST_ROW', '启用', 'enable', NULL, NULL, 'NO', NULL, 'YES', 'enable', 'NO', '03', '1648657732663795713', '', '2023-04-19 20:00:09', '',
        '2023-04-19 20:00:09', 1, 'NO'),
       ('LIST_ROW', '停用', 'disable', NULL, NULL, 'NO', NULL, 'YES', 'disable', 'NO', '04', '1648657850746036226', '', '2023-04-19 20:00:37', '',
        '2023-04-19 20:00:37', 1, 'NO');

-- 导出  表 abc.cfg_view_property 结构
CREATE TABLE IF NOT EXISTS `cfg_view_property`
(
    `view` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '视图',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `data_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'STRING' COMMENT '数据类型',
    `dictionary_type` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字典类型',
    `widget_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '控件类型',
    `format_pattern` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '显示格式',
    `readonly_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '是否只读',
    `default_value` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '默认值',
    `show_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '是否显示',
    `show_expression` varchar
(
    1000
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '显示表达式',
    `require_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'NO' COMMENT '是否必填',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='视图属性';

-- 正在导出表  abc.cfg_view_property 的数据：~446 rows (大约)
INSERT INTO `cfg_view_property` (`view`, `name`, `code`, `data_type`, `dictionary_type`, `widget_type`, `format_pattern`, `readonly_flag`,
                                 `default_value`, `show_flag`, `show_expression`, `require_flag`, `order_no`, `id`, `create_id`, `create_time`,
                                 `update_id`, `update_time`, `version`, `delete_flag`)
VALUES ('1646859594433093634', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1647217925228105730', '',
        '2023-04-15 20:38:52', '', '2023-04-15 20:48:18', 2, 'YES'),
       ('1646859594433093634', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647217925236494338', '',
        '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646859594433093634', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647217925236494339', '',
        '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646859594433093634', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 'STRING', 'YES',
        NULL, 'YES', NULL, '1647217925236494340', '', '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646859594433093634', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647217925236494341',
        '', '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646859594433093634', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647217925236494342',
        '', '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646859594433093634', '显示格式', 'formatPattern', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647217925303603201',
        '', '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646859594433093634', '是否只读', 'readonlyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        NULL, '1647217925303603202', '', '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646859594433093634', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647217925303603203', '',
        '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646859594433093634', '是否显示', 'showFlag', 'DATA_DICTIONARY', 'ShowControl', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        NULL, '1647217925303603204', '', '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646859594433093634', '是否必填', 'requireFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1647217925303603205', '', '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646859594433093634', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647217925370712066', '',
        '2023-04-15 20:38:52', '', '2023-04-15 20:38:52', 1, 'YES'),
       ('1646862452985487361', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '00', '1647218131533336577', '',
        '2023-04-15 20:39:41', '', '2023-04-17 19:32:05', 8, 'NO'),
       ('1646862452985487361', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '01', '1647218131533336578', '',
        '2023-04-15 20:39:41', '', '2023-04-17 19:32:05', 8, 'NO'),
       ('1646862452985487361', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '02', '1647218131533336579', '',
        '2023-04-15 20:39:41', '', '2023-04-17 19:32:06', 8, 'NO'),
       ('1646862452985487361', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 'STRING', 'YES',
        NULL, 'YES', '03', '1647218131533336580', '', '2023-04-15 20:39:41', '', '2023-04-17 19:32:06', 8, 'NO'),
       ('1646862452985487361', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '04', '1647218131533336581',
        '', '2023-04-15 20:39:41', '', '2023-04-17 19:32:06', 8, 'NO'),
       ('1646862452985487361', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '05', '1647218131600445442',
        '', '2023-04-15 20:39:41', '', '2023-04-17 19:32:06', 8, 'NO'),
       ('1646862452985487361', '显示格式', 'formatPattern', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '06', '1647218131600445443',
        '', '2023-04-15 20:39:41', '', '2023-04-17 19:32:06', 8, 'NO'),
       ('1646862452985487361', '是否只读', 'readonlyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        '07', '1647218131600445444', '', '2023-04-15 20:39:41', '', '2023-04-17 19:32:06', 8, 'NO'),
       ('1646862452985487361', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '08', '1647218131600445445', '',
        '2023-04-15 20:39:41', '', '2023-04-17 19:32:06', 8, 'NO'),
       ('1646862452985487361', '是否显示', 'showFlag', 'DATA_DICTIONARY', 'ShowControl', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        '09', '1647218131600445446', '', '2023-04-15 20:39:41', '', '2023-04-17 19:32:06', 8, 'NO'),
       ('1646862452985487361', '是否必填', 'requireFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        '11', '1647218131600445447', '', '2023-04-15 20:39:41', '', '2023-04-17 19:32:06', 8, 'NO'),
       ('1646862452985487361', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '12', '1647218131600445448', '',
        '2023-04-15 20:39:41', '', '2023-04-17 19:32:06', 8, 'NO'),
       ('1646859594433093634', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', '00', '1647224733816643586', '',
        '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 6, 'NO'),
       ('1646859594433093634', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '01', '1647224733816643587', '',
        '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 5, 'NO'),
       ('1646859594433093634', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '02', '1647224733833420802', '',
        '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 5, 'NO'),
       ('1646859594433093634', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 'STRING', 'YES',
        NULL, 'YES', '03', '1647224733833420803', '', '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 6, 'NO'),
       ('1646859594433093634', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '04', '1647224733833420804',
        '', '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 5, 'NO'),
       ('1646859594433093634', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '05', '1647224733833420805',
        '', '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 5, 'NO'),
       ('1646859594433093634', '显示格式', 'formatPattern', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '06', '1647224733833420806',
        '', '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 5, 'NO'),
       ('1646859594433093634', '是否只读', 'readonlyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        '07', '1647224733833420807', '', '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 5, 'NO'),
       ('1646859594433093634', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '08', '1647224733896335362', '',
        '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 5, 'NO'),
       ('1646859594433093634', '是否显示', 'showFlag', 'DATA_DICTIONARY', 'ShowControl', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        '09', '1647224733896335363', '', '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 5, 'NO'),
       ('1646859594433093634', '是否必填', 'requireFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        '12', '1647224733896335364', '', '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 5, 'NO'),
       ('1646859594433093634', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '11', '1647224733896335365', '',
        '2023-04-15 21:05:55', '', '2023-04-17 19:32:21', 5, 'NO'),
       ('1646859594433093634', '123', '123', 'STRING', NULL, 'TEXT', NULL, 'YES', '213', 'YES', NULL, 'NO', NULL, '1647225326031396866', '',
        '2023-04-15 21:08:16', '', '2023-04-15 21:08:20', 1, 'YES'),
       ('1647404556346236930', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1647404655491194882', '',
        '2023-04-16 09:00:52', '', '2023-04-16 09:01:01', 2, 'NO'),
       ('1647404556346236930', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647404655491194883', '',
        '2023-04-16 09:00:52', '', '2023-04-16 09:00:52', 1, 'NO'),
       ('1647404556346236930', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647404655558303746', '',
        '2023-04-16 09:00:52', '', '2023-04-16 09:00:52', 1, 'NO'),
       ('1647404556346236930', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 'STRING', 'YES',
        NULL, 'YES', NULL, '1647404655558303747', '', '2023-04-16 09:00:52', '', '2023-04-16 09:00:52', 1, 'NO'),
       ('1647404556346236930', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647404655558303748',
        '', '2023-04-16 09:00:52', '', '2023-04-16 09:00:52', 1, 'NO'),
       ('1647404556346236930', '宽度', 'width', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647404655558303749', '',
        '2023-04-16 09:00:52', '', '2023-04-22 20:32:57', 2, 'NO'),
       ('1647404556346236930', '是否支持排序', 'sortableFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL,
        'YES', NULL, '1647404655558303750', '', '2023-04-16 09:00:52', '', '2023-04-16 09:00:52', 1, 'NO'),
       ('1647404556346236930', '格式化方法', 'formatFunction', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1647404655625412609', '', '2023-04-16 09:00:52', '', '2023-04-16 09:00:52', 1, 'NO'),
       ('1647404556346236930', '是否悬浮显示', 'showOverflowTooltipFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES',
        'YES', NULL, 'YES', NULL, '1647404655625412610', '', '2023-04-16 09:00:52', '', '2023-04-16 09:00:52', 1, 'NO'),
       ('1647404556346236930', '是否显示', 'showFlag', 'DATA_DICTIONARY', 'ShowControl', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        NULL, '1647404655625412611', '', '2023-04-16 09:00:52', '', '2023-04-16 09:00:52', 1, 'NO'),
       ('1647404556346236930', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647404655625412612', '',
        '2023-04-16 09:00:52', '', '2023-04-16 09:00:52', 1, 'NO'),
       ('1647404577783324674', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1647404785489453057', '',
        '2023-04-16 09:01:23', '', '2023-04-16 09:01:28', 2, 'NO'),
       ('1647404577783324674', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647404785552367618', '',
        '2023-04-16 09:01:23', '', '2023-04-16 09:01:23', 1, 'NO'),
       ('1647404577783324674', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647404785552367619', '',
        '2023-04-16 09:01:23', '', '2023-04-16 09:01:23', 1, 'NO'),
       ('1647404577783324674', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 'STRING', 'YES',
        NULL, 'YES', NULL, '1647404785552367620', '', '2023-04-16 09:01:23', '', '2023-04-16 09:01:23', 1, 'NO'),
       ('1647404577783324674', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647404785552367621',
        '', '2023-04-16 09:01:23', '', '2023-04-16 09:01:23', 1, 'NO'),
       ('1647404577783324674', '宽度', 'width', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647404785552367622', '',
        '2023-04-16 09:01:23', '', '2023-04-22 20:33:20', 2, 'NO'),
       ('1647404577783324674', '是否支持排序', 'sortableFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL,
        'YES', NULL, '1647404785552367623', '', '2023-04-16 09:01:23', '', '2023-04-16 09:01:23', 1, 'NO'),
       ('1647404577783324674', '格式化方法', 'formatFunction', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1647404785619476482', '', '2023-04-16 09:01:23', '', '2023-04-16 09:01:23', 1, 'NO'),
       ('1647404577783324674', '是否悬浮显示', 'showOverflowTooltipFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES',
        'YES', NULL, 'YES', NULL, '1647404785619476483', '', '2023-04-16 09:01:23', '', '2023-04-16 09:01:23', 1, 'NO'),
       ('1647404577783324674', '是否显示', 'showFlag', 'DATA_DICTIONARY', 'ShowControl', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        NULL, '1647404785619476484', '', '2023-04-16 09:01:23', '', '2023-04-16 09:01:23', 1, 'NO'),
       ('1647404577783324674', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647404785619476485', '',
        '2023-04-16 09:01:23', '', '2023-04-16 09:01:23', 1, 'NO'),
       ('1647471028607176706', '按钮类型', 'buttonType', 'DATA_DICTIONARY', 'ViewButtonType', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'LIST_PAGE', 'YES',
        NULL, 'YES', NULL, '1647471055668826113', '', '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471028607176706', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647471055668826114', '',
        '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471028607176706', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647471055668826115', '',
        '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471028607176706', '内容', 'content', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471055735934977', '',
        '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471028607176706', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471055735934978', '',
        '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471028607176706', '是否需确认', 'confirmFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1647471055735934979', '', '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471028607176706', '确认信息', 'confirmMessage', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471055803043841',
        '', '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471028607176706', '是否控制权限', 'permissionFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL,
        'YES', NULL, '1647471055803043842', '', '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471028607176706', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471055803043843',
        '', '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471028607176706', '是否用于更多', 'moreFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1647471055865958401', '', '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471028607176706', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471055865958402', '',
        '2023-04-16 13:24:43', '', '2023-04-16 13:24:43', 1, 'NO'),
       ('1647471167728046082', '按钮类型', 'buttonType', 'DATA_DICTIONARY', 'ViewButtonType', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'LIST_PAGE', 'YES',
        NULL, 'YES', NULL, '1647471167795154946', '', '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471167728046082', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647471167795154947', '',
        '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471167728046082', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647471167795154948', '',
        '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471167728046082', '内容', 'content', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471167795154949', '',
        '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471167728046082', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471167795154950', '',
        '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471167728046082', '是否需确认', 'confirmFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1647471167795154951', '', '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471167728046082', '确认信息', 'confirmMessage', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471167795154952',
        '', '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471167728046082', '是否控制权限', 'permissionFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL,
        'YES', NULL, '1647471167795154953', '', '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471167728046082', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471167795154954',
        '', '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471167728046082', '是否用于更多', 'moreFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1647471167795154955', '', '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471167728046082', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471167862263809', '',
        '2023-04-16 13:25:10', '', '2023-04-16 13:25:10', 1, 'NO'),
       ('1647471292642807809', '按钮类型', 'buttonType', 'DATA_DICTIONARY', 'ViewButtonType', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'LIST_PAGE', 'YES',
        NULL, 'YES', NULL, '1647471292642807810', '', '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647471292642807809', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647471292709916673', '',
        '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647471292642807809', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647471292709916674', '',
        '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647471292642807809', '内容', 'content', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471292709916675', '',
        '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647471292642807809', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471292709916676', '',
        '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647471292642807809', '是否需确认', 'confirmFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1647471292709916677', '', '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647471292642807809', '确认信息', 'confirmMessage', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471292709916678',
        '', '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647471292642807809', '是否控制权限', 'permissionFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL,
        'YES', NULL, '1647471292709916679', '', '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647471292642807809', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471292709916680',
        '', '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647471292642807809', '是否用于更多', 'moreFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1647471292709916681', '', '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647471292642807809', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647471292777025538', '',
        '2023-04-16 13:25:39', '', '2023-04-16 13:25:39', 1, 'NO'),
       ('1647480604186402818', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1647480628635000833', '',
        '2023-04-16 14:02:45', '', '2023-04-16 14:03:10', 2, 'NO'),
       ('1647480604186402818', '按钮类型', 'buttonType', 'DATA_DICTIONARY', 'ViewButtonType', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'LIST_PAGE', 'YES',
        NULL, 'YES', NULL, '1647480628702109698', '', '2023-04-16 14:02:45', '', '2023-04-16 14:02:45', 1, 'NO'),
       ('1647480604186402818', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647480628702109699', '',
        '2023-04-16 14:02:45', '', '2023-04-16 14:02:45', 1, 'NO'),
       ('1647480604186402818', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647480628702109700', '',
        '2023-04-16 14:02:45', '', '2023-04-16 14:02:45', 1, 'NO'),
       ('1647480604186402818', '内容', 'content', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647480628702109701', '',
        '2023-04-16 14:02:45', '', '2023-04-16 14:02:45', 1, 'NO'),
       ('1647480604186402818', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647480628702109702', '',
        '2023-04-16 14:02:45', '', '2023-04-16 14:02:45', 1, 'NO'),
       ('1647480604186402818', '是否需确认', 'confirmFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1647480628769218562', '', '2023-04-16 14:02:45', '', '2023-04-16 14:02:45', 1, 'NO'),
       ('1647480604186402818', '确认信息', 'confirmMessage', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'CUSTOM',
        'entityData.confirmFlag == $constant.YES', 'NO', NULL, '1647480628769218563', '', '2023-04-16 14:02:45', '', '2023-04-17 19:55:00', 3, 'NO'),
       ('1647480604186402818', '是否控制权限', 'permissionFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL,
        'YES', NULL, '1647480628769218564', '', '2023-04-16 14:02:45', '', '2023-04-16 14:02:45', 1, 'NO'),
       ('1647480604186402818', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'CUSTOM',
        'entityData.permissionFlag == $constant.YES', 'NO', NULL, '1647480628769218565', '', '2023-04-16 14:02:45', '', '2023-04-17 19:54:37', 3,
        'NO'),
       ('1647480604186402818', '是否用于更多', 'moreFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1647480628769218566', '', '2023-04-16 14:02:45', '', '2023-04-16 14:02:45', 1, 'NO'),
       ('1647480604186402818', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647480628836327425', '',
        '2023-04-16 14:02:45', '', '2023-04-16 14:02:45', 1, 'NO'),
       ('1647480841919553537', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1647480841986662402', '',
        '2023-04-16 14:03:36', '', '2023-04-16 14:03:36', 1, 'NO'),
       ('1647480841919553537', '按钮类型', 'buttonType', 'DATA_DICTIONARY', 'ViewButtonType', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'LIST_PAGE', 'YES',
        NULL, 'YES', NULL, '1647480841986662403', '', '2023-04-16 14:03:36', '', '2023-04-16 14:03:36', 1, 'NO'),
       ('1647480841919553537', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647480841986662404', '',
        '2023-04-16 14:03:36', '', '2023-04-16 14:03:36', 1, 'NO'),
       ('1647480841919553537', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647480841986662405', '',
        '2023-04-16 14:03:36', '', '2023-04-16 14:03:36', 1, 'NO'),
       ('1647480841919553537', '内容', 'content', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647480841986662406', '',
        '2023-04-16 14:03:36', '', '2023-04-16 14:03:36', 1, 'NO'),
       ('1647480841919553537', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647480841986662407', '',
        '2023-04-16 14:03:36', '', '2023-04-16 14:03:36', 1, 'NO'),
       ('1647480841919553537', '是否需确认', 'confirmFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1647480841986662408', '', '2023-04-16 14:03:36', '', '2023-04-16 14:03:36', 1, 'NO'),
       ('1647480841919553537', '确认信息', 'confirmMessage', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647480841986662409',
        '', '2023-04-16 14:03:36', '', '2023-04-16 14:03:36', 1, 'NO'),
       ('1647480841919553537', '是否控制权限', 'permissionFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL,
        'YES', NULL, '1647480841986662410', '', '2023-04-16 14:03:36', '', '2023-04-16 14:03:36', 1, 'NO'),
       ('1647480841919553537', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'CUSTOM',
        'entityData.permissionFlag == $constant.YES', 'NO', NULL, '1647480841986662411', '', '2023-04-16 14:03:36', '', '2023-04-17 20:01:34', 2,
        'NO'),
       ('1647480841919553537', '是否用于更多', 'moreFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', '', 'YES',
        NULL, '1647480841986662412', '', '2023-04-16 14:03:36', '', '2023-04-17 20:05:03', 3, 'NO'),
       ('1647480841919553537', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647480841986662413', '',
        '2023-04-16 14:03:36', '', '2023-04-16 14:03:36', 1, 'NO'),
       ('1646114040468049921', '视图类型', 'entityViewType', 'DATA_DICTIONARY', 'EntityViewType', 'DROP_DOWN_LIST', NULL, 'NO', '', 'NO', NULL, 'YES',
        NULL, '1647835189837152257', '', '2023-04-17 13:31:39', '', '2023-04-17 13:31:56', 2, 'YES'),
       ('1646114040468049921', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647835189837152258', '',
        '2023-04-17 13:31:39', '', '2023-04-17 13:31:39', 1, 'YES'),
       ('1646114040468049921', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647835189904261121', '',
        '2023-04-17 13:31:39', '', '2023-04-17 13:31:39', 1, 'YES'),
       ('1646114040468049921', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647835189904261122', '',
        '2023-04-17 13:31:39', '', '2023-04-17 13:31:39', 1, 'YES'),
       ('1646114040468049921', '初始化前', 'beforeInit', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647835189904261123',
        '', '2023-04-17 13:31:39', '', '2023-04-17 13:31:39', 1, 'YES'),
       ('1646114040468049921', '初始化后', 'afterInit', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647835189904261124',
        '', '2023-04-17 13:31:39', '', '2023-04-17 13:31:39', 1, 'YES'),
       ('1646114040468049921', '验证数据', 'validateData', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1647835189971369985', '', '2023-04-17 13:31:39', '', '2023-04-17 13:31:39', 1, 'YES'),
       ('1646114040468049921', '保存前', 'beforeSave', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647835189971369986',
        '', '2023-04-17 13:31:39', '', '2023-04-17 13:31:39', 1, 'YES'),
       ('1646114040468049921', '保存后', 'afterSave', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647835189971369987',
        '', '2023-04-17 13:31:39', '', '2023-04-17 13:31:39', 1, 'YES'),
       ('1646114040468049921', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647835189971369988', '',
        '2023-04-17 13:31:39', '', '2023-04-17 13:31:39', 1, 'YES'),
       ('1646114040468049921', '实体模型', 'entityModel', 'ENTITY', 'BaseModel', 'ENTITY_SELECT', NULL, 'NO', '', 'NO', NULL, 'YES', NULL,
        '1647835189971369989', '', '2023-04-17 13:31:39', '', '2023-04-17 13:32:13', 2, 'YES'),
       ('1646114040468049921', '实体', 'entity', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1647835189971369990', '',
        '2023-04-17 13:31:39', '', '2023-04-17 13:32:21', 2, 'YES'),
       ('1646114040468049921', '视图类型', 'entityViewType', 'DATA_DICTIONARY', 'EntityViewType', 'DROP_DOWN_LIST', NULL, 'NO', '', 'YES', NULL,
        'YES', '00', '1647835897701449729', '', '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '实体模型', 'entityModel', 'ENTITY', 'BaseModel', 'ENTITY_SELECT', NULL, 'NO', '', 'YES', NULL, 'YES', '01',
        '1647835897768558593', '', '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '02', '1647835897768558594', '',
        '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '03', '1647835897768558595', '',
        '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '05', '1647835897768558596', '',
        '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '初始化前', 'beforeInit', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '06', '1647835897768558597',
        '', '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '初始化后', 'afterInit', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '07', '1647835897835667458',
        '', '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '验证数据', 'validateData', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '08',
        '1647835897835667459', '', '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '保存前', 'beforeSave', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '09', '1647835897902776322',
        '', '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '保存后', 'afterSave', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '10', '1647835897902776323',
        '', '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '11', '1647835897902776324', '',
        '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114040468049921', '实体', 'entity', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', '12', '1647835897902776325', '',
        '2023-04-17 13:34:28', '', '2023-04-23 08:54:30', 4, 'NO'),
       ('1646114409386446849', '视图类型', 'entityViewType', 'DATA_DICTIONARY', 'EntityViewType', 'DROP_DOWN_LIST', NULL, 'NO', '', 'YES', NULL,
        'YES', '00', '1647861669178753026', '', '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '实体模型', 'entityModel', 'ENTITY', 'BaseModel', 'ENTITY_SELECT', NULL, 'NO', '', 'YES', NULL, 'YES', '01',
        '1647861669245861889', '', '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '02', '1647861669245861890', '',
        '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '03', '1647861669245861891', '',
        '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '05', '1647861669245861892', '',
        '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '初始化前', 'beforeInit', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '06', '1647861669312970753',
        '', '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '初始化后', 'afterInit', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '07', '1647861669312970754',
        '', '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '验证数据', 'validateData', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '08',
        '1647861669312970755', '', '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '保存前', 'beforeSave', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '09', '1647861669312970756',
        '', '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '保存后', 'afterSave', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '10', '1647861669312970757',
        '', '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '11', '1647861669312970758', '',
        '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114409386446849', '实体', 'entity', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', '12', '1647861669312970759', '',
        '2023-04-17 15:16:52', '', '2023-04-23 08:54:49', 3, 'NO'),
       ('1646114523928694785', '视图类型', 'entityViewType', 'DATA_DICTIONARY', 'EntityViewType', 'DROP_DOWN_LIST', NULL, 'NO', '', 'YES', NULL,
        'YES', '00', '1647861803404869633', '', '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '实体模型', 'entityModel', 'ENTITY', 'BaseModel', 'ENTITY_SELECT', NULL, 'NO', '', 'YES', NULL, 'YES', '01',
        '1647861803404869634', '', '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '02', '1647861803404869635', '',
        '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '03', '1647861803467784194', '',
        '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '05', '1647861803467784195', '',
        '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '初始化前', 'beforeInit', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '06', '1647861803467784196',
        '', '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '初始化后', 'afterInit', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '07', '1647861803467784197',
        '', '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '验证数据', 'validateData', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '08',
        '1647861803467784198', '', '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '保存前', 'beforeSave', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '09', '1647861803467784199',
        '', '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '保存后', 'afterSave', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', '10', '1647861803534893057',
        '', '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '11', '1647861803534893058', '',
        '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1646114523928694785', '实体', 'entity', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', '12', '1647861803534893059', '',
        '2023-04-17 15:17:24', '', '2023-04-23 08:54:59', 3, 'NO'),
       ('1646862452985487361', '显示表达式', 'showExpression', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '10',
        '1647925894735577090', '', '2023-04-17 19:32:05', '', '2023-04-17 19:32:06', 2, 'NO'),
       ('1646859594433093634', '显示表达式', 'showExpression', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '10',
        '1647925959508213762', '', '2023-04-17 19:32:20', '', '2023-04-17 19:32:21', 2, 'NO'),
       ('1647946485639266306', '参数名称', 'paramName', 'STRING', NULL, 'TEXT', NULL, 'NO', '', 'YES', NULL, 'YES', NULL, '1647946595215458306', '',
        '2023-04-17 20:54:20', '', '2023-04-17 20:54:20', 1, 'NO'),
       ('1647946485639266306', '参数编码', 'paramKey', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647946595215458307', '',
        '2023-04-17 20:54:20', '', '2023-04-17 20:54:20', 1, 'NO'),
       ('1647946485639266306', '参数值', 'paramValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647946595282567170', '',
        '2023-04-17 20:54:20', '', '2023-04-17 20:54:20', 1, 'NO'),
       ('1647946485639266306', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647946595282567171', '',
        '2023-04-17 20:54:20', '', '2023-04-17 20:54:20', 1, 'NO'),
       ('1647946510154973186', '参数名称', 'paramName', 'STRING', NULL, 'TEXT', NULL, 'NO', '', 'YES', NULL, 'YES', NULL, '1647946646130114561', '',
        '2023-04-17 20:54:32', '', '2023-04-17 20:54:32', 1, 'NO'),
       ('1647946510154973186', '参数编码', 'paramKey', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647946646130114562', '',
        '2023-04-17 20:54:32', '', '2023-04-17 20:54:32', 1, 'NO'),
       ('1647946510154973186', '参数值', 'paramValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647946646130114563', '',
        '2023-04-17 20:54:32', '', '2023-04-17 20:54:32', 1, 'NO'),
       ('1647946510154973186', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647946646130114564', '',
        '2023-04-17 20:54:32', '', '2023-04-17 20:54:32', 1, 'NO'),
       ('1647946675444105218', '参数名称', 'paramName', 'STRING', NULL, 'TEXT', NULL, 'NO', '', 'YES', NULL, 'YES', NULL, '1647946675444105219', '',
        '2023-04-17 20:54:39', '', '2023-04-17 20:54:39', 1, 'NO'),
       ('1647946675444105218', '参数编码', 'paramKey', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1647946675444105220', '',
        '2023-04-17 20:54:39', '', '2023-04-17 20:54:39', 1, 'NO'),
       ('1647946675444105218', '参数值', 'paramValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647946675444105221', '',
        '2023-04-17 20:54:39', '', '2023-04-17 20:54:39', 1, 'NO'),
       ('1647946675444105218', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1647946675444105222', '',
        '2023-04-17 20:54:39', '', '2023-04-17 20:54:39', 1, 'NO'),
       ('1645661443412058113', '父级模型', 'parentModel', 'DATA_DICTIONARY', 'BaseModel', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'BUSINESS_MODEL', 'YES',
        NULL, 'YES', NULL, '1648557166725959681', '', '2023-04-19 13:20:32', '', '2023-04-19 13:20:32', 1, 'NO'),
       ('1645661443412058113', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648557166725959682', '',
        '2023-04-19 13:20:32', '', '2023-04-19 13:20:32', 1, 'NO'),
       ('1645661443412058113', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648557166725959683', '',
        '2023-04-19 13:20:32', '', '2023-04-19 13:20:32', 1, 'NO'),
       ('1645661443412058113', '是否主模型', 'mainModelFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL,
        'YES', NULL, '1648557166725959684', '', '2023-04-19 13:20:32', '', '2023-04-19 13:20:32', 1, 'NO'),
       ('1645661443412058113', '是否自关联', 'selfReferenceFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL,
        'YES', NULL, '1648557166725959685', '', '2023-04-19 13:20:32', '', '2023-04-19 13:20:32', 1, 'NO'),
       ('1645661443412058113', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648557166793068545', '',
        '2023-04-19 13:20:32', '', '2023-04-19 13:20:32', 1, 'NO'),
       ('1645661443412058113', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648557166793068546', '',
        '2023-04-19 13:20:32', '', '2023-04-19 13:20:32', 1, 'NO'),
       ('1645661443412058113', '实体', 'entity', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1648557166793068547', '',
        '2023-04-19 13:20:32', '', '2023-04-19 13:20:53', 2, 'NO'),
       ('1645661989745319938', '父级模型', 'parentModel', 'DATA_DICTIONARY', 'BaseModel', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'BUSINESS_MODEL', 'YES',
        NULL, 'YES', NULL, '1648557289480654850', '', '2023-04-19 13:21:01', '', '2023-04-19 13:21:01', 1, 'NO'),
       ('1645661989745319938', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648557289480654851', '',
        '2023-04-19 13:21:01', '', '2023-04-19 13:21:01', 1, 'NO'),
       ('1645661989745319938', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648557289547763713', '',
        '2023-04-19 13:21:01', '', '2023-04-19 13:21:01', 1, 'NO'),
       ('1645661989745319938', '是否主模型', 'mainModelFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL,
        'YES', NULL, '1648557289547763714', '', '2023-04-19 13:21:01', '', '2023-04-19 13:21:01', 1, 'NO'),
       ('1645661989745319938', '是否自关联', 'selfReferenceFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL,
        'YES', NULL, '1648557289547763715', '', '2023-04-19 13:21:01', '', '2023-04-19 13:21:01', 1, 'NO'),
       ('1645661989745319938', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648557289547763716', '',
        '2023-04-19 13:21:01', '', '2023-04-19 13:21:01', 1, 'NO'),
       ('1645661989745319938', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648557289547763717', '',
        '2023-04-19 13:21:01', '', '2023-04-19 13:21:01', 1, 'NO'),
       ('1645661989745319938', '实体', 'entity', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1648557289547763718', '',
        '2023-04-19 13:21:01', '', '2023-04-19 13:21:09', 2, 'NO'),
       ('1645662010179969026', '父级模型', 'parentModel', 'DATA_DICTIONARY', 'BaseModel', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'BUSINESS_MODEL', 'YES',
        NULL, 'YES', NULL, '1648557352089030657', '', '2023-04-19 13:21:16', '', '2023-04-19 13:21:16', 1, 'NO'),
       ('1645662010179969026', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648557352089030658', '',
        '2023-04-19 13:21:16', '', '2023-04-19 13:21:16', 1, 'NO'),
       ('1645662010179969026', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648557352089030659', '',
        '2023-04-19 13:21:16', '', '2023-04-19 13:21:16', 1, 'NO'),
       ('1645662010179969026', '是否主模型', 'mainModelFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL,
        'YES', NULL, '1648557352089030660', '', '2023-04-19 13:21:16', '', '2023-04-19 13:21:16', 1, 'NO'),
       ('1645662010179969026', '是否自关联', 'selfReferenceFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL,
        'YES', NULL, '1648557352089030661', '', '2023-04-19 13:21:16', '', '2023-04-19 13:21:16', 1, 'NO'),
       ('1645662010179969026', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648557352089030662', '',
        '2023-04-19 13:21:16', '', '2023-04-19 13:21:16', 1, 'NO'),
       ('1645662010179969026', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648557352160333825', '',
        '2023-04-19 13:21:16', '', '2023-04-19 13:21:16', 1, 'NO'),
       ('1645662010179969026', '实体', 'entity', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1648557352160333826', '',
        '2023-04-19 13:21:16', '', '2023-04-19 13:21:23', 2, 'NO'),
       ('1647034718641836034', '实体模型', 'entityModel', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1648577758699196418',
        '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:43', 2, 'NO'),
       ('1647034718641836034', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648577758699196419', '',
        '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648577758699196420', '',
        '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 'STRING', 'YES',
        NULL, 'YES', NULL, '1648577758699196421', '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648577758699196422',
        '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648577758699196423',
        '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '显示格式', 'formatPattern', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648577758699196424',
        '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '最大长度', 'maxLength', 'INTEGER', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648577758766305282', '',
        '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '小数位数', 'decimalLength', 'INTEGER', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648577758766305283',
        '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648577758766305284', '',
        '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '是否可为空', 'nullFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'NO',
        NULL, '1648577758766305285', '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '是否唯一', 'uniqueFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1648577758766305286', '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '唯一性参照', 'entityModelProperty', 'STRING', NULL, 'TEXT', NULL, 'NO', '', 'YES', NULL, 'NO', NULL,
        '1648577758766305287', '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '是否主属性', 'mainFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1648577758766305288', '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '是否上级属性', 'parentPropertyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES',
        NULL, 'YES', NULL, '1648577758766305289', '', '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648577758766305290', '',
        '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647034718641836034', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648577758766305291', '',
        '2023-04-19 14:42:21', '', '2023-04-19 14:42:21', 1, 'NO'),
       ('1647036417238487041', '实体模型', 'entityModel', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1648578063113392130',
        '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:41', 2, 'NO'),
       ('1647036417238487041', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648578063113392131', '',
        '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648578063113392132', '',
        '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 'STRING', 'YES',
        NULL, 'YES', NULL, '1648578063176306690', '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578063176306691',
        '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648578063176306692',
        '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '显示格式', 'formatPattern', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578063176306693',
        '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '最大长度', 'maxLength', 'INTEGER', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578063176306694', '',
        '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '小数位数', 'decimalLength', 'INTEGER', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578063176306695',
        '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578063176306696', '',
        '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '是否可为空', 'nullFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'NO',
        NULL, '1648578063176306697', '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '是否唯一', 'uniqueFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1648578063176306698', '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '唯一性参照', 'entityModelProperty', 'STRING', NULL, 'TEXT', NULL, 'NO', '', 'YES', NULL, 'NO', NULL,
        '1648578063176306699', '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '是否主属性', 'mainFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1648578063243415554', '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '是否上级属性', 'parentPropertyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES',
        NULL, 'YES', NULL, '1648578063243415555', '', '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578063243415556', '',
        '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036417238487041', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578063243415557', '',
        '2023-04-19 14:43:34', '', '2023-04-19 14:43:34', 1, 'NO'),
       ('1647036588785520642', '实体模型', 'entityModel', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1648578354072260610',
        '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:49', 2, 'NO'),
       ('1647036588785520642', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648578354072260611', '',
        '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648578354072260612', '',
        '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 'STRING', 'YES',
        NULL, 'YES', NULL, '1648578354072260613', '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578354072260614',
        '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648578354072260615',
        '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '显示格式', 'formatPattern', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578354072260616',
        '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '最大长度', 'maxLength', 'INTEGER', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578354139369474', '',
        '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '小数位数', 'decimalLength', 'INTEGER', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578354139369475',
        '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578354139369476', '',
        '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '是否可为空', 'nullFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'NO',
        NULL, '1648578354139369477', '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '是否唯一', 'uniqueFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1648578354139369478', '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '唯一性参照', 'entityModelProperty', 'STRING', NULL, 'TEXT', NULL, 'NO', '', 'YES', NULL, 'NO', NULL,
        '1648578354139369479', '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '是否主属性', 'mainFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES', NULL, 'YES',
        NULL, '1648578354139369480', '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '是否上级属性', 'parentPropertyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES',
        NULL, 'YES', NULL, '1648578354139369481', '', '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578354206478338', '',
        '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1647036588785520642', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1648578354206478339', '',
        '2023-04-19 14:44:43', '', '2023-04-19 14:44:43', 1, 'NO'),
       ('1648589615623430146', '组织机构', 'organization', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1648589652248092674', '', '2023-04-19 15:29:37', '', '2023-04-19 15:29:37', 1, 'NO'),
       ('1648589615623430146', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589652248092675', '',
        '2023-04-19 15:29:37', '', '2023-04-19 15:29:37', 1, 'NO'),
       ('1648589615623430146', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589652248092676', '',
        '2023-04-19 15:29:37', '', '2023-04-19 15:29:37', 1, 'NO'),
       ('1648589615623430146', '类型', 'type', 'DATA_DICTIONARY', 'OrganizationType', 'DROP_DOWN_LIST', NULL, 'NO', 'DEPARTMENT', 'YES', NULL, 'YES',
        NULL, '1648589652311007234', '', '2023-04-19 15:29:37', '', '2023-04-19 15:29:37', 1, 'NO'),
       ('1648589615623430146', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'DROP_DOWN_LIST', NULL, 'NO', 'NORMAL', 'YES', NULL, 'YES', NULL,
        '1648589652311007235', '', '2023-04-19 15:29:37', '', '2023-04-19 15:29:37', 1, 'NO'),
       ('1648589615623430146', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589652311007236', '',
        '2023-04-19 15:29:37', '', '2023-04-19 15:29:37', 1, 'NO'),
       ('1648589615623430146', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589652311007237', '',
        '2023-04-19 15:29:37', '', '2023-04-19 15:29:37', 1, 'NO'),
       ('1648589714055356417', '组织机构', 'organization', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1648589714055356418', '', '2023-04-19 15:29:52', '', '2023-04-19 15:29:52', 1, 'NO'),
       ('1648589714055356417', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589714055356419', '',
        '2023-04-19 15:29:52', '', '2023-04-19 15:29:52', 1, 'NO'),
       ('1648589714055356417', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589714055356420', '',
        '2023-04-19 15:29:52', '', '2023-04-19 15:29:52', 1, 'NO'),
       ('1648589714055356417', '类型', 'type', 'DATA_DICTIONARY', 'OrganizationType', 'DROP_DOWN_LIST', NULL, 'NO', 'DEPARTMENT', 'YES', NULL, 'YES',
        NULL, '1648589714055356421', '', '2023-04-19 15:29:52', '', '2023-04-19 15:29:52', 1, 'NO'),
       ('1648589714055356417', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'DROP_DOWN_LIST', NULL, 'NO', 'NORMAL', 'YES', NULL, 'YES', NULL,
        '1648589714055356422', '', '2023-04-19 15:29:52', '', '2023-04-19 15:29:52', 1, 'NO'),
       ('1648589714055356417', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589714122465282', '',
        '2023-04-19 15:29:52', '', '2023-04-19 15:29:52', 1, 'NO'),
       ('1648589714055356417', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589714122465283', '',
        '2023-04-19 15:29:52', '', '2023-04-19 15:29:52', 1, 'NO'),
       ('1648589837569220609', '组织机构', 'organization', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1648589837569220610', '', '2023-04-19 15:30:21', '', '2023-04-19 15:30:21', 1, 'NO'),
       ('1648589837569220609', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589837569220611', '',
        '2023-04-19 15:30:21', '', '2023-04-19 15:30:21', 1, 'NO'),
       ('1648589837569220609', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589837569220612', '',
        '2023-04-19 15:30:21', '', '2023-04-19 15:30:21', 1, 'NO'),
       ('1648589837569220609', '类型', 'type', 'DATA_DICTIONARY', 'OrganizationType', 'DROP_DOWN_LIST', NULL, 'NO', 'DEPARTMENT', 'YES', NULL, 'YES',
        NULL, '1648589837569220613', '', '2023-04-19 15:30:21', '', '2023-04-19 15:30:21', 1, 'NO'),
       ('1648589837569220609', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'DROP_DOWN_LIST', NULL, 'NO', 'NORMAL', 'YES', NULL, 'YES', NULL,
        '1648589837569220614', '', '2023-04-19 15:30:21', '', '2023-04-19 15:30:21', 1, 'NO'),
       ('1648589837569220609', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589837632135169', '',
        '2023-04-19 15:30:21', '', '2023-04-19 15:30:21', 1, 'NO'),
       ('1648589837569220609', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1648589837632135170', '',
        '2023-04-19 15:30:21', '', '2023-04-19 15:30:21', 1, 'NO'),
       ('1649753938068566017', '组织机构', 'organization', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1649754114845896705', '', '2023-04-22 20:36:47', '', '2023-04-22 20:36:47', 1, 'NO'),
       ('1649753938068566017', '姓名', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1649754114913005570', '',
        '2023-04-22 20:36:47', '', '2023-04-22 20:36:47', 1, 'NO'),
       ('1649753938068566017', '账号', 'account', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1649754114913005571', '',
        '2023-04-22 20:36:47', '', '2023-04-22 20:36:47', 1, 'NO'),
       ('1649753938068566017', '密码', 'password', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1649754114913005572', '',
        '2023-04-22 20:36:47', '', '2023-04-23 20:01:25', 1, 'YES'),
       ('1649753938068566017', '性别', 'gender', 'DATA_DICTIONARY', 'Gender', 'RADIO_BUTTON_GROUP', NULL, 'NO', '', 'YES', NULL, 'YES', NULL,
        '1649754114913005573', '', '2023-04-22 20:36:47', '', '2023-04-22 20:36:47', 1, 'NO'),
       ('1649753938068566017', '出生日期', 'birthday', 'DATETIME', NULL, 'DATETIME', 'DAY', 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1649754114913005574', '', '2023-04-22 20:36:47', '', '2023-04-24 08:17:12', 2, 'NO'),
       ('1649753938068566017', '手机号', 'telephone', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1649754114913005575', '',
        '2023-04-22 20:36:47', '', '2023-04-22 20:36:47', 1, 'NO'),
       ('1649753938068566017', '邮箱', 'email', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1649754114980114433', '',
        '2023-04-22 20:36:47', '', '2023-04-22 20:36:47', 1, 'NO'),
       ('1649753938068566017', '职位', 'position', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1649754114980114434', '',
        '2023-04-22 20:36:47', '', '2023-04-22 20:36:47', 1, 'NO'),
       ('1649753938068566017', '状态', 'status', 'DATA_DICTIONARY', 'UserStatus', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NORMAL', 'YES', NULL, 'YES',
        NULL, '1649754114980114435', '', '2023-04-22 20:36:47', '', '2023-04-22 20:36:47', 1, 'NO'),
       ('1649753938068566017', '强制修改密码', 'forceChangePasswordFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES',
        'YES', NULL, 'YES', NULL, '1649754114980114436', '', '2023-04-22 20:36:47', '', '2023-04-22 20:39:04', 3, 'NO'),
       ('1649753938068566017', '失败登录次数', 'fail_login_count', 'INTEGER', NULL, 'TEXT', NULL, 'NO', '0', 'YES', NULL, 'YES', NULL,
        '1649754114980114437', '', '2023-04-22 20:36:47', '', '2023-04-22 20:37:35', 1, 'YES'),
       ('1649753938068566017', '锁定时间', 'lockTime', 'DATETIME', NULL, 'DATETIME', 'SECOND', 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1649754114980114438', '', '2023-04-22 20:36:47', '', '2023-04-22 20:37:37', 1, 'YES'),
       ('1649753938068566017', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1649754114980114439', '',
        '2023-04-22 20:36:47', '', '2023-04-22 20:36:47', 1, 'NO'),
       ('1649754414826713090', '组织机构', 'organization', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '00',
        '1649754414826713091', '', '2023-04-22 20:37:58', '', '2023-04-22 20:39:38', 3, 'NO'),
       ('1649754414826713090', '姓名', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '01', '1649754414826713092', '',
        '2023-04-22 20:37:58', '', '2023-04-22 20:39:38', 3, 'NO'),
       ('1649754414826713090', '账号', 'account', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '02', '1649754414826713093', '',
        '2023-04-22 20:37:58', '', '2023-04-22 20:39:38', 3, 'NO'),
       ('1649754414826713090', '密码', 'password', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '03', '1649754414826713094', '',
        '2023-04-22 20:37:58', '', '2023-04-23 20:24:08', 3, 'YES'),
       ('1649754414826713090', '性别', 'gender', 'DATA_DICTIONARY', 'Gender', 'RADIO_BUTTON_GROUP', NULL, 'NO', '', 'YES', NULL, 'YES', '04',
        '1649754414889627649', '', '2023-04-22 20:37:58', '', '2023-04-22 20:39:38', 3, 'NO'),
       ('1649754414826713090', '出生日期', 'birthday', 'DATETIME', NULL, 'DATETIME', 'DAY', 'NO', NULL, 'YES', NULL, 'NO', '05',
        '1649754414889627650', '', '2023-04-22 20:37:58', '', '2023-04-24 08:17:32', 4, 'NO'),
       ('1649754414826713090', '手机号', 'telephone', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '06', '1649754414889627651', '',
        '2023-04-22 20:37:58', '', '2023-04-22 20:39:38', 3, 'NO'),
       ('1649754414826713090', '邮箱', 'email', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '07', '1649754414889627652', '',
        '2023-04-22 20:37:58', '', '2023-04-22 20:39:38', 3, 'NO'),
       ('1649754414826713090', '职位', 'position', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '08', '1649754414889627653', '',
        '2023-04-22 20:37:58', '', '2023-04-22 20:39:38', 3, 'NO'),
       ('1649754414826713090', '状态', 'status', 'DATA_DICTIONARY', 'UserStatus', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NORMAL', 'YES', NULL, 'YES',
        '09', '1649754414889627654', '', '2023-04-22 20:37:58', '', '2023-04-22 20:39:38', 3, 'NO'),
       ('1649754414826713090', '强制修改密码', 'forceChangePasswordFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES',
        NULL, 'YES', '10', '1649754414889627655', '', '2023-04-22 20:37:58', '', '2023-04-22 20:39:38', 5, 'NO'),
       ('1649754414826713090', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '13', '1649754414889627656', '',
        '2023-04-22 20:37:58', '', '2023-04-22 20:39:38', 3, 'NO'),
       ('1649754722730569730', '组织机构', 'organization', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1649754722730569731', '', '2023-04-22 20:39:12', '', '2023-04-22 20:39:12', 1, 'NO'),
       ('1649754722730569730', '姓名', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1649754722730569732', '',
        '2023-04-22 20:39:12', '', '2023-04-22 20:39:12', 1, 'NO'),
       ('1649754722730569730', '账号', 'account', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1649754722730569733', '',
        '2023-04-22 20:39:12', '', '2023-04-22 20:39:12', 1, 'NO'),
       ('1649754722730569730', '密码', 'password', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1649754722730569734', '',
        '2023-04-22 20:39:12', '', '2023-04-24 09:42:58', 1, 'YES'),
       ('1649754722730569730', '性别', 'gender', 'DATA_DICTIONARY', 'Gender', 'RADIO_BUTTON_GROUP', NULL, 'NO', '', 'YES', NULL, 'YES', NULL,
        '1649754722730569735', '', '2023-04-22 20:39:12', '', '2023-04-22 20:39:12', 1, 'NO'),
       ('1649754722730569730', '出生日期', 'birthday', 'DATETIME', NULL, 'DATETIME', 'DAY', 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1649754722730569736', '', '2023-04-22 20:39:12', '', '2023-04-24 08:17:44', 2, 'NO'),
       ('1649754722730569730', '手机号', 'telephone', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1649754722730569737', '',
        '2023-04-22 20:39:12', '', '2023-04-22 20:39:12', 1, 'NO'),
       ('1649754722730569730', '邮箱', 'email', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1649754722730569738', '',
        '2023-04-22 20:39:12', '', '2023-04-22 20:39:12', 1, 'NO'),
       ('1649754722730569730', '职位', 'position', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1649754722730569739', '',
        '2023-04-22 20:39:12', '', '2023-04-22 20:39:12', 1, 'NO'),
       ('1649754722730569730', '状态', 'status', 'DATA_DICTIONARY', 'UserStatus', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NORMAL', 'YES', NULL, 'YES',
        NULL, '1649754722793484290', '', '2023-04-22 20:39:12', '', '2023-04-22 20:39:12', 1, 'NO'),
       ('1649754722730569730', '强制修改密码', 'forceChangePasswordFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES',
        'YES', NULL, 'YES', NULL, '1649754722793484291', '', '2023-04-22 20:39:12', '', '2023-04-22 20:39:12', 1, 'NO'),
       ('1649754722730569730', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1649754722793484292', '',
        '2023-04-22 20:39:12', '', '2023-04-22 20:39:12', 1, 'NO'),
       ('1649754414826713090', '失败登录次数', 'fail_login_count', 'INTEGER', NULL, 'TEXT', NULL, 'NO', '0', 'YES', NULL, 'YES', '11',
        '1649754820977946626', '', '2023-04-22 20:39:35', '', '2023-04-23 20:02:40', 3, 'YES'),
       ('1649754414826713090', '锁定时间', 'lockTime', 'DATETIME', NULL, 'DATETIME', 'SECOND', 'NO', NULL, 'YES', NULL, 'NO', '12',
        '1649754832269012993', '', '2023-04-22 20:39:38', '', '2023-04-23 20:02:46', 2, 'YES'),
       ('1646114040468049921', '主参照视图', 'mainReferenceViewFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES',
        NULL, 'YES', '04', '1649939769584549890', '', '2023-04-23 08:54:30', '', '2023-04-23 08:54:30', 2, 'NO'),
       ('1646114409386446849', '主参照视图', 'mainReferenceViewFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES',
        NULL, 'YES', '04', '1649939847460192258', '', '2023-04-23 08:54:49', '', '2023-04-23 08:54:49', 2, 'NO'),
       ('1646114523928694785', '主参照视图', 'mainReferenceViewFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NO', 'YES',
        NULL, 'YES', '04', '1649939888421765121', '', '2023-04-23 08:54:58', '', '2023-04-23 08:54:59', 2, 'NO'),
       ('1644978953978421250', '模块', 'module', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650003808452956162',
        '', '2023-04-23 13:08:58', '', '2023-04-23 13:08:58', 1, 'NO'),
       ('1644978953978421250', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650003808452956163', '',
        '2023-04-23 13:08:58', '', '2023-04-23 13:08:58', 1, 'NO'),
       ('1644978953978421250', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650003808499093506', '',
        '2023-04-23 13:08:58', '', '2023-04-23 13:08:58', 1, 'NO'),
       ('1644978953978421250', '作者', 'author', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650003808499093507', '',
        '2023-04-23 13:08:58', '', '2023-04-23 13:08:58', 1, 'NO'),
       ('1644978953978421250', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650003808499093508', '',
        '2023-04-23 13:08:58', '', '2023-04-23 13:08:58', 1, 'NO'),
       ('1644978953978421250', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650003808499093509', '',
        '2023-04-23 13:08:58', '', '2023-04-23 13:08:58', 1, 'NO'),
       ('1647160664963153921', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1650317089973514241', '',
        '2023-04-24 09:53:50', '', '2023-04-24 09:54:43', 2, 'NO'),
       ('1647160664963153921', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650317090036428802', '',
        '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160664963153921', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650317090036428803', '',
        '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160664963153921', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 'STRING', 'YES',
        NULL, 'YES', NULL, '1650317090036428804', '', '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160664963153921', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650317090036428805',
        '', '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160664963153921', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650317090103537666',
        '', '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160664963153921', '匹配规则', 'matchRule', 'DATA_DICTIONARY', 'TextPatternRule', 'DROP_DOWN_LIST', NULL, 'NO', 'LK', 'YES', NULL, 'NO',
        NULL, '1650317090103537667', '', '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160664963153921', '显示格式', 'formatPattern', 'DATA_DICTIONARY', 'DatetimeFormat', 'DROP_DOWN_LIST', NULL, 'NO', '', 'YES', NULL, 'NO',
        NULL, '1650317090103537668', '', '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160664963153921', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650317090103537669', '',
        '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160664963153921', '是否只读', 'readonlyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        NULL, '1650317090103537670', '', '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160664963153921', '是否显示', 'showFlag', 'DATA_DICTIONARY', 'ShowControl', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        NULL, '1650317090170646529', '', '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160664963153921', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650317090170646530', '',
        '2023-04-24 09:53:50', '', '2023-04-24 09:53:50', 1, 'NO'),
       ('1647160941803995138', '视图', 'view', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'NO', NULL, 'YES', NULL, '1650317158462304258', '',
        '2023-04-24 09:54:07', '', '2023-04-24 09:54:52', 2, 'NO'),
       ('1647160941803995138', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650317158525218818', '',
        '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1647160941803995138', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650317158525218819', '',
        '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1647160941803995138', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', 'DROP_DOWN_LIST', NULL, 'NO', 'STRING', 'YES',
        NULL, 'YES', NULL, '1650317158525218820', '', '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1647160941803995138', '字典类型', 'dictionaryType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650317158525218821',
        '', '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1647160941803995138', '控件类型', 'widgetType', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650317158592327682',
        '', '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1647160941803995138', '匹配规则', 'matchRule', 'DATA_DICTIONARY', 'TextPatternRule', 'DROP_DOWN_LIST', NULL, 'NO', 'LK', 'YES', NULL, 'NO',
        NULL, '1650317158592327683', '', '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1647160941803995138', '显示格式', 'formatPattern', 'DATA_DICTIONARY', 'DatetimeFormat', 'DROP_DOWN_LIST', NULL, 'NO', '', 'YES', NULL, 'NO',
        NULL, '1650317158592327684', '', '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1647160941803995138', '默认值', 'defaultValue', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650317158592327685', '',
        '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1647160941803995138', '是否只读', 'readonlyFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        NULL, '1650317158592327686', '', '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1647160941803995138', '是否显示', 'showFlag', 'DATA_DICTIONARY', 'ShowControl', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'YES', 'YES', NULL, 'YES',
        NULL, '1650317158592327687', '', '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1647160941803995138', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650317158592327688', '',
        '2023-04-24 09:54:07', '', '2023-04-24 09:54:07', 1, 'NO'),
       ('1650337116860755969', '上级', 'userGroup', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1650337142915772418', '', '2023-04-24 11:13:31', '', '2023-04-24 11:13:31', 1, 'NO'),
       ('1650337116860755969', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650337142915772419', '',
        '2023-04-24 11:13:31', '', '2023-04-24 11:13:31', 1, 'NO'),
       ('1650337116860755969', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650337142915772420', '',
        '2023-04-24 11:13:31', '', '2023-04-24 14:54:45', 2, 'NO'),
       ('1650337116860755969', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NORMAL', 'YES', NULL, 'YES', NULL,
        '1650337142915772421', '', '2023-04-24 11:13:31', '', '2023-04-24 11:13:31', 1, 'NO'),
       ('1650337116860755969', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650337142982881281', '',
        '2023-04-24 11:13:31', '', '2023-04-24 11:13:31', 1, 'NO'),
       ('1650337116860755969', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650337142982881282', '',
        '2023-04-24 11:13:31', '', '2023-04-24 11:13:31', 1, 'NO'),
       ('1650337274377842689', '上级', 'userGroup', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1650337274377842690', '', '2023-04-24 11:14:03', '', '2023-04-24 11:14:03', 1, 'NO'),
       ('1650337274377842689', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650337274377842691', '',
        '2023-04-24 11:14:03', '', '2023-04-24 11:14:03', 1, 'NO'),
       ('1650337274377842689', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650337274377842692', '',
        '2023-04-24 11:14:03', '', '2023-04-24 14:54:59', 2, 'NO'),
       ('1650337274377842689', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NORMAL', 'YES', NULL, 'YES', NULL,
        '1650337274444951554', '', '2023-04-24 11:14:03', '', '2023-04-24 11:14:03', 1, 'NO'),
       ('1650337274377842689', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650337274444951555', '',
        '2023-04-24 11:14:03', '', '2023-04-24 11:14:03', 1, 'NO'),
       ('1650337274377842689', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650337274444951556', '',
        '2023-04-24 11:14:03', '', '2023-04-24 11:14:03', 1, 'NO'),
       ('1650337350240219137', '上级', 'userGroup', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1650337350240219138', '', '2023-04-24 11:14:21', '', '2023-04-24 11:14:21', 1, 'NO'),
       ('1650337350240219137', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650337350240219139', '',
        '2023-04-24 11:14:21', '', '2023-04-24 11:14:21', 1, 'NO'),
       ('1650337350240219137', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650337350240219140', '',
        '2023-04-24 11:14:21', '', '2023-04-24 11:14:21', 1, 'NO'),
       ('1650337350240219137', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NORMAL', 'YES', NULL, 'YES', NULL,
        '1650337350240219141', '', '2023-04-24 11:14:21', '', '2023-04-24 11:14:21', 1, 'NO'),
       ('1650337350240219137', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650337350240219142', '',
        '2023-04-24 11:14:21', '', '2023-04-24 11:14:21', 1, 'NO'),
       ('1650337350240219137', '备注', 'remark', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650337350240219143', '',
        '2023-04-24 11:14:21', '', '2023-04-24 11:14:21', 1, 'NO'),
       ('1650672680004775937', '上级', 'permissionItem', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1650672710237319170', '', '2023-04-25 09:26:57', '', '2023-04-25 09:26:57', 1, 'YES'),
       ('1650672680004775937', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650672710237319171', '',
        '2023-04-25 09:26:57', '', '2023-04-25 09:26:57', 1, 'YES'),
       ('1650672680004775937', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650672710237319172', '',
        '2023-04-25 09:26:57', '', '2023-04-25 09:26:57', 1, 'YES'),
       ('1650672680004775937', '类型', 'type', 'DATA_DICTIONARY', 'PermissionType', 'DROP_DOWN_LIST', NULL, 'NO', '', 'YES', NULL, 'YES', NULL,
        '1650672710237319173', '', '2023-04-25 09:26:57', '', '2023-04-25 09:26:57', 1, 'YES'),
       ('1650672680004775937', '组件', 'component', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'CUSTOM',
        'entityData.type==\'MENU\' || entityData.type==\'PAGE\'', 'NO', NULL, '1650672710237319174', '', '2023-04-25 09:26:57', '',
        '2023-04-25 09:29:16', 2, 'YES'),
       ('1650672680004775937', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650672710304428034', '',
        '2023-04-25 09:26:57', '', '2023-04-25 09:26:57', 1, 'YES'),
       ('1650672680004775937', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', '', 'NO', NULL, '1650672710304428035',
        '', '2023-04-25 09:26:57', '', '2023-04-25 09:31:09', 2, 'YES'),
       ('1650672680004775937', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NORMAL', 'YES', NULL, 'NO', NULL,
        '1650672710304428036', '', '2023-04-25 09:26:57', '', '2023-04-25 09:26:57', 1, 'YES'),
       ('1650672680004775937', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650672710304428037', '',
        '2023-04-25 09:26:57', '', '2023-04-25 09:26:57', 1, 'YES'),
       ('1650673931077578754', '上级', 'permissionItem', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '00',
        '1650673931077578755', '', '2023-04-25 09:31:48', '', '2023-04-25 13:40:08', 4, 'NO'),
       ('1650673931077578754', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '01', '1650673931077578756', '',
        '2023-04-25 09:31:48', '', '2023-04-25 13:40:08', 4, 'NO'),
       ('1650673931077578754', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '02', '1650673931144687618', '',
        '2023-04-25 09:31:48', '', '2023-04-25 13:40:08', 4, 'NO'),
       ('1650673931077578754', '类型', 'type', 'DATA_DICTIONARY', 'PermissionType', 'DROP_DOWN_LIST', NULL, 'NO', '', 'YES', NULL, 'YES', '03',
        '1650673931144687619', '', '2023-04-25 09:31:48', '', '2023-04-25 13:40:08', 4, 'NO'),
       ('1650673931077578754', '组件', 'component', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'CUSTOM',
        'entityData.type==\'MENU\' || entityData.type==\'PAGE\'', 'NO', NULL, '1650673931144687620', '', '2023-04-25 09:31:48', '',
        '2023-04-25 13:39:54', 1, 'YES'),
       ('1650673931077578754', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '05', '1650673931144687621', '',
        '2023-04-25 09:31:48', '', '2023-04-25 13:40:08', 4, 'NO'),
       ('1650673931077578754', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', '', 'NO', '04', '1650673931144687622',
        '', '2023-04-25 09:31:48', '', '2023-04-25 13:40:00', 2, 'YES'),
       ('1650673931077578754', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NORMAL', 'YES', NULL, 'NO', '06',
        '1650673931144687623', '', '2023-04-25 09:31:48', '', '2023-04-25 13:40:08', 4, 'NO'),
       ('1650673931077578754', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '07', '1650673931144687624', '',
        '2023-04-25 09:31:48', '', '2023-04-25 13:40:08', 4, 'NO'),
       ('1650674030222536706', '上级', 'permissionItem', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '00',
        '1650674030222536707', '', '2023-04-25 09:32:12', '', '2023-04-25 13:40:38', 4, 'NO'),
       ('1650674030222536706', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '01', '1650674030222536708', '',
        '2023-04-25 09:32:12', '', '2023-04-25 13:40:38', 4, 'NO'),
       ('1650674030222536706', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '02', '1650674030222536709', '',
        '2023-04-25 09:32:12', '', '2023-04-25 13:40:38', 4, 'NO'),
       ('1650674030222536706', '类型', 'type', 'DATA_DICTIONARY', 'PermissionType', 'DROP_DOWN_LIST', NULL, 'NO', '', 'YES', NULL, 'YES', '03',
        '1650674030289645570', '', '2023-04-25 09:32:12', '', '2023-04-25 13:40:38', 4, 'NO'),
       ('1650674030222536706', '组件', 'component', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'CUSTOM',
        'entityData.type==\'MENU\' || entityData.type==\'PAGE\'', 'NO', '06', '1650674030289645571', '', '2023-04-25 09:32:12', '',
        '2023-04-25 13:40:38', 4, 'NO'),
       ('1650674030222536706', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '07', '1650674030289645572', '',
        '2023-04-25 09:32:12', '', '2023-04-25 13:40:38', 4, 'NO'),
       ('1650674030222536706', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', '', 'NO', '04', '1650674030289645573',
        '', '2023-04-25 09:32:12', '', '2023-04-25 13:40:38', 4, 'NO'),
       ('1650674030222536706', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NORMAL', 'YES', NULL, 'NO', '08',
        '1650674030289645574', '', '2023-04-25 09:32:12', '', '2023-04-25 13:40:38', 4, 'NO'),
       ('1650674030222536706', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '09', '1650674030289645575', '',
        '2023-04-25 09:32:12', '', '2023-04-25 13:40:38', 4, 'NO'),
       ('1650672680004775937', '上级', 'permissionItem', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1650736057548828673', '', '2023-04-25 13:38:40', '', '2023-04-25 13:38:40', 1, 'NO'),
       ('1650672680004775937', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650736057548828674', '',
        '2023-04-25 13:38:40', '', '2023-04-25 13:38:40', 1, 'NO'),
       ('1650672680004775937', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1650736057548828675', '',
        '2023-04-25 13:38:40', '', '2023-04-25 13:38:40', 1, 'NO'),
       ('1650672680004775937', '类型', 'type', 'DATA_DICTIONARY', 'PermissionType', 'DROP_DOWN_LIST', NULL, 'NO', '', 'YES', NULL, 'YES', NULL,
        '1650736057615937538', '', '2023-04-25 13:38:40', '', '2023-04-25 13:38:40', 1, 'NO'),
       ('1650672680004775937', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650736057615937539',
        '', '2023-04-25 13:38:40', '', '2023-04-25 13:39:05', 1, 'YES'),
       ('1650672680004775937', '视图编码', 'viewCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'CUSTOM',
        'entityData.type == \'MENU\' || entityData.type == \'PAGE\'', 'NO', NULL, '1650736057615937540', '', '2023-04-25 13:38:40', '',
        '2023-04-25 20:16:13', 2, 'NO'),
       ('1650672680004775937', '组件', 'component', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650736057678852098', '',
        '2023-04-25 13:38:40', '', '2023-04-25 13:39:34', 1, 'YES'),
       ('1650672680004775937', '图标', 'icon', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650736057678852099', '',
        '2023-04-25 13:38:40', '', '2023-04-25 13:38:40', 1, 'NO'),
       ('1650672680004775937', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', 'NORMAL', 'YES', NULL, 'NO', NULL,
        '1650736057745960961', '', '2023-04-25 13:38:40', '', '2023-04-25 13:38:40', 1, 'NO'),
       ('1650672680004775937', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1650736057745960962', '',
        '2023-04-25 13:38:40', '', '2023-04-25 13:38:40', 1, 'NO'),
       ('1650673931077578754', '视图编码', 'viewCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '04', '1650736410856026113', '',
        '2023-04-25 13:40:04', '', '2023-04-25 13:40:08', 3, 'NO'),
       ('1650674030222536706', '视图编码', 'viewCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '05', '1650736549742014466', '',
        '2023-04-25 13:40:37', '', '2023-04-25 13:40:38', 2, 'NO'),
       ('1651033967490551809', '上级', 'dictionaryType', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1651033993889501185', '', '2023-04-26 09:22:34', '', '2023-04-26 09:55:02', 1, 'YES'),
       ('1651033967490551809', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '01', '1651033993889501186', '',
        '2023-04-26 09:22:34', '', '2023-04-26 09:55:05', 2, 'NO'),
       ('1651033967490551809', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '02', '1651033993956610050', '',
        '2023-04-26 09:22:34', '', '2023-04-26 09:55:05', 2, 'NO'),
       ('1651033967490551809', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '03', '1651033993956610051', '',
        '2023-04-26 09:22:34', '', '2023-04-26 09:55:05', 2, 'NO'),
       ('1651034297791991809', '上级', 'dictionaryType', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1651034297859100674', '', '2023-04-26 09:23:46', '', '2023-04-26 09:55:16', 1, 'YES'),
       ('1651034297791991809', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '01', '1651034297859100675', '',
        '2023-04-26 09:23:46', '', '2023-04-26 09:55:18', 2, 'NO'),
       ('1651034297791991809', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '02', '1651034297859100676', '',
        '2023-04-26 09:23:46', '', '2023-04-26 09:55:18', 2, 'NO'),
       ('1651034297791991809', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', '03', '1651034297859100677', '',
        '2023-04-26 09:23:46', '', '2023-04-26 09:55:18', 2, 'NO'),
       ('1651034515786747906', '上级', 'dictionaryType', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1651034515786747907', '', '2023-04-26 09:24:38', '', '2023-04-26 13:53:08', 3, 'NO'),
       ('1651034515786747906', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1651034515786747908', '',
        '2023-04-26 09:24:38', '', '2023-04-26 09:24:38', 1, 'NO'),
       ('1651034515786747906', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1651034515786747909', '',
        '2023-04-26 09:24:38', '', '2023-04-26 09:24:38', 1, 'NO'),
       ('1651034515786747906', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1651034515786747910', '',
        '2023-04-26 09:24:38', '', '2023-04-26 09:24:38', 1, 'NO'),
       ('1651033967490551809', '上级', 'dictionaryType', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '00',
        '1651042175948992513', '', '2023-04-26 09:55:04', '', '2023-04-26 13:52:44', 3, 'NO'),
       ('1651034297791991809', '上级', 'dictionaryType', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', '00',
        '1651042233008304130', '', '2023-04-26 09:55:18', '', '2023-04-26 13:52:54', 3, 'NO'),
       ('1651185031766392833', '字典类型', 'dictionaryType', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1651185055757811714', '', '2023-04-26 19:22:49', '', '2023-04-26 19:22:49', 1, 'NO'),
       ('1651185031766392833', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1651185055824920577', '',
        '2023-04-26 19:22:50', '', '2023-04-26 19:22:50', 1, 'NO'),
       ('1651185031766392833', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1651185055824920578', '',
        '2023-04-26 19:22:50', '', '2023-04-26 19:22:50', 1, 'NO'),
       ('1651185031766392833', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', '', 'YES', NULL, 'YES', NULL,
        '1651185055824920579', '', '2023-04-26 19:22:50', '', '2023-04-26 19:22:50', 1, 'NO'),
       ('1651185031766392833', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1651185055824920580', '',
        '2023-04-26 19:22:50', '', '2023-04-26 19:22:50', 1, 'NO'),
       ('1651185359832268802', '字典类型', 'dictionaryType', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1651185359832268803', '', '2023-04-26 19:24:02', '', '2023-04-26 19:24:02', 1, 'NO'),
       ('1651185359832268802', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1651185359832268804', '',
        '2023-04-26 19:24:02', '', '2023-04-26 19:24:02', 1, 'NO'),
       ('1651185359832268802', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1651185359899377666', '',
        '2023-04-26 19:24:02', '', '2023-04-26 19:24:02', 1, 'NO'),
       ('1651185359832268802', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', '', 'YES', NULL, 'YES', NULL,
        '1651185359899377667', '', '2023-04-26 19:24:02', '', '2023-04-26 19:24:02', 1, 'NO'),
       ('1651185359832268802', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1651185359899377668', '',
        '2023-04-26 19:24:02', '', '2023-04-26 19:24:02', 1, 'NO'),
       ('1651185414270140418', '字典类型', 'dictionaryType', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL,
        '1651185414270140419', '', '2023-04-26 19:24:15', '', '2023-04-26 19:24:15', 1, 'NO'),
       ('1651185414270140418', '名称', 'name', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1651185414270140420', '',
        '2023-04-26 19:24:15', '', '2023-04-26 19:24:15', 1, 'NO'),
       ('1651185414270140418', '编码', 'code', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'YES', NULL, '1651185414270140421', '',
        '2023-04-26 19:24:15', '', '2023-04-26 19:24:15', 1, 'NO'),
       ('1651185414270140418', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'RADIO_BUTTON_GROUP', NULL, 'NO', '', 'YES', NULL, 'YES', NULL,
        '1651185414337249282', '', '2023-04-26 19:24:15', '', '2023-04-26 19:24:15', 1, 'NO'),
       ('1651185414270140418', '排序', 'orderNo', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1651185414337249283', '',
        '2023-04-26 19:24:15', '', '2023-04-26 19:24:15', 1, 'NO'),
       ('1651199009330606081', '内容', 'content', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1651199032382500866', '',
        '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '日志类型', 'logType', 'DATA_DICTIONARY', 'LogType', 'DROP_DOWN_LIST', NULL, 'NO', '', 'YES', NULL, 'NO', NULL,
        '1651199032382500867', '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '请求时间', 'requestTime', 'DATETIME', NULL, 'DATETIME', 'SECOND', 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1651199032382500868', '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '请求参数', 'requestParam', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1651199032382500869', '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '请求路径', 'requestPath', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1651199032382500870', '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '请求方法', 'requestMethod', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1651199032445415426',
        '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '操作人标识', 'operatorId', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1651199032445415427',
        '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '操作人账号', 'operatorAccount', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1651199032445415428', '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '操作人姓名', 'operatorName', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1651199032445415429',
        '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '操作人ip', 'operatorIp', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1651199032445415430', '',
        '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '响应结果', 'responseCode', 'STRING', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL, '1651199032445415431',
        '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '响应数据', 'responseData', 'STRING', NULL, 'TEXTAREA', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1651199032512524290', '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO'),
       ('1651199009330606081', '执行耗时ms', 'timeConsuming', 'INTEGER', NULL, 'TEXT', NULL, 'NO', NULL, 'YES', NULL, 'NO', NULL,
        '1651199032512524291', '', '2023-04-26 20:18:22', '', '2023-04-26 20:18:22', 1, 'NO');

-- 导出  表 abc.cfg_view_query_condition 结构
CREATE TABLE IF NOT EXISTS `cfg_view_query_condition`
(
    `view` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '视图',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `data_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'STRING' COMMENT '数据类型',
    `dictionary_type` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字典类型',
    `widget_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '控件类型',
    `match_rule` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT 'NO' COMMENT '匹配规则',
    `format_pattern` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '显示格式',
    `default_value` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '默认值',
    `readonly_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '是否只读',
    `show_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '是否显示',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='视图查询条件';

-- 正在导出表  abc.cfg_view_query_condition 的数据：~49 rows (大约)
INSERT INTO `cfg_view_query_condition` (`view`, `name`, `code`, `data_type`, `dictionary_type`, `widget_type`, `match_rule`, `format_pattern`,
                                        `default_value`, `readonly_flag`, `show_flag`, `order_no`, `id`, `create_id`, `create_time`, `update_id`,
                                        `update_time`, `version`, `delete_flag`)
VALUES ('1641975231912390658', '参数名称', 'paramName', 'STRING', NULL, 'TEXT', 'LK', NULL, '', 'NO', 'YES', '00', '1643227851578724354', '',
        '2023-04-04 20:23:44', '', '2023-04-04 20:23:47', 3, 'NO'),
       ('1641975231912390658', '参数编码', 'paramKey', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1643227863364718593', '',
        '2023-04-04 20:23:47', '', '2023-04-04 20:23:47', 2, 'NO'),
       ('1643425456518680578', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1643425704603373570', '',
        '2023-04-05 09:29:56', '', '2023-04-18 14:41:25', 21, 'NO'),
       ('1643425456518680578', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '02', '1643425724757004290', '',
        '2023-04-05 09:30:01', '', '2023-04-18 14:41:25', 20, 'NO'),
       ('1644957439652581378', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1644957440059428866', '',
        '2023-04-09 14:56:30', '', '2023-04-09 14:56:30', 1, 'NO'),
       ('1644957439652581378', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '02', '1644957440122343425', '',
        '2023-04-09 14:56:30', '', '2023-04-09 14:56:30', 1, 'NO'),
       ('1644957439652581378', '应用', 'app', 'DATA_DICTIONARY', 'AppCode', 'DROP_DOWN_LIST', NULL, NULL, '', 'NO', 'YES', '00',
        '1644957440122343426', '', '2023-04-09 14:56:30', '', '2023-04-09 20:38:17', 2, 'NO'),
       ('1644978322668560386', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1644978586850992130', '',
        '2023-04-09 16:20:32', '', '2023-04-10 10:49:55', 3, 'NO'),
       ('1644978322668560386', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '02', '1644978586850992131', '',
        '2023-04-09 16:20:32', '', '2023-04-10 10:49:55', 3, 'NO'),
       ('1644978322668560386', '作者', 'author', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '03', '1644978586850992132', '',
        '2023-04-09 16:20:32', '', '2023-04-10 10:49:55', 3, 'NO'),
       ('1644979290651009026', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1644979290651009028', '',
        '2023-04-09 16:23:20', '', '2023-04-10 10:51:33', 2, 'NO'),
       ('1644979290651009026', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '02', '1644979290651009029', '',
        '2023-04-09 16:23:20', '', '2023-04-10 10:51:33', 2, 'NO'),
       ('1644979290651009026', '作者', 'author', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '03', '1644979290651009030', '',
        '2023-04-09 16:23:20', '', '2023-04-10 10:51:33', 2, 'NO'),
       ('1643425456518680578', '应用', 'app', 'DATA_DICTIONARY', 'AppCode', 'DROP_DOWN_LIST', NULL, NULL, '', 'NO', 'YES', '00',
        '1645043182806773761', '', '2023-04-09 20:37:13', '', '2023-04-18 14:41:25', 18, 'NO'),
       ('1644978322668560386', '模块', 'module', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, NULL, NULL, 'NO', 'YES', '00', '1645257767262416897', '',
        '2023-04-10 10:49:54', '', '2023-04-10 10:49:55', 3, 'NO'),
       ('1644979290651009026', '模块', 'module', 'ENTITY', NULL, 'ENTITY_SELECT', NULL, NULL, NULL, 'NO', 'YES', '00', '1645258183308013569', '',
        '2023-04-10 10:51:33', '', '2023-04-10 10:51:33', 2, 'NO'),
       ('1645624558534787074', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', NULL, '1645624809702293507', '',
        '2023-04-11 11:08:24', '', '2023-04-11 11:08:24', 1, 'NO'),
       ('1645624558534787074', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', NULL, '1645624809702293508', '',
        '2023-04-11 11:08:24', '', '2023-04-11 11:08:24', 1, 'NO'),
       ('1645624558534787074', '实体', 'entity', 'STRING', NULL, 'TEXT', 'EQ', NULL, NULL, 'NO', 'NO', NULL, '1645624809769402370', '',
        '2023-04-11 11:08:24', '', '2023-04-15 17:26:04', 5, 'NO'),
       ('1645685382146187265', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1645698710868353025', '',
        '2023-04-11 16:02:03', '', '2023-04-16 09:46:25', 5, 'NO'),
       ('1645685382146187265', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '02', '1645698719521202177', '',
        '2023-04-11 16:02:05', '', '2023-04-16 09:46:25', 4, 'NO'),
       ('1645685382146187265', '实体模型', 'entityModel', 'STRING', NULL, 'TEXT', 'EQ', NULL, NULL, 'NO', 'NO', '00', '1645698740928929793', '',
        '2023-04-11 16:02:10', '', '2023-04-16 09:46:25', 5, 'NO'),
       ('1646053022073143297', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '00', '1646053220413390849', '',
        '2023-04-12 15:30:45', '', '2023-04-15 19:57:20', 6, 'NO'),
       ('1646053022073143297', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1646053232019030017', '',
        '2023-04-12 15:30:47', '', '2023-04-15 19:57:20', 5, 'NO'),
       ('1646070570890080257', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '00', '1646073129662984193', '',
        '2023-04-12 16:49:51', '', '2023-04-15 07:34:00', 33, 'NO'),
       ('1646070570890080257', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1646073137514721282', '',
        '2023-04-12 16:49:53', '', '2023-04-15 07:34:00', 31, 'NO'),
       ('1646053022073143297', '实体', 'entity', 'STRING', NULL, 'TEXT', 'EQ', NULL, NULL, 'NO', 'NO', '02', '1646134940705755138', '',
        '2023-04-12 20:55:28', '', '2023-04-15 19:57:20', 5, 'NO'),
       ('1646070570890080257', '实体', 'entity', 'STRING', NULL, 'TEXT', 'EQ', NULL, NULL, 'NO', 'NO', '02', '1646506781135921153', '',
        '2023-04-13 21:33:02', '', '2023-04-15 19:51:52', 32, 'NO'),
       ('1647036734806020097', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1647036734868934657', '',
        '2023-04-15 08:38:53', '', '2023-04-15 08:38:53', 1, 'NO'),
       ('1647036734806020097', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '02', '1647036734868934658', '',
        '2023-04-15 08:38:53', '', '2023-04-15 08:38:53', 1, 'NO'),
       ('1647036734806020097', '实体模型', 'entityModel', 'STRING', NULL, 'TEXT', 'EQ', NULL, NULL, 'NO', 'NO', '00', '1647036734868934659', '',
        '2023-04-15 08:38:53', '', '2023-04-15 19:58:02', 2, 'NO'),
       ('1645685382146187265', '是否可为空', 'nullFlag', 'DATA_DICTIONARY', 'YesOrNo', 'RADIO_BUTTON_GROUP', 'NO', NULL, 'YES', 'NO', 'YES', '03',
        '1647416119471951874', '', '2023-04-16 09:46:25', '', '2023-04-16 09:46:25', 2, 'NO'),
       ('1643425456518680578', '123', '123', 'DATA_DICTIONARY', 'OrganizationType', 'DROP_DOWN_LIST', 'LK', '123123', 'GROUP', 'YES', 'YES', NULL,
        '1647417824901124098', '', '2023-04-16 09:53:12', '', '2023-04-17 20:11:07', 1, 'YES'),
       ('1647470434446266370', '按钮类型', 'buttonType', 'DATA_DICTIONARY', 'ViewButtonType', 'DROP_DOWN_LIST', 'NO', NULL, 'LIST_PAGE', 'NO', 'YES',
        '00', '1647470572048797698', '', '2023-04-16 13:22:48', '', '2023-04-16 13:23:20', 5, 'NO'),
       ('1647470434446266370', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1647470659105771521', '',
        '2023-04-16 13:23:08', '', '2023-04-16 13:23:20', 3, 'NO'),
       ('1647470434446266370', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '02', '1647470706400743425', '',
        '2023-04-16 13:23:20', '', '2023-04-16 13:23:20', 2, 'NO'),
       ('1643425456518680578', '缩略码', 'abbreviation', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '03', '1648212856348360705', '',
        '2023-04-18 14:32:22', '', '2023-04-18 14:32:33', 2, 'YES'),
       ('1643425456518680578', '缩略码', 'abbreviation', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '03', '1648212913483169793', '',
        '2023-04-18 14:32:36', '', '2023-04-18 14:40:01', 5, 'YES'),
       ('1643425456518680578', '包路径', 'packagePath', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '05', '1648213062687145985', '',
        '2023-04-18 14:33:11', '', '2023-04-18 14:39:59', 5, 'YES'),
       ('1643425456518680578', '排序号', 'orderNo', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '06', '1648213924398514177', '',
        '2023-04-18 14:36:37', '', '2023-04-18 14:39:57', 3, 'YES'),
       ('1643425456518680578', '缩略码', 'abbreviation', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '03', '1648215070802460673', '',
        '2023-04-18 14:41:10', '', '2023-04-18 14:41:31', 3, 'YES'),
       ('1643425456518680578', '包路径', 'packagePath', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '04', '1648215134430052353', '',
        '2023-04-18 14:41:25', '', '2023-04-18 14:41:29', 2, 'YES'),
       ('1648589167218778113', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '00', '1648589318788341762', '',
        '2023-04-19 15:28:18', '', '2023-04-19 15:28:33', 5, 'NO'),
       ('1648589167218778113', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1648589327403442177', '',
        '2023-04-19 15:28:20', '', '2023-04-19 15:28:33', 4, 'NO'),
       ('1648589167218778113', '类型', 'type', 'DATA_DICTIONARY', 'OrganizationType', 'DROP_DOWN_LIST', 'NO', NULL, '', 'NO', 'YES', '02',
        '1648589339365597185', '', '2023-04-19 15:28:22', '', '2023-04-20 09:50:40', 4, 'NO'),
       ('1648589167218778113', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'DROP_DOWN_LIST', 'NO', NULL, 'NORMAL', 'NO', 'YES', '03',
        '1648589383460315138', '', '2023-04-19 15:28:33', '', '2023-04-19 15:28:33', 2, 'NO'),
       ('1648664997567430657', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '00', '1648664997642928129', '',
        '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1648664997642928130', '',
        '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', '类型', 'type', 'DATA_DICTIONARY', 'OrganizationType', 'DROP_DOWN_LIST', 'NO', NULL, 'DEPARTMENT', 'NO', 'YES', '02',
        '1648664997710036993', '', '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'DROP_DOWN_LIST', 'NO', NULL, 'NORMAL', 'NO', 'YES', '03',
        '1648664997710036994', '', '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1649751408416714753', '组织机构', 'organization', 'STRING', NULL, 'TEXT', 'EQ', NULL, NULL, 'NO', 'NO', '00', '1649751618261938178', '',
        '2023-04-22 20:26:51', '', '2023-04-24 08:16:26', 7, 'YES'),
       ('1649751408416714753', '姓名', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1649751806141591553', '',
        '2023-04-22 20:27:36', '', '2023-04-24 08:12:39', 4, 'NO'),
       ('1649751408416714753', '账号', 'account', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '02', '1649751812672122881', '',
        '2023-04-22 20:27:38', '', '2023-04-24 08:12:39', 3, 'NO'),
       ('1649751408416714753', '状态', 'status', 'DATA_DICTIONARY', 'UserStatus', 'DROP_DOWN_LIST', 'NO', NULL, 'NORMAL', 'NO', 'YES', '03',
        '1650291625989017601', '', '2023-04-24 08:12:39', '', '2023-04-24 08:30:57', 3, 'NO'),
       ('1650336635631480834', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '00', '1650336715730104322', '',
        '2023-04-24 11:11:49', '', '2023-04-24 11:11:54', 4, 'NO'),
       ('1650336635631480834', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1650336722080280578', '',
        '2023-04-24 11:11:51', '', '2023-04-24 11:11:54', 3, 'NO'),
       ('1650336635631480834', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'DROP_DOWN_LIST', 'NO', NULL, 'NORMAL', 'NO', 'YES', '02',
        '1650336732213719042', '', '2023-04-24 11:11:53', '', '2023-04-24 14:55:14', 3, 'NO'),
       ('1650671993913110530', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '00', '1650672099034951681', '',
        '2023-04-25 09:24:31', '', '2023-04-25 09:25:10', 7, 'NO'),
       ('1650671993913110530', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1650672110195994625', '',
        '2023-04-25 09:24:34', '', '2023-04-25 09:25:10', 6, 'NO'),
       ('1650671993913110530', '类型', 'type', 'DATA_DICTIONARY', 'PermissionType', 'DROP_DOWN_LIST', 'NO', NULL, '', 'NO', 'YES', '02',
        '1650672139723894785', '', '2023-04-25 09:24:41', '', '2023-04-25 09:25:10', 4, 'NO'),
       ('1650671993913110530', '权限编码', 'permissionCode', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '03', '1650672227250630657', '',
        '2023-04-25 09:25:02', '', '2023-04-25 09:25:10', 3, 'NO'),
       ('1650671993913110530', '状态', 'status', 'DATA_DICTIONARY', 'Status', 'DROP_DOWN_LIST', 'NO', NULL, 'NORMAL', 'NO', 'YES', '04',
        '1650672262503755778', '', '2023-04-25 09:25:10', '', '2023-04-26 13:59:08', 3, 'NO'),
       ('1651033706957164546', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '00', '1651033814209712130', '',
        '2023-04-26 09:21:51', '', '2023-04-26 09:21:52', 3, 'NO'),
       ('1651033706957164546', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1651033820308230146', '',
        '2023-04-26 09:21:52', '', '2023-04-26 09:21:52', 2, 'NO'),
       ('1651184645340971009', '名称', 'name', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '00', '1651184710159745025', '',
        '2023-04-26 19:21:27', '', '2023-04-26 19:21:29', 3, 'NO'),
       ('1651184645340971009', '编码', 'code', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1651184716740608002', '',
        '2023-04-26 19:21:29', '', '2023-04-26 19:21:29', 2, 'NO'),
       ('1651198471625027585', '操作人账号', 'operatorAccount', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '01', '1651198580278472705',
        '', '2023-04-26 20:16:34', '', '2023-04-26 20:16:44', 5, 'NO'),
       ('1651198471625027585', '操作人姓名', 'operatorName', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '02', '1651198591733116930', '',
        '2023-04-26 20:16:37', '', '2023-04-26 20:16:44', 4, 'NO'),
       ('1651198471625027585', '操作人ip', 'operatorIp', 'STRING', NULL, 'TEXT', 'LK', NULL, NULL, 'NO', 'YES', '03', '1651198604580270081', '',
        '2023-04-26 20:16:40', '', '2023-04-26 20:16:44', 3, 'NO'),
       ('1651198471625027585', '日志类型', 'logType', 'DATA_DICTIONARY', 'LogType', 'DROP_DOWN_LIST', 'NO', NULL, '', 'NO', 'YES', '00',
        '1651198620657037313', '', '2023-04-26 20:16:44', '', '2023-04-26 20:16:44', 2, 'NO');

-- 导出  表 abc.cfg_view_query_result 结构
CREATE TABLE IF NOT EXISTS `cfg_view_query_result`
(
    `view` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '视图',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `data_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'STRING' COMMENT '数据类型',
    `dictionary_type` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '字典类型',
    `width` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '宽度',
    `sortable_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '是否支持排序',
    `format_function` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '格式化方法',
    `show_overflow_tooltip_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '是否悬浮显示',
    `show_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '是否显示',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='视图查询结果';

-- 正在导出表  abc.cfg_view_query_result 的数据：~150 rows (大约)
INSERT INTO `cfg_view_query_result` (`view`, `name`, `code`, `data_type`, `dictionary_type`, `width`, `sortable_flag`, `format_function`,
                                     `show_overflow_tooltip_flag`, `show_flag`, `order_no`, `id`, `create_id`, `create_time`, `update_id`,
                                     `update_time`, `version`, `delete_flag`)
VALUES ('1641975231912390658', '参数名称', 'paramName', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '00', '1643229400342253570', '',
        '2023-04-04 20:29:53', '', '2023-04-17 20:52:45', 6, 'NO'),
       ('1641975231912390658', '参数编码', 'paramKey', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1643229408256905217', '',
        '2023-04-04 20:29:55', '', '2023-04-17 20:52:45', 5, 'NO'),
       ('1641975231912390658', '参数值', 'paramValue', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '02', '1643229419292119042', '',
        '2023-04-04 20:29:58', '', '2023-04-17 20:52:45', 4, 'NO'),
       ('1641975231912390658', '排序号', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1643229428670582786', '',
        '2023-04-04 20:30:00', '', '2023-04-17 20:52:40', 2, 'YES'),
       ('1643425456518680578', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '01', '1643425757208334337', '',
        '2023-04-05 09:30:09', '', '2023-04-18 14:36:42', 12, 'NO'),
       ('1643425456518680578', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '02', '1643425764321873921', '',
        '2023-04-05 09:30:10', '', '2023-04-18 14:36:42', 10, 'NO'),
       ('1643425456518680578', '缩略码', 'abbreviation', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '00', '1643425780767739905', '',
        '2023-04-05 09:30:14', '', '2023-04-18 14:36:42', 8, 'NO'),
       ('1643425456518680578', '包路径', 'packagePath', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1643425789001158658', '',
        '2023-04-05 09:30:16', '', '2023-04-18 14:36:42', 7, 'NO'),
       ('1643425456518680578', '排序号', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1643425811159666690', '',
        '2023-04-05 09:30:21', '', '2023-04-18 14:36:42', 6, 'NO'),
       ('1644957439652581378', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1644957440193646593', '',
        '2023-04-09 14:56:30', '', '2023-04-14 20:38:30', 2, 'NO'),
       ('1644957439652581378', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1644957440193646594', '',
        '2023-04-09 14:56:30', '', '2023-04-14 20:38:30', 2, 'NO'),
       ('1644957439652581378', '应用', 'app', 'DATA_DICTIONARY', 'AppCode', '120', 'YES', NULL, 'YES', 'YES', '02', '1644957440256561156', '',
        '2023-04-09 14:56:30', '', '2023-04-14 20:38:30', 3, 'NO'),
       ('1644978322668560386', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1644978597890400259', '',
        '2023-04-09 16:20:35', '', '2023-04-10 10:50:08', 3, 'NO'),
       ('1644978322668560386', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1644978597890400260', '',
        '2023-04-09 16:20:35', '', '2023-04-10 10:50:08', 3, 'NO'),
       ('1644978322668560386', '作者', 'author', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '02', '1644978597957509121', '',
        '2023-04-09 16:20:35', '', '2023-04-10 10:50:08', 3, 'NO'),
       ('1644978322668560386', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1644978597957509122', '',
        '2023-04-09 16:20:35', '', '2023-04-10 10:50:08', 3, 'NO'),
       ('1644979290651009026', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1644979290713923587', '',
        '2023-04-09 16:23:20', '', '2023-04-10 10:51:42', 3, 'NO'),
       ('1644979290651009026', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1644979290713923588', '',
        '2023-04-09 16:23:20', '', '2023-04-10 10:51:42', 3, 'NO'),
       ('1644979290651009026', '作者', 'author', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '02', '1644979290713923589', '',
        '2023-04-09 16:23:20', '', '2023-04-10 10:51:42', 3, 'NO'),
       ('1644979290651009026', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1644979290713923590', '',
        '2023-04-09 16:23:20', '', '2023-04-10 10:51:42', 3, 'NO'),
       ('1643425456518680578', '应用', 'app', 'DATA_DICTIONARY', 'AppCode', '120', 'YES', NULL, 'YES', 'YES', '04', '1645043211395149825', '',
        '2023-04-09 20:37:20', '', '2023-04-18 14:36:42', 3, 'NO'),
       ('1644978322668560386', '模块', 'module', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1645257797968916481', '',
        '2023-04-10 10:50:01', '', '2023-04-10 10:50:08', 3, 'NO'),
       ('1644979290651009026', '模块', 'module', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1645258199623856129', '',
        '2023-04-10 10:51:37', '', '2023-04-10 10:51:42', 3, 'NO'),
       ('1645624558534787074', '父级模型', 'parentModel', 'DATA_DICTIONARY', 'BaseModel', '120', 'YES', NULL, 'YES', 'YES', '02',
        '1645624832590610434', '', '2023-04-11 11:08:29', '', '2023-04-19 13:19:56', 4, 'NO'),
       ('1645624558534787074', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1645624832590610435', '',
        '2023-04-11 11:08:29', '', '2023-04-19 13:19:56', 4, 'NO'),
       ('1645624558534787074', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1645624832590610436', '',
        '2023-04-11 11:08:29', '', '2023-04-19 13:19:56', 4, 'NO'),
       ('1645624558534787074', '是否主模型', 'mainModelFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '03',
        '1645624832590610437', '', '2023-04-11 11:08:29', '', '2023-04-19 13:19:56', 4, 'NO'),
       ('1645624558534787074', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1645624832590610438', '',
        '2023-04-11 11:08:29', '', '2023-04-19 13:19:56', 4, 'NO'),
       ('1646053022073143297', '父级模型', 'parentModel', 'DATA_DICTIONARY', 'BaseModel', '120', 'YES', NULL, 'YES', 'YES', '04',
        '1646053358217248770', '', '2023-04-12 15:31:17', '', '2023-04-19 13:22:08', 2, 'YES'),
       ('1646053022073143297', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1646053358217248771', '',
        '2023-04-12 15:31:17', '', '2023-04-19 13:22:18', 3, 'NO'),
       ('1646053022073143297', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1646053358217248772', '',
        '2023-04-12 15:31:17', '', '2023-04-19 13:22:18', 3, 'NO'),
       ('1646053022073143297', '是否主模型', 'mainModelFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '02',
        '1646053358217248773', '', '2023-04-12 15:31:17', '', '2023-04-19 13:22:18', 3, 'NO'),
       ('1646053022073143297', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1646053358284357634', '',
        '2023-04-12 15:31:17', '', '2023-04-19 13:22:04', 2, 'YES'),
       ('1646070570890080257', '视图类型', 'entityViewType', 'DATA_DICTIONARY', 'EntityViewType', '120', 'YES', NULL, 'YES', 'YES', '00',
        '1646073029482033153', '', '2023-04-12 16:49:27', '', '2023-04-23 08:52:41', 3, 'NO'),
       ('1646070570890080257', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '01', '1646073029482033154', '',
        '2023-04-12 16:49:27', '', '2023-04-23 08:52:41', 2, 'NO'),
       ('1646070570890080257', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '02', '1646073029482033155', '',
        '2023-04-12 16:49:27', '', '2023-04-23 08:52:41', 2, 'NO'),
       ('1646070570890080257', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1646073029482033156', '',
        '2023-04-12 16:49:27', '', '2023-04-23 08:52:41', 2, 'NO'),
       ('1646070570890080257', '实体模型', 'entityModel', 'ENTITY', 'BaseModel', '120', 'YES', NULL, 'YES', 'YES', '05', '1646073029549142020', '',
        '2023-04-12 16:49:27', '', '2023-04-23 08:52:41', 2, 'NO'),
       ('1645685382146187265', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1647033727280336898', '',
        '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1647033727280336899', '',
        '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', '120', 'YES', NULL, 'YES', 'YES', '02',
        '1647033727280336900', '', '2023-04-15 08:26:56', '', '2023-04-26 15:23:56', 4, 'YES'),
       ('1645685382146187265', '字典类型', 'dictionaryType', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1647033727280336901', '',
        '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '控件类型', 'widgetType', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1647033727280336902', '',
        '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '显示格式', 'formatPattern', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1647033727280336903', '',
        '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '最大长度', 'maxLength', 'INTEGER', NULL, '120', 'YES', NULL, 'YES', 'YES', '06', '1647033727347445761', '',
        '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '小数位数', 'decimalLength', 'INTEGER', NULL, '120', 'YES', NULL, 'YES', 'YES', '07', '1647033727347445762', '',
        '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '默认值', 'defaultValue', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '08', '1647033727347445763', '',
        '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '是否可为空', 'nullFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '09', '1647033727347445764',
        '', '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '是否唯一', 'uniqueFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '10', '1647033727347445765',
        '', '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '是否主属性', 'mainFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '12', '1647033727347445767',
        '', '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1645685382146187265', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '14', '1647033727347445768', '',
        '2023-04-15 08:26:56', '', '2023-04-26 15:24:03', 5, 'NO'),
       ('1647036734806020097', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1647037607904923649', '',
        '2023-04-15 08:42:21', '', '2023-04-15 08:42:23', 3, 'NO'),
       ('1647036734806020097', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1647037617367273473', '',
        '2023-04-15 08:42:23', '', '2023-04-15 08:42:23', 2, 'NO'),
       ('1645685382146187265', '唯一性参照', 'entityModelProperty', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '11', '1647127640720609281', '',
        '2023-04-15 14:40:06', '', '2023-04-26 15:24:03', 4, 'NO'),
       ('1643425456518680578', '123', '123', 'STRING', '1', '120', 'YES', '213', 'YES', 'YES', '123', '1647409737930690562', '',
        '2023-04-16 09:21:04', '', '2023-04-16 09:21:07', 1, 'YES'),
       ('1647470434446266370', '按钮类型', 'buttonType', 'DATA_DICTIONARY', 'ViewButtonType', '120', 'YES', NULL, 'YES', 'YES', '02',
        '1647470751992827905', '', '2023-04-16 13:23:30', '', '2023-04-16 13:23:40', 2, 'NO'),
       ('1647470434446266370', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1647470751992827906', '',
        '2023-04-16 13:23:30', '', '2023-04-16 13:23:40', 2, 'NO'),
       ('1647470434446266370', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1647470751992827907', '',
        '2023-04-16 13:23:30', '', '2023-04-16 13:23:40', 2, 'NO'),
       ('1647470434446266370', '内容', 'content', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1647470752055742466', '',
        '2023-04-16 13:23:30', '', '2023-04-16 13:23:42', 2, 'YES'),
       ('1647470434446266370', '图标', 'icon', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1647470752055742467', '',
        '2023-04-16 13:23:30', '', '2023-04-16 13:23:44', 2, 'YES'),
       ('1647470434446266370', '是否需确认', 'confirmFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '05',
        '1647470752055742468', '', '2023-04-16 13:23:30', '', '2023-04-16 13:23:40', 2, 'NO'),
       ('1647470434446266370', '确认信息', 'confirmMessage', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '06', '1647470752118657026', '',
        '2023-04-16 13:23:30', '', '2023-04-16 13:23:50', 2, 'YES'),
       ('1647470434446266370', '是否控制权限', 'permissionFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '07',
        '1647470752118657027', '', '2023-04-16 13:23:30', '', '2023-04-16 13:23:40', 2, 'NO'),
       ('1647470434446266370', '权限编码', 'permissionCode', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '08', '1647470752118657028', '',
        '2023-04-16 13:23:31', '', '2023-04-16 13:23:40', 2, 'NO'),
       ('1647470434446266370', '是否用于更多', 'moreFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '09',
        '1647470752185765889', '', '2023-04-16 13:23:31', '', '2023-04-16 13:23:40', 2, 'NO'),
       ('1647470434446266370', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '10', '1647470752185765890', '',
        '2023-04-16 13:23:31', '', '2023-04-16 13:23:40', 2, 'NO'),
       ('1641975231912390658', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1647946193988337666', '',
        '2023-04-17 20:52:45', '', '2023-04-17 20:52:45', 2, 'NO'),
       ('1645624558534787074', '是否自关联', 'selfReferenceFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '04',
        '1648557008483258369', '', '2023-04-19 13:19:54', '', '2023-04-19 13:19:56', 3, 'NO'),
       ('1646053022073143297', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1648557610504294401', '',
        '2023-04-19 13:22:18', '', '2023-04-19 13:22:18', 2, 'NO'),
       ('1645685382146187265', '是否上级属性', 'parentPropertyFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '13',
        '1648577130258239490', '', '2023-04-19 14:39:52', '', '2023-04-26 15:24:03', 3, 'NO'),
       ('1648589167218778113', '组织机构', 'organization', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', NULL, '1648589404658327553', '',
        '2023-04-19 15:28:38', '', '2023-04-20 09:55:03', 1, 'YES'),
       ('1648589167218778113', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1648589404721242114', '',
        '2023-04-19 15:28:38', '', '2023-04-20 09:55:14', 2, 'NO'),
       ('1648589167218778113', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1648589404721242115', '',
        '2023-04-19 15:28:38', '', '2023-04-20 09:55:14', 2, 'NO'),
       ('1648589167218778113', '类型', 'type', 'DATA_DICTIONARY', 'OrganizationType', '120', 'YES', NULL, 'YES', 'YES', '02', '1648589404721242116',
        '', '2023-04-19 15:28:38', '', '2023-04-20 09:55:14', 2, 'NO'),
       ('1648589167218778113', '状态', 'status', 'DATA_DICTIONARY', 'Status', '120', 'YES', NULL, 'YES', 'YES', '03', '1648589404721242117', '',
        '2023-04-19 15:28:38', '', '2023-04-20 09:55:14', 2, 'NO'),
       ('1648589167218778113', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1648589404721242118', '',
        '2023-04-19 15:28:38', '', '2023-04-20 09:55:14', 2, 'NO'),
       ('1648589167218778113', '备注', 'remark', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', NULL, '1648589404721242119', '',
        '2023-04-19 15:28:38', '', '2023-04-19 15:28:42', 1, 'YES'),
       ('1648664997567430657', '组织机构', 'organization', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', NULL, '1648664997777145858', '',
        '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', NULL, '1648664997777145859', '',
        '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', NULL, '1648664997777145860', '',
        '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', '类型', 'type', 'DATA_DICTIONARY', 'OrganizationType', '120', 'YES', NULL, 'YES', 'YES', NULL, '1648664997777145861',
        '', '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', '状态', 'status', 'DATA_DICTIONARY', 'Status', '120', 'YES', NULL, 'YES', 'YES', NULL, '1648664997777145862', '',
        '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648664997567430657', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', NULL, '1648664997777145863', '',
        '2023-04-19 20:29:01', '', '2023-04-19 20:29:01', 1, 'YES'),
       ('1648589167218778113', '上级组织', 'organization', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1648867889121349634', '',
        '2023-04-20 09:55:14', '', '2023-04-20 09:55:14', 2, 'NO'),
       ('1649751408416714753', '组织机构', 'organization', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', '02', '1649751848839606274', '',
        '2023-04-22 20:27:46', '', '2023-04-24 08:16:14', 6, 'NO'),
       ('1649751408416714753', '姓名', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1649751848839606275', '',
        '2023-04-22 20:27:46', '', '2023-04-24 08:16:14', 6, 'NO'),
       ('1649751408416714753', '账号', 'account', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1649751848902520833', '',
        '2023-04-22 20:27:46', '', '2023-04-24 08:16:14', 6, 'NO'),
       ('1649751408416714753', '密码', 'password', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1649751848902520834', '',
        '2023-04-22 20:27:46', '', '2023-04-22 20:27:58', 2, 'YES'),
       ('1649751408416714753', '性别', 'gender', 'DATA_DICTIONARY', 'Gender', '120', 'YES', NULL, 'YES', 'YES', '03', '1649751848902520835', '',
        '2023-04-22 20:27:46', '', '2023-04-24 08:16:14', 6, 'NO'),
       ('1649751408416714753', '生日', 'birthday', 'DATETIME', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1649751848902520836', '',
        '2023-04-22 20:27:46', '', '2023-04-22 20:28:03', 2, 'YES'),
       ('1649751408416714753', '手机号', 'telephone', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1649751848969629697', '',
        '2023-04-22 20:27:46', '', '2023-04-24 08:16:14', 6, 'NO'),
       ('1649751408416714753', '邮箱', 'email', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1649751848969629698', '',
        '2023-04-22 20:27:46', '', '2023-04-24 08:16:14', 6, 'NO'),
       ('1649751408416714753', '职位', 'position', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '06', '1649751848969629699', '',
        '2023-04-22 20:27:46', '', '2023-04-24 08:16:14', 6, 'NO'),
       ('1649751408416714753', '状态', 'status', 'DATA_DICTIONARY', 'UserStatus', '120', 'YES', NULL, 'YES', 'YES', '08', '1649751848969629700', '',
        '2023-04-22 20:27:46', '', '2023-04-24 08:16:14', 6, 'NO'),
       ('1649751408416714753', '强制修改密码', 'forceChangePasswordFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '10',
        '1649751849036738562', '', '2023-04-22 20:27:46', '', '2023-04-22 20:28:09', 2, 'YES'),
       ('1649751408416714753', '失败登录次数', 'fail_login_count', 'INTEGER', NULL, '120', 'YES', NULL, 'YES', 'YES', '11', '1649751849036738563', '',
        '2023-04-22 20:27:46', '', '2023-04-22 20:28:11', 2, 'YES'),
       ('1649751408416714753', '锁定时间', 'lockTime', 'DATETIME', NULL, '120', 'YES', NULL, 'YES', 'YES', '12', '1649751849036738564', '',
        '2023-04-22 20:27:46', '', '2023-04-22 20:28:13', 2, 'YES'),
       ('1649751408416714753', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '10', '1649751849036738565', '',
        '2023-04-22 20:27:46', '', '2023-04-24 08:16:14', 6, 'NO'),
       ('1649751408416714753', '锁定时间', 'lockTime', 'DATETIME', NULL, '120', 'YES', 'formatTime', 'YES', 'YES', '09', '1649752018490814465', '',
        '2023-04-22 20:28:27', '', '2023-04-30 16:04:16', 6, 'NO'),
       ('1649751408416714753', '失败登录次数', 'fail_login_count', 'INTEGER', NULL, '120', 'YES', NULL, 'YES', 'YES', '08', '1649752051973943297', '',
        '2023-04-22 20:28:35', '', '2023-04-24 08:13:47', 2, 'YES'),
       ('1646070570890080257', '主参照视图', 'mainReferenceViewFlag', 'DATA_DICTIONARY', 'YesOrNo', '120', 'YES', NULL, 'YES', 'YES', '03',
        '1649939309825916930', '', '2023-04-23 08:52:41', '', '2023-04-23 08:52:41', 2, 'NO'),
       ('1649751408416714753', '出生日期', 'birthday', 'DATETIME', NULL, '120', 'YES', 'formatDate', 'YES', 'YES', '07', '1650292490086944769', '',
        '2023-04-24 08:16:05', '', '2023-04-30 16:04:07', 4, 'NO'),
       ('1650336635631480834', '上级', 'userGroup', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', '02', '1650336757698310146', '',
        '2023-04-24 11:11:59', '', '2023-04-24 11:12:07', 2, 'NO'),
       ('1650336635631480834', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1650336757698310147', '',
        '2023-04-24 11:11:59', '', '2023-04-24 11:12:07', 2, 'NO'),
       ('1650336635631480834', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1650336757698310148', '',
        '2023-04-24 11:11:59', '', '2023-04-24 11:12:07', 2, 'NO'),
       ('1650336635631480834', '状态', 'status', 'DATA_DICTIONARY', 'Status', '120', 'YES', NULL, 'YES', 'YES', '03', '1650336757761224705', '',
        '2023-04-24 11:11:59', '', '2023-04-24 11:12:07', 2, 'NO'),
       ('1650336635631480834', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1650336757761224706', '',
        '2023-04-24 11:11:59', '', '2023-04-24 11:12:07', 2, 'NO'),
       ('1650336635631480834', '备注', 'remark', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1650336757761224707', '',
        '2023-04-24 11:11:59', '', '2023-04-24 11:12:11', 2, 'YES'),
       ('1650671993913110530', '上级', 'permissionItem', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', '02', '1650672324814336001', '',
        '2023-04-25 09:25:25', '', '2023-04-25 09:26:00', 4, 'YES'),
       ('1650671993913110530', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1650672324814336002', '',
        '2023-04-25 09:25:25', '', '2023-04-25 09:26:00', 4, 'YES'),
       ('1650671993913110530', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1650672324877250561', '',
        '2023-04-25 09:25:25', '', '2023-04-25 09:26:00', 4, 'YES'),
       ('1650671993913110530', '类型', 'type', 'DATA_DICTIONARY', 'PermissionType', '120', 'YES', NULL, 'YES', 'YES', '03', '1650672324877250562', '',
        '2023-04-25 09:25:25', '', '2023-04-25 09:26:00', 4, 'YES'),
       ('1650671993913110530', '组件', 'component', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1650672324877250563', '',
        '2023-04-25 09:25:25', '', '2023-04-25 09:26:00', 4, 'YES'),
       ('1650671993913110530', '图标', 'icon', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1650672324877250564', '',
        '2023-04-25 09:25:25', '', '2023-04-25 09:25:44', 2, 'YES'),
       ('1650671993913110530', '权限编码', 'permissionCode', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1650672324877250565', '',
        '2023-04-25 09:25:25', '', '2023-04-25 09:26:00', 4, 'YES'),
       ('1650671993913110530', '状态', 'status', 'DATA_DICTIONARY', 'Status', '120', 'YES', NULL, 'YES', 'YES', '07', '1650672324877250566', '',
        '2023-04-25 09:25:25', '', '2023-04-25 09:26:00', 4, 'YES'),
       ('1650671993913110530', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '08', '1650672324877250567', '',
        '2023-04-25 09:25:25', '', '2023-04-25 09:26:00', 4, 'YES'),
       ('1650671993913110530', '图标', 'icon', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '06', '1650672459904479233', '',
        '2023-04-25 09:25:57', '', '2023-04-25 09:26:00', 3, 'YES'),
       ('1650671993913110530', '上级', 'permissionItem', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', '08', '1650735785812455426', '',
        '2023-04-25 13:37:35', '', '2023-04-25 13:37:42', 2, 'NO'),
       ('1650671993913110530', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1650735785812455427', '',
        '2023-04-25 13:37:35', '', '2023-04-25 13:37:42', 2, 'NO'),
       ('1650671993913110530', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1650735785812455428', '',
        '2023-04-25 13:37:35', '', '2023-04-25 13:37:42', 2, 'NO'),
       ('1650671993913110530', '类型', 'type', 'DATA_DICTIONARY', 'PermissionType', '120', 'YES', NULL, 'YES', 'YES', '02', '1650735785879564290', '',
        '2023-04-25 13:37:35', '', '2023-04-25 13:37:42', 2, 'NO'),
       ('1650671993913110530', '权限编码', 'permissionCode', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1650735785879564291', '',
        '2023-04-25 13:37:35', '', '2023-04-25 13:37:42', 2, 'NO'),
       ('1650671993913110530', '视图编码', 'viewCode', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1650735785879564292', '',
        '2023-04-25 13:37:35', '', '2023-04-25 13:37:42', 2, 'NO'),
       ('1650671993913110530', '组件', 'component', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1650735785879564293', '',
        '2023-04-25 13:37:35', '', '2023-04-25 13:37:42', 2, 'NO'),
       ('1650671993913110530', '图标', 'icon', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '06', '1650735785946673153', '',
        '2023-04-25 13:37:35', '', '2023-04-25 13:37:45', 2, 'YES'),
       ('1650671993913110530', '状态', 'status', 'DATA_DICTIONARY', 'Status', '120', 'YES', NULL, 'YES', 'YES', '07', '1650735785946673154', '',
        '2023-04-25 13:37:35', '', '2023-04-25 13:37:42', 2, 'NO'),
       ('1650671993913110530', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '09', '1650735786013782017', '',
        '2023-04-25 13:37:35', '', '2023-04-25 13:37:42', 2, 'NO'),
       ('1651033706957164546', '上级', 'dictionaryType', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1651033845604077569', '',
        '2023-04-26 09:21:58', '', '2023-04-26 09:41:20', 3, 'YES'),
       ('1651033706957164546', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', '00', '1651033845604077570', '',
        '2023-04-26 09:21:58', '', '2023-04-26 09:41:22', 4, 'NO'),
       ('1651033706957164546', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '01', '1651033845604077571', '',
        '2023-04-26 09:21:58', '', '2023-04-26 09:41:22', 4, 'NO'),
       ('1651033706957164546', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '02', '1651033845675380738', '',
        '2023-04-26 09:21:58', '', '2023-04-26 09:41:22', 4, 'NO'),
       ('1651033706957164546', '上级', 'dictionaryType', 'ENTITY', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1651038727413223425', '',
        '2023-04-26 09:41:22', '', '2023-04-26 13:52:28', 3, 'NO'),
       ('1645685382146187265', '数据类型', 'dataType', 'DATA_DICTIONARY', 'EntityModelPropertyType', '120', 'NO', NULL, 'YES', 'YES', '02',
        '1651124964845195266', '', '2023-04-26 15:24:03', '', '2023-04-26 15:24:03', 2, 'NO'),
       ('1651184645340971009', '字典类型', 'dictionaryType', 'ENTITY', NULL, '120', 'NO', NULL, 'YES', 'YES', NULL, '1651184748239831041', '',
        '2023-04-26 19:21:36', '', '2023-04-26 19:21:39', 1, 'YES'),
       ('1651184645340971009', '名称', 'name', 'STRING', NULL, NULL, 'YES', NULL, 'YES', 'YES', NULL, '1651184748239831042', '',
        '2023-04-26 19:21:36', '', '2023-04-26 19:21:36', 1, 'NO'),
       ('1651184645340971009', '编码', 'code', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', NULL, '1651184748239831043', '',
        '2023-04-26 19:21:36', '', '2023-04-26 19:21:36', 1, 'NO'),
       ('1651184645340971009', '状态', 'status', 'DATA_DICTIONARY', 'Status', '120', 'NO', NULL, 'YES', 'YES', NULL, '1651184748302745601', '',
        '2023-04-26 19:21:36', '', '2023-04-26 19:21:36', 1, 'NO'),
       ('1651184645340971009', '排序', 'orderNo', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', NULL, '1651184748302745602', '',
        '2023-04-26 19:21:36', '', '2023-04-26 19:21:36', 1, 'NO'),
       ('1651198471625027585', '内容', 'content', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '00', '1651198704748638210', '',
        '2023-04-26 20:17:04', '', '2023-04-26 20:17:49', 12, 'NO'),
       ('1651198471625027585', '日志类型', 'logType', 'DATA_DICTIONARY', 'LogType', '120', 'NO', NULL, 'YES', 'YES', '01', '1651198714177433602', '',
        '2023-04-26 20:17:06', '', '2023-04-26 20:17:49', 11, 'NO'),
       ('1651198471625027585', '请求时间', 'requestTime', 'DATETIME', NULL, '120', 'YES', NULL, 'YES', 'YES', '02', '1651198722859642881', '',
        '2023-04-26 20:17:08', '', '2023-04-26 20:17:49', 10, 'NO'),
       ('1651198471625027585', '请求路径', 'requestPath', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '03', '1651198766266494977', '',
        '2023-04-26 20:17:18', '', '2023-04-26 20:17:49', 9, 'NO'),
       ('1651198471625027585', '请求方法', 'requestMethod', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '04', '1651198776706117633', '',
        '2023-04-26 20:17:21', '', '2023-04-26 20:17:49', 8, 'NO'),
       ('1651198471625027585', '操作人标识', 'operatorId', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '05', '1651198787024105473', '',
        '2023-04-26 20:17:23', '', '2023-04-26 20:17:49', 7, 'NO'),
       ('1651198471625027585', '操作人账号', 'operatorAccount', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '06', '1651198823388721154', '',
        '2023-04-26 20:17:32', '', '2023-04-26 20:17:49', 6, 'NO'),
       ('1651198471625027585', '操作人姓名', 'operatorName', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '07', '1651198837733240834', '',
        '2023-04-26 20:17:35', '', '2023-04-26 20:17:49', 5, 'NO'),
       ('1651198471625027585', '操作人ip', 'operatorIp', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '08', '1651198847677935617', '',
        '2023-04-26 20:17:38', '', '2023-04-26 20:17:49', 4, 'NO'),
       ('1651198471625027585', '响应结果', 'responseCode', 'STRING', NULL, '120', 'YES', NULL, 'YES', 'YES', '09', '1651198867890286593', '',
        '2023-04-26 20:17:43', '', '2023-04-26 20:17:49', 3, 'NO'),
       ('1651198471625027585', '执行耗时ms', 'timeConsuming', 'INTEGER', NULL, '120', 'YES', NULL, 'YES', 'YES', '10', '1651198892775092226', '',
        '2023-04-26 20:17:49', '', '2023-04-26 20:17:49', 2, 'NO');

-- 导出  表 abc.sys_dictionary_item 结构
CREATE TABLE IF NOT EXISTS `sys_dictionary_item`
(
    `dictionary_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '字典类型',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `status` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '状态',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='字典项';

-- 正在导出表  abc.sys_dictionary_item 的数据：~211 rows (大约)
INSERT INTO `sys_dictionary_item` (`dictionary_type`, `name`, `code`, `status`, `order_no`, `id`, `create_id`, `create_time`, `update_id`,
                                   `update_time`, `version`, `delete_flag`)
VALUES ('1127134167920594946', '驾驶证', 'DRIVER_LICENSE', 'NORMAL', '02', '1127134866104438786', NULL, NULL, '1', '2019-09-04 05:42:07', 1, 'NO'),
       ('1127134167920594946', '身份证', 'ID_CARD', 'NORMAL', '01', '1152138596943245314', NULL, '2019-07-19 16:50:22', '1', '2019-09-04 05:40:23', 1,
        'NO'),
       ('1170950228873932802', '集团', 'GROUP', 'NORMAL', '01', '1170951102539067394', '1', '2019-10-29 06:34:28', '1', '2019-10-29 06:34:28', 1,
        'NO'),
       ('1170950228873932802', '公司', 'COMPANY', 'NORMAL', '02', '1170951205265960961', '1', '2019-10-29 06:34:28', '1', '2019-10-29 06:34:28', 1,
        'NO'),
       ('1170950228873932802', '子公司', 'SUBSIDIARY', 'NORMAL', '04', '1170951849544609794', '1', '2019-10-29 06:34:28', '1', '2019-10-29 06:34:28',
        1, 'NO'),
       ('1170950228873932802', '分公司', 'BRANCH', 'NORMAL', '03', '1170952018130464769', '1', '2019-10-29 06:34:28', '1', '2019-10-29 06:34:28', 1,
        'NO'),
       ('1170950228873932802', '部门', 'DEPARTMENT', 'NORMAL', '05', '1170952191841759233', '1', '2019-10-29 06:34:28', '1', '2019-10-29 06:34:28', 1,
        'NO'),
       ('1170950228873932802', '模块', 'MODULE', 'NORMAL', '06', '1170953248974139393', '1', '2019-10-29 06:34:28', '1', '2019-10-29 06:34:28', 1,
        'NO'),
       ('1174599224604467202', '女', 'FEMALE', 'NORMAL', '02', '1174599527127031810', '1', '2019-09-19 08:22:05', '1', '2019-09-19 08:22:05', 1,
        'NO'),
       ('1174599224604467202', '男', 'MALE', 'NORMAL', '01', '1174599590674931714', '1', '2019-09-19 08:22:20', '1', '2019-09-19 08:22:20', 1, 'NO'),
       ('1174603142700965890', '正常', 'NORMAL', 'NORMAL', '01', '1174603330467373058', '1', '2019-09-19 08:37:12', '1', '2019-09-19 08:37:12', 1,
        'NO'),
       ('1174603142700965890', '停用', 'DEAD', 'NORMAL', '02', '1174603434867793921', '1', '2019-09-19 08:37:37', '1', '2019-09-19 08:37:37', 1,
        'NO'),
       ('1174603142700965890', '锁定', 'LOCK', 'NORMAL', '03', '1174603497677496322', '1', '2019-09-19 08:37:52', '1', '2019-09-19 08:37:52', 1,
        'NO'),
       ('1179592839783043073', '化工', 'CHEMICALS', 'NORMAL', '01', '1179593220667789314', '1', '2019-10-03 03:05:14', '1', '2019-10-03 03:05:14', 1,
        'NO'),
       ('1179592839783043073', '石油', 'OILS', 'NORMAL', '02', '1179593667751235585', '1', '2019-10-03 03:07:01', '1', '2019-10-03 03:07:01', 1,
        'NO'),
       ('1179592839783043073', '冶炼', 'METALLURGY', 'NORMAL', '03', '1179593831735939074', '1', '2019-10-03 03:07:40', '1', '2019-10-03 03:07:40', 1,
        'NO'),
       ('1179594199698034690', '特大型', 'LARGE', 'NORMAL', '01', '1179594772203753474', '1', '2019-10-03 03:11:24', '1', '2019-10-03 03:11:24', 1,
        'NO'),
       ('1179594199698034690', '大型', 'BIG', 'NORMAL', '02', '1179594832698200066', '1', '2019-10-03 03:11:39', '1', '2019-10-03 03:11:39', 1, 'NO'),
       ('1179594199698034690', '中型', 'MEDIUM', 'NORMAL', '03', '1179594996624183297', '1', '2019-10-03 03:12:18', '1', '2019-10-03 03:12:18', 1,
        'NO'),
       ('1179594199698034690', '小型', 'SMALL', 'NORMAL', '04', '1179595078354391041', '1', '2019-10-03 03:12:37', '1', '2019-10-03 03:12:37', 1,
        'NO'),
       ('1179594199698034690', '微型', 'MINI', 'NORMAL', '05', '1179595148265050114', '1', '2019-10-03 03:12:54', '1', '2019-10-03 03:13:04', 1,
        'NO'),
       ('1179599571133452289', '滨海新区', '01', 'NORMAL', '01', '1179600210748035073', '1', '2019-10-03 03:33:01', '', '2023-04-29 21:04:41', 1,
        'YES'),
       ('1179599571133452289', '和平区', '02', 'NORMAL', '02', '1179600284144160769', '1', '2019-10-03 03:33:18', '', '2023-04-29 21:04:41', 1,
        'YES'),
       ('1179599571133452289', '南开区', '03', 'NORMAL', '03', '1179600486431248385', '1', '2019-10-03 03:34:07', '', '2023-04-29 21:04:41', 1,
        'YES'),
       ('1262358593149861889', '运行', 'RUNNING', 'NORMAL', '01', '1262358917289869313', '1', '2020-05-18 12:26:34', '1', '2020-05-18 12:26:34', 1,
        'NO'),
       ('1262358593149861889', '暂停', 'PAUSE', 'NORMAL', '02', '1262358917403115522', '1', '2020-05-18 12:26:34', '1', '2020-05-18 12:26:34', 1,
        'NO'),
       ('1174854852808916994', '操作日志', 'OPERATION', 'NORMAL', '01', '1262556152227258370', '1', '2020-05-19 01:30:19', '1', '2020-05-19 01:30:19',
        1, 'NO'),
       ('1174854852808916994', '审计日志', 'AUDIT', 'NORMAL', '02', '1262556152365670402', '1', '2020-05-19 01:30:19', '1', '2020-05-19 01:30:19', 1,
        'NO'),
       ('1174854852808916994', '调度日志', 'SCHEDULER', 'NORMAL', '03', '1262556152441167873', '1', '2020-05-19 01:30:19', '1', '2020-05-19 01:30:19',
        1, 'NO'),
       ('1265177705416810498', '简体中文(中国)', 'zh_CN', 'NORMAL', '01', '1266697034650177538', '1', '2020-05-30 11:44:42', '1',
        '2020-05-30 11:44:42', 1, 'NO'),
       ('1265177705416810498', '英语(美国)', 'en_US', 'NORMAL', '02', '1266697034826338305', '1', '2020-05-30 11:44:42', '1', '2020-05-30 11:44:42',
        1, 'NO'),
       ('1269113842543939585', '协同办公', 'OA', 'NORMAL', '01', '1269114091245195265', '1', '2020-06-06 03:49:13', '1', '2020-06-06 03:49:13', 1,
        'NO'),
       ('1269113842543939585', '生产管理', 'PRODUCE', 'NORMAL', '02', '1269114091563962370', '1', '2020-06-06 03:49:13', '1', '2020-06-06 03:49:13',
        1, 'NO'),
       ('1272515129729941506', '运行', 'ACTIVE', 'NORMAL', '01', '1272515335129202690', '1', '2020-06-15 13:04:33', '1', '2020-06-15 13:04:33', 1,
        'NO'),
       ('1272515129729941506', '挂起', 'SUSPENDED', 'NORMAL', '02', '1272515335334723586', '1', '2020-06-15 13:04:33', '1', '2020-06-15 13:04:33', 1,
        'NO'),
       ('1272515129729941506', '结束', 'COMPLETED', 'NORMAL', '03', '1272515335426998273', '1', '2020-06-15 13:04:33', '1', '2020-06-15 13:04:33', 1,
        'NO'),
       ('1273602535702962177', '事假', 'PERSONAL', 'NORMAL', '01', '1273602926956027906', '1', '2020-06-18 13:06:15', '1', '2020-06-18 13:06:15', 1,
        'NO'),
       ('1273602535702962177', '病假', 'SICK', 'NORMAL', '02', '1273602927039913986', '1', '2020-06-18 13:06:15', '1', '2020-06-18 13:06:15', 1,
        'NO'),
       ('1277567432352706561', '同意', 'AGREE', 'NORMAL', '01', '1277568116233973762', '1', '2020-06-29 11:42:30', '1', '2020-06-29 11:42:30', 1,
        'NO'),
       ('1277567432352706561', '驳回', 'REJECT', 'NORMAL', '02', '1277568116443688961', '1', '2020-06-29 11:42:30', '1', '2020-06-29 11:42:30', 1,
        'NO'),
       ('1277567432352706561', '已处理', 'HANDLED', 'NORMAL', '03', '1277568116540157954', '1', '2020-06-29 11:42:30', '1', '2020-06-29 11:42:30', 1,
        'NO'),
       ('1282502015493943298', '任务监听器', 'TASK', 'NORMAL', '01', '1282502187162611714', '1', '2020-07-13 02:28:44', '1', '2020-07-13 02:28:44', 1,
        'NO'),
       ('1282502015493943298', '执行监听器', 'EXECUTION', 'NORMAL', '02', '1282502187263275009', '1', '2020-07-13 02:28:44', '1',
        '2020-07-13 02:28:44', 1, 'NO'),
       ('1282503938662019073', ' 开始', 'START', 'NORMAL', '01', '1282504522815320066', '1', '2020-07-13 02:38:01', '1', '2020-07-13 02:38:01', 1,
        'NO'),
       ('1282503938662019073', '结束', 'END', 'NORMAL', '02', '1282504522874040322', '1', '2020-07-13 02:38:01', '1', '2020-07-13 02:38:01', 1, 'NO'),
       ('1282503938662019073', '输入流', 'TAKE', 'NORMAL', '03', '1282504522915983362', '1', '2020-07-13 02:38:01', '1', '2020-07-13 02:38:01', 1,
        'NO'),
       ('1282502372156583937', '创建', 'CREATE', 'NORMAL', '01', '1282504714088165377', '1', '2020-07-13 02:38:46', '1', '2020-07-13 02:38:46', 1,
        'NO'),
       ('1282502372156583937', '指派', 'ASSIGNMENT', 'NORMAL', '02', '1282504714117525505', '1', '2020-07-13 02:38:46', '1', '2020-07-13 02:38:46', 1,
        'NO'),
       ('1282502372156583937', '完成', 'COMPLETE', 'NORMAL', '03', '1282504714260131841', '1', '2020-07-13 02:38:46', '1', '2020-07-13 02:38:46', 1,
        'NO'),
       ('1282502372156583937', '删除', 'DELETE', 'NORMAL', '04', '1282504714302074881', '1', '2020-07-13 02:38:46', '1', '2020-07-13 02:38:46', 1,
        'NO'),
       ('1282512573043683329', '类', 'CLASS', 'NORMAL', '01', '1282513739349602306', '1', '2020-07-13 03:14:38', '1', '2020-07-13 03:14:38', 1, 'NO'),
       ('1282512573043683329', '表达式', 'EXPRESSION', 'NORMAL', '02', '1282513739831947266', '1', '2020-07-13 03:14:38', '1', '2020-07-13 03:14:38',
        1, 'NO'),
       ('1282512573043683329', '委托类', 'DELEGATE_EXPRESSION', 'NORMAL', '03', '1282513739882278913', '1', '2020-07-13 03:14:38', '1',
        '2020-07-13 03:14:38', 1, 'NO'),
       ('1296000032435920897', '提交', 'COMMIT', 'NORMAL', '01', '1296000262959063042', '1', '2020-08-19 08:25:16', '1', '2020-08-19 08:25:16', 1,
        'NO'),
       ('1296000032435920897', '转办', 'TRANSFER', 'NORMAL', '02', '1296000263084892161', '1', '2020-08-19 08:25:16', '1', '2020-08-19 08:25:16', 1,
        'NO'),
       ('1296000032435920897', '委派', 'DELEGATE', 'NORMAL', '03', '1296000263198138369', '1', '2020-08-19 08:25:16', '1', '2020-08-19 08:25:16', 1,
        'NO'),
       ('1174607762093481986', '按钮', 'BUTTON', 'NORMAL', '01', '1325059473888022529', '1', '2020-11-07 20:56:12', '1', '2020-11-07 20:56:12', 1,
        'NO'),
       ('1174607762093481986', '菜单', 'MENU', 'NORMAL', '02', '1325059473955131394', '1', '2020-11-07 20:56:12', '1', '2020-11-07 20:56:12', 1,
        'NO'),
       ('1174607762093481986', '模块', 'MODULE', 'NORMAL', '03', '1325059474051600385', '1', '2020-11-07 20:56:12', '1', '2020-11-07 20:56:12', 1,
        'NO'),
       ('1174607762093481986', '页面', 'PAGE', 'NORMAL', '04', '1325059474122903553', '1', '2020-11-07 20:56:12', '1', '2020-11-07 20:56:12', 1,
        'NO'),
       ('1174607762093481986', '分组', 'GROUP', 'NORMAL', '05', '1325059474223566850', '1', '2020-11-07 20:56:12', '1', '2020-11-07 20:56:12', 1,
        'NO'),
       ('1174607762093481986', '流程', 'PROCESS', 'NORMAL', '06', '1325059474303258626', '1', '2020-11-07 20:56:12', '1', '2020-11-07 20:56:12', 1,
        'NO'),
       ('1174607762093481986', '区域', 'AREA', 'NORMAL', '07', '1325059474357784577', '1', '2020-11-07 20:56:12', '1', '2020-11-07 20:56:12', 1,
        'NO'),
       ('1325222761112305666', '不可见', 'INVISIBLE', 'NORMAL', '01', '1325223289607192578', '1', '2020-11-08 07:47:08', '1', '2020-11-08 07:47:08',
        1, 'NO'),
       ('1325222761112305666', '只读', 'READONLY', 'NORMAL', '02', '1325223289833684993', '1', '2020-11-08 07:47:08', '1', '2020-11-08 07:47:08', 1,
        'NO'),
       ('1325222761112305666', '编辑', 'EDIT', 'NORMAL', '03', '1325223289917571073', '1', '2020-11-08 07:47:08', '1', '2020-11-08 07:47:08', 1,
        'NO'),
       ('1311548054114738178', '普通', 'NORMAL', 'NORMAL', '01', '1325966778752122882', '1', '2020-11-10 09:01:30', '1', '2020-11-10 09:01:30', 1,
        'NO'),
       ('1311548054114738178', '会签', 'COUNTERSIGN', 'NORMAL', '02', '1325966778907312130', '1', '2020-11-10 09:01:30', '1', '2020-11-10 09:01:30',
        1, 'NO'),
       ('1341260929215250433', '百度', 'BAIDU', 'NORMAL', '01', '1341263922601934850', '1', '2020-12-22 14:06:53', '1', '2020-12-22 14:06:53', 1,
        'YES'),
       ('1341264114575228930', '标准', 'STANDARD', 'NORMAL', '01', '1341264820900216834', '1', '2020-12-22 14:10:27', '1', '2020-12-22 14:10:27', 1,
        'YES'),
       ('1341264114575228930', '高精度', 'ACCURATE', 'NORMAL', '02', '1341264821034434561', '1', '2020-12-22 14:10:27', '1', '2020-12-22 14:10:27', 1,
        'YES'),
       ('1341265461290745858', '中英文混合', 'CHN_ENG', 'NORMAL', '01', '1341266262079848449', '1', '2020-12-22 14:16:11', '1', '2020-12-22 14:16:11',
        1, 'YES'),
       ('1341265461290745858', '英文', 'ENG', 'NORMAL', '02', '1341266262578970626', '1', '2020-12-22 14:16:11', '1', '2020-12-22 14:16:11', 1,
        'YES'),
       ('1341265461290745858', '日语', 'JAP', 'NORMAL', '03', '1341266262700605441', '1', '2020-12-22 14:16:11', '1', '2020-12-22 14:16:11', 1,
        'YES'),
       ('1341265461290745858', '韩语', 'KOR', 'NORMAL', '04', '1341266262763520001', '1', '2020-12-22 14:16:11', '1', '2020-12-22 14:16:11', 1,
        'YES'),
       ('1341265461290745858', '法语', 'FRE', 'NORMAL', '05', '1341266262822240257', '1', '2020-12-22 14:16:11', '1', '2020-12-22 14:16:11', 1,
        'YES'),
       ('1341265461290745858', '西班牙语', 'SPA', 'NORMAL', '06', '1341266262851600385', '1', '2020-12-22 14:16:11', '1', '2020-12-22 14:16:11', 1,
        'YES'),
       ('1341265461290745858', '葡萄牙语', 'POR', 'NORMAL', '07', '1341266262851600386', '1', '2020-12-22 14:16:11', '1', '2020-12-22 14:16:11', 1,
        'YES'),
       ('1341265461290745858', '德语', 'GER', 'NORMAL', '08', '1341266263040344066', '1', '2020-12-22 14:16:11', '1', '2020-12-22 14:16:11', 1,
        'YES'),
       ('1341265461290745858', '意大利语', 'ITA', 'NORMAL', '09', '1341266263048732673', '1', '2020-12-22 14:16:11', '1', '2020-12-22 14:16:11', 1,
        'YES'),
       ('1341265461290745858', '俄语', 'RUS', 'NORMAL', '10', '1341266263048732674', '1', '2020-12-22 14:16:11', '1', '2020-12-22 14:16:11', 1,
        'YES'),
       ('1341266372721393665', '逐行显示', 'LINE', 'NORMAL', '01', '1341266625667284994', '1', '2020-12-22 14:17:38', '1', '2020-12-22 14:17:38', 1,
        'YES'),
       ('1341266372721393665', '自动分段', 'PARAGRAPH', 'NORMAL', '02', '1341266625893777409', '1', '2020-12-22 14:17:38', '1', '2020-12-22 14:17:38',
        1, 'YES'),
       ('1350339368731893761', '按年', 'YEAR', 'NORMAL', '01', '1350339585476747266', '1', '2021-01-16 15:10:20', '1', '2021-01-16 15:10:20', 1,
        'NO'),
       ('1350339368731893761', '按月', 'MONTH', 'NORMAL', '02', '1350339585636130818', '1', '2021-01-16 15:10:20', '1', '2021-01-16 15:10:20', 1,
        'NO'),
       ('1350339368731893761', '按日', 'DAY', 'NORMAL', '03', '1350339585690656769', '1', '2021-01-16 15:10:20', '1', '2021-01-16 15:10:20', 1, 'NO'),
       ('1123580626102722562', '正常', 'NORMAL', 'NORMAL', '01', '1353609223002210306', '1', '2021-01-25 15:42:42', '1', '2021-01-25 15:42:42', 1,
        'NO'),
       ('1123580626102722562', '停用', 'DEAD', 'NORMAL', '02', '1353609223140622338', '1', '2021-01-25 15:42:42', '1', '2021-01-25 15:42:42', 1,
        'NO'),
       ('1360478516031590401', '纯文本', 'TEXT', 'NORMAL', '01', '1361105334644314113', '1', '2021-02-15 08:09:35', '1', '2021-02-15 08:09:35', 1,
        'NO'),
       ('1360478516031590401', '富文本', 'HTML', 'NORMAL', '02', '1361105334807891969', '1', '2021-02-15 08:09:35', '1', '2021-02-15 08:09:35', 1,
        'NO'),
       ('1383303458082246658', '正常', 'NORMAL', 'NORMAL', '01', '1383303664341340162', '1', '2021-04-17 14:17:49', '1', '2021-04-17 14:17:49', 1,
        'YES'),
       ('1383303458082246658', '锁定', 'LOCKED', 'NORMAL', '02', '1383303664429420545', '1', '2021-04-17 14:17:49', '1', '2021-04-17 14:17:49', 1,
        'YES'),
       ('1371737392916668417', '查看文件夹', 'VIEW_FOLDER', 'NORMAL', '01', '1384069613887361025', '1', '2021-03-16 17:19:09', '1',
        '2021-03-16 17:19:09', 0, 'YES'),
       ('1371737392916668417', '创建文件夹', 'CREATE_FOLDER', 'NORMAL', '02', '1384069614092881921', '1', '2021-03-16 17:19:09', '1',
        '2021-03-16 17:19:09', 0, 'YES'),
       ('1371737392916668417', '更名文件夹', 'RENAME_FOLDER', 'NORMAL', '03', '1384069614092881922', '1', '2021-03-16 17:19:09', '1',
        '2021-03-16 17:19:09', 0, 'YES'),
       ('1371737392916668417', '删除文件夹', 'REMOVE_FOLDER', 'NORMAL', '04', '1384069614260654082', '1', '2021-03-16 17:19:09', '1',
        '2021-03-16 17:19:09', 0, 'YES'),
       ('1371737392916668417', '复制到…', 'COPY_FOLDER', 'NORMAL', '05', '1384069614260654083', '1', '2021-03-20 14:18:21', '1',
        '2021-03-20 14:18:21', 0, 'YES'),
       ('1371737392916668417', '移动到…', 'MOVE_FOLDER', 'NORMAL', '06', '1384069614260654084', '1', '2021-03-20 14:18:21', '1',
        '2021-03-20 14:18:21', 0, 'YES'),
       ('1371737392916668417', '按用户组授权', 'GRANT_PERMISSION_BY_USER_GROUP', 'NORMAL', '08', '1384069614260654085', '1', '2021-03-17 14:16:04',
        '1', '2021-03-17 14:16:04', 0, 'YES'),
       ('1371737392916668417', '按组织授权', 'GRANT_PERMISSION_BY_ORGANIZATION', 'NORMAL', '09', '1384069614260654086', '1', '2021-03-17 14:16:04',
        '1', '2021-03-17 14:16:04', 0, 'YES'),
       ('1371737392916668417', '预览文档', 'PREVIEW_DOCUMENT', 'NORMAL', '11', '1384069614260654087', '1', '2021-03-16 16:20:58', '1',
        '2021-03-16 16:20:58', 0, 'YES'),
       ('1371737392916668417', '上传文档', 'UPLOAD_DOCUMENT', 'NORMAL', '12', '1384069614260654088', '1', '2021-03-16 16:20:58', '1',
        '2021-03-16 16:20:58', 0, 'YES'),
       ('1371737392916668417', '下载文档', 'DOWNLOAD_DOCUMENT', 'NORMAL', '13', '1384069614726221825', '1', '2021-03-16 16:20:58', '1',
        '2021-03-16 16:20:58', 0, 'YES'),
       ('1371737392916668417', '更名文档', 'RENAME_DOCUMENT', 'NORMAL', '14', '1384069614726221826', '1', '2021-03-16 16:20:58', '1',
        '2021-03-16 16:20:58', 0, 'YES'),
       ('1371737392916668417', '更新文档', 'UPDATE_DOCUMENT', 'NORMAL', '1401', '1384069614810107906', '1', '2021-04-17 14:20:40', '1',
        '2021-04-17 14:20:40', 1, 'YES'),
       ('1371737392916668417', '分享文档', 'SHARE_DOCUMENT', 'NORMAL', '1402', '1384069614893993985', '1', '2021-04-17 14:20:40', '1',
        '2021-04-17 14:20:40', 1, 'YES'),
       ('1371737392916668417', '删除文档', 'REMOVE_DOCUMENT', 'NORMAL', '15', '1384069614935937025', '1', '2021-03-21 18:13:54', '1',
        '2021-03-21 18:13:54', 0, 'YES'),
       ('1371737392916668417', '复制文档', 'COPY_DOCUMENT', 'NORMAL', '16', '1384069614990462977', '1', '2021-03-22 11:07:13', '1',
        '2021-03-22 11:07:13', 1, 'YES'),
       ('1371737392916668417', '移动文档', 'MOVE_DOCUMENT', 'NORMAL', '17', '1384069615078543362', '1', '2021-03-22 11:07:13', '1',
        '2021-03-22 11:07:13', 1, 'YES'),
       ('1371737392916668417', '锁定文档', 'LOCK_DOCUMENT', 'NORMAL', '18', '1384069615137263618', '1', '2021-04-17 14:20:39', '1',
        '2021-04-17 14:20:39', 1, 'YES'),
       ('1371737392916668417', '解锁文档', 'UNLOCK_DOCUMENT', 'NORMAL', '19', '1384069615187595265', '1', '2021-04-17 14:20:40', '1',
        '2021-04-17 14:20:40', 1, 'YES'),
       ('1371737392916668417', '查看版本', 'VIEW_DOCUMENT_VERSION', 'NORMAL', '20', '1384069615233732610', '1', '2021-04-19 16:45:08', '1',
        '2021-04-19 16:45:08', 1, 'YES'),
       ('1371737392916668417', '恢复版本', 'RESTORE_DOCUMENT_VERSION', 'NORMAL', '21', '1384069615284064258', '1', '2021-04-19 16:45:08', '1',
        '2021-04-19 16:45:08', 1, 'YES'),
       ('1388656423496265729', '正常', 'NORMAL', 'NORMAL', '01', '1388656943157948418', '1', '2021-05-02 08:49:50', '1', '2021-05-02 08:49:50', 1,
        'YES'),
       ('1388656423496265729', '回忆', 'REMINISCENCE', 'NORMAL', '02', '1388656943527047169', '1', '2021-05-02 08:49:50', '1', '2021-05-02 08:49:50',
        1, 'YES'),
       ('1388660434081361922', '培育', 'CULTIVATE', 'NORMAL', '01', '1388661648353341442', '1', '2021-05-02 09:08:32', '1', '2021-05-02 09:08:32', 1,
        'YES'),
       ('1388660434081361922', '购买', 'BUY', 'NORMAL', '02', '1388661648810520577', '1', '2021-05-02 09:08:32', '1', '2021-05-02 09:08:32', 1,
        'YES'),
       ('1388660434081361922', '受赠', 'RECIPIENT', 'NORMAL', '03', '1388661648844075010', '1', '2021-05-02 09:08:32', '1', '2021-05-02 09:08:32', 1,
        'YES'),
       ('1388660434081361922', '交换', 'EXCHANGE', 'NORMAL', '04', '1388661648890212353', '1', '2021-05-02 09:08:32', '1', '2021-05-02 09:08:32', 1,
        'YES'),
       ('1388660434081361922', '其他', 'OTHER', 'NORMAL', '05', '1388661648932155393', '1', '2021-05-02 09:08:32', '1', '2021-05-02 09:08:32', 1,
        'YES'),
       ('1388664937186799618', '其他', 'OTHER', 'NORMAL', '05', '1388685397630894081', '1', '2021-05-02 09:29:03', '1', '2021-05-02 09:29:03', 1,
        'YES'),
       ('1388664937186799618', '自然枯萎', ' NATURAL_DEATH', 'NORMAL', '01', '1388685397832220674', '1', '2021-05-02 09:29:01', '1',
        '2021-05-02 09:29:01', 1, 'YES'),
       ('1388664937186799618', '意外死亡', 'ACCIDENT_DEATH', 'NORMAL', '02', '1388685397903523842', '1', '2021-05-02 09:29:01', '1',
        '2021-05-02 09:29:01', 1, 'YES'),
       ('1388664937186799618', '送人', 'GIVE', 'NORMAL', '03', '1388685397966438401', '1', '2021-05-02 09:29:01', '1', '2021-05-02 09:29:01', 1,
        'YES'),
       ('1388664937186799618', '丢失', 'LOST', 'NORMAL', '04', '1388685398012575746', '1', '2021-05-02 09:29:01', '1', '2021-05-02 09:29:01', 1,
        'YES'),
       ('1388670333649629185', '播种', 'SEED', 'NORMAL', '01', '1388774781994577922', '1', '2021-05-02 09:51:35', '1', '2021-05-02 09:51:35', 1,
        'YES'),
       ('1388670333649629185', '扦插', 'CUTTAGE', 'NORMAL', '02', '1388774782506283009', '1', '2021-05-02 09:51:36', '1', '2021-05-02 09:51:36', 1,
        'YES'),
       ('1388670333649629185', '分株', 'RAMET', 'NORMAL', '03', '1388774782531448833', '1', '2021-05-02 09:51:36', '1', '2021-05-02 09:51:36', 1,
        'YES'),
       ('1388670333649629185', '发芽', 'SPROUT', 'NORMAL', '04', '1388774782531448834', '1', '2021-05-02 09:51:36', '1', '2021-05-02 09:51:36', 1,
        'YES'),
       ('1388670333649629185', '生根', 'ROOT', 'NORMAL', '05', '1388774782632112129', '1', '2021-05-02 09:51:36', '1', '2021-05-02 09:51:36', 1,
        'YES'),
       ('1388670333649629185', '生长', 'GROW', 'NORMAL', '06', '1388774782711803906', '1', '2021-05-02 09:51:36', '1', '2021-05-02 09:51:36', 1,
        'YES'),
       ('1388670333649629185', '蕴蕾', 'FLOWER_BUD', 'NORMAL', '07', '1388774782766329858', '1', '2021-05-02 09:51:36', '1', '2021-05-02 09:51:36', 1,
        'YES'),
       ('1388670333649629185', '开花', 'BLOOM', 'NORMAL', '08', '1388774782774718466', '1', '2021-05-02 09:51:36', '1', '2021-05-02 09:51:36', 1,
        'YES'),
       ('1388670333649629185', '结果', 'RESULT', 'NORMAL', '09', '1388774782774718467', '1', '2021-05-02 09:51:36', '1', '2021-05-02 09:51:36', 1,
        'YES'),
       ('1388670333649629185', '休眠', 'DORMANCY', 'NORMAL', '10', '1388774782866993153', '1', '2021-05-02 09:51:36', '1', '2021-05-02 09:51:36', 1,
        'YES'),
       ('1388670333649629185', '离园', 'LEAVE', 'NORMAL', '11', '1388774782866993154', '1', '2021-05-02 09:51:36', '1', '2021-05-02 09:51:36', 1,
        'YES'),
       ('1388670333649629185', '其他', 'OTHER', 'NORMAL', '12', '1388774782866993155', '1', '2021-05-02 16:38:05', '1', '2021-05-02 16:38:05', 1,
        'YES'),
       ('1427825609281265665', '成功', 'SUCCESS', 'NORMAL', '01', '1427825760758554625', '1', '2021-08-18 10:52:44', '1', '2021-08-18 10:52:44', 1,
        'NO'),
       ('1427825609281265665', '错误', 'ERROR', 'NORMAL', '02', '1427825760985047041', '1', '2021-08-18 10:52:44', '1', '2021-08-18 10:52:44', 1,
        'NO'),
       ('1427790110311309314', '用户', 'USER', 'NORMAL', '01', '1428612289915445249', '1', '2021-08-18 08:32:20', '1', '2021-08-18 08:32:20', 1,
        'NO'),
       ('1427790110311309314', '类目', 'CATEGORY', 'NORMAL', '02', '1428612292960509954', '1', '2021-08-18 08:32:20', '1', '2021-08-18 08:32:20', 1,
        'NO'),
       ('1427790110311309314', '商品', 'PRODUCT', 'NORMAL', '03', '1428612293086339073', '1', '2021-08-18 08:32:20', '1', '2021-08-18 08:32:20', 1,
        'NO'),
       ('1427790110311309314', '消息', 'MESSAGE', 'NORMAL', '04', '1428612293086339074', '1', '2021-08-20 14:58:08', '1', '2021-08-20 14:58:08', 1,
        'NO'),
       ('1428993861053530114', '用户', 'USER', 'NORMAL', '01', '1428994000203759617', '1', '2021-08-21 16:14:54', '1', '2021-08-21 16:14:54', 1,
        'NO'),
       ('1428993861053530114', '订单', 'ORDER', 'NORMAL', '02', '1428994000262479873', '1', '2021-08-21 16:14:54', '1', '2021-08-21 16:14:54', 1,
        'NO'),
       ('1428993861053530114', '商品', 'PRODUCT', 'NORMAL', '03', '1428994000308617217', '1', '2021-08-21 16:14:54', '1', '2021-08-21 16:14:54', 1,
        'NO'),
       ('1429635583670210561', '成功', 'SUCCESS', 'NORMAL', '01', '1429635698300538882', '1', '2021-08-23 10:44:46', '1', '2021-08-23 10:44:46', 1,
        'NO'),
       ('1429635583670210561', '错误', 'ERROR', 'NORMAL', '02', '1429635698459922434', '1', '2021-08-23 10:44:47', '1', '2021-08-23 10:44:47', 1,
        'NO'),
       ('1429643182180388865', '待发送', 'WAIT_REQUEST', 'NORMAL', '01', '1429643484497432578', '1', '2021-08-23 11:15:43', '1',
        '2021-08-23 11:15:43', 1, 'NO'),
       ('1429643182180388865', '已发送', 'REQUESTED', 'NORMAL', '02', '1429643484690370561', '1', '2021-08-23 11:15:43', '1', '2021-08-23 11:15:43',
        1, 'NO'),
       ('1429643182180388865', '已响应', 'RESPONSED', 'NORMAL', '03', '1429643484774256642', '1', '2021-08-23 11:15:43', '1', '2021-08-23 11:15:43',
        1, 'NO'),
       ('1429643182180388865', '待处理', 'WAIT_HANDLE', 'NORMAL', '04', '1429643484874919938', '1', '2021-08-23 11:15:43', '1', '2021-08-23 11:15:43',
        1, 'NO'),
       ('1450627333771464706', '客户端', 'CLIENT', 'NORMAL', '01', '1450627445981679618', '1', '2021-10-20 08:58:29', '1', '2021-10-20 08:58:29', 1,
        'NO'),
       ('1450627333771464706', '接口', 'INTERFACE', 'NORMAL', '02', '1450627446346584065', '1', '2021-10-20 08:58:29', '1', '2021-10-20 08:58:29', 1,
        'NO'),
       ('1451349441031917570', '承运商', 'CARRIER', 'NORMAL', '01', '1451349580714823681', '1', '2021-10-22 08:47:59', '1', '2021-10-22 08:47:59', 1,
        'NO'),
       ('1451349441031917570', '跟踪服务商', 'TRACER', 'NORMAL', '02', '1451349580773543937', '1', '2021-10-22 08:47:59', '1', '2021-10-22 08:47:59',
        1, 'NO'),
       ('1169086045509345282', '是', 'YES', 'NORMAL', '01', '1582558230768222209', '1', '2019-09-04 03:20:25', '1', '2020-05-15 11:36:14', 3, 'NO'),
       ('1169086045509345282', '否', 'NO', 'NORMAL', '02', '1582558232324308994', '1', '2019-09-04 03:20:40', '', '2023-04-26 19:48:07', 5, 'NO'),
       ('1586952096636190722', '是', 'YES', 'NORMAL', '01', '1586952096636190122', '1', '2019-09-04 03:20:25', '1', '2020-05-15 11:36:14', 3, 'NO'),
       ('1586952096636190722', '否', 'NO', 'NORMAL', '02', '1586952096636190123', '1', '2019-09-04 03:20:25', '1', '2020-05-15 11:36:14', 3, 'NO'),
       ('1586952096636190722', '自定义', 'CUSTOM', 'NORMAL', '03', '1586952096636190124', '1', '2019-09-04 03:20:25', '1', '2020-05-15 11:36:14', 3,
        'NO'),
       ('1586952096636190721', '列表视图', 'LIST', 'NORMAL', '01', '1586954427855519745', '1', '2022-10-31 13:33:14', '1', '2022-10-31 13:33:14', 1,
        'NO'),
       ('1586952096636190721', '修改视图', 'MODIFY', 'NORMAL', '03', '1586954428660826114', '1', '2022-10-31 13:33:15', '1', '2022-10-31 13:33:15', 1,
        'NO'),
       ('1586952096636190721', '新增视图', 'ADD', 'NORMAL', '02', '1586954428660826115', '1', '2022-10-31 13:33:15', '1', '2022-10-31 13:33:15', 1,
        'NO'),
       ('1586952096636190721', '查看视图', 'VIEW', 'NORMAL', '04', '1586954428719546370', '1', '2022-10-31 13:33:15', '1', '2022-10-31 13:33:15', 1,
        'NO'),
       ('1586952096636190721', '参照视图', 'REFERENCE', 'NORMAL', '05', '1586954428757295105', '1', '2022-10-31 13:33:15', '1', '2022-10-31 13:33:15',
        1, 'NO'),
       ('1586952096636190721', '树视图', 'TREE', 'NORMAL', '06', '1586954428820209666', '1', '2022-10-31 13:33:15', '1', '2022-10-31 13:33:15', 1,
        'NO'),
       ('1586952096636190721', '树表视图', 'TREE_LIST', 'NORMAL', '07', '1586954428820209667', '1', '2022-10-31 13:33:15', '', '2023-04-30 08:38:38',
        2, 'NO'),
       ('1586952096636190721', '树参照视图', 'TREE_REFERENCE', 'NORMAL', '08', '1586954428874735618', '1', '2022-10-31 13:33:15', '',
        '2023-04-30 08:38:45', 2, 'NO'),
       ('1586952096636190721', '自定义视图', 'CUSTOM', 'NORMAL', '99', '1586954428874735619', '1', '2022-10-31 13:33:15', '1', '2022-10-31 13:33:15',
        1, 'NO'),
       ('1589891416225394690', '标识模型', 'ID_MODEL', 'NORMAL', '02', '1589898075278553090', '1', '2022-11-08 16:07:01', '', '2023-04-30 10:19:46',
        1, 'YES'),
       ('1589891416225394690', '业务模型', 'BUSINESS_MODEL', 'NORMAL', '01', '1589898075404382209', '1', '2022-11-08 16:07:01', '1',
        '2022-11-08 16:07:01', 1, 'NO'),
       ('1599667172472147969', '文本框', 'TEXT', 'NORMAL', '01', '1599667433102004225', '1', '2022-12-05 15:30:11', '1', '2022-12-05 15:30:11', 1,
        'NO'),
       ('1599667633765896194', '日期时间', 'DATETIME', 'NORMAL', '01', '1599667712199380994', '1', '2022-12-05 15:31:18', '1', '2022-12-05 15:31:18',
        1, 'NO'),
       ('1586886552545378305', '文本', 'STRING', 'NORMAL', '01', '1599667972527247362', '1', '2022-10-31 10:07:56', '1', '2022-10-31 10:07:56', 1,
        'NO'),
       ('1586886552545378305', '整形', 'INTEGER', 'NORMAL', '02', '1599667972611133442', '1', '2022-10-31 10:07:56', '1', '2022-10-31 10:07:56', 1,
        'NO'),
       ('1586886552545378305', '长整型', 'LONG', 'NORMAL', '03', '1599667972669853697', '1', '2022-10-31 10:07:57', '1', '2022-10-31 10:07:57', 1,
        'NO'),
       ('1586886552545378305', '浮点数', 'DOUBLE', 'NORMAL', '04', '1599667972720185346', '1', '2022-10-31 10:07:57', '1', '2022-10-31 10:07:57', 1,
        'NO'),
       ('1586886552545378305', '日期时间', 'DATETIME', 'NORMAL', '05', '1599667972795682818', '1', '2022-10-31 10:07:57', '1', '2022-10-31 10:07:57',
        1, 'NO'),
       ('1586886552545378305', '金额', 'DECIMAL', 'NORMAL', '06', '1599667972866985986', '1', '2022-10-31 10:07:57', '1', '2022-10-31 10:07:57', 1,
        'NO'),
       ('1586886552545378305', '数据字典', 'DATA_DICTIONARY', 'NORMAL', '07', '1599667972887957505', '1', '2022-12-05 15:32:20', '1',
        '2022-12-05 15:32:20', 1, 'NO'),
       ('1586886552545378305', '实体', 'ENTITY', 'NORMAL', '08', '1599667972887957506', '1', '2022-12-05 15:32:20', '1', '2022-12-05 15:32:20', 1,
        'NO'),
       ('1599668268703830018', '实体选择', 'ENTITY_SELECT', 'NORMAL', '01', '1599668268703839018', '1', '2022-12-05 15:35:06', '1',
        '2022-12-05 15:35:06', 1, 'NO'),
       ('1599668268703830017', '下拉列表', 'DROP_DOWN_LIST', 'NORMAL', '01', '1599668669171781634', '1', '2022-12-05 15:35:06', '1',
        '2022-12-05 15:35:06', 1, 'NO'),
       ('1599668268703830017', '复选框组', 'CHECK_BOX_GROUP', 'NORMAL', '02', '1599668669247279106', '1', '2022-12-05 15:35:06', '',
        '2023-04-29 21:05:23', 1, 'YES'),
       ('1599668268703830017', '单选按钮组', 'RADIO_BUTTON_GROUP', 'NORMAL', '03', '1599668669310193665', '1', '2022-12-05 15:35:06', '1',
        '2022-12-05 15:35:06', 1, 'NO'),
       ('1600408579986063363', '开发平台', 'PLATFORM', 'NORMAL', '01', '1600408579986063364', '1', '2023-03-02 10:23:25', '1', '2023-03-02 10:23:25',
        1, 'NO'),
       ('1600408579986063363', '接口平台', 'CIP', 'NORMAL', '02', '1600408579986063365', '1', '2023-03-02 10:23:25', '1', '2023-03-02 10:23:25', 1,
        'NO'),
       ('1600408579986063362', '模糊匹配', 'LK', 'NORMAL', '01', '1600409116005531650', '1', '2022-12-07 16:37:22', '1', '2022-12-07 16:37:22', 1,
        'NO'),
       ('1600408579986063362', '以……开始', 'LL', 'NORMAL', '02', '1600409116072640514', '1', '2022-12-07 16:37:22', '1', '2022-12-07 16:37:22', 1,
        'NO'),
       ('1600408579986063362', '以……结束', 'RL', 'NORMAL', '03', '1600409116148137986', '1', '2022-12-07 16:37:22', '1', '2022-12-07 16:37:22', 1,
        'NO'),
       ('1600408579986063362', '精确匹配', 'EQ', 'NORMAL', '04', '1600409116223635457', '1', '2022-12-07 16:37:22', '1', '2022-12-07 16:37:22', 1,
        'NO'),
       ('1600056686730432514', '年月日时分秒', 'SECOND', 'NORMAL', '01', '1603582379607191553', '1', '2022-12-06 17:18:25', '1',
        '2022-12-06 17:18:25', 1, 'NO'),
       ('1600056686730432514', '年月日', 'DAY', 'NORMAL', '02', '1603582381863727106', '1', '2022-12-06 17:18:25', '1', '2022-12-06 17:18:25', 1,
        'NO'),
       ('1600056686730432514', '年月日时分', 'MINITE', 'NORMAL', '03', '1603582381863727107', '1', '2022-12-06 17:18:25', '1', '2022-12-06 17:18:25',
        1, 'NO'),
       ('1599665547623944193', '文本框', 'TEXT', 'NORMAL', '01', '1621029095935537153', '1', '2022-12-05 15:25:33', '1', '2022-12-05 15:25:33', 1,
        'NO'),
       ('1599665547623944193', '长文本', 'TEXTAREA', 'NORMAL', '02', '1621029096300441601', '1', '2023-02-02 14:13:48', '1', '2023-02-02 14:13:48', 1,
        'NO'),
       ('1599665547623944193', '富文本', 'RICH_TEXT', 'NORMAL', '03', '1621029096367550465', '1', '2023-02-02 14:13:48', '1', '2023-02-02 14:13:48',
        1, 'NO'),
       ('1595299760662601730', '列表视图页面按钮', 'LIST_PAGE', 'NORMAL', '01', '1631109805775097858', '1', '2022-11-23 14:15:34', '1',
        '2022-11-23 14:15:34', 1, 'NO'),
       ('1595299760662601730', '列表视图行按钮', 'LIST_ROW', 'NORMAL', '02', '1631109805775097859', '1', '2022-11-23 14:15:35', '1',
        '2022-11-23 14:15:35', 1, 'NO'),
       ('1595299760662601730', '新增视图页面按钮', 'ADD_PAGE', 'DEAD', '03', '1631109805775097860', '1', '2023-03-02 09:50:57', '',
        '2023-04-30 15:13:49', 2, 'NO'),
       ('1595299760662601730', '修改视图页面按钮', 'MODIFY_PAGE', 'DEAD', '04', '1631109805775097861', '1', '2023-03-02 09:50:57', '',
        '2023-04-30 15:13:53', 2, 'NO'),
       ('1597516489367216129', '新增', 'ADD', 'NORMAL', '01', '1631117975465594882', '1', '2022-11-29 18:00:41', '1', '2022-11-29 18:00:41', 1, 'NO'),
       ('1597516489367216129', '修改', 'MODIFY', 'NORMAL', '02', '1631117975465594883', '1', '2022-11-29 18:00:41', '1', '2022-11-29 18:00:41', 1,
        'NO'),
       ('1597516489367216129', '删除', 'REMOVE', 'NORMAL', '03', '1631117975465594884', '1', '2022-11-29 18:00:42', '1', '2022-11-29 18:00:42', 1,
        'NO'),
       ('1597516489367216129', '保存', 'SAVE', 'NORMAL', '04', '1631117975465594885', '1', '2023-03-02 10:20:37', '1', '2023-03-02 10:20:37', 1,
        'NO'),
       ('1597516489367216129', '取消', 'CANCEL', 'NORMAL', '98', '1631117975465594886', '1', '2023-03-02 10:21:08', '1', '2023-03-02 10:21:08', 1,
        'NO'),
       ('1597516489367216129', '复制新增', 'ADD_BY_COPY', 'NORMAL', '04', '1631117975465594894', '1', '2022-11-29 18:00:42', '1',
        '2022-11-29 18:00:42', 1, 'NO'),
       ('1597516489367216129', '自定义', 'CUSTOM', 'NORMAL', '99', '1631117975532703745', '1', '2022-11-29 18:00:42', '1', '2022-11-29 18:00:42', 1,
        'NO'),
       ('1597516489367216129', '关闭', 'CLOSE', 'NORMAL', '05', '1631117975532703746', '1', '2023-03-02 10:23:25', '1', '2023-03-02 10:23:25', 1,
        'NO'),
       ('1597516489367216129', '刷新', 'REFRESH', 'NORMAL', '97', '1631117975532703747', '1', '2023-03-02 10:23:25', '1', '2023-03-02 10:23:25', 1,
        'NO'),
       ('1169086045509345282', '213', '213', 'NORMAL', '213', '1651190351666393089', '', '2023-04-26 19:43:52', '', '2023-04-26 19:44:25', 1, 'YES'),
       ('1586952096636190721', '树多选参照视图', 'TREE_MULTIPLE_REFERENCE', 'NORMAL', '09', '1651742101448200194', '', '2023-04-28 08:16:20', '',
        '2023-04-30 08:39:00', 3, 'NO');

-- 导出  表 abc.sys_dictionary_type 结构
CREATE TABLE IF NOT EXISTS `sys_dictionary_type`
(
    `dictionary_type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '上级',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='字典类型';

-- 正在导出表  abc.sys_dictionary_type 的数据：~71 rows (大约)
INSERT INTO `sys_dictionary_type` (`dictionary_type`, `name`, `code`, `order_no`, `id`, `create_id`, `create_time`, `update_id`, `update_time`,
                                   `version`, `delete_flag`)
VALUES ('0', '字典类型', 'ZDLX', '01', '1', NULL, NULL, NULL, '2019-07-09 13:29:36', 1, 'NO'),
       ('1', '公用模块', 'Common', '01', '1123532280403148801', NULL, NULL, '1', '2022-10-18 15:43:35', 2, 'NO'),
       ('1', '系统管理', 'SystemManagement', '02', '1123533546831298562', NULL, NULL, NULL, '2019-07-02 14:08:44', 1, 'NO'),
       ('1123532280403148801', '状态', 'Status', '02', '1123580626102722562', NULL, NULL, '1', '2020-12-31 17:04:00', 2, 'NO'),
       ('1123532280403148801', '证件类型', 'IdentificationType', '99', '1127134167920594946', NULL, NULL, '1', '2019-09-04 03:14:31', 1, 'NO'),
       ('1123532280403148801', '是否', 'YesOrNo', '01', '1169086045509345282', '1', '2019-09-04 03:13:28', '1', '2022-10-17 13:14:17', 4, 'NO'),
       ('1123533546831298562', '组织机构类型', 'OrganizationType', '01', '1170950228873932802', '1', '2019-09-09 06:41:04', '1',
        '2019-10-29 06:34:28', 1, 'NO'),
       ('1123532280403148801', '性别', 'Gender', '03', '1174599224604467202', '1', '2019-09-19 08:20:53', '1', '2019-09-19 08:23:46', 1, 'NO'),
       ('1123533546831298562', '用户状态', 'UserStatus', '02', '1174603142700965890', '1', '2019-09-19 08:36:27', '1', '2019-10-03 03:04:01', 1,
        'NO'),
       ('1123533546831298562', '权限项目类型', 'PermissionType', '03', '1174607762093481986', '1', '2019-09-19 08:54:48', '1', '2020-05-10 01:48:22',
        1, 'NO'),
       ('1123533546831298562', '系统日志类型', 'LogType', '06', '1174854852808916994', '', '2019-09-20 01:16:39', '1', '2020-05-19 01:30:30', 2,
        'NO'),
       ('1123533546831298562', '行业类别', 'IndustryClassification', '11', '1179592839783043073', '1', '2019-10-03 03:03:43', '1',
        '2019-10-03 03:38:11', 1, 'NO'),
       ('1123533546831298562', '企业规模', 'CompanyScale', '12', '1179594199698034690', '1', '2019-10-03 03:09:08', '1', '2019-10-03 03:38:34', 1,
        'NO'),
       ('1123533546831298562', '区域', 'District', '13', '1179599571133452289', '1', '2019-10-03 03:30:28', '1', '2019-10-03 03:39:00', 1, 'NO'),
       ('1', '任务调度', 'Scheduler', '04', '1262358501588205570', '1', '2020-05-18 12:24:55', '1', '2020-05-18 12:24:55', 1, 'NO'),
       ('1262358501588205570', '任务状态', 'JobStatus', '01', '1262358593149861889', '1', '2020-05-18 12:25:17', '1', '2020-05-18 12:25:17', 1, 'NO'),
       ('1123533546831298562', '时区', 'TimeZone', '07', '1265177591377879041', '1', '2020-05-26 07:06:58', '1', '2020-05-26 07:07:10', 2, 'NO'),
       ('1123533546831298562', '语种', 'Language', '08', '1265177705416810498', '1', '2020-05-26 07:07:25', '1', '2020-05-26 07:07:37', 2, 'NO'),
       ('1', '工作流', 'Workflow', '05', '1269113727745839106', '1', '2020-06-06 03:47:46', '1', '2020-06-06 03:47:46', 1, 'NO'),
       ('1269113727745839106', '模板分类', 'TemplateCategory', '01', '1269113842543939585', '1', '2020-06-06 03:48:14', '1', '2020-06-06 03:48:14', 1,
        'NO'),
       ('1269113727745839106', '流程状态', 'FlowStatus', '02', '1272515129729941506', '1', '2020-06-15 13:03:44', '1', '2020-06-21 02:40:20', 3,
        'NO'),
       ('1', '业务流程', 'BusinessFlow', '06', '1273602414458216450', '1', '2020-06-18 13:04:13', '1', '2020-06-18 13:04:13', 1, 'NO'),
       ('1273602414458216450', '请假类型', 'LeaveType', '01', '1273602535702962177', '1', '2020-06-18 13:04:42', '1', '2020-06-18 13:04:42', 1, 'NO'),
       ('1269113727745839106', '常用审批意见', 'CommonAdvice', '03', '1277567432352706561', '1', '2020-06-29 11:39:47', '1', '2020-06-29 11:39:47', 1,
        'NO'),
       ('1269113727745839106', '流程监听器类别', 'WorkflowListenerCategory', '04', '1282502015493943298', '1', '2020-07-13 02:28:03', '1',
        '2020-07-13 03:24:10', 2, 'NO'),
       ('1269113727745839106', '任务监听器事件类型', 'TaskListenerEventCategory', '05', '1282502372156583937', '1', '2020-07-13 02:29:28', '1',
        '2020-07-13 02:35:47', 3, 'NO'),
       ('1269113727745839106', '执行监听器事件类型', 'ExecutionListenerEventCategory', '06', '1282503938662019073', '1', '2020-07-13 02:35:41', '1',
        '2020-07-13 02:35:41', 1, 'NO'),
       ('1269113727745839106', '监听器类型', 'WorkflowListenerType', '07', '1282512573043683329', '1', '2020-07-13 03:10:00', '1',
        '2020-07-13 03:25:40', 2, 'NO'),
       ('1269113727745839106', '提交类型', 'CommitType', '08', '1296000032435920897', '1', '2020-08-19 08:24:21', '1', '2020-08-19 08:24:21', 1,
        'NO'),
       ('1269113727745839106', '环节模式', 'NodeMode', '09', '1311548054114738178', '1', '2020-10-01 14:06:38', '1', '2020-10-01 14:06:38', 1, 'NO'),
       ('1269113727745839106', '访问编码', 'AccessCode', '10', '1325222761112305666', '1', '2020-11-08 07:45:02', '1', '2020-11-08 07:45:14', 2,
        'NO'),
       ('1', '文字识别', 'Ocr', '07', '1341260546183020546', '1', '2020-12-22 13:53:28', '', '2023-04-29 21:06:12', 1, 'YES'),
       ('1341260546183020546', '识别引擎', 'RecognizeEngine', '01', '1341260929215250433', '1', '2020-12-22 13:55:00', '', '2023-04-29 21:06:12', 2,
        'YES'),
       ('1341260546183020546', '识别精度', 'RecognizeProbability', '02', '1341264114575228930', '1', '2020-12-22 14:07:39', '', '2023-04-29 21:06:12',
        1, 'YES'),
       ('1341260546183020546', '语言类型', 'LanguageType', '03', '1341265461290745858', '1', '2020-12-22 14:13:00', '', '2023-04-29 21:06:12', 1,
        'YES'),
       ('1341260546183020546', '显示模式', 'DisplayMode', '04', '1341266372721393665', '1', '2020-12-22 14:16:37', '', '2023-04-29 21:06:12', 1,
        'YES'),
       ('1', '业务支撑', 'Support', '03', '1350339296518561793', '1', '2021-01-16 15:09:11', '1', '2021-01-16 15:09:11', 1, 'NO'),
       ('1350339296518561793', '流水号重置策略', 'SerialNoResetStrategy', '01', '1350339368731893761', '1', '2021-01-16 15:09:28', '1',
        '2021-01-16 15:09:28', 1, 'NO'),
       ('1350339296518561793', '内容模板类型', 'ContentTemplateType', '02', '1360478516031590401', '1', '2021-02-13 14:38:49', '1',
        '2021-02-13 14:38:49', 1, 'NO'),
       ('1', '文档管理', 'Edoc', '99', '1371736935821418498', '1', '2021-03-16 16:15:46', '', '2023-04-29 21:05:56', 1, 'YES'),
       ('1371736935821418498', '文档权限项', 'DocumentPermissionItem', '01', '1371737392916668417', '1', '2021-03-16 16:17:35', '',
        '2023-04-29 21:05:56', 2, 'YES'),
       ('1371736935821418498', '文档状态', 'DocumentStatus', '02', '1383303458082246658', '1', '2021-04-17 14:17:00', '', '2023-04-29 21:05:56', 1,
        'YES'),
       ('1', '小花园', 'LittleGarden', '98', '1388656218348662785', '1', '2021-05-02 08:46:57', '', '2023-04-29 21:05:56', 1, 'YES'),
       ('1388656218348662785', '植物状态', 'PlantStatus', '01', '1388656423496265729', '1', '2021-05-02 08:47:46', '', '2023-04-29 21:05:44', 1,
        'YES'),
       ('1388656218348662785', '植物来源', 'PlantSource', '02', '1388660434081361922', '1', '2021-05-02 09:03:42', '', '2023-04-29 21:05:44', 2,
        'YES'),
       ('1388656218348662785', '植物离园原因', 'PlantLeaveReason', '03', '1388664937186799618', '1', '2021-05-02 09:21:36', '', '2023-04-29 21:05:44',
        1, 'YES'),
       ('1388656218348662785', '植物成长阶段', 'PlantGrowthStage', '04', '1388670333649629185', '1', '2021-05-02 09:43:02', '', '2023-04-29 21:05:44',
        1, 'YES'),
       ('1', '接口平台', 'ApiPlatform', '999', '1427789981864943617', '1', '2021-08-18 08:30:33', '1', '2021-08-18 08:30:33', 1, 'NO'),
       ('1427789981864943617', '接口服务分类', 'ApiServiceCategory', '01', '1427790110311309314', '1', '2021-08-18 08:31:04', '1',
        '2021-08-18 08:31:04', 1, 'NO'),
       ('1427789981864943617', '接口服务执行结果', 'ApiServiceExecuteResult', '02', '1427825609281265665', '1', '2021-08-18 10:52:08', '1',
        '2021-08-23 10:43:26', 2, 'NO'),
       ('1427789981864943617', '消息主题分类', 'ApiMessageTopicCategory', '03', '1428993861053530114', '1', '2021-08-21 16:14:21', '1',
        '2021-08-21 16:14:21', 1, 'NO'),
       ('1427789981864943617', '消息响应结果', 'MessageResponseResult', '04', '1429635583670210561', '1', '2021-08-23 10:44:19', '1',
        '2021-08-23 11:28:24', 2, 'NO'),
       ('1427789981864943617', '消息状态', 'MessageStatus', '05', '1429643182180388865', '1', '2021-08-23 11:14:31', '1', '2021-08-23 11:14:31', 1,
        'NO'),
       ('1427789981864943617', '对接模式', 'IntegrationModel', '06', '1450627333771464706', '1', '2021-10-20 08:58:02', '1', '2021-10-20 08:58:02', 1,
        'NO'),
       ('1427789981864943617', '数据角色', 'DataRole', '07', '1451349441031917570', '1', '2021-10-22 08:47:26', '1', '2021-10-22 08:47:26', 1, 'NO'),
       ('1123532280403148801', '测试', 'test', NULL, '1582558526084972545', '1', '2022-10-19 10:25:30', '', '2023-04-26 14:06:28', 1, 'YES'),
       ('1', '实体配置', 'EntityConfig', '0101', '1586886291055689730', '1', '2022-10-31 09:02:29', '1', '2022-10-31 09:02:29', 1, 'NO'),
       ('1586886291055689730', '实体模型属性类型', 'EntityModelPropertyType', '01', '1586886552545378305', '1', '2022-10-31 09:03:32', '1',
        '2022-10-31 10:11:33', 2, 'NO'),
       ('1586886291055689730', '实体视图类型', 'EntityViewType', '02', '1586952096636190721', '1', '2022-10-31 13:23:59', '1', '2022-10-31 13:23:59',
        1, 'NO'),
       ('1586886291055689730', '显示控制', 'ShowControl', '11', '1586952096636190722', '1', '2022-10-31 13:23:59', '1', '2022-10-31 13:23:59', 1,
        'NO'),
       ('1586886291055689730', '基础模型', 'BaseModel', '03', '1589891416225394690', '1', '2022-11-08 16:03:47', '1', '2022-11-08 16:10:44', 3, 'NO'),
       ('1586886291055689730', '视图按钮类型', 'ViewButtonType', '04', '1595299760662601730', '1', '2022-11-23 14:14:37', '1', '2023-03-02 09:51:29',
        2, 'NO'),
       ('1586886291055689730', '按钮操作类型', 'ButtonOperationType', '05', '1597516489367216129', '1', '2022-11-29 17:03:06', '1',
        '2022-11-29 17:03:06', 1, 'NO'),
       ('1586886291055689730', '文本属性显示控件', 'TextDisplayComponent', '06', '1599665547623944193', '1', '2022-12-05 15:22:42', '1',
        '2022-12-05 15:22:42', 1, 'NO'),
       ('1586886291055689730', '数值属性显示控件', 'NumberDisplayComponent', '07', '1599667172472147969', '1', '2022-12-05 15:29:09', '1',
        '2022-12-05 15:29:09', 1, 'NO'),
       ('1586886291055689730', '日期时间显示控件', 'DateTimeDisplayComponent', '08', '1599667633765896194', '1', '2022-12-05 15:30:59', '1',
        '2022-12-05 15:30:59', 1, 'NO'),
       ('1586886291055689730', '数据字典显示控件', 'DataDictionaryDisplayComponent', '09', '1599668268703830017', '1', '2022-12-05 15:33:30', '1',
        '2022-12-05 15:35:23', 2, 'NO'),
       ('1586886291055689730', '实体显示控件', 'EntityDisplayComponent', '10', '1599668268703830018', '1', '2022-12-05 15:33:30', '1',
        '2022-12-05 15:35:23', 2, 'NO'),
       ('1123532280403148801', '日期时间格式', 'DatetimeFormat', '04', '1600056686730432514', '1', '2022-12-06 17:16:56', '1', '2022-12-06 17:16:56',
        1, 'NO'),
       ('1586886291055689730', '文本匹配规则', 'TextPatternRule', '10', '1600408579986063362', '1', '2022-12-07 16:35:14', '1', '2022-12-07 16:35:23',
        2, 'NO'),
       ('1586886291055689730', '应用编码', 'AppCode', '10', '1600408579986063363', '1', '2022-12-07 16:35:14', '1', '2022-12-07 16:35:23', 2, 'NO'),
       ('1123532280403148801', '123123', 'Tset', NULL, '1651105291164491778', '', '2023-04-26 14:05:52', '', '2023-04-26 14:06:26', 2, 'YES');

-- 导出  表 abc.sys_group_permission_item 结构
CREATE TABLE IF NOT EXISTS `sys_group_permission_item`
(
    `id` varchar
(
    32
) NOT NULL COMMENT '标识',
    `group_id` varchar
(
    32
) DEFAULT NULL COMMENT '组标识',
    `permission_item_id` varchar
(
    32
) DEFAULT NULL COMMENT '权限项标识',
    `create_id` varchar
(
    32
) DEFAULT NULL COMMENT '创建人标识',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) DEFAULT NULL COMMENT '更新人标识',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本',
    `delete_flag` varchar
(
    32
) DEFAULT NULL COMMENT '删除标志',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组与权限项对应表';

-- 正在导出表  abc.sys_group_permission_item 的数据：~170 rows (大约)
INSERT INTO `sys_group_permission_item` (`id`, `group_id`, `permission_item_id`, `create_id`, `create_time`, `update_id`, `update_time`, `version`,
                                         `delete_flag`)
VALUES ('1', '99', '1123781860797521922', NULL, NULL, NULL, NULL, 1, 'NO'),
       ('1651826946509668354', '2', '1583621693334597634', '', '2023-04-28 13:53:28', '', '2023-04-28 13:53:28', 1, 'YES'),
       ('1651827186029592578', '2', '1', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186029592579', '2', '1583265755662610434', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186029592580', '2', '1583621684736274434', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701442', '2', '1583621693334597634', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701443', '2', '1583621694890684418', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701444', '2', '1583621695494664193', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701445', '2', '1583621695784071170', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701446', '2', '1583621696102838274', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701447', '2', '1585889231057698817', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701448', '2', '1586963701121925122', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701449', '2', '1590507235774967810', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701450', '2', '1590507417728069634', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701451', '2', '1583621695494664194', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701452', '2', '1583621695494664195', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701453', '2', '1583266846315884546', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701454', '2', '1583266854025015297', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701455', '2', '1583266854423474177', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186096701456', '2', '1583266855144894466', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810305', '2', '1583266855564324865', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810306', '2', '1583266855878897665', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810307', '2', '1584461700769234945', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810308', '2', '1584461706913890306', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810309', '2', '1584461707165548546', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810310', '2', '1584461709011042306', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810311', '2', '1584461709384335362', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810312', '2', '1584461709879263234', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810313', '2', '1584461709879263235', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810314', '2', '1586906740598751233', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810315', '2', '1586956123683893253', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810316', '2', '1584462679212220418', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810317', '2', '1584462686090878978', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810318', '2', '1584462686384480257', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810319', '2', '1584462686619361282', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810320', '2', '1584462686808104962', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810321', '2', '1584462687097511938', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810322', '2', '1586956123683893252', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810323', '2', '1586956114351566849', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810324', '2', '1586956123314794497', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810325', '2', '1586956123683893250', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810326', '2', '1586956123964911617', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810327', '2', '1586956124229152770', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186163810328', '2', '1586956124682137601', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919169', '2', '1586956124682137602', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919170', '2', '1586967216296804354', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919171', '2', '1586956123683893251', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919172', '2', '1591992839796871169', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919173', '2', '1591992980327026690', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919174', '2', '1619170258194518017', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919175', '2', '1619170258194518018', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919176', '2', '1592471425539870122', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919177', '2', '1592471434742173612', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919178', '2', '1592471435039969212', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919179', '2', '1592471436025630712', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919180', '2', '1592471436260511712', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919181', '2', '1592471436533141512', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919182', '2', '1592471425539870722', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919183', '2', '1592471434742173692', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919184', '2', '1592471435039969282', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919185', '2', '1592471436025630722', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919186', '2', '1592471436260511742', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919187', '2', '1592471436533141502', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919188', '2', '1593519785781477377', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919189', '2', '1593519794681790465', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919190', '2', '1593519797760409602', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919191', '2', '1593519799350050818', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186230919192', '2', '1593519799966613506', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028034', '2', '1593519800327323650', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028035', '2', '1594593708811984897', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028036', '2', '1594593720476344321', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028037', '2', '1594593724951666690', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028038', '2', '1594593725396262914', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028039', '2', '1594593725878607873', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028040', '2', '1594593726226735105', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028041', '2', '1619244122144473089', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028042', '2', '1619244141081755650', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028043', '2', '1619244144802103297', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028044', '2', '1619244145921982465', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028045', '2', '1619244146307858433', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028046', '2', '1619244146639208450', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028047', '2', '1123781860797521922', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028048', '2', '1258371492062494721', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028049', '2', '1258371492804886529', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028050', '2', '1258371493064933378', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028051', '2', '1258371493341757441', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028052', '2', '1258371494054789121', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028053', '2', '1258371494268698625', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028054', '2', '1258380325329633281', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028055', '2', '1258380404652310529', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028056', '2', '1586956123683893254', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028057', '2', '1258380528866623490', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028058', '2', '1258380662643949569', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028059', '2', '1258380783511207938', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186298028060', '2', '1370639564832157698', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136897', '2', '1351070763888521218', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136898', '2', '1352426900563009538', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136899', '2', '1353529204645130241', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136900', '2', '1354299184139571201', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136901', '2', '1370271549858967554', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136902', '2', '1370271550739771393', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136903', '2', '1370271551461191681', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136904', '2', '1370271551792541698', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136905', '2', '1370271551461191682', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136906', '2', '1370271552614625282', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136907', '2', '1370271552937586689', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136908', '2', '1370301447256338434', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136909', '2', '1370301510581940225', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136910', '2', '1370301696909701122', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136911', '2', '1370301777373229058', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136912', '2', '1258371930191081474', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136913', '2', '1258371930891530242', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136914', '2', '1258371932376313858', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136915', '2', '1258371932812521474', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136916', '2', '1258371933047402498', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136917', '2', '1258371933303255042', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136918', '2', '1258379307581767681', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136919', '2', '1258379488863780865', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136920', '2', '1586956123683893255', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136921', '2', '1353606456242171906', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136922', '2', '1353606536361766913', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136923', '2', '1353606636085538817', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136924', '2', '1258639382447697922', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136925', '2', '1258639382976180226', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136926', '2', '1258639384108642306', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136927', '2', '1258639384544849921', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136928', '2', '1258639384544849922', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136929', '2', '1258639384926531586', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186365136930', '2', '1258639385144635394', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051458', '2', '1259291003875799042', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051459', '2', '1259291086197403650', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051460', '2', '1260912691533144066', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051461', '2', '1260912692279730178', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051462', '2', '1260912692594302978', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051463', '2', '1260912693076647938', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051464', '2', '1260912693705793537', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051465', '2', '1260912694167166978', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051466', '2', '1261173972848664577', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051467', '2', '1651189382161412097', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051468', '2', '1651189568988295170', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051469', '2', '1651189568988295171', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051470', '2', '1651189568988295172', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051471', '2', '1651741776586772481', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051472', '2', '1651189568988295173', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051473', '2', '1651189568988295174', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051474', '2', '1651190904865730562', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051475', '2', '1651190904865730563', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051476', '2', '1259327774730788866', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051477', '2', '1259327775368323074', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051478', '2', '1259327775691284482', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051479', '2', '1259327776525950977', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051480', '2', '1259327776777609217', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051481', '2', '1259327777486446594', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051482', '2', '1167338537256849410', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051483', '2', '1167341043433181186', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051484', '2', '1261589386628005890', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051485', '2', '1264912153746104322', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051486', '2', '1264912154291363841', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051487', '2', '1264912154593353729', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051488', '2', '1264912155016978433', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051489', '2', '1264912155230887938', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051490', '2', '1264912155394465794', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051491', '2', '1381518481944162306', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051492', '2', '1381518816209219586', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051493', '2', '1381518905912799233', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051494', '2', '1381519008715190274', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('1651827186428051495', '2', '1650840779060494337', '', '2023-04-28 13:54:25', '', '2023-04-28 13:54:25', 1, 'NO'),
       ('2', '99', '1258371492062494721', NULL, NULL, NULL, NULL, 1, 'YES'),
       ('3', '99', '1258371930191081474', NULL, NULL, NULL, NULL, 1, 'YES'),
       ('4', '99', '1259327774730788866', NULL, NULL, NULL, NULL, 1, 'NO');

-- 导出  表 abc.sys_group_user 结构
CREATE TABLE IF NOT EXISTS `sys_group_user`
(
    `id` varchar
(
    32
) NOT NULL COMMENT '标识',
    `group_id` varchar
(
    32
) DEFAULT NULL COMMENT '组标识',
    `user_id` varchar
(
    32
) DEFAULT NULL COMMENT '用户标识',
    `create_id` varchar
(
    32
) DEFAULT NULL COMMENT '创建人标识',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) DEFAULT NULL COMMENT '更新人标识',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本',
    `delete_flag` varchar
(
    32
) DEFAULT NULL COMMENT '删除标志',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组与用户对应表';

-- 正在导出表  abc.sys_group_user 的数据：~51 rows (大约)
INSERT INTO `sys_group_user` (`id`, `group_id`, `user_id`, `create_id`, `create_time`, `update_id`, `update_time`, `version`, `delete_flag`)
VALUES ('1', '99', '1', NULL, NULL, NULL, NULL, 1, 'YES'),
       ('1651558220086398977', '3', '1', '', '2023-04-27 20:05:39', '', '2023-04-27 20:05:39', 1, 'YES'),
       ('1651561873841102849', '3', '1', '', '2023-04-27 20:20:10', '', '2023-04-27 20:20:10', 1, 'YES'),
       ('1651561911409483778', '3', '1', '', '2023-04-27 20:20:19', '', '2023-04-27 20:20:19', 1, 'YES'),
       ('1651561911409483779', '4', '1', '', '2023-04-27 20:20:19', '', '2023-04-27 20:20:19', 1, 'YES'),
       ('1651561911409483780', '1650396893808746498', '1', '', '2023-04-27 20:20:19', '', '2023-04-27 20:20:19', 1, 'YES'),
       ('1651561911409483781', '1650397103687524354', '1', '', '2023-04-27 20:20:19', '', '2023-04-27 20:20:19', 1, 'YES'),
       ('1651561911409483782', '1650397148998590465', '1', '', '2023-04-27 20:20:19', '', '2023-04-27 20:20:19', 1, 'YES'),
       ('1651561911409483783', '1650653075458785282', '1', '', '2023-04-27 20:20:19', '', '2023-04-27 20:20:19', 1, 'YES'),
       ('1651565812724572162', '1', '1', '', '2023-04-27 20:35:49', '', '2023-04-27 20:35:49', 1, 'YES'),
       ('1651565812791681025', '2', '1', '', '2023-04-27 20:35:49', '', '2023-04-27 20:35:49', 1, 'YES'),
       ('1651565812791681026', '99', '1', '', '2023-04-27 20:35:49', '', '2023-04-27 20:35:49', 1, 'YES'),
       ('1651565812791681027', '3', '1', '', '2023-04-27 20:35:49', '', '2023-04-27 20:35:49', 1, 'YES'),
       ('1651565812791681028', '4', '1', '', '2023-04-27 20:35:49', '', '2023-04-27 20:35:49', 1, 'YES'),
       ('1651565812791681029', '1650396893808746498', '1', '', '2023-04-27 20:35:49', '', '2023-04-27 20:35:49', 1, 'YES'),
       ('1651565812791681030', '1650397103687524354', '1', '', '2023-04-27 20:35:49', '', '2023-04-27 20:35:49', 1, 'YES'),
       ('1651565812791681031', '1650397148998590465', '1', '', '2023-04-27 20:35:49', '', '2023-04-27 20:35:49', 1, 'YES'),
       ('1651565812791681032', '1650653075458785282', '1', '', '2023-04-27 20:35:49', '', '2023-04-27 20:35:49', 1, 'YES'),
       ('1651565843238133761', '2', '1', '', '2023-04-27 20:35:56', '', '2023-04-27 20:35:56', 1, 'YES'),
       ('1651565843238133762', '99', '1', '', '2023-04-27 20:35:56', '', '2023-04-27 20:35:56', 1, 'YES'),
       ('1651565843305242625', '4', '1', '', '2023-04-27 20:35:56', '', '2023-04-27 20:35:56', 1, 'YES'),
       ('1651565843305242626', '1650396893808746498', '1', '', '2023-04-27 20:35:56', '', '2023-04-27 20:35:56', 1, 'YES'),
       ('1651565843305242627', '1650397103687524354', '1', '', '2023-04-27 20:35:56', '', '2023-04-27 20:35:56', 1, 'YES'),
       ('1651565843305242628', '1650397148998590465', '1', '', '2023-04-27 20:35:56', '', '2023-04-27 20:35:56', 1, 'YES'),
       ('1651565843305242629', '1650653075458785282', '1', '', '2023-04-27 20:35:56', '', '2023-04-27 20:35:56', 1, 'YES'),
       ('1651565892319879169', '1650396893808746498', '1', '', '2023-04-27 20:36:08', '', '2023-04-27 20:36:08', 1, 'YES'),
       ('1651566042895392769', '2', '1', '', '2023-04-27 20:36:44', '', '2023-04-27 20:36:44', 1, 'YES'),
       ('1651566042895392770', '99', '1', '', '2023-04-27 20:36:44', '', '2023-04-27 20:36:44', 1, 'YES'),
       ('1651566402498240513', '99', '1', '', '2023-04-27 20:38:10', '', '2023-04-27 20:38:10', 1, 'YES'),
       ('1651783449303965698', '99', '1', '', '2023-04-28 11:00:38', '', '2023-04-28 11:00:38', 1, 'YES'),
       ('1651783449333325826', '3', '1', '', '2023-04-28 11:00:38', '', '2023-04-28 11:00:38', 1, 'YES'),
       ('1652144397940166657', '1', '1', '', '2023-04-29 10:54:54', '', '2023-04-29 10:54:54', 1, 'NO'),
       ('1652144397990498306', '2', '1', '', '2023-04-29 10:54:54', '', '2023-04-29 10:54:54', 1, 'YES'),
       ('1652144397990498307', '99', '1', '', '2023-04-29 10:54:54', '', '2023-04-29 10:54:54', 1, 'NO'),
       ('1652144397990498308', '1651566243760611330', '1', '', '2023-04-29 10:54:54', '', '2023-04-29 10:54:54', 1, 'NO'),
       ('1652144397990498309', '3', '1', '', '2023-04-29 10:54:55', '', '2023-04-29 10:54:55', 1, 'NO'),
       ('1652144397990498310', '4', '1', '', '2023-04-29 10:54:55', '', '2023-04-29 10:54:55', 1, 'NO'),
       ('1652144397990498311', '1650396893808746498', '1', '', '2023-04-29 10:54:55', '', '2023-04-29 10:54:55', 1, 'YES'),
       ('1652144397990498312', '1650397103687524354', '1', '', '2023-04-29 10:54:55', '', '2023-04-29 10:54:55', 1, 'NO'),
       ('1652144397990498313', '1650397148998590465', '1', '', '2023-04-29 10:54:55', '', '2023-04-29 10:54:55', 1, 'YES'),
       ('1652144397990498314', '1650653075458785282', '1', '', '2023-04-29 10:54:55', '', '2023-04-29 10:54:55', 1, 'NO'),
       ('1652144416508350465', '1', '1651546954525593601', '', '2023-04-29 10:54:59', '', '2023-04-29 10:54:59', 1, 'NO'),
       ('1652144416508350466', '2', '1651546954525593601', '', '2023-04-29 10:54:59', '', '2023-04-29 10:54:59', 1, 'YES'),
       ('1652144416508350467', '99', '1651546954525593601', '', '2023-04-29 10:54:59', '', '2023-04-29 10:54:59', 1, 'NO'),
       ('1652144416508350468', '1651566243760611330', '1651546954525593601', '', '2023-04-29 10:54:59', '', '2023-04-29 10:54:59', 1, 'NO'),
       ('1652144416508350469', '3', '1651546954525593601', '', '2023-04-29 10:54:59', '', '2023-04-29 10:54:59', 1, 'NO'),
       ('1652144416508350470', '4', '1651546954525593601', '', '2023-04-29 10:54:59', '', '2023-04-29 10:54:59', 1, 'NO'),
       ('1652144416508350471', '1650396893808746498', '1651546954525593601', '', '2023-04-29 10:54:59', '', '2023-04-29 10:54:59', 1, 'YES'),
       ('1652144416508350472', '1650397103687524354', '1651546954525593601', '', '2023-04-29 10:54:59', '', '2023-04-29 10:54:59', 1, 'NO'),
       ('1652144416575459330', '1650397148998590465', '1651546954525593601', '', '2023-04-29 10:54:59', '', '2023-04-29 10:54:59', 1, 'YES'),
       ('1652144416575459331', '1650653075458785282', '1651546954525593601', '', '2023-04-29 10:54:59', '', '2023-04-29 10:54:59', 1, 'NO'),
       ('1652235850355900417', '2', '1', '', '2023-04-29 16:58:18', '', '2023-04-29 16:58:18', 1, 'YES'),
       ('1652235850355900418', '2', '1651546954525593601', '', '2023-04-29 16:58:18', '', '2023-04-29 16:58:18', 1, 'YES'),
       ('1652236026407591938', '2', '1', '', '2023-04-29 16:59:00', '', '2023-04-29 16:59:00', 1, 'NO'),
       ('1652236026407591939', '2', '1651546954525593601', '', '2023-04-29 16:59:00', '', '2023-04-29 16:59:00', 1, 'NO'),
       ('1652241303378894850', '1650653075458785282', '1651846726876676097', '', '2023-04-29 17:19:59', '', '2023-04-29 17:19:59', 1, 'NO'),
       ('1652241303378894851', '1650653075458785282', '1650311010862542849', '', '2023-04-29 17:19:59', '', '2023-04-29 17:19:59', 1, 'NO');

-- 导出  表 abc.sys_log 结构
CREATE TABLE IF NOT EXISTS `sys_log`
(
    `content` varchar
(
    256
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '内容',
    `log_type` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '日志类型',
    `request_time` datetime DEFAULT NULL COMMENT '请求时间',
    `request_param` varchar
(
    5120
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求参数',
    `request_path` varchar
(
    1024
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求路径',
    `request_method` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求方法',
    `operator_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人标识',
    `operator_account` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人账号',
    `operator_name` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人姓名',
    `operator_ip` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人ip',
    `response_code` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '响应结果',
    `response_data` varchar
(
    5120
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '响应数据',
    `time_consuming` int
(
    11
) DEFAULT NULL COMMENT '执行耗时ms',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='日志';

-- 正在导出表  abc.sys_log 的数据：~0 rows (大约)

-- 导出  表 abc.sys_organization 结构
CREATE TABLE IF NOT EXISTS `sys_organization`
(
    `organization` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '组织机构',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
    `type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'DEPARTMENT' COMMENT '类型',
    `status` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'NORMAL' COMMENT '状态',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `remark` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='组织机构';

-- 正在导出表  abc.sys_organization 的数据：~20 rows (大约)
INSERT INTO `sys_organization` (`organization`, `name`, `code`, `type`, `status`, `order_no`, `remark`, `id`, `create_id`, `create_time`, `update_id`,
                                `update_time`, `version`, `delete_flag`)
VALUES ('1', '未分配', 'unsigned', 'DEPARTMENT', 'NORMAL', '999', NULL, '-1', '1', '2021-01-22 08:49:02', '1', '2021-01-22 08:49:02', 1, 'NO'),
       ('0', '一二三集团', 'huayuan', 'GROUP', 'NORMAL', '01', NULL, '1', '1', NULL, '1', NULL, 1, 'NO'),
       ('1', '信息中心', NULL, 'DEPARTMENT', 'NORMAL', '03', NULL, '1180012958376042498', '1', '2019-10-04 06:53:08', '1', '2019-10-04 06:53:08', 1,
        'NO'),
       ('1', '生产部', NULL, 'DEPARTMENT', 'NORMAL', '01', NULL, '1180013063376248834', '1', '2019-10-04 06:53:33', '', '2023-04-22 10:55:03', 4,
        'NO'),
       ('1', '销售部', NULL, 'DEPARTMENT', 'NORMAL', '02', NULL, '1180013123820363778', '1', '2019-10-04 06:53:47', '1', '2019-10-04 06:53:47', 1,
        'NO'),
       ('1180013063376248834', '生产一部', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1186911044748820481', '1', '2019-10-23 07:43:39', '1',
        '2019-10-23 07:43:39', 1, 'NO'),
       ('1180013063376248834', '生产二部', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1186911094312910849', '1', '2019-10-23 07:43:51', '1',
        '2019-10-23 07:43:51', 1, 'NO'),
       ('1180013063376248834', '生产三部', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1186911138961276930', '1', '2019-10-23 07:44:02', '1',
        '2020-05-20 11:18:16', 2, 'NO'),
       ('1', '行政部', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1186911361171308545', '1', '2019-10-23 07:44:55', '', '2023-04-22 10:55:29', 9,
        'NO'),
       ('1186911361171308545', '人力资源', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1186911443060899841', '1', '2019-10-23 07:45:14', '1',
        '2019-10-23 07:45:14', 1, 'NO'),
       ('1186911361171308545', '办公室', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1186911503463071745', '1', '2019-10-23 07:45:29', '1',
        '2019-10-23 07:45:29', 1, 'NO'),
       ('1186911361171308545', '后勤', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1186911538351292417', '1', '2019-10-23 07:45:37', '1',
        '2019-10-23 07:45:37', 1, 'NO'),
       ('1', '财务部', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1186911884368789506', '1', '2019-10-23 07:47:00', '', '2023-04-22 10:55:26', 5,
        'NO'),
       ('1186911884368789506', '会计', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1186911937498038274', '1', '2019-10-23 07:47:12', '1',
        '2020-05-20 11:21:52', 3, 'NO'),
       ('1186911884368789506', '出纳', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1186911985829003266', '1', '2019-10-23 07:47:24', '1',
        '2019-10-23 07:47:24', 1, 'NO'),
       ('1180012958376042498', '生产IT', 'PP', 'MODULE', 'NORMAL', '01', NULL, '1298459665034199042', '1', '2020-08-26 03:18:03', '1',
        '2020-08-26 03:18:03', 1, 'NO'),
       ('1', '人事部', NULL, 'DEPARTMENT', 'NORMAL', '05', NULL, '1319510646789570561', '1301393416705126402', '2020-10-23 13:27:08',
        '1301393416705126402', '2020-10-23 13:27:08', 1, 'NO'),
       ('1180013123820363778', '办公室', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1353505356692381698', '1', '2021-01-25 08:49:59', '1',
        '2021-01-25 08:49:59', 1, 'NO'),
       ('1180012958376042498', '办公室', NULL, 'DEPARTMENT', 'NORMAL', NULL, NULL, '1353505406197751809', '1', '2021-01-25 08:50:10', '1',
        '2021-01-25 08:50:10', 1, 'NO'),
       ('1298459665034199042', '研发小组', NULL, 'DEPARTMENT', 'NORMAL', '01', NULL, '1353622765709393922', '1', '2021-01-25 16:36:31', '1',
        '2021-01-25 16:36:31', 1, 'NO'),
       ('1186911044748820481', '21323', '123', 'DEPARTMENT', 'NORMAL', '123', NULL, '1648974743721852930', '', '2023-04-20 16:59:50', '',
        '2023-04-20 17:00:57', 1, 'YES'),
       ('1186911044748820481', '1223132', '213123', 'DEPARTMENT', 'NORMAL', NULL, NULL, '1648974923460362241', '', '2023-04-20 17:00:33', '',
        '2023-04-22 10:57:59', 1, 'YES'),
       ('1186911044748820481', '24213', '12313', 'DEPARTMENT', 'NORMAL', NULL, NULL, '1649321972508700673', '', '2023-04-21 15:59:36', '',
        '2023-04-22 10:58:03', 1, 'YES'),
       ('1186911443060899841', '133', '123', 'DEPARTMENT', 'NORMAL', '213', NULL, '1649593489192677378', '', '2023-04-22 09:58:30', '',
        '2023-04-22 09:58:58', 2, 'YES'),
       ('1186911094312910849', '123', '123', 'DEPARTMENT', 'NORMAL', NULL, NULL, '1649608506872983553', '', '2023-04-22 10:58:11', '',
        '2023-04-22 10:59:12', 2, 'NO'),
       ('1649608506872983553', '123', '123123', 'DEPARTMENT', 'NORMAL', NULL, NULL, '1649608547331239937', '', '2023-04-22 10:58:21', '',
        '2023-04-22 10:58:51', 1, 'YES');

-- 导出  表 abc.sys_param 结构
CREATE TABLE IF NOT EXISTS `sys_param`
(
    `param_name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '参数名称',
    `param_key` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '参数编码',
    `param_value` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '参数值',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序号',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT 'NO' COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='系统参数';

-- 正在导出表  abc.sys_param 的数据：~5 rows (大约)
INSERT INTO `sys_param` (`param_name`, `param_key`, `param_value`, `order_no`, `id`, `create_id`, `create_time`, `update_id`, `update_time`,
                         `version`, `delete_flag`)
VALUES ('密码最小长度', 'PASSWORD_LENGTH', '8', '001', '1158916418164588545', '1', '2019-08-07 09:43:00', '', '2023-04-17 20:56:43', 5, 'NO'),
       ('用户密码修改最少时间间隔（天）', 'PASSWORD_UPDATE_INTERVAL', '90', '002', '1158917461589983233', '1', '2019-08-07 09:47:09', '1',
        '2019-08-13 14:58:47', 1, 'NO'),
       ('用户修改密码允许的历史相同次数', 'PASSWORD_UPDATE_SAME_TIMES', '5', '003', '1158917636437934081', '1', '2019-08-07 09:47:51', '1',
        '2019-08-13 15:00:27', 2, 'NO'),
       ('用户登录最多输错次数', 'PASSWORD_INPUT_ERROR_TIMES', '10', '004', '1158917826028863489', '1', '2019-08-07 09:48:36', '1',
        '2020-08-26 03:21:29', 5, 'NO'),
       ('账号锁定自动解锁时间间隔（分）', 'ACCOUNT_UNLOCK_INTERVAL', '10', '005', '1158917976734400513', '1', '2019-08-07 09:49:12', '',
        '2023-03-24 10:47:21', 3, 'NO'),
       ('123', '123', '123', NULL, '1647947222863691777', '', '2023-04-17 20:56:50', '', '2023-04-17 20:57:17', 1, 'YES');

-- 导出  表 abc.sys_param_back 结构
CREATE TABLE IF NOT EXISTS `sys_param_back`
(
    `id` varchar
(
    32
) NOT NULL COMMENT '标识',
    `param_name` varchar
(
    32
) DEFAULT NULL COMMENT '参数名称',
    `param_key` varchar
(
    32
) DEFAULT NULL COMMENT '参数编码',
    `param_value` varchar
(
    32
) DEFAULT NULL COMMENT '参数值',
    `order_no` varchar
(
    10
) DEFAULT NULL COMMENT '排序号',
    `create_id` varchar
(
    32
) DEFAULT NULL COMMENT '创建人标识',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) DEFAULT NULL COMMENT '更新人标识',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本',
    `delete_flag` varchar
(
    32
) DEFAULT NULL COMMENT '删除标志',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数';

-- 正在导出表  abc.sys_param_back 的数据：~5 rows (大约)
INSERT INTO `sys_param_back` (`id`, `param_name`, `param_key`, `param_value`, `order_no`, `create_id`, `create_time`, `update_id`, `update_time`,
                              `version`, `delete_flag`)
VALUES ('1158916418164588545', '密码最小长度', 'PASSWORD_LENGTH', '8', '001', '1', '2019-08-07 09:43:00', '1', '2020-05-10 12:01:17', 4, 'NO'),
       ('1158917461589983233', '用户密码修改最少时间间隔（天）', 'PASSWORD_UPDATE_INTERVAL', '90', '002', '1', '2019-08-07 09:47:09', '1',
        '2019-08-13 14:58:47', 1, 'NO'),
       ('1158917636437934081', '用户修改密码允许的历史相同次数', 'PASSWORD_UPDATE_SAME_TIMES', '5', '003', '1', '2019-08-07 09:47:51', '1',
        '2019-08-13 15:00:27', 2, 'NO'),
       ('1158917826028863489', '用户登录最多输错次数', 'PASSWORD_INPUT_ERROR_TIMES', '10', '004', '1', '2019-08-07 09:48:36', '1',
        '2020-08-26 03:21:29', 5, 'NO'),
       ('1158917976734400513', '账号锁定自动解锁时间间隔（分）', 'ACCOUNT_UNLOCK_INTERVAL', '10', '005', '1', '2019-08-07 09:49:12', '',
        '2023-03-24 10:47:21', 3, 'YES');

-- 导出  表 abc.sys_permission_item 结构
CREATE TABLE IF NOT EXISTS `sys_permission_item`
(
    `permission_item` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '上级',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
    `type` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '类型',
    `permission_code` varchar
(
    256
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限编码',
    `view_code` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '视图编码',
    `component` varchar
(
    256
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组件',
    `icon` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图标',
    `status` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT 'NORMAL' COMMENT '状态',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='权限项';

-- 正在导出表  abc.sys_permission_item 的数据：~174 rows (大约)
INSERT INTO `sys_permission_item` (`permission_item`, `name`, `code`, `type`, `permission_code`, `view_code`, `component`, `icon`, `status`,
                                   `order_no`, `id`, `create_id`, `create_time`, `update_id`, `update_time`, `version`, `delete_flag`)
VALUES ('0', '系统资源', 'root', 'GROUP', 'root', NULL, '#', 'bx:bxs-component', 'NORMAL', '010', '1', NULL, NULL, '1', '2020-09-03 07:14:53', 2,
        'NO'),
       ('1', '系统管理', 'system', 'MODULE', 'system', NULL, '#', 'bx:bxs-component', 'NORMAL', '01', '1123781860797521922', NULL, NULL, '',
        '2023-04-29 20:33:53', 5, 'NO'),
       ('1149205382738038785', '列表', 'dataDictionary-list', 'PAGE', 'system:dataDictionary:dataDictionary-list', NULL, NULL, 'bx:bxs-component',
        'NORMAL', NULL, '1150946763743498241', NULL, '2019-07-16 09:54:27', NULL, '2019-07-16 09:54:27', 1, 'NO'),
       ('1149209869561188354', '列表', 'dictionaryItem-list', 'PAGE', 'system:dictionaryItem:dictionaryItem-list', NULL, NULL, 'bx:bxs-component',
        'NORMAL', NULL, '1150947746414399489', NULL, '2019-07-16 09:58:21', NULL, '2019-07-16 09:58:21', 1, 'NO'),
       ('1123781860797521922', '系统日志', 'log', 'MENU', 'system:log', 'list', 'system/view/log/list', 'bx:bxs-component', 'NORMAL', '09',
        '1167338537256849410', '1', '2019-08-30 15:29:30', '', '2023-04-26 20:28:07', 3, 'NO'),
       ('1167338537256849410', '查询', 'query', 'BUTTON', 'system:log:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01', '1167341043433181186',
        '1', '2019-08-30 15:39:28', '1', '2020-05-16 09:26:50', 2, 'NO'),
       ('1123781860797521922', '用户', 'user', 'MENU', 'system:user', NULL, 'system/view/user/treeList', 'bx:bxs-component', 'NORMAL', '01',
        '1258371492062494721', '1', '2020-05-07 12:21:57', '1', '2020-05-08 08:40:58', 3, 'NO'),
       ('1258371492062494721', '查询', 'query', 'BUTTON', 'system:user:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01', '1258371492804886529',
        '1', '2020-05-07 12:21:57', '1', '2020-05-07 12:21:57', 1, 'NO'),
       ('1258371492062494721', '查看', 'view', 'BUTTON', 'system:user:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02', '1258371493064933378',
        '1', '2020-05-07 12:21:57', '1', '2020-05-07 12:21:57', 1, 'NO'),
       ('1258371492062494721', '新增', 'add', 'BUTTON', 'system:user:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03', '1258371493341757441', '1',
        '2020-05-07 12:21:57', '1', '2020-05-07 12:21:57', 1, 'NO'),
       ('1258371492062494721', '修改', 'modify', 'BUTTON', 'system:user:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1258371494054789121', '1', '2020-05-07 12:21:57', '1', '2020-05-07 12:21:57', 1, 'NO'),
       ('1258371492062494721', '删除', 'remove', 'BUTTON', 'system:user:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1258371494268698625', '1', '2020-05-07 12:21:57', '1', '2020-05-07 12:21:57', 1, 'NO'),
       ('1123781860797521922', '组织机构', 'organization', 'MENU', 'system:organization', NULL, 'system/view/organization/treeList',
        'bx:bxs-component', 'NORMAL', '03', '1258371930191081474', '1', '2020-05-07 12:23:42', '1', '2020-05-07 12:47:19', 3, 'NO'),
       ('1258371930191081474', '查询', 'query', 'BUTTON', 'system:organization:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1258371930891530242', '1', '2020-05-07 12:23:42', '1', '2020-05-07 12:23:42', 1, 'NO'),
       ('1258371930191081474', '查看', 'view', 'BUTTON', 'system:organization:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1258371932376313858', '1', '2020-05-07 12:23:42', '1', '2020-05-07 12:23:42', 1, 'NO'),
       ('1258371930191081474', '新增', 'add', 'BUTTON', 'system:organization:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1258371932812521474', '1', '2020-05-07 12:23:42', '1', '2020-05-07 12:23:42', 1, 'NO'),
       ('1258371930191081474', '修改', 'modify', 'BUTTON', 'system:organization:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1258371933047402498', '1', '2020-05-07 12:23:42', '1', '2020-05-07 12:23:42', 1, 'NO'),
       ('1258371930191081474', '删除', 'remove', 'BUTTON', 'system:organization:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1258371933303255042', '1', '2020-05-07 12:23:42', '1', '2020-05-07 12:23:42', 1, 'NO'),
       ('1258371930191081474', '启用', 'enable', 'BUTTON', 'system:organization:enable', NULL, NULL, 'bx:bxs-component', 'NORMAL', '06',
        '1258379307581767681', '1', '2020-05-07 12:53:01', '1', '2020-05-07 12:53:01', 1, 'NO'),
       ('1258371930191081474', '停用', 'disable', 'BUTTON', 'system:organization:disable', NULL, NULL, 'bx:bxs-component', 'NORMAL', '07',
        '1258379488863780865', '1', '2020-05-07 12:53:44', '1', '2020-05-07 12:55:22', 2, 'NO'),
       ('1258371492062494721', '启用', 'enable', 'BUTTON', 'system:user:enable', NULL, NULL, 'bx:bxs-component', 'NORMAL', '06',
        '1258380325329633281', '1', '2020-05-07 12:57:04', '1', '2020-05-07 12:57:04', 1, 'NO'),
       ('1258371492062494721', '停用', 'disable', 'BUTTON', 'system:user:disable', NULL, NULL, 'bx:bxs-component', 'NORMAL', '07',
        '1258380404652310529', '1', '2020-05-07 12:57:23', '1', '2020-05-07 12:57:23', 1, 'NO'),
       ('1258371492062494721', '重置密码', 'resetPassword', 'BUTTON', 'system:user:resetPassword', NULL, NULL, 'bx:bxs-component', 'NORMAL', '08',
        '1258380528866623490', '1', '2020-05-07 12:57:52', '1', '2020-05-07 12:57:52', 1, 'NO'),
       ('1258371492062494721', '手工解锁', 'unlock', 'BUTTON', 'system:user:unlock', NULL, NULL, 'bx:bxs-component', 'NORMAL', '09',
        '1258380662643949569', '1', '2020-05-07 12:58:24', '1', '2020-05-07 12:58:24', 1, 'NO'),
       ('1258371492062494721', '配置角色', 'configRole', 'BUTTON', 'system:user:configRole', NULL, NULL, 'bx:bxs-component', 'NORMAL', '10',
        '1258380783511207938', '1', '2020-05-07 12:58:53', '1', '2020-05-07 12:58:53', 1, 'NO'),
       ('1123781860797521922', '权限项', 'permissionItem', 'MENU', 'system:permissionItem', 'treelist', 'system/view/permissionItem/treeList',
        'bx:bxs-component', 'NORMAL', '05', '1258639382447697922', '1', '2020-05-08 06:06:28', '', '2023-04-25 19:13:19', 5, 'NO'),
       ('1258639382447697922', '查询', 'query', 'BUTTON', 'system:permissionItem:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1258639382976180226', '1', '2020-05-08 06:06:28', '', '2023-04-25 19:01:05', 3, 'NO'),
       ('1258639382447697922', '查看', 'view', 'BUTTON', 'system:permissionItem:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1258639384108642306', '1', '2020-05-08 06:06:28', '1', '2020-05-08 06:06:28', 1, 'NO'),
       ('1258639382447697922', '新增', 'add', 'BUTTON', 'system:permissionItem:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1258639384544849921', '1', '2020-05-08 06:06:28', '1', '2020-05-08 06:06:28', 1, 'NO'),
       ('1258639382447697922', '复制新增', 'addByCopy', 'BUTTON', 'system:permissionItem:addByCopy', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1258639384544849922', '1', '2020-05-08 06:06:28', '', '2023-04-25 19:16:38', 4, 'NO'),
       ('1258639382447697922', '修改', 'modify', 'BUTTON', 'system:permissionItem:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1258639384926531586', '1', '2020-05-08 06:06:28', '1', '2020-05-08 06:06:28', 1, 'NO'),
       ('1258639382447697922', '删除', 'remove', 'BUTTON', 'system:permissionItem:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1258639385144635394', '1', '2020-05-08 06:06:28', '1', '2020-05-08 06:06:28', 1, 'NO'),
       ('1258639382447697922', '启用', 'enable', 'BUTTON', 'system:permissionItem:enable', NULL, NULL, 'bx:bxs-component', 'NORMAL', '06',
        '1259291003875799042', '1', '2020-05-10 01:15:46', '1', '2020-05-10 01:15:46', 1, 'NO'),
       ('1258639382447697922', '停用', 'disable', 'BUTTON', 'system:permissionItem:disable', NULL, NULL, 'bx:bxs-component', 'NORMAL', '07',
        '1259291086197403650', '1', '2020-05-10 01:16:06', '1', '2020-05-10 01:16:06', 1, 'NO'),
       ('1123781860797521922', '系统参数', 'param', 'MENU', 'system:param', NULL, 'system/view/param/list', 'bx:bxs-component', 'NORMAL', '08',
        '1259327774730788866', '1', '2020-05-10 03:41:53', '1', '2020-05-10 09:45:00', 2, 'NO'),
       ('1259327774730788866', '查询', 'query', 'BUTTON', 'system:param:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01', '1259327775368323074',
        '1', '2020-05-10 03:41:53', '1', '2020-05-10 03:41:53', 1, 'NO'),
       ('1259327774730788866', '查看', 'view', 'BUTTON', 'system:param:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02', '1259327775691284482',
        '1', '2020-05-10 03:41:53', '1', '2020-05-10 03:41:53', 1, 'NO'),
       ('1259327774730788866', '新增', 'add', 'BUTTON', 'system:param:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03', '1259327776525950977',
        '1', '2020-05-10 03:41:53', '1', '2020-05-10 03:41:53', 1, 'NO'),
       ('1259327774730788866', '修改', 'modify', 'BUTTON', 'system:param:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1259327776777609217', '1', '2020-05-10 03:41:53', '1', '2020-05-10 03:41:53', 1, 'NO'),
       ('1259327774730788866', '删除', 'remove', 'BUTTON', 'system:param:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1259327777486446594', '1', '2020-05-10 03:41:53', '1', '2020-05-10 03:41:53', 1, 'NO'),
       ('1123781860797521922', '字典类型', 'dictionaryType', 'MENU', 'system:dictionaryType', 'treelist', 'system/view/dictionaryType/treeList',
        'bx:bxs-component', 'NORMAL', '07', '1260912691533144066', '1', '2020-05-14 12:39:47', '', '2023-04-26 15:02:01', 9, 'NO'),
       ('1260912691533144066', '查询', 'query', 'BUTTON', 'system:dictionaryType:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1260912692279730178', '1', '2020-05-14 12:39:47', '1', '2020-05-14 12:39:47', 1, 'NO'),
       ('1260912691533144066', '查看', 'view', 'BUTTON', 'system:dictionaryType:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1260912692594302978', '1', '2020-05-14 12:39:47', '1', '2020-05-14 12:39:47', 1, 'NO'),
       ('1260912691533144066', '新增', 'add', 'BUTTON', 'system:dictionaryType:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1260912693076647938', '1', '2020-05-14 12:39:47', '1', '2020-05-14 12:39:47', 1, 'NO'),
       ('1260912691533144066', '修改', 'modify', 'BUTTON', 'system:dictionaryType:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1260912693705793537', '1', '2020-05-14 12:39:47', '1', '2020-05-14 12:39:47', 1, 'NO'),
       ('1260912691533144066', '删除', 'remove', 'BUTTON', 'system:dictionaryType:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1260912694167166978', '1', '2020-05-14 12:39:47', '1', '2020-05-14 12:39:47', 1, 'NO'),
       ('1260912691533144066', '字典项', 'item', 'BUTTON', 'system:dictionaryType:item', NULL, NULL, 'bx:bxs-component', 'NORMAL', '06',
        '1261173972848664577', '1', '2020-05-15 05:58:01', '1', '2020-05-15 05:58:01', 1, 'NO'),
       ('1167338537256849410', '查看', 'view', 'BUTTON', 'system:log:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02', '1261589386628005890',
        '1', '2020-05-16 09:28:44', '1', '2020-05-16 09:28:52', 2, 'NO'),
       ('1123781860797521922', '用户设置', 'userProfile', 'BUTTON', 'system:userProfile', NULL, NULL, 'bx:bxs-component', 'NORMAL', '10',
        '1264912153746104322', '1', '2020-05-25 13:32:13', '1', '2020-05-30 11:47:56', 5, 'NO'),
       ('1264912153746104322', '查询', 'query', 'BUTTON', 'system:userProfile:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1264912154291363841', '1', '2020-05-25 13:32:13', '1', '2020-05-25 13:32:13', 1, 'NO'),
       ('1264912153746104322', '查看', 'view', 'BUTTON', 'system:userProfile:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1264912154593353729', '1', '2020-05-25 13:32:13', '1', '2020-05-25 13:32:13', 1, 'NO'),
       ('1264912153746104322', '新增', 'add', 'BUTTON', 'system:userProfile:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1264912155016978433', '1', '2020-05-25 13:32:13', '1', '2020-05-25 13:32:13', 1, 'NO'),
       ('1264912153746104322', '修改', 'modify', 'BUTTON', 'system:userProfile:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1264912155230887938', '1', '2020-05-25 13:32:13', '1', '2020-05-25 13:32:13', 1, 'NO'),
       ('1264912153746104322', '删除', 'remove', 'BUTTON', 'system:userProfile:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1264912155394465794', '1', '2020-05-25 13:32:13', '1', '2020-05-25 13:32:13', 1, 'NO'),
       ('1258371492062494721', '导出', 'export', 'BUTTON', 'system:user:export', NULL, NULL, 'bx:bxs-component', 'NORMAL', '11',
        '1351070763888521218', '1', '2021-01-18 15:35:46', '1', '2021-01-18 15:35:46', 1, 'NO'),
       ('1258371492062494721', '导入', 'import', 'BUTTON', 'system:user:import', NULL, NULL, 'bx:bxs-component', 'NORMAL', '12',
        '1352426900563009538', '1', '2021-01-22 09:24:35', '1', '2021-01-22 09:24:35', 1, 'NO'),
       ('1258371492062494721', '下载导入模板', 'downloadImportTemplate', 'BUTTON', 'system:user:downloadImportTemplate', NULL, NULL,
        'bx:bxs-component', 'NORMAL', '13', '1353529204645130241', '1', '2021-01-25 10:24:44', '1', '2021-01-25 10:24:44', 1, 'NO'),
       ('1258371930191081474', '导入', 'import', 'BUTTON', 'system:organization:import', NULL, NULL, 'bx:bxs-component', 'NORMAL', '08',
        '1353606456242171906', '1', '2021-01-25 15:31:43', '1', '2021-01-25 15:35:16', 2, 'NO'),
       ('1258371930191081474', '导出', 'export', 'BUTTON', 'system:organization:export', NULL, NULL, 'bx:bxs-component', 'NORMAL', '09',
        '1353606536361766913', '1', '2021-01-25 15:32:02', '1', '2021-01-25 15:35:23', 2, 'NO'),
       ('1258371930191081474', '下载导入模板', 'downloadImportTemplate', 'BUTTON', 'system:organization:downloadImportTemplate', NULL, NULL,
        'bx:bxs-component', 'NORMAL', '10', '1353606636085538817', '1', '2021-01-25 15:32:26', '1', '2021-01-25 15:32:26', 1, 'NO'),
       ('1258371492062494721', '高级查询', 'advanceQuery', 'BUTTON', 'system:user:advanceQuery', NULL, NULL, 'bx:bxs-component', 'NORMAL', '14',
        '1354299184139571201', '1', '2021-01-27 13:24:22', '1', '2021-01-27 13:24:22', 1, 'NO'),
       ('1123781860797521922', '用户组', 'userGroup', 'MENU', 'system:userGroup', NULL, 'system/view/userGroup/treeList', 'bx:bxs-component',
        'NORMAL', '02', '1370271549858967554', '1', '2021-03-12 15:12:51', '1', '2021-03-12 15:12:51', 2, 'NO'),
       ('1370271549858967554', '查询', 'query', 'BUTTON', 'system:userGroup:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1370271550739771393', '1', '2021-03-12 15:12:51', '1', '2021-04-30 16:32:05', 3, 'NO'),
       ('1370271549858967554', '查看', 'view', 'BUTTON', 'system:userGroup:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1370271551461191681', '1', '2021-03-12 15:12:51', '1', '2021-03-12 15:12:51', 1, 'NO'),
       ('1370271549858967554', '复制新增', 'addByCopy', 'BUTTON', 'system:userGroup:addByCopy', NULL, NULL, 'bx:bxs-component', 'NORMAL', '0301',
        '1370271551461191682', '1', '2021-03-12 15:12:51', '', '2023-04-28 13:15:47', 2, 'NO'),
       ('1370271549858967554', '新增', 'add', 'BUTTON', 'system:userGroup:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03', '1370271551792541698',
        '1', '2021-03-12 15:12:51', '1', '2021-03-12 15:12:51', 1, 'NO'),
       ('1370271549858967554', '修改', 'modify', 'BUTTON', 'system:userGroup:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1370271552614625282', '1', '2021-03-12 15:12:51', '1', '2021-03-12 15:12:51', 1, 'NO'),
       ('1370271549858967554', '删除', 'remove', 'BUTTON', 'system:userGroup:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1370271552937586689', '1', '2021-03-12 15:12:51', '1', '2021-03-12 15:12:51', 1, 'NO'),
       ('1370271549858967554', '启用', 'enable', 'BUTTON', 'system:userGroup:enable', NULL, NULL, 'bx:bxs-component', 'NORMAL', '06',
        '1370301447256338434', '1', '2021-03-12 17:11:39', '1', '2021-03-12 17:11:39', 1, 'NO'),
       ('1370271549858967554', '停用', 'disable', 'BUTTON', 'system:userGroup:disable', NULL, NULL, 'bx:bxs-component', 'NORMAL', '07',
        '1370301510581940225', '1', '2021-03-12 17:11:54', '1', '2021-03-12 17:11:54', 1, 'NO'),
       ('1370271549858967554', '配置人员', 'configUser', 'BUTTON', 'system:userGroup:configUser', NULL, NULL, 'bx:bxs-component', 'NORMAL', '08',
        '1370301696909701122', '1', '2021-03-12 17:12:38', '', '2023-04-28 13:16:09', 2, 'NO'),
       ('1370271549858967554', '配置权限', 'configPermission', 'BUTTON', 'system:userGroup:configPermission', NULL, NULL, 'bx:bxs-component',
        'NORMAL', '09', '1370301777373229058', '1', '2021-03-12 17:12:57', '', '2023-04-28 13:15:25', 2, 'NO'),
       ('1258371492062494721', '配置用户组', 'configUserGroup', 'BUTTON', 'system:user:configUserGroup', NULL, NULL, 'bx:bxs-component', 'NORMAL',
        '10', '1370639564832157698', '1', '2021-03-13 15:35:12', '1', '2021-03-13 15:35:12', 1, 'NO'),
       ('1123781860797521922', '系统维护', 'systemManage', 'MENU', 'system:systemManage', NULL, 'system/view/systemManage/list', 'bx:bxs-component',
        'NORMAL', '99', '1381518481944162306', '1', '2021-04-12 16:04:08', '1', '2021-04-12 16:04:08', 1, 'NO'),
       ('1381518481944162306', '重建缓存', 'rebuildSystemCache', 'BUTTON', 'system:systemManage:rebuildSystemCache', NULL, NULL, 'bx:bxs-component',
        'NORMAL', '01', '1381518816209219586', '1', '2021-04-12 16:05:28', '1', '2021-04-12 16:05:28', 1, 'NO'),
       ('1381518481944162306', '获取授权信息', 'getLicenceInfo', 'BUTTON', 'system:systemManage:getLicenceInfo', NULL, NULL, 'bx:bxs-component',
        'NORMAL', '02', '1381518905912799233', '1', '2021-04-12 16:05:49', '1', '2021-04-12 16:05:49', 1, 'NO'),
       ('1381518481944162306', '保存授权信息', 'saveLicenceCode', 'BUTTON', 'system:systemManage:saveLicenceCode', NULL, NULL, 'bx:bxs-component',
        'NORMAL', '03', '1381519008715190274', '1', '2021-04-12 16:06:14', '1', '2021-04-12 16:06:14', 1, 'NO'),
       ('1', '实体配置', 'entityconfig', 'MODULE', 'entityconfig', NULL, '#', 'bx:bxs-component', 'NORMAL', '99', '1583265755662610434', '1',
        '2022-10-21 09:15:46', '', '2023-04-29 19:48:07', 5, 'NO'),
       ('1583265755662610434', '模块', 'module', 'MENU', 'entityconfig:module', NULL, 'entityconfig/view/module/list', 'bx:bxs-component', 'NORMAL',
        '02', '1583266846315884546', '1', '2022-10-21 09:20:06', '1', '2022-10-22 09:34:48', 4, 'NO'),
       ('1583266846315884546', '查询', 'query', 'BUTTON', 'entityconfig:module:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1583266854025015297', '1', '2022-10-21 09:20:06', '1', '2022-10-21 09:20:06', 1, 'NO'),
       ('1583266846315884546', '查看', 'view', 'BUTTON', 'entityconfig:module:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1583266854423474177', '1', '2022-10-21 09:20:06', '1', '2022-10-21 09:20:06', 1, 'NO'),
       ('1583266846315884546', '新增', 'add', 'BUTTON', 'entityconfig:module:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1583266855144894466', '1', '2022-10-21 09:20:06', '1', '2022-10-21 09:20:06', 1, 'NO'),
       ('1583266846315884546', '修改', 'modify', 'BUTTON', 'entityconfig:module:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1583266855564324865', '1', '2022-10-21 09:20:06', '1', '2022-10-21 09:20:06', 1, 'NO'),
       ('1583266846315884546', '删除', 'remove', 'BUTTON', 'entityconfig:module:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1583266855878897665', '1', '2022-10-21 09:20:06', '1', '2022-10-21 09:20:06', 1, 'NO'),
       ('1583265755662610434', '实体', 'entity', 'MENU', 'entityconfig:entity', NULL, 'entityconfig/view/entity/list', 'bx:bxs-component', 'NORMAL',
        '01', '1583621684736274434', '1', '2022-10-22 08:50:07', '1', '2022-10-22 09:34:40', 3, 'NO'),
       ('1583621684736274434', '查询', 'query', 'BUTTON', 'entityconfig:entity:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1583621693334597634', '1', '2022-10-22 08:50:07', '1', '2022-10-22 08:50:07', 1, 'NO'),
       ('1583621684736274434', '查看', 'view', 'BUTTON', 'entityconfig:entity:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1583621694890684418', '1', '2022-10-22 08:50:07', '1', '2022-10-22 08:50:07', 1, 'NO'),
       ('1583621684736274434', '新增', 'add', 'BUTTON', 'entityconfig:entity:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1583621695494664193', '1', '2022-10-22 08:50:07', '1', '2022-10-22 08:50:07', 1, 'NO'),
       ('1583621684736274434', '配置', 'config', 'BUTTON', 'entityconfig:entity:config', NULL, NULL, 'bx:bxs-component', 'NORMAL', '10',
        '1583621695494664194', '1', '2022-10-22 08:50:07', '1', '2022-10-22 08:50:07', 1, 'NO'),
       ('1583621684736274434', '实体配置', 'configEntity', 'PAGE', 'entityconfig:configEntity', NULL, 'entityconfig/view/entity/config/config',
        'bx:bxs-component', 'NORMAL', '10', '1583621695494664195', '1', '2022-10-22 08:50:07', '1', '2022-10-22 08:50:07', 1, 'NO'),
       ('1583621684736274434', '修改', 'modify', 'BUTTON', 'entityconfig:entity:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1583621695784071170', '1', '2022-10-22 08:50:07', '1', '2022-10-22 08:50:07', 1, 'NO'),
       ('1583621684736274434', '删除', 'remove', 'BUTTON', 'entityconfig:entity:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1583621696102838274', '1', '2022-10-22 08:50:07', '1', '2022-10-22 08:50:07', 1, 'NO'),
       ('1583265755662610434', '模型', 'entityModel', 'PAGE', 'entityconfig:entityModel', NULL, 'entityconfig/view/entityModel/list',
        'bx:bxs-component', 'NORMAL', '03', '1584461700769234945', '1', '2022-10-24 16:28:02', '1', '2022-10-31 14:20:20', 4, 'NO'),
       ('1584461700769234945', '查询', 'query', 'BUTTON', 'entityconfig:entityModel:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1584461706913890306', '1', '2022-10-24 16:28:02', '1', '2022-10-24 16:28:02', 1, 'NO'),
       ('1584461700769234945', '查看', 'view', 'BUTTON', 'entityconfig:entityModel:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1584461707165548546', '1', '2022-10-24 16:28:02', '1', '2022-10-24 16:28:02', 1, 'NO'),
       ('1584461700769234945', '新增', 'add', 'BUTTON', 'entityconfig:entityModel:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1584461709011042306', '1', '2022-10-24 16:28:02', '1', '2022-10-24 16:28:02', 1, 'NO'),
       ('1584461700769234945', '修改', 'modify', 'BUTTON', 'entityconfig:entityModel:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1584461709384335362', '1', '2022-10-24 16:28:02', '1', '2022-10-24 16:28:02', 1, 'NO'),
       ('1584461700769234945', '删除', 'remove', 'BUTTON', 'entityconfig:entityModel:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1584461709879263234', '1', '2022-10-24 16:28:02', '1', '2022-10-24 16:28:02', 1, 'NO'),
       ('1584461700769234945', '配置属性', 'configModelProperty', 'BUTTON', 'entityconfig:entityModel:configModelProperty', NULL, NULL,
        'bx:bxs-component', 'NORMAL', '06', '1584461709879263235', '1', '2022-10-24 16:28:02', '1', '2022-10-24 16:28:02', 1, 'NO'),
       ('1583265755662610434', '模型属性', 'entityModelProperty', 'PAGE', 'entityconfig:entityModelProperty', NULL,
        'entityconfig/view/entityModelProperty/list', 'bx:bxs-component', 'NORMAL', '04', '1584462679212220418', '1', '2022-10-24 16:31:55', '1',
        '2022-10-31 14:20:29', 3, 'NO'),
       ('1584462679212220418', '查询', 'query', 'BUTTON', 'entityconfig:entityModelProperty:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1584462686090878978', '1', '2022-10-24 16:31:55', '1', '2022-10-24 16:31:55', 1, 'NO'),
       ('1584462679212220418', '查看', 'view', 'BUTTON', 'entityconfig:entityModelProperty:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1584462686384480257', '1', '2022-10-24 16:31:55', '1', '2022-10-24 16:31:55', 1, 'NO'),
       ('1584462679212220418', '新增', 'add', 'BUTTON', 'entityconfig:entityModelProperty:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1584462686619361282', '1', '2022-10-24 16:31:55', '1', '2022-10-24 16:31:55', 1, 'NO'),
       ('1584462679212220418', '修改', 'modify', 'BUTTON', 'entityconfig:entityModelProperty:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1584462686808104962', '1', '2022-10-24 16:31:55', '1', '2022-10-24 16:31:55', 1, 'NO'),
       ('1584462679212220418', '删除', 'remove', 'BUTTON', 'entityconfig:entityModelProperty:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1584462687097511938', '1', '2022-10-24 16:31:55', '1', '2022-10-24 16:31:55', 1, 'NO'),
       ('1583621684736274434', '配置模型', 'configModel', 'BUTTON', 'entityconfig:entity:configModel', NULL, NULL, 'bx:bxs-component', 'NORMAL', '06',
        '1585889231057698817', '1', '2022-10-28 15:00:32', '1', '2022-10-28 15:00:32', 1, 'NO'),
       ('1584461700769234945', '配置属性', 'configProperty', 'BUTTON', 'entityconfig:entityModel:configProperty', NULL, NULL, 'bx:bxs-component',
        'NORMAL', '06', '1586906740598751233', '1', '2022-10-31 10:23:45', '1', '2022-10-31 10:23:45', 1, 'NO'),
       ('1583265755662610434', '视图', 'entityView', 'PAGE', 'entityconfig:entityView', NULL, 'entityconfig/view/entityView/list', 'bx:bxs-component',
        'NORMAL', '05', '1586956114351566849', '1', '2022-10-31 13:39:56', '1', '2022-10-31 14:21:42', 4, 'NO'),
       ('1586956114351566849', '查询', 'query', 'BUTTON', 'entityconfig:entityView:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1586956123314794497', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1586956114351566849', '查看', 'view', 'BUTTON', 'entityconfig:entityView:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1586956123683893250', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1586956114351566849', '复制新增', 'addByCopy', 'BUTTON', 'entityconfig:entityView:addByCopy', NULL, NULL, 'bx:bxs-component', 'NORMAL', '07',
        '1586956123683893251', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1584462679212220418', '复制新增', 'addByCopy', 'BUTTON', 'entityconfig:entityModelProperty:addByCopy', NULL, NULL, 'bx:bxs-component',
        'NORMAL', '07', '1586956123683893252', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1584461700769234945', '复制新增', 'addByCopy', 'BUTTON', 'entityconfig:entityModel:addByCopy', NULL, NULL, 'bx:bxs-component', 'NORMAL',
        '07', '1586956123683893253', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1258371492062494721', '复制新增', 'addByCopy', 'BUTTON', 'system:user:addByCopy', NULL, NULL, 'bx:bxs-component', 'NORMAL', '07',
        '1586956123683893254', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1258371930191081474', '复制新增', 'addByCopy', 'BUTTON', 'system:organization:addByCopy', NULL, NULL, 'bx:bxs-component', 'NORMAL', '07',
        '1586956123683893255', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1586956114351566849', '新增', 'add', 'BUTTON', 'entityconfig:entityView:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1586956123964911617', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1586956114351566849', '修改', 'modify', 'BUTTON', 'entityconfig:entityView:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1586956124229152770', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1586956114351566849', '删除', 'remove', 'BUTTON', 'entityconfig:entityView:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1586956124682137601', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1586956114351566849', '配置', 'configView', 'BUTTON', 'entityconfig:entityView:configView', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1586956124682137602', '1', '2022-10-31 13:39:56', '1', '2022-10-31 13:39:56', 1, 'NO'),
       ('1583621684736274434', '配置视图', 'configView', 'BUTTON', 'entityconfig:entity:configView', NULL, NULL, 'bx:bxs-component', 'NORMAL', '07',
        '1586963701121925122', '1', '2022-10-31 14:10:05', '1', '2022-10-31 14:10:05', 1, 'NO'),
       ('1586956114351566849', '配置', 'config', 'BUTTON', 'entityconfig:entityView:config', NULL, NULL, 'bx:bxs-component', 'NORMAL', '06',
        '1586967216296804354', '1', '2022-10-31 14:24:03', '1', '2022-11-14 10:59:22', 2, 'NO'),
       ('1583621684736274434', '生成库表', 'generateTable', 'BUTTON', 'entityconfig:entity:generateTable', NULL, NULL, 'bx:bxs-component', 'NORMAL',
        '08', '1590507235774967810', '1', '2022-11-10 08:50:50', '1', '2022-11-10 08:50:50', 1, 'NO'),
       ('1583621684736274434', '生成代码', 'generateCode', 'BUTTON', 'entityconfig:entity:generateCode', NULL, NULL, 'bx:bxs-component', 'NORMAL',
        '09', '1590507417728069634', '1', '2022-11-10 08:51:33', '1', '2022-11-10 08:51:33', 1, 'NO'),
       ('1583265755662610434', '视图配置', 'entityViewConfig', 'PAGE', 'entityconfig:entityViewConfig', NULL, NULL, 'bx:bxs-component', 'NORMAL',
        '06', '1591992839796871169', '1', '2022-11-14 11:14:05', '1', '2022-11-14 11:14:19', 2, 'NO'),
       ('1591992839796871169', '列表视图', 'list', 'PAGE', 'entityconfig:entityViewConfig:list', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1591992980327026690', '1', '2022-11-14 11:14:39', '1', '2022-11-14 11:14:39', 1, 'NO'),
       ('1583265755662610434', '视图按钮', 'viewButton', 'BUTTON', 'entityconfig:viewButton', NULL, 'entityconfig/view/viewButton/list',
        'bx:bxs-component', 'NORMAL', '07', '1592471425539870122', '1', '2022-11-15 18:55:49', '1', '2022-11-29 11:10:31', 4, 'NO'),
       ('1583265755662610434', '视图按钮模板', 'viewButtonTemplate', 'MENU', 'entityconfig:viewButtonTemplate', NULL,
        'entityconfig/view/viewButtonTemplate/list', 'bx:bxs-component', 'NORMAL', '07', '1592471425539870722', '1', '2022-11-15 18:55:49', '1',
        '2022-11-29 11:10:31', 4, 'NO'),
       ('1592471425539870122', '查询', 'query', 'BUTTON', 'entityconfig:viewButton:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1592471434742173612', '1', '2022-11-15 18:55:49', '1', '2022-11-15 18:55:49', 1, 'NO'),
       ('1592471425539870722', '查询', 'query', 'BUTTON', 'entityconfig:viewButtonTemplate:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1592471434742173692', '1', '2022-11-15 18:55:49', '1', '2022-11-15 18:55:49', 1, 'NO'),
       ('1592471425539870122', '查看', 'view', 'BUTTON', 'entityconfig:viewButton:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1592471435039969212', '1', '2022-11-15 18:55:49', '1', '2022-11-15 18:55:49', 1, 'NO'),
       ('1592471425539870722', '查看', 'view', 'BUTTON', 'entityconfig:viewButtonTemplate:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1592471435039969282', '1', '2022-11-15 18:55:49', '1', '2022-11-15 18:55:49', 1, 'NO'),
       ('1592471425539870122', '新增', 'add', 'BUTTON', 'entityconfig:viewButton:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1592471436025630712', '1', '2022-11-15 18:55:49', '1', '2022-11-15 18:55:49', 1, 'NO'),
       ('1592471425539870722', '新增', 'add', 'BUTTON', 'entityconfig:viewButtonTemplate:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1592471436025630722', '1', '2022-11-15 18:55:49', '1', '2022-11-15 18:55:49', 1, 'NO'),
       ('1592471425539870122', '修改', 'modify', 'BUTTON', 'entityconfig:viewButton:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1592471436260511712', '1', '2022-11-15 18:55:49', '1', '2022-11-15 18:55:49', 1, 'NO'),
       ('1592471425539870722', '修改', 'modify', 'BUTTON', 'entityconfig:viewButtonTemplate:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1592471436260511742', '1', '2022-11-15 18:55:49', '1', '2022-11-15 18:55:49', 1, 'NO'),
       ('1592471425539870722', '删除', 'remove', 'BUTTON', 'entityconfig:viewButtonTemplate:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1592471436533141502', '1', '2022-11-15 18:55:49', '1', '2022-11-15 18:55:49', 1, 'NO'),
       ('1592471425539870122', '删除', 'remove', 'BUTTON', 'entityconfig:viewButton:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1592471436533141512', '1', '2022-11-15 18:55:49', '1', '2022-11-15 18:55:49', 1, 'NO'),
       ('1583265755662610434', '视图查询条件', 'viewQueryCondition', 'PAGE', 'entityconfig:viewQueryCondition', NULL, NULL, 'bx:bxs-component',
        'NORMAL', '08', '1593519785781477377', '1', '2022-11-18 16:21:38', '1', '2022-11-21 15:39:49', 2, 'NO'),
       ('1593519785781477377', '查询', 'query', 'BUTTON', 'entityconfig:viewQueryCondition:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1593519794681790465', '1', '2022-11-18 16:21:38', '1', '2022-11-18 16:21:38', 1, 'NO'),
       ('1593519785781477377', '查看', 'view', 'BUTTON', 'entityconfig:viewQueryCondition:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1593519797760409602', '1', '2022-11-18 16:21:38', '1', '2022-11-18 16:21:38', 1, 'NO'),
       ('1593519785781477377', '新增', 'add', 'BUTTON', 'entityconfig:viewQueryCondition:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1593519799350050818', '1', '2022-11-18 16:21:38', '1', '2022-11-18 16:21:38', 1, 'NO'),
       ('1593519785781477377', '修改', 'modify', 'BUTTON', 'entityconfig:viewQueryCondition:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1593519799966613506', '1', '2022-11-18 16:21:38', '1', '2022-11-18 16:21:38', 1, 'NO'),
       ('1593519785781477377', '删除', 'remove', 'BUTTON', 'entityconfig:viewQueryCondition:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1593519800327323650', '1', '2022-11-18 16:21:38', '1', '2022-11-18 16:21:38', 1, 'NO'),
       ('1583265755662610434', '视图查询结果', 'viewQueryResult', 'PAGE', 'entityconfig:viewQueryResult', NULL, NULL, 'bx:bxs-component', 'NORMAL',
        '09', '1594593708811984897', '1', '2022-11-21 15:29:01', '1', '2022-11-21 15:39:58', 2, 'NO'),
       ('1594593708811984897', '查询', 'query', 'BUTTON', 'entityconfig:viewQueryResult:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1594593720476344321', '1', '2022-11-21 15:29:01', '1', '2022-11-21 15:29:01', 1, 'NO'),
       ('1594593708811984897', '查看', 'view', 'BUTTON', 'entityconfig:viewQueryResult:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1594593724951666690', '1', '2022-11-21 15:29:01', '1', '2022-11-21 15:29:01', 1, 'NO'),
       ('1594593708811984897', '新增', 'add', 'BUTTON', 'entityconfig:viewQueryResult:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1594593725396262914', '1', '2022-11-21 15:29:01', '1', '2022-11-21 15:29:01', 1, 'NO'),
       ('1594593708811984897', '修改', 'modify', 'BUTTON', 'entityconfig:viewQueryResult:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1594593725878607873', '1', '2022-11-21 15:29:01', '1', '2022-11-21 15:29:01', 1, 'NO'),
       ('1594593708811984897', '删除', 'remove', 'BUTTON', 'entityconfig:viewQueryResult:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1594593726226735105', '1', '2022-11-21 15:29:01', '1', '2022-11-21 15:29:01', 1, 'NO'),
       ('1583265755662610434', '列表视图配置', 'listViewConfig', 'PAGE', 'entityconfig:listViewConfig', NULL,
        'entityconfig/view/entityViewConfig/listView/index', 'bx:bxs-component', 'NORMAL', '0601', '1619170258194518017', '1', '2023-01-28 11:07:27',
        '1', '2023-01-28 11:08:31', 2, 'NO'),
       ('1583265755662610434', '详情视图配置', 'detailViewConfig', 'PAGE', 'entityconfig:detailViewConfig', NULL,
        'entityconfig/view/entityViewConfig/detailView/index', 'bx:bxs-component', 'NORMAL', '0602', '1619170258194518018', '1',
        '2023-01-28 11:07:27', '1', '2023-01-28 11:08:31', 2, 'NO'),
       ('1583265755662610434', '新增视图配置', 'addViewConfig', 'PAGE', 'entityconfig:addViewConfig', NULL,
        'entityconfig/view/entityViewConfig/addView/index', 'bx:bxs-component', 'NORMAL', '0602', '1619170612739035138', '1', '2023-01-28 11:08:51',
        '1', '2023-01-28 11:08:51', 1, 'YES'),
       ('1583265755662610434', '修改视图配置', 'modifyViewConfig', 'PAGE', 'entityconfig:modifyViewConfig', NULL,
        'entityconfig/view/entityViewConfig/modifyView/index', 'bx:bxs-component', 'NORMAL', '0603', '1619170612739035139', '1',
        '2023-01-28 11:08:51', '1', '2023-01-28 11:08:51', 1, 'YES'),
       ('1583265755662610434', '查看视图配置', 'viewViewConfig', 'PAGE', 'entityconfig:viewViewConfig', NULL,
        'entityconfig/view/entityViewConfig/viewView/index', 'bx:bxs-component', 'NORMAL', '0604', '1619170612739035140', '1', '2023-01-28 11:08:51',
        '1', '2023-01-28 11:08:51', 1, 'YES'),
       ('1583265755662610434', '参照视图配置', 'referenceViewConfig', 'PAGE', 'entityconfig:referenceViewConfig', NULL,
        'entityconfig/view/entityViewConfig/referenceView/index', 'bx:bxs-component', 'NORMAL', '0605', '1619170612739035141', '1',
        '2023-01-28 11:08:51', '1', '2023-01-28 11:08:51', 1, 'YES'),
       ('1583265755662610434', '视图属性', 'viewPropertity', 'PAGE', 'entityconfig:viewProperty', NULL, NULL, 'bx:bxs-component', 'NORMAL', '10',
        '1619244122144473089', '1', '2023-01-28 16:00:57', '1', '2023-01-28 16:12:44', 4, 'NO'),
       ('1619244122144473089', '查询', 'query', 'BUTTON', 'entityconfig:viewProperty:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1619244141081755650', '1', '2023-01-28 16:00:57', '1', '2023-01-28 16:00:57', 1, 'NO'),
       ('1619244122144473089', '查看', 'view', 'BUTTON', 'entityconfig:viewProperty:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1619244144802103297', '1', '2023-01-28 16:00:57', '1', '2023-01-28 16:00:57', 1, 'NO'),
       ('1619244122144473089', '新增', 'add', 'BUTTON', 'entityconfig:viewProperty:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1619244145921982465', '1', '2023-01-28 16:00:57', '1', '2023-01-28 16:00:57', 1, 'NO'),
       ('1619244122144473089', '修改', 'modify', 'BUTTON', 'entityconfig:viewProperty:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1619244146307858433', '1', '2023-01-28 16:00:57', '1', '2023-01-28 16:00:57', 1, 'NO'),
       ('1619244122144473089', '删除', 'remove', 'BUTTON', 'entityconfig:viewProperty:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1619244146639208450', '1', '2023-01-28 16:00:57', '1', '2023-01-28 16:00:57', 1, 'NO'),
       ('1381518481944162306', '123', '123', 'BUTTON', 'system:systemManage:123', NULL, NULL, NULL, 'NORMAL', '213', '1650840779060494337', '',
        '2023-04-25 20:34:48', '', '2023-04-25 20:34:48', 1, 'NO'),
       ('1', '接口平台', 'cip', 'MODULE', 'cip', NULL, '#', NULL, 'NORMAL', '99', '1651012453940621314', '', '2023-04-26 07:56:58', '',
        '2023-04-26 14:00:46', 4, 'YES'),
       ('1651012453940621314', '应用', 'app', 'MENU', 'cip:app', 'list', 'cip/view/app/list', NULL, 'NORMAL', '01', '1651013446585651201', '',
        '2023-04-26 08:00:55', '', '2023-04-26 14:00:37', 3, 'YES'),
       ('1651013446585651201', '查询', 'query', 'BUTTON', 'cip:app:query', NULL, NULL, NULL, 'NORMAL', '01', '1651013638923849730', '',
        '2023-04-26 08:01:41', '', '2023-04-26 14:00:31', 1, 'YES'),
       ('1123781860797521922', '字典项', 'dictionaryItem', 'MENU', 'system:dictionaryItem', 'treelist', 'system/view/dictionaryItem/treeList',
        'bx:bxs-component', 'NORMAL', '0701', '1651189382161412097', '', '2023-04-26 19:40:01', '', '2023-04-26 19:40:24', 2, 'NO'),
       ('1651189382161412097', '查询', 'query', 'BUTTON', 'system:dictionaryItem:query', NULL, NULL, 'bx:bxs-component', 'NORMAL', '01',
        '1651189568988295170', '', '2023-04-26 19:40:46', '', '2023-04-26 19:41:01', 2, 'NO'),
       ('1651189382161412097', '查看', 'view', 'BUTTON', 'system:dictionaryItem:view', NULL, NULL, 'bx:bxs-component', 'NORMAL', '02',
        '1651189568988295171', '', '2023-04-26 19:40:46', '', '2023-04-26 19:41:14', 2, 'NO'),
       ('1651189382161412097', '新增', 'add', 'BUTTON', 'system:dictionaryItem:add', NULL, NULL, 'bx:bxs-component', 'NORMAL', '03',
        '1651189568988295172', '', '2023-04-26 19:40:46', '', '2023-04-26 19:41:31', 2, 'NO'),
       ('1651189382161412097', '修改', 'modify', 'BUTTON', 'system:dictionaryItem:modify', NULL, NULL, 'bx:bxs-component', 'NORMAL', '04',
        '1651189568988295173', '', '2023-04-26 19:40:46', '', '2023-04-26 19:41:45', 2, 'NO'),
       ('1651189382161412097', '删除', 'remove', 'BUTTON', 'system:dictionaryItem:remove', NULL, NULL, 'bx:bxs-component', 'NORMAL', '05',
        '1651189568988295174', '', '2023-04-26 19:40:46', '', '2023-04-26 19:41:57', 2, 'NO'),
       ('1651189382161412097', '启用', 'enable', 'BUTTON', 'system:dictionaryItem:enable', NULL, NULL, 'bx:bxs-component', 'NORMAL', '06',
        '1651190904865730562', '', '2023-04-26 19:46:04', '', '2023-04-26 19:46:24', 2, 'NO'),
       ('1651189382161412097', '停用', 'disable', 'BUTTON', 'system:dictionaryItem:disable', NULL, NULL, 'bx:bxs-component', 'NORMAL', '07',
        '1651190904865730563', '', '2023-04-26 19:46:04', '', '2023-04-26 19:46:40', 2, 'NO'),
       ('1651189382161412097', '复制新增', 'addByCopy', 'BUTTON', 'system:dictionaryItem:addByCopy', NULL, NULL, 'bx:bxs-component', 'NORMAL', '0301',
        '1651741776586772481', '', '2023-04-28 08:15:02', '', '2023-04-28 08:15:39', 3, 'NO');

-- 导出  表 abc.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user`
(
    `organization` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '组织机构',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '姓名',
    `account` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '账号',
    `password` varchar
(
    256
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
    `gender` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '性别',
    `birthday` datetime DEFAULT NULL COMMENT '生日',
    `telephone` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
    `email` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
    `position` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '职位',
    `status` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'NORMAL' COMMENT '状态',
    `force_change_password_flag` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'YES' COMMENT '强制修改密码',
    `fail_login_count` int
(
    11
) DEFAULT '0' COMMENT '失败登录次数',
    `lock_time` datetime DEFAULT NULL COMMENT '锁定时间',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='用户';

-- 正在导出表  abc.sys_user 的数据：~7 rows (大约)
INSERT INTO `sys_user` (`organization`, `name`, `account`, `password`, `gender`, `birthday`, `telephone`, `email`, `position`, `status`,
                        `force_change_password_flag`, `fail_login_count`, `lock_time`, `order_no`, `id`, `create_id`, `create_time`, `update_id`,
                        `update_time`, `version`, `delete_flag`)
VALUES ('1', '系统管理员', 'admin', '$2a$10$wdXuR/yQsIwdHBr5N0oiLe7LkfR6Q.K0qJo59TNlSfJ1rosO6bkO2', 'MALE', NULL, NULL, NULL, NULL, 'NORMAL', 'YES',
        0, NULL, NULL, '1', '1', NULL, '1', '2023-04-24 08:53:46', 25, 'NO'),
       ('1', '2131', '231', '$2a$10$6NxFg/CscL9NiynyZxE6ruAC.vU/rbRlqznaoGe/2Mpk9vxXwJR.W', 'MALE', '2023-04-19 00:00:00', '123', '123', '123',
        'NORMAL', 'NO', 0, NULL, NULL, '1650119063178883073', '', '2023-04-23 20:46:57', '', '2023-04-23 20:48:52', 4, 'YES'),
       ('1', '系统管理员', 'admin', '$2a$10$wdXuR/yQsIwdHBr5N0oiLe7LkfR6Q.K0qJo59TNlSfJ1rosO6bkO2', 'MALE', NULL, NULL, NULL, NULL, 'NORMAL', 'YES',
        0, NULL, NULL, '1650306354417524737', '', '2023-04-24 09:11:11', '', '2023-04-24 09:11:36', 1, 'YES'),
       ('1', '张三', 'zhangsan', '$2a$10$wdXuR/yQsIwdHBr5N0oiLe7LkfR6Q.K0qJo59TNlSfJ1rosO6bkO2', 'MALE', '2023-04-06 00:00:00', '13131312331',
        '1@126.com', NULL, 'DEAD', 'YES', 0, NULL, '02', '1650310811029123073', '', '2023-04-24 09:28:53', '', '2023-04-24 19:49:39', 3, 'NO'),
       ('1', '李四', 'lisi', '$2a$10$wdXuR/yQsIwdHBr5N0oiLe7LkfR6Q.K0qJo59TNlSfJ1rosO6bkO2', 'FEMALE', '2023-04-06 00:00:00', '13131312331',
        '1@126.com', NULL, 'NORMAL', 'NO', 0, NULL, '03', '1650311010862542849', '', '2023-04-24 09:29:41', '', '2023-04-24 09:30:05', 2, 'NO'),
       ('1', '王企鹅群翁', '王企鹅', '$2a$10$u6AY238G.j7tig2y6qHPFe6Vj0RTF4pcqE/U4RBDxL3CRRzVzaHxy', 'MALE', NULL, NULL, NULL, NULL, 'NORMAL', 'YES',
        0, NULL, NULL, '1651546954525593601', '', '2023-04-27 19:20:53', '', '2023-04-27 19:20:53', 1, 'NO'),
       ('1', '213123', '123123', '$2a$10$gidorVjELI87m9oPT8a6suMjtg9pTzhz0JhC53JT.SoTNzQqkSMY.', 'MALE', NULL, NULL, NULL, NULL, 'LOCK', 'YES', 0,
        '2023-04-23 20:48:52', NULL, '1651846726876676097', '', '2023-04-28 15:12:04', '', '2023-04-30 15:36:27', 2, 'NO');

-- 导出  表 abc.sys_user_group 结构
CREATE TABLE IF NOT EXISTS `sys_user_group`
(
    `user_group` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '上级',
    `name` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '名称',
    `code` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
    `status` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL DEFAULT 'NORMAL' COMMENT '状态',
    `order_no` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序',
    `remark` varchar
(
    256
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
    `id` varchar
(
    32
) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
    `create_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本号',
    `delete_flag` varchar
(
    32
) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标识',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE =utf8mb4_bin COMMENT='用户组';

-- 正在导出表  abc.sys_user_group 的数据：~7 rows (大约)
INSERT INTO `sys_user_group` (`user_group`, `name`, `code`, `status`, `order_no`, `remark`, `id`, `create_id`, `create_time`, `update_id`,
                              `update_time`, `version`, `delete_flag`)
VALUES ('0', '用户组', 'root', 'NORMAL', '01', NULL, '1', '1', '2021-03-13 10:08:56', '1', '2021-03-13 10:08:59', 1, 'NO'),
       ('4', '1', '', 'NORMAL', '1', NULL, '1650396893808746498', '', '2023-04-24 15:10:57', '', '2023-04-25 08:29:39', 10, 'NO'),
       ('4', '2', ' ', 'NORMAL', '2', NULL, '1650397103687524354', '', '2023-04-24 15:11:47', '', '2023-04-25 08:29:45', 4, 'NO'),
       ('4', '3', NULL, 'NORMAL', '3', NULL, '1650397148998590465', '', '2023-04-24 15:11:58', '', '2023-04-25 08:08:17', 2, 'NO'),
       ('4', '4', NULL, 'NORMAL', '4', NULL, '1650653075458785282', '', '2023-04-25 08:08:56', '', '2023-04-25 08:08:56', 1, 'NO'),
       ('2', '213123', '213', 'NORMAL', '23', NULL, '1651566243760611330', '', '2023-04-27 20:37:32', '', '2023-04-30 16:07:23', 1, 'YES'),
       ('1', '岗位角色', 'role', 'NORMAL', '01', NULL, '2', '1', '2021-03-13 11:08:06', '1', '2022-12-05 15:19:48', 2, 'NO'),
       ('1', '流程', 'workflow', 'NORMAL', '02', NULL, '3', '1', '2021-03-13 11:08:34', '1', '2021-03-13 11:08:34', 2, 'NO'),
       ('1', '通讯组', 'message', 'NORMAL', '03', '', '4', '1', '2021-03-13 10:50:44', '1', '2021-03-13 10:50:44', 3, 'NO'),
       ('2', '系统管理员', 'admin', 'NORMAL', '01', NULL, '99', '1', '2021-03-13 11:10:46', '1', '2021-03-13 11:10:46', 3, 'NO');

-- 导出  表 abc.sys_user_password_change_log 结构
CREATE TABLE IF NOT EXISTS `sys_user_password_change_log`
(
    `id` varchar
(
    32
) NOT NULL COMMENT '编号',
    `user_id` varchar
(
    32
) DEFAULT NULL COMMENT '用户标识',
    `account` varchar
(
    32
) DEFAULT NULL COMMENT '用户账号',
    `change_time` datetime DEFAULT NULL COMMENT '密码更新时间',
    `origin_password` varchar
(
    128
) DEFAULT NULL COMMENT '原密码',
    `create_id` varchar
(
    32
) DEFAULT NULL COMMENT '创建人标识',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) DEFAULT NULL COMMENT '更新人标识',
    `update_time` datetime DEFAULT NULL COMMENT '更新人标识',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本',
    `delete_flag` varchar
(
    32
) DEFAULT NULL COMMENT '删除标志',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户密码修改日志';

-- 正在导出表  abc.sys_user_password_change_log 的数据：~0 rows (大约)

-- 导出  表 abc.sys_user_profile 结构
CREATE TABLE IF NOT EXISTS `sys_user_profile`
(
    `id` varchar
(
    32
) NOT NULL COMMENT '标识',
    `user_id` varchar
(
    32
) DEFAULT NULL COMMENT '用户标识',
    `desktop_config` varchar
(
    6000
) DEFAULT NULL COMMENT '桌面配置',
    `time_zone` varchar
(
    32
) DEFAULT NULL COMMENT '时区',
    `language` varchar
(
    32
) DEFAULT NULL COMMENT '语种',
    `create_id` varchar
(
    32
) DEFAULT NULL COMMENT '创建人标识',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` varchar
(
    32
) DEFAULT NULL COMMENT '更新人标识',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `version` int
(
    11
) DEFAULT NULL COMMENT '版本',
    `delete_flag` varchar
(
    32
) DEFAULT NULL COMMENT '删除标志',
    PRIMARY KEY
(
    `id`
)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户配置';

-- 正在导出表  abc.sys_user_profile 的数据：~0 rows (大约)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
