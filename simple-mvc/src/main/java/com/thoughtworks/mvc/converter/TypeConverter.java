package com.thoughtworks.mvc.converter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.List;

import static com.thoughtworks.simpleframework.util.Lang.isPrimitive;

public abstract class TypeConverter {

    protected final Class<?> type;

    public TypeConverter(Class<?> type) {
        this.type = type;
    }

    public static TypeConverter create(Type type) {
        return converterFor(type);
    }

    private static TypeConverter converterFor(Class<?> type) {

        TypeConverter typeConverter;

        if (isPrimitive(type)) {
            typeConverter = new PrimitiveTypeConverter(type);

        } else if (type.equals(List.class)) {
            typeConverter = new ListTypeConverter(type);
        } else {
            typeConverter = new ComplexTypeConverter(type);
        }
        return typeConverter;
    }

    public abstract Object convert(HttpServletRequest request, String name);
}
