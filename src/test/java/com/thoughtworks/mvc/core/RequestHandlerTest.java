package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class RequestHandlerTest {

    private Controller controller;

    @Before
    public void setUp() {
        controller = new UserController();
    }

    @Test
    public void should_return_view_name() {
        RequestHandler handler = new RequestHandler(controller, "show");
        String viewName = handler.handle();
        assertThat(viewName, notNullValue());
    }

    @Test
    public void should_set_model_in_controller() {
        RequestHandler handler = new RequestHandler(controller, "show");
        handler.handle();
        assertThat(controller.getModelMap(), notNullValue());
    }
}
