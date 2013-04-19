package com.thoughtworks.mvc.core;

import com.thoughtworks.di.utils.ClassUtil;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.mvc.annotations.*;
import com.thoughtworks.utils.Lang;
import com.thoughtworks.mvc.annotations.Controller;
import com.thoughtworks.utils.StringUtils;

public class URLMapping {

    private final Map<String, ControllerMappingEntry> controllerMappingMap;

    private static final Pattern URL_PATTERN = Pattern.compile("/(\\w+)(/?.*)");

    public URLMapping() {
        controllerMappingMap = new HashMap<>();
    }

    public static URLMapping load(String packageName) {

        URLMapping mapping = new URLMapping();

        Collection<Class> allClasses = ClassUtil.getClassInfos(packageName);


        for (Class<?> clazz : allClasses) {
            if (Lang.isController(clazz)) {
                Controller controller = clazz.getAnnotation(Controller.class);
                for (Method method : Lang.actionMethods(clazz)) {
                    Action action = method.getAnnotation(Action.class);
                    mapping.add(clazz, method, action, controller);
                }
            }
        }

        return mapping;
    }

    public ActionInfo get(String url) {

        Matcher matcher = URL_PATTERN.matcher(url);
        if (!matcher.matches()) {
            Lang.makeThrow("encountered an invalid url %s, look up failed", url);
        }

        String controllerUri = matcher.group(1);
        String actionUri = matcher.group(2);
        ControllerMappingEntry entry = controllerMappingMap.get(controllerUri);

        if (null == entry) {
            throw Lang.makeThrow("Can't find class for url %s", url);
        }

        return entry.actionFor(actionUri);
    }


    private void add(Class<?> clazz, Method method, Action action, Controller controller) {
        addController(controller, clazz);

        ControllerMappingEntry controllerMappingEntry = this.moduleFor(controller.url());

        controllerMappingEntry.addAction(action, method);
    }

    private ControllerMappingEntry moduleFor(String controllerUrl) {
        return controllerMappingMap.get(StringUtils.stripLeadSlash(controllerUrl));
    }

    private void addController(Controller controller, Class<?> clazz) {
        String controllerUrl = StringUtils.stripLeadSlash(controller.url());
        if (controllerMappingMap.get(controllerUrl) == null) {
            controllerMappingMap.put(controllerUrl, new ControllerMappingEntry(clazz));
        }
    }

    private class ControllerMappingEntry {
        private final Map<String, Method> actionMappingMap = new HashMap<>();
        private final Class<?> controller;

        public ControllerMappingEntry(Class<?> controller) {
            this.controller = controller;
        }

        public void addAction(Action action, Method method) {
            String actionUrl = StringUtils.stripLeadSlash(action.url());
            if (actionUrl.isEmpty()) {
                actionUrl = method.getName();
            }
            actionMappingMap.put(actionUrl, method);
        }

        public ActionInfo actionFor(String actionUrl) {
            String url = StringUtils.stripLeadSlash(actionUrl);
            return new ActionInfo(controller, actionMappingMap.get(url));
        }
    }
}
