package com.thoughtworks.mvc.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.thoughtworks.mvc.exceptions.RouterErrorException;

import java.io.IOException;

public class ClassUtil {

   public static ImmutableSet<ClassPath.ClassInfo> getClassInfos(String packageName1) {
       ClassPath classPath;
       try {
           classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
       } catch (IOException e) {
           throw new RouterErrorException(e);
       }

       return classPath.getTopLevelClassesRecursive(packageName1);
   }
}
