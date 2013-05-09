package com.thoughtworks.mvc.converter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ListTypeConverter extends TypeConverter {

    public ListTypeConverter(Class<?> type) {
        super(type);
    }

    @Override
    public Object convert(HttpServletRequest request, String name) {
        System.out.println("=================");
        System.out.println(type);
        System.out.println("=================");
        List value = new ArrayList();

        return value;
    }
}
