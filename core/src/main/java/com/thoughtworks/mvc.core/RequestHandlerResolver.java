package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;
import com.thoughtworks.utils.Lang;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class RequestHandlerResolver {

    private final Injector container;
    private final URLMapping urlMapping;
    private final FreeMarkerViewResolver viewResolver;

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
            throw Lang.makeThrow("can not find action for requested URI %s.", request.getRequestURI());
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

        Object param;

        if (Lang.isPrimitive(requiredParam.getType())) {
            param = typeConvert(request, requiredParam.getType(), requiredParam.getName());
        } else {
            param = objectConvert(request, requiredParam.getType(), requiredParam.getName());
        }
        return param;
    }

    private Object objectConvert(HttpServletRequest request, Class<?> type, String name) {
        Object instance = Lang.instanceFor(type);
        Field[] fields = type.getDeclaredFields();

        Object fieldValue;
        for (Field field : fields) {
            String paramName = name == null ? field.getName() : name + "." + field.getName();

            if (Lang.isPrimitive(field.getType())) {
                fieldValue = typeConvert(request, field.getType(), paramName);

            } else {
                fieldValue = objectConvert(request, field.getType(), paramName);
            }

            try {
                field.setAccessible(true);
                field.set(instance, fieldValue);
            } catch (Exception e) {
                Lang.makeThrow("Setting param failed. error: %s", Lang.stackTrace(e));
            }
        }
        return instance;
    }

    private Object typeConvert(HttpServletRequest request, Class<?> type, String name) {
        String val = request.getParameter(name);
        if (null == val)
            return null;
        Object param;
        if (type == Long.class) {
            param = Long.parseLong(val);
        } else if (type == Integer.class) {
            param = Integer.parseInt(val);
        } else {
            param = val;
        }
        return param;
    }


}
