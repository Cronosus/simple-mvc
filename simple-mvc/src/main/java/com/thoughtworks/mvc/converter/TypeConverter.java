package com.thoughtworks.mvc.converter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;

import static com.thoughtworks.simpleframework.util.Lang.isPrimitive;

public abstract class TypeConverter {

    protected Class<?> type;

    public TypeConverter(Class<?> type) {
        this.type = type;
    }

    public static TypeConverter create(Field field) {
        return converterFor(field);
    }

    public static TypeConverter create(Class type) {
        return converterFor(type);
    }

    private static TypeConverter converterFor(Class fieldType) {
        TypeConverter typeConverter;
        if (isPrimitive(fieldType)) {
            typeConverter = new PrimitiveTypeConverter(fieldType);

        } else {
            typeConverter = new ComplexTypeConverter(fieldType);
        }
        return typeConverter;

    }

    private static TypeConverter converterFor(Field field) {

        TypeConverter typeConverter;
        if (isCollection(field.getType())) {
            Type fieldType = field.getGenericType();
            Class itemClass = (Class) ((ParameterizedType) fieldType).getActualTypeArguments()[0];

            System.out.println("i: " + itemClass);

            typeConverter = new ListTypeConverter(itemClass);
        } else {
            typeConverter = converterFor(field.getType());
        }

        return typeConverter;
    }

    private static boolean isCollection(Class fieldType) {
        return Arrays.asList(fieldType.getInterfaces()).contains(Collection.class);
    }

    public abstract Object convert(HttpServletRequest request, String name);
}
