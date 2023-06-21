package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.frame.UiFrameConstants;
import com.github.ulwx.aka.frame.protocol.HandleStatus;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.frame.services.service.InterLogService;
import com.github.ulwx.aka.webmvc.*;
import com.github.ulwx.aka.webmvc.web.action.CbResult;
import com.github.ulwx.aka.webmvc.web.action.Status;
import com.ulwx.tool.ObjectUtils;
import com.ulwx.tool.RequestUtils;
import com.ulwx.tool.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("com.github.ulwx.aka.frame.process.StoreRequestProcessor")
@Order(Integer.MAX_VALUE)
public class StoreRequestProcessor implements RequestProcessor {
    private static Logger log = Logger.getLogger(StoreRequestProcessor.class);
    private BeanGet beanGet;


    public BeanGet getBeanGet() {
        return beanGet;
    }

    @Autowired
    public void setBeanGet(BeanGet beanGet) {
        this.beanGet = beanGet;
    }

    public static final String InsertLogID = "InsertLogID";
    public static final String Start = "handle-start-time";

    @Override
    public String onBefore(HttpServletRequest request, ActionMethodInfo actionMethodInfo, RequestUtils context) {
        UIFrameAppConfig uiFrameAppConfig = beanGet.bean(UIFrameAppConfig.class);
        String namespace = actionMethodInfo.getNamespace().getName();
        AkaFrameProperties.ProtocolProperties protocolProperties = uiFrameAppConfig.getProtocolInfo(namespace);
        if (protocolProperties == null) return null;
        String builderClassName = StringUtils.trim(protocolProperties.getProtocolBuilderClass());
        if (StringUtils.isEmpty(builderClassName)) {
            return null;
        }
        Protocol protocol = null;
        try {
            ProtocolBuilder builder = (ProtocolBuilder) Class.forName(builderClassName).getConstructor().newInstance();
            protocol = builder.build(request, actionMethodInfo, context);
        } catch (Exception e) {
            log.error("" + e, e);
            throw new RuntimeException(e);
        }

        context.setObject(UiFrameConstants.PROTOCOL_REQ_PROTOCOL, protocol);
        InterLogService interLogService = beanGet.bean(InterLogService.class);
        try {
            long interLogId = interLogService.insertLog(protocol,
                    actionMethodInfo.getActionObj().getClass());
            request.setAttribute(InsertLogID, interLogId);
            request.setAttribute(Start, System.currentTimeMillis());
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    @Override
    public boolean onFinished(HttpServletRequest request, ActionMethodInfo actionMethodInfo,
                              RequestUtils context, String resultViewName, Exception e,
                              ProcessorStatus status) {
        Long insertLogId = (Long) request.getAttribute(InsertLogID);
        if (insertLogId == null) return true;
        InterLogService interLogService = beanGet.bean(InterLogService.class);
        Long start = (Long) request.getAttribute(Start);
        CbResult cbResult = (CbResult) request.getAttribute(WebMvcCbConstants.ResultKey);
        String ret="";
        if(cbResult !=null) {
            ret = ObjectUtils.toStringUseFastJson(cbResult.getData());
        }else{
            ret=resultViewName;
        }

        try {
            if( e==null) {
                if(cbResult !=null) {
                    interLogService.updateLog(insertLogId, cbResult.getStatus(), cbResult.getError() + "",
                            cbResult.getMessage(), HandleStatus.suc, ret, start);
                }else{
                    interLogService.updateLog(insertLogId, Status.SUC,  "0",
                           "", HandleStatus.suc, ret, start);
                }
            }else{
                interLogService.updateLog(insertLogId, Status.ERR, "0", e + "",
                        HandleStatus.fail,
                        ret,
                        start);

            }

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


        return true;
    }




}
