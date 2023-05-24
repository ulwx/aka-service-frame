package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.webmvc.ActionMethodInfo;
import com.ulwx.tool.RequestUtils;

import javax.servlet.http.HttpServletRequest;

public interface FrameProcess {
    public String process(HttpServletRequest request, ActionMethodInfo actionMethodInfo, RequestUtils context);
}
