package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;
import com.thoughtworks.utils.Lang;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class RequestHandlerResolver {

    private final Injector container;
    private final URLMapping urlMapping;

    public RequestHandlerResolver(Injector container, String packageName, ServletContext servletContext) {
        this.container = container;
        this.urlMapping = URLMapping.load(packageName, servletContext);
    }

    public RequestHandler resolve(HttpServletRequest request) {

        ActionInfo actionInfo = urlMapping.get(request.getRequestURI());
        if (null == actionInfo) {
            throw Lang.makeThrow("can not find action for requested URI %s.", request.getRequestURI());
        }

        RequestHandler handler = new RequestHandler(container.get(actionInfo.getController()), actionInfo.getMethod());
        return handler;
    }
}
