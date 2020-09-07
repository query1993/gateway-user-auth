package com.simple.cloud.common.exception;

public class PlatformException extends RuntimeException {

    private static final long serialVersionUID = -1605856181815216874L;

    private int errorCode;
    private String message;


    public PlatformException(final ErrorCode errorCode) {
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
