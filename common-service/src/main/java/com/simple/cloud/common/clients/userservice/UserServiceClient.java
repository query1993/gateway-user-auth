package com.simple.cloud.common.clients.userservice;

import com.simple.cloud.common.entity.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service")
public interface UserServiceClient {

    @PostMapping("/api/user/users/verify-token")
    CommonResult<Long> verifyToken(@RequestParam("token") String token);
}
