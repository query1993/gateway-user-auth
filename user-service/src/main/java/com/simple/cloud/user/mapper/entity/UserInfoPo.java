package com.simple.cloud.user.mapper.entity;

import java.util.Date;
import lombok.Data;

@Data
public class UserInfoPo {

    private Long userId;
    private String userName;
    private String email;
    private String cellphone;
    private String password;
    private Integer role;
    private Date createTime;
    private Date updateTime;


}
