package com.simple.cloud.user.controller;


import com.simple.cloud.common.constant.RequestConstants;
import com.simple.cloud.common.entity.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @GetMapping("/users/user-info")
    public CommonResult<Long> getUserInfo(@RequestHeader(RequestConstants.USER_ID) Long userId) {
        log.info("******************** userId={}", userId);
        return CommonResult.ok(userId);
    }

}
