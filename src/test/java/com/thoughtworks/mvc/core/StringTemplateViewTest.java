package com.thoughtworks.mvc.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class StringTemplateViewTest {

    @Test
    public void should_render_content_of_template() {
        StringTemplateView view = new StringTemplateView("hello-world");
        assertThat(view.render(), equalTo("hello, world"));
    }
}
