package com.thoughtworks.mvc.core;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

public class StringTemplateView {

    private final String name;

    public StringTemplateView(String name) {
        this.name = name;
    }

    public String render() {
        STGroup group = new STGroupDir("resources");
        ST st = group.getInstanceOf(name);
        return st.render();
    }
}
