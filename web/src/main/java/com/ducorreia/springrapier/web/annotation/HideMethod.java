package com.ducorreia.springrapier.web.annotation;

import com.ducorreia.springrapier.web.util.CrudMethod;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HideMethod {

    CrudMethod[] value();
}
