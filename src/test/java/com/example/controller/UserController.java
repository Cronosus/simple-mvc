package com.example.controller;

import com.thoughtworks.di.annotation.Component;
import com.thoughtworks.mvc.annotations.Path;
import com.thoughtworks.mvc.core.Controller;

@Path(url = "/user")
@Component
public class UserController implements Controller{
}
