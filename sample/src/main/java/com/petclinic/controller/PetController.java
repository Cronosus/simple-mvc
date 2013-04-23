package com.petclinic.controller;

import com.petclinic.model.Pet;
import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.Controller;
import com.thoughtworks.mvc.core.RequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
@Path(url = "/pet")
public class PetController implements Controller, RequestAware{

    private Map<String, Object> modelMap = new HashMap<>();
    private HttpServletRequest request;

    @Path
    public String index() {
        modelMap.put("total", 5);
        return "pet/index";
    }

    @Path
    public String show() {
        Pet pet = new Pet(request.getParameter("id"), "Doudou");
        modelMap.put("pet", pet);
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

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
