
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `common-frame` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `common-frame`;


DROP TABLE IF EXISTS `inter_log_req`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inter_log_req` (
     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水号',
     `request_no` varchar(30) DEFAULT '' COMMENT '业务请求订单号，协议对象里的业务请求订单号',
     `type` char(20) DEFAULT '' COMMENT '分：网关接口，直连接口，对账下载',
     `class_name` varchar(50) DEFAULT '' COMMENT '处理类',
     `response_type` varchar(20) DEFAULT '' COMMENT '回调类型：回调类型：异步回调 , 网关回调',
     `service_name` varchar(50) DEFAULT '' COMMENT '接口名称',
     `class_req_args` varchar(1000) DEFAULT '' COMMENT '处理类请求参数',
     `platform_no` varchar(30) DEFAULT '' COMMENT '平台编码',
     `user_device` varchar(80) DEFAULT '' COMMENT '用户终端设备类型',
     `platform_user_no` varchar(30) DEFAULT '' COMMENT '平台用户id',
     `req_data` varchar(2000) DEFAULT '' COMMENT '请求数据体',
     `error_code` varchar(20) DEFAULT '' COMMENT '请求里的错误码',
     `error_message` varchar(100) DEFAULT '' COMMENT '请求里的的错误信息',
     `return_str` varchar(500) DEFAULT '' COMMENT '响应字符串',
     `ext_str` varchar(250) DEFAULT '' COMMENT '扩展字符串',
     `done_status` int(11) DEFAULT '0' COMMENT '响应的处理状态',
     `code` varchar(10) DEFAULT '' COMMENT '响应里的code',
     `status` varchar(20) DEFAULT '' COMMENT '响应里的状态',
     `logid` bigint(20) DEFAULT '0' COMMENT '日志id',
     `add_time` datetime DEFAULT NULL COMMENT '添加时间',
     `ref_req_no` varchar(30) DEFAULT '' COMMENT '关联请求流水号',
     `handler_times` int(11) DEFAULT '0' COMMENT '处理的毫秒数',
     `ip` varchar(20) DEFAULT '' COMMENT 'ip',
     PRIMARY KEY (`id`),
     UNIQUE KEY `UIILR_REQ_NO` (`request_no`,`service_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


