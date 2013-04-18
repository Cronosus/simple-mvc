package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.exceptions.RenderViewException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FreeMarkerView {

    public static final String SUFFIX = ".ftl";
    private final String name;
    private final Configuration configuration;

    public FreeMarkerView(Configuration configuration, String name) {
        this.configuration = configuration;
        this.name = name;
    }

    public void render(Writer writer, Map<String, Object> model) throws IOException {
        Template template = configuration.getTemplate(name.concat(SUFFIX));
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            throw new RenderViewException(e);
        }
    }

    public String getName() {
        return name;
    }
}
