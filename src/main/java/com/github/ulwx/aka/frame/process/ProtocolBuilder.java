package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.webmvc.ActionMethodInfo;
import com.github.ulwx.aka.webmvc.web.action.CbResult;
import com.ulwx.tool.RequestUtils;

import javax.servlet.http.HttpServletRequest;

public interface ProtocolBuilder {
    Protocol build(HttpServletRequest request, ActionMethodInfo actionMethodInfo, RequestUtils context);

    default StoreResultInfo buildResult(CbResult cbResult){
        return null;
    }
}
