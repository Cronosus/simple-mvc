package com.example.controller;

import com.example.service.UserService;
import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.di.annotation.Inject;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.Controller;

import java.util.HashMap;
import java.util.Map;

@Component
@Path(url = "/user")
public class UserController implements Controller {

    private Map<String, Object> modelMap = new HashMap<>();

    @Inject
    public UserService service;

    @Path
    public String index() {
        modelMap.put("total", 5);
        return "user/index";
    }

    @Path
    public String show(String id) {
        return "user/show";
    }

    @Path(url = "create")
    public String createUser() {
        return "user/create";
    }

    @Path(url = "new")
    public String fresh() {
        return "user/new";
    }

    @Override
    public Map<String, Object> getModelMap() {
        return this.modelMap;
    }

    public UserService getService() {
        return service;
    }
}
