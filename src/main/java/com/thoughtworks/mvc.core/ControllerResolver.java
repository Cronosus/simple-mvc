package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;

import javax.servlet.http.HttpServletRequest;

public class ControllerResolver {
    private final Injector container;
    private final Router router;

    public ControllerResolver(Injector container, Router router) {
        this.container = container;
        this.router = router;
    }

    public Controller resolve(HttpServletRequest request) {
        String moduleUrl = request.getRequestURI();
        Class<? extends  Controller> clazz = router.classFor(moduleUrl);
        return container.get(clazz);
    }
}
