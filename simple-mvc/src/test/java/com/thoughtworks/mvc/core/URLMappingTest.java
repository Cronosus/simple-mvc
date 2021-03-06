package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import com.example.model.User;
import com.thoughtworks.mvc.entity.ActionInfo;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class URLMappingTest {

    private URLMapping mapping;

    @Before
    public void setUp() {
        mapping = URLMapping.load("com.example", "/sample");
    }

    @Test
    public void should_map_url_action() throws NoSuchMethodException {
        ActionInfo actionInfo = mapping.get("/sample/user/show");

        assertThat((Class<UserController>) actionInfo.getControllerClass(), equalTo(UserController.class));
        assertThat(actionInfo.getMethod().getName(), equalTo("show"));
    }

    @Test
    public void should_map_to_action_with_customized_url() throws NoSuchMethodException {
        ActionInfo actionInfo = mapping.get("/sample/user/create");

        assertThat((Class<UserController>) actionInfo.getControllerClass(), equalTo(UserController.class));
        assertThat(actionInfo.getMethod().getName(), equalTo("create"));
    }

    @Test
    public void should_extract_required_simple_param() {
        ActionInfo actionInfo = mapping.get("/sample/user/show");
        assertThat((Class<Long>) actionInfo.getRequiredParam().getType(), equalTo(Long.class));
        assertThat(actionInfo.getRequiredParam().getName(), equalTo("id"));
    }

    @Test
    public void should_extract_required_object_param() {
        ActionInfo actionInfo = mapping.get("/sample/user/create");
        assertThat((Class<User>) actionInfo.getRequiredParam().getType(), equalTo(User.class));
        assertThat(actionInfo.getRequiredParam().getName(), equalTo("user"));
    }

}
