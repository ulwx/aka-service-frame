package com.github.ulwx.aka.frame.protocol;

import com.github.ulwx.aka.frame.protocol.req.JSPFormProtocal;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.webmvc.BeanGet;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 返回本地网关界面
 */
abstract public class JSPFormSupport extends Protocol  implements JSPFormProtocal{
    private BeanGet beanGet;

    public BeanGet getBeanGet() {
        return beanGet;
    }

    @Autowired
    public void setBeanGet(BeanGet beanGet) {
        this.beanGet = beanGet;
    }

}
