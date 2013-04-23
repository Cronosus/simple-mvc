package com.petclinic.controller;

import com.petclinic.model.Pet;
import com.petclinic.service.PetService;
import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.di.annotation.Inject;
import com.thoughtworks.di.core.Lifecycle;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.core.Controller;
import com.thoughtworks.mvc.core.RequestAware;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component(lifecycle = Lifecycle.Transient)
@Path(url = "/pet")
public class PetController implements Controller, RequestAware {

    private Map<String, Object> modelMap = new HashMap<>();
    private HttpServletRequest request;

    @Inject
    private PetService service;

    @Path
    public String index() {
        modelMap.put("total", 5);
        return "pet/index";
    }

    @Path
    public String show() {
        Long id = Long.parseLong(request.getParameter("id"));
        modelMap.put("pet", service.get(id));
        return "pet/show";
    }

    @Path
    public String create(Pet pet) {
        Pet created = service.create(pet);
        modelMap.put("pet", created);
        return "pet/show";
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
