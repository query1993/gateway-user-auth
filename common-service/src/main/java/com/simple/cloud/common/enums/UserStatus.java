package com.simple.cloud.common.enums;

import com.simple.cloud.common.exception.ErrorCode;
import com.simple.cloud.common.exception.PlatformException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public enum UserStatus {

    NORMAL(0, "正常用户"),
    IN_VALID(1, "无效用户"),
    ;

    private Integer status;
    private String name;

    UserStatus(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public static UserStatus getUserStatus(Integer status) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getStatus().equals(status)) {
                return values()[i];
            }
        }
        log.error("Invalid UserStatus with status : " + status);
        throw new PlatformException(ErrorCode.NOT_SUPPORT_ENUM_ERROR);
    }
}
