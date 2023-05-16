package com.github.ulwx.aka.frame.web.action.log;

import com.github.ulwx.aka.admin.domain.cus.CbEasyUIGridModel;
import com.github.ulwx.aka.dbutils.tool.PageBean;
import com.github.ulwx.aka.frame.services.dao.impl.model.InterLogReq;
import com.github.ulwx.aka.frame.web.action.log.services.service.LogService;
import com.github.ulwx.aka.webmvc.web.action.ActionSupport;
import com.ulwx.tool.RequestUtils;
import org.apache.log4j.Logger;

import java.util.List;

public class LogAction extends ActionSupport {
    private static final long serialVersionUID = 6474449672706897629L;
    public static Logger logger = Logger.getLogger(LogAction.class);



    
    public String getLogReqList(){
        RequestUtils ru = this.getRequestUtils();
        //分页信息
        Integer pageNum = ru.getInt("page");
        Integer perPage = ru.getInt("rows");
        PageBean pb = new PageBean();
        //查询条件
        String sType = ru.getTrimString("sType");
        String condition = ru.getTrimString("condition");
        Integer doneStatus = ru.getInt("doneStatus");
        String startTime = ru.getTrimString("startTime");
        String endTime = ru.getTrimString("endTime");

        List<InterLogReq> list = null;
        CbEasyUIGridModel<InterLogReq> model = new CbEasyUIGridModel<>();
        try{
            list = beanGet.bean(LogService.class).getLogReqList(sType,condition,doneStatus,startTime,endTime,pageNum,perPage,pb);
            model.setTotal(pb.getTotal());
            model.setRows(list);
            return this.JSON_SUC(model);
        }catch(Exception e){
            logger.error("",e);
        }
        return this.JSON_ERR("获取失败");
    }
}
