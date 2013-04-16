package com.thoughtworks.mvc.core;

import java.util.HashMap;
import java.util.Map;

public class Router {

    private Map<String, Class<?>> routes;

    public Router(){
        routes = new HashMap<>();
    }

    public Class classFor(String url) {
        return routes.get(url);
    }

    public <T> void add(String url, Class<T> clazz) {
        this.routes.put(url, clazz);
    }
}
