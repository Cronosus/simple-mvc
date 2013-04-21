package com.petclinic.controller;

import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.Controller;

import java.util.HashMap;
import java.util.Map;

@Component
@Path(url = "/pet")
public class PetController implements Controller {

    private Map<String, Object> modelMap = new HashMap<>();

    @Path
    public String index() {
        modelMap.put("total", 5);
        return "pet/index";
    }

    @Path
    public String show(String id) {
        return "pet/show";
    }

    @Path(url = "create")
    public String createUser() {
        return "pet/create";
    }

    @Path(url = "new")
    public String fresh() {
        return "pet/new";
    }

    @Override
    public Map<String, Object> getModelMap() {
        return this.modelMap;
    }
}
