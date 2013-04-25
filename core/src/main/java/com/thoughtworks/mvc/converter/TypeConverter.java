package com.thoughtworks.mvc.converter;

import javax.servlet.http.HttpServletRequest;

public interface TypeConverter {
    Object convert(HttpServletRequest request, Class<?> type, String name);
}
