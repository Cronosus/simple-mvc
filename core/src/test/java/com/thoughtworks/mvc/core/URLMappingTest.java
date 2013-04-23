package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class URLMappingTest {

    private URLMapping mapping;

    @Before
    public void setUp() {
        mapping = URLMapping.load("com.example", "/sample");
    }

    @Test
    public void should_map_url_action() throws NoSuchMethodException {

        ActionInfo actionInfo = mapping.get("/sample/user/show");

        Method expectedMethod = UserController.class.getDeclaredMethod("show", new Class[]{String.class});

        assertThat((Class<UserController>) actionInfo.getControllerClass(), equalTo(UserController.class));
        assertThat(actionInfo.getMethod(), equalTo(expectedMethod));
    }

    @Test
    public void should_map_to_action_with_customized_url() throws NoSuchMethodException {
        ActionInfo actionInfo = mapping.get("/sample/user/create");

        Method expectedMethod = UserController.class.getDeclaredMethod("createUser");

        assertThat((Class<UserController>) actionInfo.getControllerClass(), equalTo(UserController.class));
        assertThat(actionInfo.getMethod(), equalTo(expectedMethod));
    }

}
