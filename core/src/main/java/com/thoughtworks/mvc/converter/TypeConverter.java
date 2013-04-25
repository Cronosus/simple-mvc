package com.thoughtworks.mvc.converter;

import com.thoughtworks.utils.Lang;

import javax.servlet.http.HttpServletRequest;

public abstract class TypeConverter {

    protected final Class<?> type;

    public TypeConverter(Class<?> type) {
        this.type = type;
    }

    public static TypeConverter create(Class<?> type) {
        return converterFor(type);
    }

    private static TypeConverter converterFor(Class<?> type) {

        TypeConverter typeConverter;

        if (Lang.isPrimitive(type)) {
            typeConverter = new PrimitiveTypeConverter(type);

        } else {
            typeConverter = new ComplexTypeConverter(type);
        }
        return typeConverter;
    }

    public abstract Object convert(HttpServletRequest request, String name);
}
