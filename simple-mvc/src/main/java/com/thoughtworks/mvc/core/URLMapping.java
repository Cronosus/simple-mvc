package com.thoughtworks.mvc.core;

import com.thoughtworks.mvc.annotation.Param;
import com.thoughtworks.mvc.annotation.Path;
import com.thoughtworks.mvc.entity.ActionInfo;
import com.thoughtworks.mvc.entity.RequiredParam;
import com.thoughtworks.simpleframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.thoughtworks.mvc.utils.MVCHelper.actionMethods;
import static com.thoughtworks.mvc.utils.MVCHelper.isController;
import static com.thoughtworks.simpleframework.util.Lang.getClassInfos;
import static com.thoughtworks.simpleframework.util.Lang.makeThrow;

public class URLMapping {

    private final Map<String, ControllerMappingEntry> controllerMappingMap;

    private Pattern urlPattern;

    public URLMapping(String contextPath) {
        urlPattern = Pattern.compile(contextPath + "/(\\w+)(/?.*)");
        controllerMappingMap = new HashMap<>();
    }

    public static URLMapping load(String packageName, String contextPath) {

        URLMapping mapping = new URLMapping(contextPath);

        Collection<Class> allClasses = getClassInfos(packageName);

        for (Class<?> clazz : allClasses) {
            if (isController(clazz)) {
                Path modulePath = clazz.getAnnotation(Path.class);
                for (Method method : actionMethods(clazz)) {
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
            makeThrow("encountered an invalid url %s, look up failed", url);
        }

        String controllerUri = matcher.group(1);
        String actionUri = matcher.group(2);
        ControllerMappingEntry entry = controllerMappingMap.get(controllerUri);
        if (null == entry) {
            throw makeThrow("Can't find class for controller url: %s", controllerUri);
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
        private Map<String, RequiredParam> paramMappingMap = new HashMap<>();

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

        private RequiredParam getRequiredParam(Method method) {

            Annotation[][] paramAnnotations = method.getParameterAnnotations();
            Class<?>[] paramTypes = method.getParameterTypes();

            String name = null;
            Class<?> paramType = null;

            if (paramTypes.length == 1) {
                paramType = paramTypes[0];
                if (paramAnnotations[0].length == 0) {
                    throw makeThrow("Parameter must associated with annotation");
                }
                name = ((Param) paramAnnotations[0][0]).value();
            }

            if (name != null && paramType != null) {
                return new RequiredParam(paramType, name);
            } else {
                return null;
            }

        }

        private ActionInfo actionFor(String actionUrl) {
            String url = StringUtils.stripLeadSlash(actionUrl);
            return new ActionInfo(controller, actionMappingMap.get(url), paramMappingMap.get(url));
        }
    }
}
