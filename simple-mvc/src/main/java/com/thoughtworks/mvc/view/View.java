package com.thoughtworks.mvc.view;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public interface View {
    public void render(Writer writer, Map<String, Object> model) throws IOException;

    public String getName();
}
