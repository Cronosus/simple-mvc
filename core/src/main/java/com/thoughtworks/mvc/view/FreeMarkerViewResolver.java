package com.thoughtworks.mvc.view;

import com.thoughtworks.mvc.utils.MVCHelper;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

import java.io.File;
import java.io.IOException;

import static com.thoughtworks.simpleframework.util.Lang.makeThrow;
import static com.thoughtworks.simpleframework.util.Lang.stackTrace;

public class FreeMarkerViewResolver implements ViewResolver {
    private Configuration configuration;

    public FreeMarkerViewResolver(String templatePath) {
        this.configuration = configure(templatePath);
    }

    private Configuration configure(String templatePath) {
        Configuration configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
        } catch (IOException e) {
            throw makeThrow("configure FreeMarker failed, %s", stackTrace(e));
        }
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        return configuration;
    }

    public static ViewResolver create(String templatePath) {
        return new FreeMarkerViewResolver(templatePath);
    }

    @Override
    public View resolve(String name) {
        return new FreeMarkerView(configuration, name);
    }


}
