package com.thoughtworks.mvc.core;

import com.thoughtworks.di.utils.ClassUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Router {

    private Map<String, Class<? extends com.thoughtworks.mvc.core.Controller>> routes;

    private Router(String packageName) {
        this.routes = extractRouting(packageName);
    }

    public static Router create(String packageName) {
        return new Router(packageName);
    }


    public Class classFor(String url) {
        return routes.get(url);
    }

    private Map<String, Class<? extends com.thoughtworks.mvc.core.Controller>> extractRouting(String packageName) {
        Map<String, Class<? extends com.thoughtworks.mvc.core.Controller>> routes = new HashMap<>();

        Collection<Class> allClasses = ClassUtil.getClassInfos(packageName);
        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(com.thoughtworks.mvc.annotations.Controller.class)) {
                routes.put(clazz.getAnnotation(com.thoughtworks.mvc.annotations.Controller.class).url(), (Class<? extends com.thoughtworks.mvc.core.Controller>) clazz);
            }
        }
        return routes;
    }

}
