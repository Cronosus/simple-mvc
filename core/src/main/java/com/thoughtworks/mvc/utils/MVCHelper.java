package com.thoughtworks.mvc.utils;

import com.thoughtworks.mvc.annotation.Path;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MVCHelper {

    public static boolean isController(Class<?> clazz) {
        return withControllerAnnotation(clazz) && hasActionMethod(clazz);
    }

    public static List<Method> actionMethods(Class<?> clazz) {
        List<Method> actionMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Path.class)) {
                actionMethods.add(method);
            }
        }
        return actionMethods;
    }

    private static boolean withControllerAnnotation(Class<?> clazz) {
        return clazz.isAnnotationPresent(Path.class);
    }

    private static boolean hasActionMethod(Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Path.class))
                return true;
        }
        return false;
    }

}
