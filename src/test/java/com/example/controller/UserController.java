package com.example.controller;

import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.mvc.annotations.Path;
import com.thoughtworks.mvc.core.Controller;

import java.util.HashMap;
import java.util.Map;

@Path(url = "/user")
@Component
public class UserController implements Controller {

    private Map<String, Object> modelMap;

    @Override
    public Map<String, Object> getModelMap() {
        return modelMap;
    }

    public String show() {
        this.modelMap = new HashMap<>();
        return "show";
    }
}
