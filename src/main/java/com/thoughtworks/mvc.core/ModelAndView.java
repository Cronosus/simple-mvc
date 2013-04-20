package com.thoughtworks.mvc.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ModelAndView {
    private final Map<String, Object> model;
    private final FreeMarkerView view;

    public ModelAndView(FreeMarkerView view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }

    public String getViewName() {
        return view.getName();
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void render(PrintWriter writer) throws IOException {
        this.view.render(writer, this.model);
    }
}
