package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;
import com.thoughtworks.utils.Lang;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

public class RequestHandlerResolver {

    private final Injector container;
    private final URLMapping urlMapping;
    private final FreeMarkerViewResolver viewResolver;

    private RequestHandlerResolver(Injector container, String packageName, ServletConfig servletConfig) {
        this.container = container;
        this.urlMapping = URLMapping.load(packageName, servletConfig.getServletContext());
        this.viewResolver = FreeMarkerViewResolver.create(servletConfig);
    }

    public static RequestHandlerResolver create(Injector container, String packageName, ServletConfig servletConfig) {
        return new RequestHandlerResolver(container, packageName, servletConfig);
    }

    public RequestHandler resolve(HttpServletRequest request) {

        ActionInfo actionInfo = urlMapping.get(request.getRequestURI());
        if (null == actionInfo) {
            throw Lang.makeThrow("can not find action for requested URI %s.", request.getRequestURI());
        }

        RequestHandler handler = new RequestHandler(viewResolver, (Controller) container.get(actionInfo.getController()),
                actionInfo.getMethod());
        return handler;
    }
}
