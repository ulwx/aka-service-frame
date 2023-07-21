package com.github.ulwx.aka.frame;


import com.github.ulwx.aka.webmvc.MyPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(name="classpath*:aka-application-service-frame.yml"
        , value = {"classpath*:aka-application-service-frame.yml"},
        factory = MyPropertySourceFactory.class)
@Configuration("com.github.ulwx.aka.frame.AkaFrameConfiguration")
public class AkaFrameConfiguration {

}
