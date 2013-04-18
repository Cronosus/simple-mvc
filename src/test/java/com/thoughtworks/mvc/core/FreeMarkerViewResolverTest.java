package com.thoughtworks.mvc.core;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FreeMarkerViewResolverTest {

    @Test
    public void should_resolve_view_from_template_path() throws IOException {
        FreeMarkerViewResolver resolver = FreeMarkerViewResolver.create("src/test/resources");
        FreeMarkerView view = resolver.resolve("hello-world");
        assertThat(view.getName(), is("hello-world"));
    }
}
