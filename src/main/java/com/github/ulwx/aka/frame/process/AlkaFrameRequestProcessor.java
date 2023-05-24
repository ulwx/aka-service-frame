package com.github.ulwx.aka.frame.process;

import com.github.ulwx.aka.frame.AkaFrameProperties;
import com.github.ulwx.aka.frame.UIFrameAppConfig;
import com.github.ulwx.aka.webmvc.ActionMethodInfo;
import com.github.ulwx.aka.webmvc.BeanGet;
import com.github.ulwx.aka.webmvc.RequestProcessor;
import com.ulwx.tool.RequestUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class AlkaFrameRequestProcessor implements RequestProcessor {
    private List<FrameProcess> frameProcesses=new ArrayList<>();
    private BeanGet beanGet;

    public AlkaFrameRequestProcessor(List<FrameProcess> frameProcesses,BeanGet beanGet)throws Exception{
        this.frameProcesses=frameProcesses;
        this.beanGet=beanGet;
    }

    @Override
    public String onBefore(HttpServletRequest request,
                           ActionMethodInfo actionMethodInfo, RequestUtils context) {
        try {

            UIFrameAppConfig akaFrameProperties =beanGet.bean(UIFrameAppConfig.class);
            List<FrameProcess> finalProcessors =new ArrayList<>();
            finalProcessors.addAll(this.frameProcesses);
            String namespace= actionMethodInfo.getNamespace().getName();
            AkaFrameProperties.ProtocolProperties protocolProperties=akaFrameProperties.getProtocolInfo(namespace);
            if(protocolProperties==null){
                return null;
            }
            List<String> processors = akaFrameProperties.getRequestHander(namespace).getProcessors();
            for (int i = 0; i < processors.size(); i++) {
                String className = processors.get(i).trim();
                FrameProcess p = (FrameProcess) Class.forName(className).newInstance();
                finalProcessors.add(p);
            }
            for (int i = 0; i < finalProcessors.size(); i++) {
                FrameProcess p = finalProcessors.get(i);
                String ret = p.process(request,actionMethodInfo, context);
                if (ret == null) {
                    continue;
                } else {
                    return ret;
                }
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
