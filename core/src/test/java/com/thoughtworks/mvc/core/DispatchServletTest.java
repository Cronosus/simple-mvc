package com.thoughtworks.mvc.core;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DispatchServletTest {

    private DispatchServlet servlet;

    @Before
    public void setUp() throws ServletException {
        servlet = new DispatchServlet();
        ServletConfig config = mock(ServletConfig.class);
        ServletContext servletContext = mock(ServletContext.class);

        when(servletContext.getContextPath()).thenReturn("/sample");
        when(servletContext.getRealPath(anyString())).thenReturn("./src/test/resources");


        when(config.getInitParameter("module-name")).thenReturn("com.example");
        when(config.getInitParameter("template-path")).thenReturn("src/test/resources");
        when(config.getServletContext()).thenReturn(servletContext);

        servlet.init(config);
    }

    @Test
    public void should_create_a_injector_after_initialized() throws ServletException {
        assertThat(servlet.getControllerContainer(), notNullValue());
    }

}
