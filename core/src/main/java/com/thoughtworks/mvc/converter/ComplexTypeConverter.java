package com.thoughtworks.mvc.converter;

import com.thoughtworks.utils.Lang;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class ComplexTypeConverter extends TypeConverter {

    public ComplexTypeConverter(Class<?> type) {
        super(type);
    }

    @Override
    public Object convert(HttpServletRequest request, String name) {
        Object instance = Lang.instanceFor(type);

        for (Field field : type.getDeclaredFields()) {
            Object value = create(field.getType()).convert(request, realParamName(name, field));
            injectFieldValue(instance, field, value);
        }
        return instance;
    }

    private void injectFieldValue(Object instance, Field field, Object fieldValue) {
        try {
            field.setAccessible(true);
            field.set(instance, fieldValue);
        } catch (Exception e) {
            throw Lang.makeThrow("Setting param failed. error: %s", Lang.stackTrace(e));
        }
    }

    private String realParamName(String name, Field field) {
        return name == null ? field.getName() : name + "." + field.getName();
    }
}
