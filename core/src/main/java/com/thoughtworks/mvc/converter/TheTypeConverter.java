package com.thoughtworks.mvc.converter;

import com.thoughtworks.utils.Lang;

import javax.servlet.http.HttpServletRequest;

public class TheTypeConverter implements TypeConverter {

    public TypeConverter getTypeConverter(Class<?> type) {
        TypeConverter typeConverter;

        if (Lang.isPrimitive(type)) {
            typeConverter = new PrimitiveTypeConverter();

        } else {
            typeConverter = new ComplexTypeConverter();
        }
        return typeConverter;
    }

    @Override
    public Object convert(HttpServletRequest request, Class<?> type, String name) {
        return getTypeConverter(type).convert(request, type, name);
    }
}
