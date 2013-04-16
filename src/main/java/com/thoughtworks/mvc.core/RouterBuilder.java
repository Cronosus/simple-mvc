package com.thoughtworks.mvc.core;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.mvc.annotations.Path;
import com.thoughtworks.mvc.utils.ClassUtil;

import java.util.Set;

public class RouterBuilder {

    private final String packageName;

    public RouterBuilder(String packageName) {
        this.packageName = packageName;
    }

    public Router build() {
        Router router = new Router();

        Set<ClassPath.ClassInfo> allClasses = ClassUtil.getClassInfos(packageName);

        for (ClassPath.ClassInfo classInfo : allClasses) {
            Class<?> clazz = classInfo.load();
            if (clazz.isAnnotationPresent(Path.class)) {
                router.add(clazz.getAnnotation(Path.class).url(), clazz);
            }
        }
        return router;


    }

}
