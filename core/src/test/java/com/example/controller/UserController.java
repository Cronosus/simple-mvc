package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.di.annotation.Inject;
import com.thoughtworks.di.core.Lifecycle;
import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.Controller;

import java.util.HashMap;
import java.util.Map;

@Component(lifecycle = Lifecycle.Transient)
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
    public String show(@Param("id") Long id) {
        User user = service.get(id);
        modelMap.put("user", user);
        return "user/show";
    }

    @Path(url = "create")
    public String create(@Param("user") User user) {
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
