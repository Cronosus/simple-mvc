package com.thoughtworks.mvc.entity;

import com.thoughtworks.mvc.view.View;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ModelAndView {
    private final Map<String, Object> model;
    private final View view;

    public ModelAndView(View view, Map<String, Object> model) {
        this.view = view;
        this.model = model;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void render(PrintWriter writer) throws IOException {
        this.view.render(writer, this.model);
    }

    public View getView() {
        return view;
    }
}
