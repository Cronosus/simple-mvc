package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class DispatchServlet extends HttpServlet {

    private Injector controllerContainer;
    private Router router;
    private RequestHandlerResolver requestRequestHandlerResolver;
    private FreeMarkerViewResolver viewResolver;


    @Override
    public void init(ServletConfig config) throws ServletException {

        String packageName = config.getInitParameter("module-name");
        if (null == packageName) {
            throw new ServletException("module name can not be empty");
        }

        this.controllerContainer = Injector.create(packageName);
        this.router = Router.create(packageName);
        this.requestRequestHandlerResolver = new RequestHandlerResolver(controllerContainer, router);

        String templatePath = config.getInitParameter("template-path");
        if (null == templatePath) {
            throw new ServletException("module name can not be empty");
        }

        this.viewResolver = FreeMarkerViewResolver.create(templatePath);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestHandler requestHandler = requestRequestHandlerResolver.resolve(request);
        String viewName = requestHandler.handle();
        FreeMarkerView view = viewResolver.resolve(viewName);
        view.render(response.getWriter(), new HashMap());
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
