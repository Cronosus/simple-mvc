package com.thoughtworks.mvc.core;

import java.lang.reflect.Method;

public class ActionInfo<T extends Controller> {
    private final Class<T> controller;
    private Method method;
    private Class<?> requiredParamType;

    public ActionInfo(Class<T> controller, Method method, Class<?> requiredParamType) {
        this.controller = controller;
        this.method = method;
        this.requiredParamType = requiredParamType;
    }

    public Class<T> getControllerClass() {
        return this.controller;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getRequiredParamType() {
        return requiredParamType;
    }
}
