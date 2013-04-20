package com.example.controller;

import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.mvc.annotations.Action;
import com.thoughtworks.mvc.annotations.Controller;

import java.util.HashMap;
import java.util.Map;

@Component
@Controller(url = "/user")
public class UserController implements com.thoughtworks.mvc.core.Controller {

    private Map<String, Object> modelMap = new HashMap<>();

    @Action
    public String index() {
        modelMap.put("total", 5);
        return "user/index";
    }

    @Action
    public String show(String id) {
        return "show";
    }

    @Action(url = "create")
    public String createUser() {
        return "create";
    }

    @Override
    public Map<String, Object> getModelMap() {
        return this.modelMap;
    }
}
