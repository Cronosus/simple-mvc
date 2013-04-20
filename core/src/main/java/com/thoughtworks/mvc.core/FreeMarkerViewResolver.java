package com.thoughtworks.mvc.core;

import com.thoughtworks.utils.Lang;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.IOException;

public class FreeMarkerViewResolver {
    private final Configuration configuration;

    private FreeMarkerViewResolver(ServletConfig servletConfig) throws IOException {

        String templatePath = servletConfig.getInitParameter("template-path");

        if (null == servletConfig) {
            throw Lang.makeThrow("template path can not be empty");
        }

        this.configuration = configure(servletConfig.getServletContext(), templatePath);
    }

    public static FreeMarkerViewResolver create(ServletConfig servletConfig) {
        try {
            return new FreeMarkerViewResolver(servletConfig);
        } catch (IOException e) {
            e.printStackTrace();
            throw Lang.makeThrow("create view resolver failed, %s", e.getMessage());
        }
    }

    private Configuration configure(ServletContext servletContext, String templatePath) throws IOException {
        Configuration configuration = new Configuration();
        configuration.setServletContextForTemplateLoading(servletContext, templatePath);
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        return configuration;
    }

    public View resolve(String name) {
        return new FreeMarkerView(configuration, name);
    }
}
