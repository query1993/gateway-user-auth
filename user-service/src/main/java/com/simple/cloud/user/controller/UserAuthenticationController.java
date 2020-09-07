package com.simple.cloud.user.controller;

import com.simple.cloud.common.entity.CommonResult;
import com.simple.cloud.user.request.UserLoginRequest;
import com.simple.cloud.user.response.UserLoginResponse;
import com.simple.cloud.user.service.AuthenticationService;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserAuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiOperation("用户登录")
    @PostMapping("/users/login")
    public CommonResult<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest,
            HttpServletRequest httpServletRequest, HttpServletResponse response) {
        return CommonResult.ok(authenticationService.login(userLoginRequest, httpServletRequest, response));
    }

    @ApiOperation("用户登出")
    @PostMapping("/users/logout")
    public CommonResult<Void> logout(@RequestHeader("userId") Long userId, HttpServletRequest httpServletRequest) {
        authenticationService.logout(userId, httpServletRequest);
        return CommonResult.ok();
    }

    @ApiOperation("token验证")
    @PostMapping("/users/verify-token")
    public CommonResult<Long> verifyToken(@RequestParam("token") String token) {

        return CommonResult.ok(authenticationService.verifyToken(token));
    }
}
