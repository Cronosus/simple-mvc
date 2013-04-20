package com.thoughtworks.mvc.core;

import java.lang.reflect.Method;

public class ActionInfo<T extends Controller> {
    private final Class<T> controller;
    private Method method;

    public ActionInfo(Class<T> controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Class<T> getController() {
        return this.controller;
    }

    public Method getMethod() {
        return method;
    }
}
