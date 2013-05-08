package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.entity.ModelAndView;
import com.thoughtworks.mvc.view.View;
import com.thoughtworks.mvc.view.ViewResolver;
import com.thoughtworks.mvc.utils.MVCHelper;

import java.lang.reflect.Method;

import static com.thoughtworks.simpleframework.util.Lang.makeThrow;
import static com.thoughtworks.simpleframework.util.Lang.stackTrace;

public class RequestHandler {
    private final Object param;
    private ViewResolver viewResolver;
    private Controller controller;
    private Method action;

    public RequestHandler(ViewResolver viewResolver, Controller controller, Method action, Object param) {
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
            throw makeThrow("Invoking action failed %s", stackTrace(e));
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
