package com.thoughtworks.mvc.core;

public class RequiredParam {

    private Class<?> type;
    private String name;

    public RequiredParam(Class<?> type, String name) {
        this.type = type;
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
