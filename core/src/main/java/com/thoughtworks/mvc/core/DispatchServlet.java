package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;
import com.thoughtworks.mvc.entity.ModelAndView;
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

        String controllerPackageName = config.getInitParameter("controller-module");
        String servicePackageName = config.getInitParameter("service-module");
        String templatePath = config.getInitParameter("template-path");


        if (null == controllerPackageName || null == servicePackageName) {
            throw Lang.makeThrow("module name can not be empty");
        }

        if (null == templatePath) {
            throw Lang.makeThrow("template can not be empty");
        }

        String realTemplatePath = config.getServletContext().getRealPath(templatePath);
        Injector serviceInjector = Injector.create(servicePackageName);

        this.controllerContainer = Injector.create(controllerPackageName, serviceInjector);
        this.requestRequestHandlerResolver = RequestHandlerResolver.create(controllerContainer, controllerPackageName, config.getServletContext().getContextPath(), realTemplatePath);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestHandler requestHandler = requestRequestHandlerResolver.resolve(request);
        ModelAndView modelAndView = requestHandler.handle();
        modelAndView.render(response.getWriter());
    }


    public Injector getControllerContainer() {
        return controllerContainer;
    }
}
