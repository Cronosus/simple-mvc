package com.thoughtworks.mvc.core;

import javax.servlet.http.HttpServletRequest;

public interface RequestAware {
    public void setRequest(HttpServletRequest request);
}
