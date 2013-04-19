package com.thoughtworks.mvc.core;

import com.example.controller.UserController;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class RouterTest {

    @Test
    public void should_build_router_from_given_package_name() {
        Router router = Router.create("com.example");
        assertThat(router.classFor("/user"), equalTo(UserController.class));
    }


}
