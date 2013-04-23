package com.thoughtworks.utils;

import com.thoughtworks.mvc.annotation.Path;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Lang {

    public static boolean isController(Class<?> clazz) {
        return withControllerAnnotation(clazz) && hasActionMethod(clazz);
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

    public static List<Method> actionMethods(Class<?> clazz) {
        List<Method> actionMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Path.class)) {
                actionMethods.add(method);
            }
        }
        return actionMethods;
    }

    public static RuntimeException makeThrow(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }

    public static Method methodFor(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            throw makeThrow("can not find method %s for class %s", clazz.getCanonicalName(), name);
        }
    }

    public static String stackTrace(Throwable e) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            buffer.append(element.toString()).append("\n");
        }
        return buffer.toString();
    }
}
