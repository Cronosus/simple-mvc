package com.thoughtworks.mvc.converter;

import com.thoughtworks.utils.Lang;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class ComplexTypeConverter implements TypeConverter {

    @Override
    public Object convert(HttpServletRequest request, Class<?> type, String name) {
        Object instance = Lang.instanceFor(type);

        for (Field field : type.getDeclaredFields()) {

            Object fieldValue = new TheTypeConverter().convert(request, field.getType(), realParamName(name, field));
            try {
                field.setAccessible(true);
                field.set(instance, fieldValue);
            } catch (Exception e) {
                throw Lang.makeThrow("Setting param failed. error: %s", Lang.stackTrace(e));
            }
        }
        return instance;
    }

    private String realParamName(String name, Field field) {
        return name == null ? field.getName() : name + "." + field.getName();
    }
}
