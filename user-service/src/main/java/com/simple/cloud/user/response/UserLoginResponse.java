package com.simple.cloud.user.response;

import com.simple.cloud.common.enums.UserRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@ApiModel("登录返回实体类")
public class UserLoginResponse {
    @ApiModelProperty("角色")
    private UserRole role;
    @ApiModelProperty("用户名")
    private String userName;
}
