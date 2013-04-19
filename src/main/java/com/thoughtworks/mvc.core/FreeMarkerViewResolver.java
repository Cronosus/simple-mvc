package com.thoughtworks.mvc.core;

import com.thoughtworks.utils.Lang;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

import javax.servlet.ServletContext;
import java.io.IOException;

public class FreeMarkerViewResolver {
    private final Configuration configuration;

    private FreeMarkerViewResolver(ServletContext servletContext, String templatePath) throws IOException {
        this.configuration = configure(servletContext, templatePath);
    }

    public static FreeMarkerViewResolver create(ServletContext servletContext, String templatePath) {
        try {
            return new FreeMarkerViewResolver(servletContext, templatePath);
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

    public FreeMarkerView resolve(String name) {
        return new FreeMarkerView(configuration, name);
    }
}
