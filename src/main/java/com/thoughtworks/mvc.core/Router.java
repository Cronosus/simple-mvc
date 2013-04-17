package com.thoughtworks.mvc.core;

import com.thoughtworks.di.utils.ClassUtil;
import com.thoughtworks.mvc.annotations.Path;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Router {

    private Map<String, Class<? extends Controller>> routes;

    private Router(String packageName) {
        this.routes = extractRouting(packageName);
    }

    public static Router create(String packageName) {
        return new Router(packageName);
    }


    public Class classFor(String url) {
        return routes.get(url);
    }

    private Map<String, Class<? extends Controller>> extractRouting(String packageName) {
        Map<String, Class<? extends Controller>> routes = new HashMap<>();

        Collection<Class> allClasses = ClassUtil.getClassInfos(packageName);
        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(Path.class)) {
                routes.put(clazz.getAnnotation(Path.class).url(), (Class<? extends Controller>) clazz);
            }
        }
        return routes;
    }

}
