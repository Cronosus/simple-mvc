package com.thoughtworks.mvc.core;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerView {

    public static final String SUFFIX = ".ftl";
    private final String name;
    private final Configuration configuration;
    private final Map<String, Object> root;

    public FreeMarkerView(Configuration configuration, String name, HashMap<String, Object> root) throws IOException {
        this.configuration = configuration;
        this.name = name;
        this.root = root;
    }

    public void render(Writer writer) throws IOException, TemplateException {
        Template template = configuration.getTemplate(name.concat(SUFFIX));
        template.process(root, writer);
    }
}
