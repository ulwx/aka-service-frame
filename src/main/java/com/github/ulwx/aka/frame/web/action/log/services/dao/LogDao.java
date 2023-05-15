package com.github.ulwx.aka.frame.web.action.log.services.dao;

import com.github.ulwx.aka.dbutils.tool.MD;
import com.github.ulwx.aka.dbutils.tool.PageBean;
import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.protocol.services.dao.impl.model.InterLogNotify;
import com.github.ulwx.aka.frame.protocol.services.dao.impl.model.InterLogNotifyMore;
import com.github.ulwx.aka.frame.protocol.services.dao.impl.model.InterLogReq;
import com.github.ulwx.aka.webmvc.AkaDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogDao extends AkaDaoSupport {
    @Override
    public String getDS(){
        AkaFrameProperties akaFrameProperties=this.beanGet.bean(AkaFrameProperties.class);
        String poolName="";
        if(akaFrameProperties.getStorage().getDatabse().getDs()!=null){
            poolName=akaFrameProperties.getStorage().getDatabse().getDs();
            return poolName;
        }else{
            throw new RuntimeException("没有指定数据源名称！");
        }

    }
    public  List<InterLogNotify> getLogNotifyList(String query, String condition, String startTime, String endTime,
                                                        Integer pageNum, Integer perPage, PageBean pb) throws Exception {

        Map<String,Object> map = new HashMap<>();
        map.put("query",query);
        map.put("condition",condition);
        map.put("startTime",startTime);
        map.put("endTime",endTime);

        return this.template.queryList(InterLogNotify.class,
                MD.md(),map, pageNum,perPage,pb,null);
    }

    public  List<InterLogNotifyMore> getLogNotifyMoreList(String query, String condition, String startTime, String endTime,
                                                                Integer pageNum, Integer perPage, PageBean pb) throws Exception {

        Map<String,Object> map = new HashMap<>();
        map.put("query",query);
        map.put("condition",condition);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        return this.template.queryList(InterLogNotifyMore.class,MD.md(),map,
                pageNum,perPage,pb,null);
    }

    public  List<InterLogReq> getLogReqList(String sType, String condition, Integer doneStatus, String startTime, String endTime, Integer pageNum, Integer perPage,
                                                  PageBean pb) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("sType",sType);
        map.put("condition",condition);
        map.put("doneStatus",doneStatus);
        return this.template.queryList(InterLogReq.class,MD.md(),map, pageNum,perPage,pb,null);
    }
}
