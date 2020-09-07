package com.simple.cloud.user.mapper.entity;

import java.util.Date;
import lombok.Data;

@Data
public class UserAccessLogPo {
    private Long id;
    private Long userId;
    private Integer userOption;
    private String loginIp;
    private Date createTime;
    private Date updateTime;

}
