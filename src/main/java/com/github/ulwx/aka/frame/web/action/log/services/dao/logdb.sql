

DROP TABLE IF EXISTS `inter_log_notify`;

CREATE TABLE `inter_log_notify` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `request_no` varchar(30) DEFAULT '' COMMENT '请求流水号',
  `service_name` varchar(50) DEFAULT '' COMMENT '接口名称',
  `response_type` varchar(20) DEFAULT '' COMMENT '回调类型：NOTIFY , CALLBACK，LF_CALLBACK',
  `req_code` varchar(100) DEFAULT '' COMMENT 'reqCode区分接口逻辑，[ver]_[moduleName]_[cparseCode]_[callType]_[serviceName]_[extStr]_[infKey] 。callType：1-异步回调 2-网关回调 3-本地窗体',
  `logid` bigint(20) DEFAULT '0' COMMENT '日志id',
  `class_name` varchar(50) DEFAULT '' COMMENT '处理类名',
  `platform_no` varchar(30) DEFAULT '' COMMENT '平台编码',
  `platform_user_no` varchar(30) DEFAULT '' COMMENT '平台用户id',
  `resp_data` varchar(2000) DEFAULT '' COMMENT '通知的数据体',
  `code` varchar(10) DEFAULT '' COMMENT '响应里的code',
  `status` varchar(20) DEFAULT '' COMMENT '响应里的status',
  `error_code` varchar(20) DEFAULT '' COMMENT '存管通知处理的错误码',
  `error_message` varchar(100) DEFAULT '' COMMENT '存管通知处理的错误信息',
  `return_str` varchar(800) DEFAULT '' COMMENT '响应字符串',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `done_status` int(11) DEFAULT '0' COMMENT '处理状态，对应category=11',
  `handler_times` int(11) DEFAULT '0' COMMENT '处理的毫秒数',
  `ip` varchar(20) DEFAULT '' COMMENT 'ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UI_ILN_REQ_NO` (`request_no`,`service_name`,`response_type`)
) ENGINE=InnoDB AUTO_INCREMENT=448 DEFAULT CHARSET=utf8;

/*Table structure for table `inter_log_notify_more` */

DROP TABLE IF EXISTS `inter_log_notify_more`;

CREATE TABLE `inter_log_notify_more` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `request_no` varchar(30) DEFAULT '' COMMENT '请求流水号',
  `service_name` varchar(50) DEFAULT '' COMMENT '接口名称',
  `response_type` varchar(20) DEFAULT '' COMMENT '回调类型：回调类型：NOTIFY , CALLBACK，LF_CALLBACK',
  `logid` bigint(20) DEFAULT '0' COMMENT '日志id',
  `class_name` varchar(50) DEFAULT '' COMMENT '处理类名',
  `platform_no` varchar(30) DEFAULT '' COMMENT '平台编码',
  `platform_user_no` varchar(30) DEFAULT '' COMMENT '平台用户id',
  `resp_data` varchar(1800) DEFAULT '' COMMENT '通知的数据体',
  `code` varchar(10) DEFAULT '' COMMENT '响应里的code',
  `status` varchar(20) DEFAULT '' COMMENT '响应里的status',
  `error_code` varchar(20) DEFAULT '' COMMENT '存管通知处理的错误码',
  `error_message` varchar(100) DEFAULT '' COMMENT '存管通知处理的错误信息',
  `key_serial` varchar(10) DEFAULT '' COMMENT '证书序号',
  `return_str` varchar(800) DEFAULT '' COMMENT '响应字符串',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `done_status` int(11) DEFAULT '0' COMMENT '处理状态，对应category=11',
  `handler_times` int(11) DEFAULT '0' COMMENT '处理的毫秒数',
  `ip` varchar(20) DEFAULT '' COMMENT 'ip',
  PRIMARY KEY (`id`),
  KEY `ILNM_REQ_NO` (`request_no`,`service_name`,`response_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `inter_log_req` */

DROP TABLE IF EXISTS `inter_log_req`;

CREATE TABLE `inter_log_req` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `request_no` varchar(30) DEFAULT '' COMMENT '业务请求订单号，协议对象里的业务请求订单号',
  `type` char(20) DEFAULT '' COMMENT '分：网关接口，直连接口，对账下载',
  `req_code` varchar(100) DEFAULT '' COMMENT 'reqCode区分接口逻辑，[ver]_[moduleName]_[cparseCode]_[callType]_[serviceName]_[extStr]_[infKey] 。callType：1-异步回调 2-网关回调 3-本地窗体',
  `class_name` varchar(50) DEFAULT '' COMMENT '处理类',
  `service_name` varchar(50) DEFAULT '' COMMENT '接口名称',
  `class_req_args` varchar(1000) DEFAULT '' COMMENT '处理类请求参数',
  `platform_no` varchar(30) DEFAULT '' COMMENT '平台编码',
  `user_device` varchar(80) DEFAULT '' COMMENT '用户终端设备类型',
  `platform_user_no` varchar(30) DEFAULT '' COMMENT '平台用户id',
  `req_data` varchar(2000) DEFAULT '' COMMENT '请求数据体',
  `key_serial` varchar(10) DEFAULT '' COMMENT '证书序号',
  `return_str` varchar(500) DEFAULT '' COMMENT '响应字符串',
  `ext_str` varchar(250) DEFAULT '' COMMENT '扩展字符串',
  `done_status` int(11) DEFAULT '0' COMMENT '处理状态，对应category=11',
  `status` varchar(20) DEFAULT '' COMMENT '接口返回的状态',
  `logid` bigint(20) DEFAULT '0' COMMENT '日志id',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `ref_req_no` varchar(30) DEFAULT '' COMMENT '关联请求流水号',
  `handler_times` int(11) DEFAULT '0' COMMENT '处理的毫秒数',
  `ip` varchar(20) DEFAULT '' COMMENT 'ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UIILR_REQ_NO` (`request_no`,`service_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10870 DEFAULT CHARSET=utf8;


