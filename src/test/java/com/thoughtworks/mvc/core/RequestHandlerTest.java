package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import com.thoughtworks.utils.Lang;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class RequestHandlerTest {

    private Controller controller;
    private FreeMarkerViewResolver viewResolver;

    @Before
    public void setUp() {
        controller = new UserController();
        viewResolver = mock(FreeMarkerViewResolver.class);
    }

    @Test
    public void should_return_view_name() {
        Method index = Lang.methodFor(UserController.class, "index");
        RequestHandler handler = new RequestHandler(viewResolver, controller, index);
        ModelAndView modelAndView = handler.handle();
        assertThat(modelAndView.getViewName(), equalTo("user/index"));
    }

    @Test
    public void should_return_model() {
        Method index = Lang.methodFor(UserController.class, "index");
        RequestHandler handler = new RequestHandler(viewResolver, controller, index);
        ModelAndView modelAndView = handler.handle();
        assertThat((Integer) modelAndView.getModel().get("total"), equalTo(5));
    }
}
