package com.github.ulwx.aka.frame.protocol;

import com.github.ulwx.aka.frame.protocol.req.CallBackProtocal;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.webmvc.BeanGet;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class CallBackSupport extends Protocol implements CallBackProtocal {
    private BeanGet beanGet;

    public BeanGet getBeanGet() {
        return beanGet;
    }

    @Autowired
    public void setBeanGet(BeanGet beanGet) {
        this.beanGet = beanGet;
    }
}
