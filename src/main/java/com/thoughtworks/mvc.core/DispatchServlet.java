package com.thoughtworks.mvc.core;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.di.core.Configuration;
import com.thoughtworks.di.core.Injector;
import com.thoughtworks.mvc.annotations.Path;
import com.thoughtworks.mvc.exceptions.RouterErrorException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class DispatchServlet extends HttpServlet {

    private Injector container;
    private Router router;

    @Override
    public void init(ServletConfig config) {

        String packageName = config.getInitParameter("module-name");

        this.container = Injector.create(new Configuration() {
            @Override
            protected void configure() {

            }
        });

        if (null != packageName) {
            this.router = new RouterBuilder(packageName).build();
        }
    }


    public Injector getContainer() {
        return container;
    }

    public Router getRouter() {
        return router;
    }
}
