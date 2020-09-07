package com.simple.cloud.user.mapper.entity;

import java.util.Date;
import lombok.Data;

@Data
public class UserAccessPo {
    private Long id;
    private Long userId;
    private String loginIp;
    private String token;
    private Date tokenExpiredTime;
    private Date createTime;
    private Date updateTime;

}
