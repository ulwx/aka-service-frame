package com.github.ulwx.aka.frame.web.action.log.services.dao;

import com.github.ulwx.aka.dbutils.tool.MD;
import com.github.ulwx.aka.dbutils.tool.PageBean;
import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.services.dao.impl.model.InterLogReq;
import com.github.ulwx.aka.webmvc.AkaDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogDao extends AkaDaoSupport {
    @Override
    public String getDS(){
        UIFrameAppConfig uFrameAppConfig=this.beanGet.bean(UIFrameAppConfig.class);
        String poolName="";
        if(uFrameAppConfig.getStorage().getDatabse().getDs()!=null){
            poolName=uFrameAppConfig.getStorage().getDatabse().getDs();
            return poolName;
        }else{
            throw new RuntimeException("没有指定数据源名称！");
        }

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
