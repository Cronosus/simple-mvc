package com.thoughtworks.mvc.converter;

import javax.servlet.http.HttpServletRequest;

public class PrimitiveTypeConverter implements TypeConverter {

    public Object convert(HttpServletRequest request, Class<?> type, String name) {
        String val = request.getParameter(name);
        if (null == val)
            return null;
        Object param;
        if (type == Long.class) {
            param = Long.parseLong(val);
        } else if (type == Integer.class) {
            param = Integer.parseInt(val);
        } else {
            param = val;
        }
        return param;
    }
}
