package com.example.controller;

import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.mvc.annotation.Path;
<<<<<<< HEAD
=======
import com.thoughtworks.mvc.core.Controller;
>>>>>>> switch to java 7

import java.util.HashMap;
import java.util.Map;

@Component
@Path(url = "/user")
public class UserController implements Controller {

    private Map<String, Object> modelMap = new HashMap<>();

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
}
