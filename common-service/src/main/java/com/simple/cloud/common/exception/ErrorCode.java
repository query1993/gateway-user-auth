package com.simple.cloud.common.exception;

public enum ErrorCode {

    // CommonService errorCode starts with 1000XXXX
    COMMON_SERVER_ERROR(10000010, "10000010"),
    NOT_SUPPORT_ENUM_ERROR(10000011, "10000011"),
    USER_NOT_AUTH(10000012, "用户没有鉴权"),
    USER_AUTH_FAIL(10000013, "用户鉴权失败"),
    PARAMETER_ERROR(10000014, "参数异常"),


    // UserService errorCode starts with 1001XXXX
    USER_SERVICE_UNEXPECTED_ERROR(10010000, "10010000"),
    INVALID_USER_PASSWORD_ERROR(10010001, "10010001"),
    USER_NOT_FOUND_ERROR(10010002, "10010002"),
    USER_NOT_EXIST(10010003, "user not exist"),
    USERNAME_IS_NULL(10010004, "username is empty"),
    USER_ROLE_IS_NULL(10010005, "user role is empty"),
    USERNAME_IS_IN_USE(10010006, "username is in use"),
    ROLE_NAME_IS_NULL(10010007, " role name is empty"),
    ROLE_NAME_IS_IN_USE(10010008, "role name is in use"),
    ROLE_NOT_EXIST(10010009, "role not exist"),
    EXIST_USER_IN_ROLE(10010010, "role has user in use"),
    INVALID_USERNAME_OR_PASSWORD(10010011, "invalid username or password"),
    PASSWORD_IS_NULL(10010012, "password is empty"),
    USER_ID_IS_NULL(10010013, "user id is empty"),
    TOKEN_IS_EMPTY(10010014, "token is empty"),
    TOKEN_IS_INVALID(10010015, "token is invalid"),
    INVALID_AR_ROLE_ERROR(10010016, "invalid ar role"),
    REMOTE_USER_CREATE_ERROR(10010017, "Fail to create remote user"),
    OPERATOR_ID_IS_NULL(10010018, "Operator id is empty"),
    OPERATION_NOT_ALLOWDED(10010019, "Operation is NOT allowded"),
    PASSWORD_IS_INVALID(10010020, "password is invalid");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }
}
