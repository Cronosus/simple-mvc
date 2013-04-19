package com.example.controller;

import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.mvc.annotations.Action;
import com.thoughtworks.mvc.annotations.Controller;

import java.util.HashMap;
import java.util.Map;

@Component
@Controller(url = "/user")
public class UserController implements com.thoughtworks.mvc.core.Controller {

    private Map<String, Object> modelMap;

    @Override
    public Map<String, Object> getModelMap() {
        return modelMap;
    }

    @Action
    public String show(String id) {
        this.modelMap = new HashMap<>();
        return "show";
    }

    @Action(url = "create")
    public String createUser() {
        this.modelMap = new HashMap<>();
        return "show";
    }
}
