package com.simple.cloud.user.request;

import java.util.List;
import lombok.Data;

@Data
public class UserAddRequest {

    private String userName;
    private String email;
    private String cellphone;
    private String userType;
    private List<Integer> roleIds;
}
