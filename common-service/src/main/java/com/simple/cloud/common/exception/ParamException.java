package com.simple.cloud.common.exception;

public class ParamException extends RuntimeException {

    public ParamException(String message) {
        super(message);
    }


    public ParamException() {
        super();
    }
}
