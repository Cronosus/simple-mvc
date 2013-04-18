package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.exceptions.RequestHandleException;

import java.lang.reflect.Method;

public class RequestHandler {
    private Controller controller;
    private String action;

    public RequestHandler(Controller controller, String action) {
        this.controller = controller;
        this.action = action;
    }

    public Controller getController() {
        return controller;
    }

    public String getAction() {
        return action;
    }

    public String handle() {
        try {
            Method method = controller.getClass().getDeclaredMethod(action, new Class<?>[0]);
            return (String) method.invoke(controller);
        } catch (Exception e) {
            throw new RequestHandleException(e);
        }
    }
}
