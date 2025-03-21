
getLogReqList
===
SELECT
  *
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