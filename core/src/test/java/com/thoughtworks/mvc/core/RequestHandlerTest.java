package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.entity.ModelAndView;
import com.thoughtworks.mvc.view.FreeMarkerViewResolver;
import com.thoughtworks.mvc.view.View;
import com.thoughtworks.mvc.view.ViewResolver;
import com.thoughtworks.utils.Lang;
import com.example.controller.UserController;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestHandlerTest {

    private Controller controller;
    private ViewResolver viewResolver;
    private View expectedView;

    @Before
    public void setUp() throws InvocationTargetException, IllegalAccessException {
        controller = new UserController();
        viewResolver = mock(FreeMarkerViewResolver.class);
        expectedView = mock(View.class);
        when(viewResolver.resolve(anyString())).thenReturn(expectedView);
    }

    @Test
    public void should_return_view() {
        Method method = Lang.methodFor(UserController.class, "index");
        RequestHandler handler = new RequestHandler(viewResolver, controller, method, null);
        ModelAndView modelAndView = handler.handle();
        assertThat(modelAndView.getView(), is(expectedView));
    }

    @Test
    public void should_return_model() {
        Method method = Lang.methodFor(UserController.class, "index");
        RequestHandler handler = new RequestHandler(viewResolver, controller, method, null);

        ModelAndView modelAndView = handler.handle();
        assertThat((Integer) modelAndView.getModel().get("total"), is(5));
    }
}
