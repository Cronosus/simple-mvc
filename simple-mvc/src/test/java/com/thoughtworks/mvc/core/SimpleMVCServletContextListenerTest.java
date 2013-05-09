package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.core.lisenter.SimpleMVCServletContextListener;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SimpleMVCServletContextListenerTest {

    private SimpleMVCServletContextListener listener;

    @Before
    public void before() {
        listener = new SimpleMVCServletContextListener();
    }

    @Test
    public void should_init_ioc_container_on_started() {

        ServletContext servletContext = mock(ServletContext.class);

        listener.contextInitialized(new ServletContextEvent(servletContext));

        verify(servletContext).setAttribute(eq("iocContainer"), anyObject());
    }

}
