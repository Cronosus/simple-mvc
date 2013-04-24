package com.thoughtworks.mvc.core;

import java.lang.reflect.Method;

public class ActionInfo<T extends Controller> {
    private final Class<T> controller;
    private Method method;
    private RequiredParam requiredParam;

    public ActionInfo(Class controller, Method method, RequiredParam requiredParam) {
        this.controller = controller;
        this.method = method;
        this.requiredParam = requiredParam;
    }

    public Class<T> getControllerClass() {
        return this.controller;
    }

    public Method getMethod() {
        return method;
    }

    public RequiredParam getRequiredParam() {
        return requiredParam;
    }
}
