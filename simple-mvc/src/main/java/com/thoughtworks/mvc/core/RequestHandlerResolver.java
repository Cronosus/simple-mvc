package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.converter.TypeConverter;
import com.thoughtworks.mvc.entity.ActionInfo;
import com.thoughtworks.mvc.entity.RequiredParam;
import com.thoughtworks.mvc.view.FreeMarkerViewResolver;
import com.thoughtworks.mvc.view.ViewResolver;
import com.thoughtworks.simpleframework.di.core.Injector;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Field;

import static com.thoughtworks.simpleframework.util.Lang.makeThrow;
import static com.thoughtworks.simpleframework.util.Lang.stackTrace;

public class RequestHandlerResolver {

    private final Injector container;
    private final URLMapping urlMapping;
    private final ViewResolver viewResolver;

    private RequestHandlerResolver(Injector container, String packageName, String contextPath, String templatePath) {
        this.container = container;
        this.urlMapping = URLMapping.load(packageName, contextPath);
        this.viewResolver = FreeMarkerViewResolver.create(templatePath);
    }

    public static RequestHandlerResolver create(Injector container, String packageName, String contextPath, String templatePath) {
        return new RequestHandlerResolver(container, packageName, contextPath, templatePath);
    }

    public RequestHandler resolve(HttpServletRequest request) {

        ActionInfo actionInfo = urlMapping.get(request.getRequestURI());

        if (null == actionInfo) {
            throw makeThrow("can not find action for requested URI %s.", request.getRequestURI());
        }

        Controller controller = (Controller) container.get(actionInfo.getControllerClass());

        Object param = extractParam(request, actionInfo);

        return new RequestHandler(viewResolver, controller,
                actionInfo.getMethod(), param);
    }

    private Object extractParam(HttpServletRequest request, ActionInfo actionInfo) {

        RequiredParam requiredParam = actionInfo.getRequiredParam();

        if (null == requiredParam) {
            return null;
        }

        return TypeConverter.create(requiredParam.getType()).convert(request, requiredParam.getName());
    }

    private Field getField(RequiredParam requiredParam) {
        try {
            return requiredParam.getType().getField(requiredParam.getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw makeThrow("failed to get field, error: %s", stackTrace(e));
        }
    }

}
