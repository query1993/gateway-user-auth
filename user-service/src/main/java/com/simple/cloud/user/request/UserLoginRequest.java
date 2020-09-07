package com.simple.cloud.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录请求实体类")
public class UserLoginRequest {

    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("密码")
    private String password;

}
