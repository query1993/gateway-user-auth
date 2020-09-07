package com.simple.cloud.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonResult<T> {

    private int code;
    private String message = "";
    public T data;

    private CommonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> error(int code, String message) {
        return new CommonResult<>(code, message, null);
    }

    public static <T> CommonResult<T> ok(T data) {
        return new CommonResult<>(ResultCode.SUCCESS, ResultCode.SUCCESS_MESSAGE, data);
    }

    public static CommonResult<Void> ok() {
        return ok(null);
    }


}
