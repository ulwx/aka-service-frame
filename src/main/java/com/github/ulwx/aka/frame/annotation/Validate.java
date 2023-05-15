package com.github.ulwx.aka.frame.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Repeatable(Validates.class)
@Documented
public @interface Validate {
	ValidateType[] value() default { ValidateType.NOTNULL };

	int maxLen() default Integer.MAX_VALUE;

	int minLen() default Integer.MIN_VALUE;
	

	public static enum ValidateType {
		NOTNULL, EMAIL ,CHINESE
	}
}