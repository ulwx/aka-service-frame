package com.github.ulwx.aka.frame.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Comments.class)
@Documented
public @interface Comment {
	String value() default "";//描述
	String cn() default "";//中文名称
	String method() default "";//请求的方法，post，get
	boolean done() default false; //是否已经完成，用于类
	String author() default "";//开发者，用于类

}