package com.github.ulwx.aka.frame.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface InterfaceTest {
   String value()  default ""; //默认的值
   boolean display() default true; //用于是否显示在接口测试页面上，如果为false，表明字段不会显示在测试页面上
   boolean hidden()  default true; //是否作为隐藏字段，只有display=false才生效,当hidden=true表名在页面上作为隐藏域
}