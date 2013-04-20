package com.thoughtworks.mvc.core;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class FreeMarkerViewTest {

    private Configuration configuration;
    private StringWriter writer;

    @Before
    public void setUp() throws IOException {
        configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File("core/src/test/resources"));
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        writer = new StringWriter();
    }

    @Test
    public void should_render_content_of_template() throws Exception {
        FreeMarkerView view = new FreeMarkerView(configuration, "hello-world");
        view.render(writer, new HashMap<String, Object>());
        assertThat(getActual(), equalTo("hello world"));
    }

    @Test
    public void should_render_template_with_supplied_params() throws IOException {
        FreeMarkerView view = new FreeMarkerView(configuration, "with-variable");
        Map<String, Object> root = new HashMap<>();
        root.put("v", 5);

        view.render(writer, root);
        assertThat(getActual(), equalTo("the variable is 5"));
    }


    @Test
    public void should_render_template_with_collection_variable() throws IOException {
        FreeMarkerView view = new FreeMarkerView(configuration, "with-collection-variable");
        Map<String, Object> root = new HashMap<>();

        root.put("vs", Arrays.asList(1, 2, 3, 4, 5));

        view.render(writer, root);
        assertThat(getActual(), equalTo("the variable is: 1 2 3 4 5 "));
    }

    private String getActual() {
        return writer.getBuffer().toString();
    }

}
