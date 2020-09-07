package com.simple.cloud.common.exception;

import com.simple.cloud.common.entity.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Aspect
public class ExceptionAspect {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {PlatformException.class})
    @ResponseBody
    public CommonResult<Void> resolvePlatformException(PlatformException e) {
        log.error("platform exception", e);
        return CommonResult.error(e.getErrorCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParamException.class)
    public CommonResult<Void> resolveParamException(ParamException e) {
        log.error("param exception", e);
        return CommonResult.error(ErrorCode.PARAMETER_ERROR.getCode(), ErrorCode.PARAMETER_ERROR.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResult<Void> resolveException(Exception e) {
        log.error(" exception", e);
        return CommonResult.error(ErrorCode.COMMON_SERVER_ERROR.getCode(), ErrorCode.COMMON_SERVER_ERROR.getMessage());
    }
}
