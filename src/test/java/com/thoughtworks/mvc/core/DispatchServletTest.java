package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import com.example.model.User;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
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
        assertThat(servlet.getContainer(), notNullValue());
    }

    @Test
    public void should_get_route_info_on_initialization() throws ServletException, IOException {
        Router router = servlet.getRouter();
        assertThat(router.classFor("/user"), equalTo(UserController.class));
    }

    @Test
    public void should_create_controller_from_injector(){

    }

    //RESTful url mapping
    @Test
    public void should_dispatch_get_by_id_request_to_show_action() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getRequestURI()).thenReturn("/user/1");
        when(request.getMethod()).thenReturn("GET");

        servlet.service(request, response);

    }
}
