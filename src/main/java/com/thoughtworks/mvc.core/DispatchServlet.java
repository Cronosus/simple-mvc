package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatchServlet extends HttpServlet {

    private Injector controllerContainer;
    private Router router;
    private RequestHandlerResolver requestRequestHandlerResolver;


    @Override
    public void init(ServletConfig config) {

        String packageName = config.getInitParameter("module-name");
        if (null != packageName) {
            this.controllerContainer = Injector.create(packageName);
            this.router = Router.create(packageName);
            this.requestRequestHandlerResolver = new RequestHandlerResolver(controllerContainer, router);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestHandler requestHandler = requestRequestHandlerResolver.resolve(request);
        String viewName = requestHandler.handle();
    }


    public Injector getControllerContainer() {
        return controllerContainer;
    }

    public Router getRouter() {
        return router;
    }

    public RequestHandlerResolver getRequestRequestHandlerResolver() {
        return requestRequestHandlerResolver;
    }
}
