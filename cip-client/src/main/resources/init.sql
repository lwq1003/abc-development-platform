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


-- 导出 cip_client 的数据库结构
CREATE DATABASE IF NOT EXISTS `cip_client` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `cip_client`;

-- 导出  表 cip_client.cip_message_log 结构
CREATE TABLE IF NOT EXISTS `cip_message_log` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
  `request_id` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求消息标识',
  `request_app_code` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求应用编码',
  `request_topic_code` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求消息主题编码',
  `request_time` datetime DEFAULT NULL COMMENT '请求时间',
  `request_data` varchar(2048) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求内容',
  `response_id` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '响应消息标识',
  `response_app_code` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '响应应用编码',
  `response_topic_code` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '响应消息主题编码',
  `response_time` datetime DEFAULT NULL COMMENT '响应时间',
  `response_data` varchar(2048) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '响应内容',
  `response_result` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '响应结果',
  `error_code` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '错误编码',
  `error_message` varchar(400) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '错误信息',
  `status` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '当前状态',
  `send_count` int(11) DEFAULT NULL COMMENT '发送次数',
  `create_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人标识',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  `delete_flag` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='消息日志';

-- 正在导出表  cip_client.cip_message_log 的数据：~0 rows (大约)

-- 导出  表 cip_client.cip_message_topic 结构
CREATE TABLE IF NOT EXISTS `cip_message_topic` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '标识',
  `code` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '编码',
  `name` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
  `handler` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '处理器',
  `sender` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '发送器',
  `response_topic_code` varchar(512) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '响应主题编码',
  `category` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分类',
  `status` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态',
  `remark` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注',
  `order_no` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '排序号',
  `create_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人标识',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新人标识',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  `delete_flag` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='消息主题';

-- 正在导出表  cip_client.cip_message_topic 的数据：~5 rows (大约)
INSERT INTO `cip_message_topic` (`id`, `code`, `name`, `handler`, `sender`, `response_topic_code`, `category`, `status`, `remark`, `order_no`, `create_id`, `create_time`, `update_id`, `update_time`, `version`, `delete_flag`) VALUES
	('1448247847417774081', 'framework.login.response', '登录响应', 'tech.popsoft.cip.client.handler.response.system.LoginResponseHandler', NULL, '', 'USER', 'NORMAL', NULL, '01', '1', '2021-10-13 19:22:49', '1', '2021-10-13 19:22:49', 1, 'NO'),
	('1448247847417774082', 'lms.transportbill.consignmentbill.create', '委托单创建', 'tech.popsoft.cip.client.handler.request.lms.transportbill.ConsignmentBillCreateRequestHandler', 'tech.popsoft.cip.client.sender.request.lms.transportbill.ConsignmentBillCreateSender', 'framework.message.confirm.response', 'USER', 'NORMAL', NULL, '01', '1', '2021-10-13 19:22:49', '1', '2021-10-13 19:22:49', 1, 'NO'),
	('1448247847417774083', 'framework.error.response', '错误响应', 'tech.popsoft.cip.client.handler.response.system.ErrorResponseHandler', 'tech.popsoft.cip.client.sender.response.system.ErrorResponseSender', '', 'USER', 'NORMAL', NULL, '01', '1', '2021-10-13 19:22:49', '1', '2021-10-13 19:22:49', 1, 'NO'),
	('1448247847417774084', 'framework.message.confirm.response', '消息确认响应', 'tech.popsoft.cip.client.handler.response.system.MessageConfirmResponseHandler', 'tech.popsoft.cip.client.sender.response.system.MessageConfirmResponseSender', '', 'USER', 'NORMAL', NULL, '01', '1', '2021-10-13 19:22:49', '1', '2021-10-13 19:22:49', 1, 'NO'),
	('1448247847417774086', 'framework.login.request', '登录请求', '', 'tech.popsoft.cip.client.sender.request.system.LoginRequestSender', '', 'USER', 'NORMAL', NULL, '01', '1', '2021-10-13 19:22:49', '1', '2021-10-13 19:22:49', 1, 'NO');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
