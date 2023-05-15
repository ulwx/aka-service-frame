package com.github.ulwx.aka.frame.protocol;

import com.github.ulwx.aka.frame.protocol.req.JSPProtocal;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.webmvc.BeanGet;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 返回第三方网关界面
 */
abstract public class JSPSupport extends Protocol  implements JSPProtocal{
    private BeanGet beanGet;

    public BeanGet getBeanGet() {
        return beanGet;
    }

    @Autowired
    public void setBeanGet(BeanGet beanGet) {
        this.beanGet = beanGet;
    }

}
