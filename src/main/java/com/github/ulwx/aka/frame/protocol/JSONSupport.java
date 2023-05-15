package com.github.ulwx.aka.frame.protocol;

import com.github.ulwx.aka.frame.protocol.req.JSONProtocal;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.webmvc.BeanGet;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 返回JSON接口
 */
abstract public class  JSONSupport extends Protocol implements JSONProtocal{
    private BeanGet beanGet;

    public BeanGet getBeanGet() {
        return beanGet;
    }

    @Autowired
    public void setBeanGet(BeanGet beanGet) {
        this.beanGet = beanGet;
    }
}
