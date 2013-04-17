package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import com.thoughtworks.di.core.Injector;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerResolverTest {

    private Injector container;
    private Router router;

    @Before
    public void setUp() {
        container = Injector.create("com.example");
        router = Router.create("com.example");
    }

    @Test
    public void should_find_controller_by_plain_url() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/user");
        when(request.getMethod()).thenReturn("GET");


        ControllerResolver resolver = new ControllerResolver(container, router);

        Controller controller = resolver.resolve(request);

        assertThat(controller, instanceOf(UserController.class));
    }

    @Test
    public void should_find_controller_by_url() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/user/1");
        when(request.getMethod()).thenReturn("GET");

        ControllerResolver resolver = new ControllerResolver(container, router);
        Controller controller = resolver.resolve(request);

        assertThat(controller, instanceOf(UserController.class));
    }

    //RESTful url mapping
    @Test
    public void should_dispatch_get_by_id_request_to_show_action() throws ServletException, IOException {

    }
}
