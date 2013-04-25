package com.thoughtworks.mvc.converter;

import javax.servlet.http.HttpServletRequest;

public class PrimitiveTypeConverter extends TypeConverter {

    public PrimitiveTypeConverter(Class<?> type) {
        super(type);
    }

    public Object convert(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (null == value)
            return null;
        Object param;
        if (type == Long.class) {
            param = Long.parseLong(value);
        } else if (type == Integer.class) {
            param = Integer.parseInt(value);
        } else {
            param = value;
        }
        return param;
    }
}
