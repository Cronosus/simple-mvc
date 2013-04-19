package com.thoughtworks.mvc.core;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DispatchServletTest {

    private DispatchServlet servlet;

    @Before
    public void setUp() throws ServletException {
        servlet = new DispatchServlet();
        ServletConfig config = mock(ServletConfig.class);
        when(config.getInitParameter("module-name")).thenReturn("com.example");
        when(config.getInitParameter("template-path")).thenReturn("src/test/resources");
        servlet.init(config);
    }

    @Test
    public void should_create_a_injector_after_initialized() throws ServletException {
        assertThat(servlet.getControllerContainer(), notNullValue());
    }

    @Test
    public void should_create_view_resolver_after_initialized() throws ServletException{
        assertThat(servlet.getViewResolver(), notNullValue());
    }
}
