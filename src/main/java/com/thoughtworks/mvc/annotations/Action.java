package com.thoughtworks.mvc.annotations;

public @interface Action {
    String url() default "";

    String method();
}
