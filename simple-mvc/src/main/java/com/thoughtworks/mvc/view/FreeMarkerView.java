package com.thoughtworks.mvc.view;

import com.thoughtworks.mvc.utils.MVCHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static com.thoughtworks.simpleframework.util.Lang.makeThrow;

public class FreeMarkerView implements View {

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
            throw makeThrow("render view error %s", e.getMessage());
        }
    }

    public String getName() {
        return name;
    }
}
