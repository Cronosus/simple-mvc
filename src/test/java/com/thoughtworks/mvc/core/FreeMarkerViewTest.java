package com.thoughtworks.mvc.core;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import org.junit.Test;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class FreeMarkerViewTest {

    @Test
    public void should_render_content_of_template() throws Exception {

        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File("src/test/resources"));
        configuration.setObjectWrapper(new DefaultObjectWrapper());

        FreeMarkerView view = new FreeMarkerView(configuration, "hello-world", new HashMap<>());
        StringWriter writer = new StringWriter();
        view.render(writer);
        assertThat(writer.getBuffer().toString(), equalTo("hello world"));
    }
}
