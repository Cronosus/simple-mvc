package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/")
public class DispatchServlet extends HttpServlet {

    private Injector controllerContainer;
    private Router router;


    @Override
    public void init(ServletConfig config) {

        String packageName = config.getInitParameter("module-name");

        this.controllerContainer = Injector.create(packageName);

        if (null != packageName) {
            this.router = Router.create(packageName);
        }
    }


    public Injector getControllerContainer() {
        return controllerContainer;
    }

    public Router getRouter() {
        return router;
    }
}
