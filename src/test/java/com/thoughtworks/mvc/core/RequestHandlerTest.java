package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class RequestHandlerTest {

    private Object controller;

    @Before
    public void setUp() {
        controller = new UserController();
    }

    @Test
    public void should_return_view_name() throws NoSuchMethodException {
        Method index = UserController.class.getDeclaredMethod("index");
        RequestHandler handler = new RequestHandler(controller, index);
        String viewName = handler.handle();
        assertThat(viewName, notNullValue());
    }
}
