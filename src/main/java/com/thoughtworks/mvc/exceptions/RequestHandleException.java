package com.thoughtworks.mvc.exceptions;

public class RequestHandleException extends RuntimeException {
    public RequestHandleException(Throwable e) {
        super(e);
    }
}
