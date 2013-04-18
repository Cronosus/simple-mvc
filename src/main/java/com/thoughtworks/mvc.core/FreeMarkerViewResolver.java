package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.exceptions.ViewResovlerCreationException;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

import java.io.File;
import java.io.IOException;

public class FreeMarkerViewResolver {
    private final Configuration configure;

    private FreeMarkerViewResolver(String templatePath) throws IOException {
        this.configure = configure(templatePath);
    }

    public static FreeMarkerViewResolver create(String templatePath) {
        try {
            return new FreeMarkerViewResolver(templatePath);
        } catch (IOException e) {
            throw new ViewResovlerCreationException("create view resolver failed");
        }
    }

    private Configuration configure(String templatePath) throws IOException {
        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        return configure;
    }

    public FreeMarkerView resolve(String name) {
        return new FreeMarkerView(configure, name);
    }
}
