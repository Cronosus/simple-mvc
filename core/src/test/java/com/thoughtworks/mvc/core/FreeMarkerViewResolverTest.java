package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.view.FreeMarkerViewResolver;
import com.thoughtworks.mvc.view.View;
import com.thoughtworks.mvc.view.ViewResolver;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class FreeMarkerViewResolverTest {

    @Test
    public void should_resolve_view_from_directory(){

        String templatePath = "src/test/resources";
        ViewResolver resolver = FreeMarkerViewResolver.create(templatePath);

        View view = resolver.resolve("hello-world");
        assertThat(view.getName(), is("hello-world"));

    }
}
