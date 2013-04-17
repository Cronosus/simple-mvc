package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DispatchServletTest {

    private DispatchServlet servlet;

    @Before
    public void setUp() {
        servlet = new DispatchServlet();
        ServletConfig config = mock(ServletConfig.class);
        when(config.getInitParameter("module-name")).thenReturn("com.example");
        servlet.init(config);
    }

    @Test
    public void should_create_a_injector_after_initialized() throws ServletException {
        assertThat(servlet.getControllerContainer(), notNullValue());
    }

    @Test
    public void should_get_route_info_on_initialization() throws ServletException, IOException {
        Router router = servlet.getRouter();
        assertThat(router.classFor("/user"), equalTo(UserController.class));
    }

    @Test
    public void should_create_request_resolver(){
        assertThat(servlet.getRequestRequestHandlerResolver(), notNullValue());
    }
}
