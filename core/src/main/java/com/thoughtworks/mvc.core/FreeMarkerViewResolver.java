package com.thoughtworks.mvc.core;

import com.thoughtworks.utils.Lang;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

public class FreeMarkerViewResolver {
    private Configuration configuration;

    public FreeMarkerViewResolver(String templatePath) {
        this.configuration = configure(templatePath);
    }

    private Configuration configure(String templatePath) {
        Configuration configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
        } catch (IOException e) {
            throw Lang.makeThrow("configure FreeMarker failed, %s", Lang.stackTrace(e));
        }
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        return configuration;
    }

    public static FreeMarkerViewResolver create(String templatePath) {
        return new FreeMarkerViewResolver(templatePath);
    }

    public View resolve(String name) {
        return new FreeMarkerView(configuration, name);
    }


}
