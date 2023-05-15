package com.github.ulwx.aka.frame.protocol;

import com.github.ulwx.aka.frame.protocol.req.DownLoadProtocal;
import com.github.ulwx.aka.frame.protocol.req.Protocol;
import com.github.ulwx.aka.webmvc.BeanGet;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 返回下载的文件流
 */
abstract public class DownLoadSupport extends Protocol  implements DownLoadProtocal{
    private BeanGet beanGet;

    public BeanGet getBeanGet() {
        return beanGet;
    }

    @Autowired
    public void setBeanGet(BeanGet beanGet) {
        this.beanGet = beanGet;
    }

}
