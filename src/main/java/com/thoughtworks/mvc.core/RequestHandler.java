package com.thoughtworks.mvc.core;

import com.thoughtworks.utils.Lang;

import java.lang.reflect.Method;

public class RequestHandler {
    private Object controller;
    private Method action;

    public RequestHandler(Object controller, Method action) {
        this.controller = controller;
        this.action = action;
    }


    public Object getController() {
        return controller;
    }

    public Method getAction() {
        return action;
    }

    public String handle() {
        try {
            return (String) action.invoke(controller);
        } catch (Exception e) {
            throw Lang.makeThrow("Invoking action failed %s", e.getMessage());
        }
    }
}
