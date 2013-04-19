package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import com.thoughtworks.di.core.Injector;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestHandlerResolverTest {

    private Injector container;
    private RequestHandlerResolver resolver;

    @Before
    public void setUp() {
        container = Injector.create("com.example");
        resolver = new RequestHandlerResolver(container, "com.example");
    }

    @Test
    public void should_find_controller_by_collection_url() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/user");
        when(request.getMethod()).thenReturn("GET");

        RequestHandler handler = resolver.resolve(request);

        assertThat(handler.getController(), instanceOf(UserController.class));
    }

    //REST-ful url mapping
    @Test
    public void should_find_controller_by_member_url() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/user");
        when(request.getMethod()).thenReturn("GET");

        RequestHandler requestHandler = resolver.resolve(request);

        assertThat(requestHandler.getController(), instanceOf(UserController.class));
        assertThat(requestHandler.getAction(), equalTo("show"));
    }

    @Test
    public void should_dispatch_get_by_id_request_to_show_action() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/user/1");
        when(request.getMethod()).thenReturn("GET");


        RequestHandler requestHandler = resolver.resolve(request);

        assertThat(requestHandler.getController(), instanceOf(UserController.class));
        assertThat(requestHandler.getAction(), equalTo("show"));
    }

    @Test
    public void should_dispatch_new_request_to_new_action() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/user/new");
        when(request.getMethod()).thenReturn("GET");


        RequestHandler requestHandler = resolver.resolve(request);

        assertThat(requestHandler.getController(), instanceOf(UserController.class));
        assertThat(requestHandler.getAction(), equalTo("new"));
    }
}
