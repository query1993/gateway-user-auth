package com.simple.cloud.common.enums;

import com.simple.cloud.common.exception.ErrorCode;
import com.simple.cloud.common.exception.PlatformException;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户角色类型
 */
@Slf4j
public enum UserRole {

    SUPER_ADMIN(1, "超级管理员"),
    ADMIN_USER(2, "普通管理员"),
    NORMAL_USER(3, "普通用户");

    private Integer roleType;
    private String name;

    UserRole(Integer roleType, String name) {
        this.roleType = roleType;
        this.name = name;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public String getName() {
        return name;
    }

    public static UserRole getRoleByRoleType(Integer roleType) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getRoleType().equals(roleType)) {
                return values()[i];
            }
        }
        log.error("Invalid UserRole with userRole : " + roleType);
        throw new PlatformException(ErrorCode.NOT_SUPPORT_ENUM_ERROR);
    }
}
