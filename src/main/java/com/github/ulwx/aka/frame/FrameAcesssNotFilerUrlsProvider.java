package com.github.ulwx.aka.frame;

import com.github.ulwx.aka.admin.filter.AcesssNotFilerUrlsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FrameAcesssNotFilerUrlsProvider implements AcesssNotFilerUrlsProvider {
    private UIFrameAppConfig uiFrameAppConfig;

    public UIFrameAppConfig getUiFrameAppConfig() {
        return uiFrameAppConfig;
    }
    @Autowired
    public void setUiFrameAppConfig(UIFrameAppConfig uiFrameAppConfig) {
        this.uiFrameAppConfig = uiFrameAppConfig;
    }

    @Override
    public List<String> builder() {
        Set<String> set=uiFrameAppConfig.getAllNameSpaces();
        if(set!=null && set.size()>0){
            List<String> list=set.stream().map(s->"/"+s).collect(Collectors.toList());
            return list;
        }
        return new ArrayList<>();

    }
}
