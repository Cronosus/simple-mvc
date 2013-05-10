package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import com.example.model.Pet;
import com.example.model.User;
import com.thoughtworks.simpleframework.di.core.Injector;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestHandlerResolverTest {

    private Injector container;
    private RequestHandlerResolver resolver;

    @Before
    public void setUp() {
        container = Injector.create("com.example");

        String templatePath = this.getClass().getResource("/").getPath();

        resolver = RequestHandlerResolver.create(container, "com.example", "/sample", templatePath);
    }

    @Test
    public void should_find_controller_by_collection_url() throws NoSuchFieldException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/sample/user/index");
        when(request.getMethod()).thenReturn("GET");

        RequestHandler handler = resolver.resolve(request);

        assertThat(handler.getController(), instanceOf(UserController.class));
        assertThat(handler.getActionName(), equalTo("index"));
    }

    //REST-ful url mapping
    @Test
    public void should_find_controller_by_member_url() throws NoSuchFieldException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/sample/user/show");
        when(request.getMethod()).thenReturn("GET");

        RequestHandler requestHandler = resolver.resolve(request);

        assertThat(requestHandler.getController(), instanceOf(UserController.class));
        assertThat(requestHandler.getActionName(), equalTo("show"));
    }

    @Test
    public void should_dispatch_get_by_id_request_to_show_action() throws ServletException, IOException, NoSuchFieldException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/sample/user/show");
        when(request.getMethod()).thenReturn("GET");


        RequestHandler requestHandler = resolver.resolve(request);

        assertThat(requestHandler.getController(), instanceOf(UserController.class));
        assertThat(requestHandler.getActionName(), equalTo("show"));

    }

    @Test
    public void should_dispatch_new_request_to_new_action() throws ServletException, IOException, NoSuchFieldException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/sample/user/new");
        when(request.getMethod()).thenReturn("GET");


        RequestHandler requestHandler = resolver.resolve(request);

        assertThat(requestHandler.getController(), instanceOf(UserController.class));
        assertThat(requestHandler.getActionName(), equalTo("fresh"));
    }

    @Test
    public void should_inject_required_service_to_controller() throws NoSuchFieldException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/sample/user/new");
        when(request.getMethod()).thenReturn("GET");

        RequestHandler requestHandler = resolver.resolve(request);
        assertThat(((UserController) requestHandler.getController()).getService(), notNullValue());
    }


    @Test
    public void should_inject_simple_param_on_handling_request() throws NoSuchFieldException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/sample/user/show");
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("id")).thenReturn("1");

        RequestHandler requestHandler = resolver.resolve(request);
        assertThat((Long) requestHandler.getParam(), equalTo(1L));
    }

    @Test
    public void should_inject_object_param_on_handling_request() throws NoSuchFieldException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/sample/user/create");
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("user.name")).thenReturn("Doudou");
        when(request.getParameter("user.age")).thenReturn("18");

        RequestHandler requestHandler = resolver.resolve(request);
        User user = (User) requestHandler.getParam();

        assertThat(user, Matchers.notNullValue());
        assertThat(user.getName(), equalTo("Doudou"));
        assertThat(user.getAge(), equalTo(18));
    }

    @Test
    public void should_inject_nested_param_on_handling_request() throws NoSuchFieldException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/sample/user/create");
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("user.name")).thenReturn("Doudou");
        when(request.getParameter("user.age")).thenReturn("18");
        when(request.getParameter("user.pet.name")).thenReturn("Doudou Jr");
        when(request.getParameter("user.pet.category")).thenReturn("Dog");

        RequestHandler requestHandler = resolver.resolve(request);
        User user = (User) requestHandler.getParam();

        assertThat(user, Matchers.notNullValue());
        assertThat(user.getName(), equalTo("Doudou"));
        assertThat(user.getAge(), equalTo(18));
        assertThat(user.getPet().getName(), equalTo("Doudou Jr"));
        assertThat(user.getPet().getCategory(), equalTo("Dog"));
    }

    @Test
    public void should_inject_list_field_on_handling_request() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/sample/user/create");
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("user.name")).thenReturn("Doudou");
        when(request.getParameter("user.age")).thenReturn("18");
        when(request.getParameter("user.pets[].name")).thenReturn("Doudou Jr");
        when(request.getParameter("user.pets[].category")).thenReturn("Dog");

        RequestHandler requestHandler = resolver.resolve(request);
        User user = (User) requestHandler.getParam();
        Pet pet = user.getPets().get(0);

        assertThat(user, Matchers.notNullValue());
        assertThat(user.getName(), equalTo("Doudou"));
        assertThat(user.getAge(), equalTo(18));
        assertThat(pet.getName(), equalTo("Doudou Jr"));
        assertThat(pet.getCategory(), equalTo("Dog"));
    }


}
