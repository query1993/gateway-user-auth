package com.simple.cloud.common.Utils;


import com.simple.cloud.common.exception.ParamException;

public class Assert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ParamException(message);
        }
    }
}
