package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;
import com.thoughtworks.utils.Lang;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatchServlet extends HttpServlet {

    private Injector controllerContainer;
    private RequestHandlerResolver requestRequestHandlerResolver;


    @Override
    public void init(ServletConfig config) {

        String packageName = config.getInitParameter("module-name");


        if (null == packageName) {
            throw Lang.makeThrow("module name can not be empty");
        }

        this.controllerContainer = Injector.create(packageName);
        this.requestRequestHandlerResolver = RequestHandlerResolver.create(controllerContainer, packageName, config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestHandler requestHandler = requestRequestHandlerResolver.resolve(request);
        ModelAndView modelAndView = requestHandler.handle();
        modelAndView.render(response.getWriter());
    }


    public Injector getControllerContainer() {
        return controllerContainer;
    }
}
