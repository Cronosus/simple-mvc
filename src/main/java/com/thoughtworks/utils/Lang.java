package com.thoughtworks.utils;

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

    private static boolean isAvailableModuleClass(Class<?> clazz) {
        int classModify = clazz.getModifiers();
        if (!Modifier.isPublic(classModify)
                || Modifier.isAbstract(classModify)
                || Modifier.isInterface(classModify))
            return false;
        return true;
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

    /**
     * 根据格式化字符串，生成运行时异常
     *
     * @param format 格式
     * @param args   参数
     * @return 运行时异常
     */
    public static RuntimeException makeThrow(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }
}
