package com.zcy.cn.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TokenModel {

    String value() default "";

    boolean required() default false;

}
