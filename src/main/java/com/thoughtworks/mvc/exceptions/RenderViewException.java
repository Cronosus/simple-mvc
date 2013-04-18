package com.thoughtworks.mvc.exceptions;

public class RenderViewException extends RuntimeException {
    public RenderViewException(Throwable t) {
        super(t);
    }
}
