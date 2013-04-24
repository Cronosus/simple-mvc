package com.thoughtworks.mvc.core;

import com.thoughtworks.di.utils.ClassUtil;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.utils.Lang;
import com.thoughtworks.utils.StringUtils;

import javax.servlet.ServletContext;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLMapping {

    private final Map<String, ControllerMappingEntry> controllerMappingMap;

    private Pattern urlPattern;

    public URLMapping(String contextPath) {
        urlPattern = Pattern.compile(contextPath + "/(\\w+)(/?.*)");
        controllerMappingMap = new HashMap<>();
    }

    public static URLMapping load(String packageName, String contextPath) {

        URLMapping mapping = new URLMapping(contextPath);

        Collection<Class> allClasses = ClassUtil.getClassInfos(packageName);

        for (Class<?> clazz : allClasses) {
            if (Lang.isController(clazz)) {
                Path modulePath = clazz.getAnnotation(Path.class);
                for (Method method : Lang.actionMethods(clazz)) {
                    Path actionPath = method.getAnnotation(Path.class);
                    mapping.add(clazz, method, actionPath, modulePath);
                }
            }
        }
        return mapping;
    }

    public ActionInfo get(String url) {
        Matcher matcher = urlPattern.matcher(url);
        if (!matcher.matches()) {
            Lang.makeThrow("encountered an invalid url %s, look up failed", url);
        }

        String controllerUri = matcher.group(1);
        String actionUri = matcher.group(2);
        ControllerMappingEntry entry = controllerMappingMap.get(controllerUri);
        if (null == entry) {
            throw Lang.makeThrow("Can't find class for controller url: %s", controllerUri);
        }

        return entry.actionFor(actionUri);
    }


    private void add(Class<?> clazz, Method method, Path action, Path controller) {
        addController(controller, clazz);

        ControllerMappingEntry controllerMappingEntry = this.moduleFor(controller.url());

        controllerMappingEntry.addAction(action, method);
    }

    private ControllerMappingEntry moduleFor(String controllerUrl) {
        return controllerMappingMap.get(StringUtils.stripLeadSlash(controllerUrl));
    }

    private void addController(Path controller, Class<?> clazz) {
        String controllerUrl = StringUtils.stripLeadSlash(controller.url());
        if (controllerMappingMap.get(controllerUrl) == null) {
            controllerMappingMap.put(controllerUrl, new ControllerMappingEntry(clazz));
        }
    }

    private class ControllerMappingEntry {
        private final Map<String, Method> actionMappingMap = new HashMap<>();
        private final Class<?> controller;
        private Map<String, Class<?>> paramMappingMap = new HashMap<>();

        public ControllerMappingEntry(Class<?> controller) {
            this.controller = controller;
        }

        private void addAction(Path action, Method method) {
            String actionUrl = StringUtils.stripLeadSlash(action.url());
            if (actionUrl.isEmpty()) {
                actionUrl = method.getName();
            }
            actionMappingMap.put(actionUrl, method);
            paramMappingMap.put(actionUrl, getRequiredParam(method));
        }

        private Class<?> getRequiredParam(Method method) {
            Class<?> [] paramTypes = method.getParameterTypes();
            Class<?> requiredParam = null;
            if(paramTypes.length > 0){
                requiredParam = paramTypes[0];
            }
            return requiredParam;
        }

        private ActionInfo actionFor(String actionUrl) {
            String url = StringUtils.stripLeadSlash(actionUrl);
            return new ActionInfo(controller, actionMappingMap.get(url), paramMappingMap.get(url));
        }
    }
}
