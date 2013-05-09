package com.thoughtworks.mvc.core.lisenter;

import com.thoughtworks.simpleframework.di.core.Injector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SimpleMVCServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("iocContainer", createIocContainer());
    }

    private Injector createIocContainer() {
        return Injector.create("abc");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
