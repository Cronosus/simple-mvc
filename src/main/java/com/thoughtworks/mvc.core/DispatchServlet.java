package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;
import com.thoughtworks.utils.Lang;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
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
    public void init(ServletConfig config) {

        String packageName = config.getInitParameter("module-name");
        if (null == packageName) {
            throw Lang.makeThrow("module name can not be empty");
        }

        String templatePath = config.getInitParameter("template-path");
        if (null == templatePath) {
            throw Lang.makeThrow("template path can not be empty");
        }

        this.controllerContainer = Injector.create(packageName);
        this.router = Router.create(packageName);
        this.requestRequestHandlerResolver = new RequestHandlerResolver(controllerContainer, packageName, config.getServletContext());


        this.viewResolver = FreeMarkerViewResolver.create(config.getServletContext(), templatePath);
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

    public RequestHandlerResolver getRequestRequestHandlerResolver() {
        return requestRequestHandlerResolver;
    }

    public FreeMarkerViewResolver getViewResolver() {
        return viewResolver;
    }
}
