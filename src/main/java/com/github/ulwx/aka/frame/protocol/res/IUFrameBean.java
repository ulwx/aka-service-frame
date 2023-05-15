package com.github.ulwx.aka.frame.protocol.res;

import com.github.ulwx.aka.frame.annotation.Comment;
import com.github.ulwx.aka.frame.protocol.utils.ReqContext;
import com.ulwx.tool.StringUtils;

public abstract class IUFrameBean<T extends BaseRes> extends BaseRes{

    @Comment("接口信息")
    public InterInfo interInfo=new InterInfo();


    public static class InterInfo{
        @Comment("接口全包类名称")
        public String classname="";
        @Comment("接口标识，可自定义，默认为接口类名")
        public String interId="";
        public InterInfo() {
            if(StringUtils.isEmpty(classname) && ReqContext.getRequestBean()!=null){
                classname=ReqContext.getRequestBean().getClass().getName();
                interId=classname.substring(classname.lastIndexOf(".")+1);
            }
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getInterId() {
            return interId;
        }

        public void setInterId(String interId) {
            this.interId = interId;
        }
    }
}
