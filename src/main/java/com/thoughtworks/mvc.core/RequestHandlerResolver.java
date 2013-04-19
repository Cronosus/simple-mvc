package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;
import com.thoughtworks.di.utils.ClassUtil;
import com.thoughtworks.mvc.annotations.Action;
import com.thoughtworks.mvc.annotations.Controller;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RequestHandlerResolver {

    private static final Pattern COLLECTION_URL_PATTERN = Pattern.compile("(/\\w+)");
    private static final Pattern NEW_URL_PATTERN = Pattern.compile("(/\\w+)/new");
    private static final Pattern EDIT_URL_PATTERN = Pattern.compile("(/\\w+)/(\\d+)/edit");
    private static final Pattern MEMBER_URL_PATTERN = Pattern.compile("(/\\w+)/(\\d+)");

    private final Injector container;
    private final String packageName;

    public RequestHandlerResolver(Injector container, String packageName) {
        this.container = container;
        this.packageName = packageName;
    }

    public RequestHandler resolve(HttpServletRequest request) {
        request.getRequestURI();
        request.getMethod();
        request.getParameterNames();

        return null;
    }

    private Map<String, Class<? extends com.thoughtworks.mvc.core.Controller>> extractRouting(String packageName) {
        Map<String, Class<? extends com.thoughtworks.mvc.core.Controller>> routes = new HashMap<>();

        Collection<Class> allClasses = ClassUtil.getClassInfos(packageName);
        for (Class<?> clazz : allClasses) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                String url = clazz.getAnnotation(Controller.class).url();

                for(Method method : Arrays.asList(clazz.getDeclaredMethods())){
                    if (method.isAnnotationPresent(Action.class)){

                    };
                }
            }
        }
        return routes;
    }


}
