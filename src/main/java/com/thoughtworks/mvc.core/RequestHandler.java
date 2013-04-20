package com.thoughtworks.mvc.core;

import com.thoughtworks.utils.Lang;

import java.lang.reflect.Method;

public class RequestHandler {
    private FreeMarkerViewResolver viewResolver;
    private Controller controller;
    private Method action;

    public RequestHandler(FreeMarkerViewResolver viewResolver, Controller controller, Method action) {
        this.viewResolver = viewResolver;
        this.controller = controller;
        this.action = action;
    }

    public ModelAndView handle() {
        String viewName;
        try {
            viewName =  (String) action.invoke(controller);
        } catch (Exception e) {
            throw Lang.makeThrow("Invoking action failed %s", e.getMessage());
        }
        View view = viewResolver.resolve(viewName);

        return new ModelAndView(view, controller.getModelMap());
    }

    public Controller getController() {
        return controller;
    }

    public String getActionName() {
        return action.getName();
    }
}
