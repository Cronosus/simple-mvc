package com.thoughtworks.utils;

import com.example.controller.UserController;
import com.thoughtworks.mvc.annotations.Action;
import com.thoughtworks.mvc.annotations.Controller;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Lang {

    public static boolean isController(Class<?> clazz) {
        return withControllerAnnotation(clazz) && hasActionMethod(clazz);
    }

    private static boolean withControllerAnnotation(Class<?> clazz) {
        return clazz.isAnnotationPresent(Controller.class);
    }

    private static boolean hasActionMethod(Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Action.class))
                return true;
        }
        return false;
    }

    public static List<Method> actionMethods(Class<?> clazz) {
        List<Method> actionMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Action.class)) {
                actionMethods.add(method);
            }
        }
        return actionMethods;
    }

    public static RuntimeException makeThrow(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }

    public static Method methodFor(Class<UserController> clazz, String name) {
        try {
            return clazz.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
            throw makeThrow("can not find method %s for class %s", clazz.getCanonicalName(), name);
        }
    }
}
