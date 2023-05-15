package com.github.ulwx.aka.frame.protocol;

import com.github.ulwx.aka.frame.protocol.req.ForwardProtocal;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.webmvc.BeanGet;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 转发
 */
abstract public class ForwardSupport extends Protocol  implements ForwardProtocal{
    private BeanGet beanGet;

    public BeanGet getBeanGet() {
        return beanGet;
    }

    @Autowired
    public void setBeanGet(BeanGet beanGet) {
        this.beanGet = beanGet;
    }
}
