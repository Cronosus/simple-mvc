package com.thoughtworks.mvc.core;

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
        Method method = null;
        String viewName = null;
        try {
            method = controller.getClass().getDeclaredMethod(action, new Class<?>[0]);
        } catch (Exception e) {
        }
        try {
            viewName = (String) method.invoke(controller);
        } catch (Exception e) {
        }
        return viewName;
    }
}
