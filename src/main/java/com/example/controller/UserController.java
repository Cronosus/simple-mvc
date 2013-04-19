package com.example.controller;

import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.mvc.annotations.Action;
import com.thoughtworks.mvc.annotations.Controller;

@Component
@Controller(url = "/user")
public class UserController implements com.thoughtworks.mvc.core.Controller {

    @Action
    public String index() {
        return "user/index";
    }

    @Action
    public String show(String id) {
        return "show";
    }

    @Action(url = "create")
    public String createUser() {
        return "show";
    }
}
