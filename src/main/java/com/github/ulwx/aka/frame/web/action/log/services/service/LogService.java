package com.github.ulwx.aka.frame.web.action.log.services.service;

import com.github.ulwx.aka.dbutils.tool.PageBean;
import com.github.ulwx.aka.frame.services.dao.impl.model.InterLogReq;
import com.github.ulwx.aka.frame.web.action.log.services.dao.LogDao;
import com.github.ulwx.aka.webmvc.AkaServiceSupport;
import org.apache.log4j.Logger;

import java.util.List;

public class LogService extends AkaServiceSupport {

    private static Logger logger = Logger.getLogger(LogService.class);




    public List<InterLogReq> getLogReqList(String sType, String condition, Integer doneStatus, String startTime, String endTime, Integer pageNum, Integer perPage,
                                           PageBean pb) throws Exception {
        return beanGet.bean(LogDao.class).getLogReqList(sType,condition,doneStatus,startTime, endTime, pageNum, perPage, pb);
    }
}
