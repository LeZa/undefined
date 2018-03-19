package com.build.config;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonKey {
    String value();

    boolean  isCollection() default false;

    boolean  isEncode() default true;
}
