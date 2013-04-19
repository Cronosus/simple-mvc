package com.thoughtworks.mvc.core;

import org.junit.Test;

import javax.servlet.ServletContext;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class FreeMarkerViewResolverTest {

    @Test
    public void should_resolve_view_from_template_path() throws IOException {
        ServletContext servletContext = mock(ServletContext.class);
        FreeMarkerViewResolver resolver = FreeMarkerViewResolver.create(servletContext, "src/test/resources");
        FreeMarkerView view = resolver.resolve("hello-world");
        assertThat(view.getName(), is("hello-world"));
    }
}
