package com.thoughtworks.mvc.core;

import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FreeMarkerViewResolverTest {

    @Test
    public void should_resolve_view_from_directory(){

        String templatePath = "src/test/resources";
        FreeMarkerViewResolver resolver = FreeMarkerViewResolver.create(templatePath);

        View view = resolver.resolve("hello-world");
        assertThat(view.getName(), is("hello-world"));

    }
}
