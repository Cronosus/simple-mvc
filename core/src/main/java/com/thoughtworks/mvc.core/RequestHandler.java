package com.thoughtworks.mvc.core;

import com.thoughtworks.utils.Lang;

import java.lang.reflect.Method;

public class RequestHandler {
    private final Object param;
    private FreeMarkerViewResolver viewResolver;
    private Controller controller;
    private Method action;

    public RequestHandler(FreeMarkerViewResolver viewResolver, Controller controller, Method action, Object param) {
        this.viewResolver = viewResolver;
        this.controller = controller;
        this.action = action;
        this.param = param;
    }

    public ModelAndView handle() {
        assert action != null;
        assert controller != null;

        String viewName;
        try {
            if (null == param) {
                viewName = (String) action.invoke(controller);
            } else {
                viewName = (String) action.invoke(controller, param);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw Lang.makeThrow("Invoking action failed %s", Lang.stackTrace(e));
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

    public Object getParam() {
        return param;
    }
}
