package com.example.controller;

import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.mvc.annotations.Action;
import com.thoughtworks.mvc.annotations.Controller;
import com.thoughtworks.mvc.annotations.Param;

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

    @Action(method = "GET")
    public String show(@Param String id) {
        this.modelMap = new HashMap<>();
        return "show";
    }
}
