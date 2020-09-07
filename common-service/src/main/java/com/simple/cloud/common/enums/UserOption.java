package com.simple.cloud.common.enums;

import com.simple.cloud.common.exception.ErrorCode;
import com.simple.cloud.common.exception.PlatformException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum UserOption {
    LOGIN(1, "登录"),
    LOGOUT(2, "登出");

    private Integer type;
    private String name;

    UserOption(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static UserOption getUserOption(Integer type) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getType().equals(type)) {
                return values()[i];
            }
        }
        log.error("Invalid UserOption with userOption : " + type);
        throw new PlatformException(ErrorCode.NOT_SUPPORT_ENUM_ERROR);
    }
}
