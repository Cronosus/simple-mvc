package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;
import com.thoughtworks.utils.Lang;

import javax.servlet.ServletConfig;
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

        Object param = null;
        String stringValue = request.getParameter(requiredParam.getName());

        if (Lang.isPrimitive(requiredParam.getType())) {
            param = typeConvert(stringValue, requiredParam.getType());
        } else {
            param = Lang.instanceFor(requiredParam.getType());
            Field[] fields = requiredParam.getType().getDeclaredFields();

            for (Field field : fields) {
                String value = request.getParameter(field.getName());
                if (null != value) {
                    Object fieldValue = typeConvert(value, field.getType());
                    try {
                        field.setAccessible(true);
                        field.set(param, fieldValue);
                    } catch (Exception e) {
                        Lang.makeThrow("Setting param failed. error: %s", Lang.stackTrace(e));
                    }
                }
            }
        }
        return param;
    }

    private Object typeConvert(String stringValue, Class<?> type) {
        if (null == stringValue)
            return null;
        Object param;
        if (type == Long.class) {
            param = Long.parseLong(stringValue);
        } else if (type == Integer.class) {
            param = Integer.parseInt(stringValue);
        } else {
            param = stringValue;
        }
        System.out.println("Converted value: " + param);
        return param;
    }


}
