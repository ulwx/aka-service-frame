package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.webmvc.ActionMethodInfo;
import com.ulwx.tool.RequestUtils;

import javax.servlet.http.HttpServletRequest;

public interface ProtocolBuilder {
    Protocol build(HttpServletRequest request, ActionMethodInfo actionMethodInfo, RequestUtils context);
}
