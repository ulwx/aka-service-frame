
CREATE DATABASE /*!32312 IF NOT EXISTS*/ `common-frame` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `common-frame`;


DROP TABLE IF EXISTS `inter_log_req`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inter_log_req` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '流水号',
     `request_no` varchar(30) DEFAULT '' COMMENT '业务请求订单号，协议对象里的业务请求订单号',
     `type` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT 'OutGateWay:对外网关转接，对接第三方网关接口,要通过主动发送HTTP请求. OutDirect:对外直连转接，对接第三方直连接口,要通过主动发送HTTP请求。JSON:本地JSON返回接口。 JSPPost:本地Post提交接口。Redirect:本地跳转接口。 STR:本地返回文本接口。DownLoad:本地下载接口。Forward: 本地forward接口 CB_GW: 网关回调。CB_NF: 异步回调。None:未知',
     `class_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '协议处理类的名称',
     `response_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '回调类型：异步回调,网关回调，有一些接口存在回调',
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
     `done_status` int DEFAULT '0' COMMENT '响应的处理状态，0-初始，24-处理成功，23-处理失败，134：处理中',
     `code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '响应里状态code，如错误码,根据业务定义',
     `status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '响应的状态，1：成功  0：失败',
     `logid` bigint DEFAULT '0' COMMENT '日志id',
     `add_time` datetime DEFAULT NULL COMMENT '添加时间',
     `ref_req_no` varchar(30) DEFAULT '' COMMENT '关联请求流水号',
     `handler_times` int DEFAULT '0' COMMENT '处理的毫秒数',
     `ip` varchar(20) DEFAULT '' COMMENT 'ip',
     PRIMARY KEY (`id`),
     UNIQUE KEY `UIILR_REQ_NO` (`request_no`,`service_name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;




