getLogNotifyList
===
SELECT id,
	request_no,
	service_name,
	response_type,
	logid,
	platform_no,
	platform_user_no,
	resp_data,
	`code`,
	`status`,
	class_name,
	error_code,
	error_message,
	return_str,
	add_time,
	ip
FROM `inter_log_notify`
WHERE 1=1
@ if($$:condition && $$.query.equals("requestNo")){
    and request_no = #{condition}
@}
@ if($$:condition && $$.query.equals("serviceName")){
    and service_name = #{condition}
@}
@ if($$:condition && $$.query.equals("className")){
    and class_name = #{condition}
@}
@ if($$:condition && $$.query.equals("status")){
    and status = #{condition}
@}
@ if($$:startTime){
    and add_time >= #{startTime}
    @ if($$:endTime){
        and add_time < #{endTime}
    @}
@}
@ else{
    and add_time >= DATE_SUB(CURDATE(),INTERVAL 0 DAY)
    and add_time < DATE_SUB(CURDATE(),INTERVAL 1 DAY)
@}
order by add_time desc

getLogNotifyMoreList
===
SELECT id,
	request_no,
	service_name,
	response_type,
	logid,
	platform_no,
	platform_user_no,
	resp_data,
	`code`,
	`status`,
	error_code,
	error_message,
	key_serial,
	class_name,
	return_str,
	add_time,
	ip
FROM `inter_log_notify_more`
WHERE 1=1
@ if($$:condition && $$.query.equals("requestNo")){
    and request_no = #{condition}
@}
@ if($$:condition && $$.query.equals("serviceName")){
    and service_name = #{condition}
@}
@ if($$:condition && $$.query.equals("className")){
    and class_name = #{condition}
@}
@ if($$:condition && $$.query.equals("status")){
    and status = #{condition}
@}
@ if($$:startTime){
    and add_time >= #{startTime}
    @ if($$:endTime){
        and add_time < #{endTime}
    @}
@}
@ else{
    and add_time >= DATE_SUB(CURDATE(),INTERVAL 0 DAY)
    and add_time < DATE_SUB(CURDATE(),INTERVAL 1 DAY)
@}
order by add_time desc

getLogReqList
===
SELECT
  `id`,
  `request_no`,
  `type`,
  `req_code`,
  `class_name`,
  `service_name`,
  `class_req_args`,
  `platform_no`,
  `user_device`,
  `platform_user_no`,
  `req_data`,
  `key_serial`,
  `return_str`,
  `ext_str`,
  `done_status`,
  `status`,
  `logid`,
  `add_time`,
  `ref_req_no`,
  `handler_times`,
  `ip`
FROM
  `inter_log_req`
WHERE 1=1
@ if($$:startTime){
    and add_time >= #{startTime}
    @ if($$:endTime){
        and add_time < #{endTime}
    @}
@ }else{
    and add_time >= DATE_SUB(CURDATE(),INTERVAL 0 DAY)
    and add_time < DATE_SUB(CURDATE(),INTERVAL 1 DAY)
@ }
@ if($$:condition && $$.sType.equals("requestNo")){
    and request_no like #{condition%}
@}
@ if($$:condition && $$.sType.equals("serviceName")){
    and service_name like #{condition%}
@}
@ if($$:condition && $$.sType.equals("className")){
    and class_name like #{condition%}
@}
@ if($$:condition && $$.sType.equals("status")){
    and status like #{condition%}
@}
@ if($$:doneStatus){
    and done_status = #{doneStatus}
@}
order by add_time desc