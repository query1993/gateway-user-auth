package com.simple.cloud.user.service;

import com.simple.cloud.common.Utils.TokenUtils;
import com.simple.cloud.common.constant.RequestConstants;
import com.simple.cloud.common.enums.UserOption;
import com.simple.cloud.common.enums.UserRole;
import com.simple.cloud.common.exception.ErrorCode;
import com.simple.cloud.common.exception.PlatformException;
import com.simple.cloud.user.config.RefreshConfigManager;
import com.simple.cloud.user.mapper.UserAccessLogMapper;
import com.simple.cloud.user.mapper.UserAccessMapper;
import com.simple.cloud.user.mapper.UserInfoMapper;
import com.simple.cloud.user.mapper.entity.UserAccessLogPo;
import com.simple.cloud.user.mapper.entity.UserAccessPo;
import com.simple.cloud.user.mapper.entity.UserInfoPo;
import com.simple.cloud.user.request.UserLoginRequest;
import com.simple.cloud.user.response.UserLoginResponse;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserAccessMapper userAccessMapper;
    @Autowired
    private UserAccessLogMapper userAccessLogMapper;
    @Autowired
    private RefreshConfigManager refreshConfigManager;

    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest, HttpServletRequest request,
            HttpServletResponse response) {
        String userName = userLoginRequest.getUserName();
        String loginPassword = userLoginRequest.getPassword();
        String loginIp = getIpFromRequest(request);

        log.info("login with  userName : {},  loginIp : {}", userName, loginIp);
        checkParam(userName, loginPassword);

        UserInfoPo userInfoPo = userInfoMapper.selectByUserName(userName);
        if (userInfoPo == null) {
            throw new PlatformException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }
        // 1.1 Check password
        String password = userInfoPo.getPassword();
        checkPassword(loginPassword, password);

        Long userId = userInfoPo.getUserId();

        // 2. from user_access, check user_id, username,role,login_source, login_ip
        UserAccessPo userAccessPo = userAccessMapper.selectByUserIdAndIp(userId, loginIp);
        String token = UUID.randomUUID().toString();
        if (null == userAccessPo) {
            // 插入用户登录记录
            saveUserLoginAccess(userId, token, loginIp);
        } else if (StringUtils.isEmpty(userAccessPo.getToken())) {
            setUserLoginToken(userAccessPo.getId(), token);
        } else {
            updateUserLoginTokenExpireTime(userAccessPo.getId());
        }
        saveUserLoginAccessLog(userId, UserOption.LOGIN, loginIp);
        // set request userId
        request.setAttribute(RequestConstants.USER_ID, userId);
        userAccessPo = userAccessMapper.selectByUserIdAndIp(userId, loginIp);
        response.setHeader(RequestConstants.HEADER_ACCESS_TOKEN, userAccessPo.getToken());
        return UserLoginResponse.builder().userName(userName).role(UserRole.getRoleByRoleType(userInfoPo.getRole()))
                .build();
    }


    @Transactional
    public Long verifyToken(String token) {
        UserAccessPo userAccessPo = userAccessMapper.selectByToken(token);
        if (null == userAccessPo) {
            throw new PlatformException(ErrorCode.TOKEN_IS_EMPTY);
        }
        if (TokenUtils.checkTokenIsExpired(userAccessPo.getTokenExpiredTime())) {
            throw new PlatformException(ErrorCode.TOKEN_IS_INVALID);
        }
        return userAccessPo.getUserId();
    }

    @Transactional
    public void logout(Long userId, HttpServletRequest request) {
        UserInfoPo userInfoPo = userInfoMapper.selectById(userId);
        if (null == userInfoPo) {
            log.error("logout error user is not exist, userId:{}", userId);
            throw new PlatformException(ErrorCode.USER_NOT_EXIST);
        }
        String loginIp = getIpFromRequest(request);
        UserAccessPo userAccessPoExist = userAccessMapper.selectByUserIdAndIp(userId, loginIp);
        if (null == userAccessPoExist || null == userAccessPoExist.getToken()) {
            log.error("userAccessPo is not Exist,userId:{}", userId);
            throw new PlatformException(ErrorCode.TOKEN_IS_INVALID);
        }
        UserAccessPo userAccessPo = new UserAccessPo();
        userAccessPo.setUserId(userId);
        userAccessPo.setLoginIp(loginIp);
        userAccessPo.setToken(null);
        userAccessPo.setTokenExpiredTime(null);
        userAccessPo.setUpdateTime(new Date());
        userAccessMapper.updateUserLogout(userAccessPo);
        saveUserLoginAccessLog(userId, UserOption.LOGOUT, loginIp);
    }

    private void checkPassword(String userLoginPassword, String dbPassword) {
        log.info("checkPassword with userLoginPassword : {} dbPassword : {}", userLoginPassword, dbPassword);
        if (!userLoginPassword.equals(dbPassword)) {
            throw new PlatformException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }
    }

    private void checkParam(String userName, String password) {
        if (StringUtils.isEmpty(userName)) {
            throw new PlatformException(ErrorCode.USERNAME_IS_NULL);
        }
        if (StringUtils.isEmpty(password)) {
            throw new PlatformException(ErrorCode.PASSWORD_IS_NULL);
        }
    }

    private void updateUserLoginTokenExpireTime(Long id) {
        UserAccessPo userAccessPo = new UserAccessPo();
        userAccessPo.setId(id);
        userAccessPo.setTokenExpiredTime(TokenUtils.getTokenExpiredTime(refreshConfigManager.getExpiration()));
        userAccessPo.setUpdateTime(new Date());
        userAccessMapper.update(userAccessPo);
    }

    private void setUserLoginToken(Long id, String token) {
        UserAccessPo userAccessPo = new UserAccessPo();
        userAccessPo.setId(id);
        userAccessPo.setToken(token);
        userAccessPo.setTokenExpiredTime(TokenUtils.getTokenExpiredTime(refreshConfigManager.getExpiration()));
        userAccessPo.setUpdateTime(new Date());
        userAccessMapper.update(userAccessPo);
    }

    private void saveUserLoginAccess(Long userId, String token, String loginIp) {
        UserAccessPo userAccessPo = new UserAccessPo();
        userAccessPo.setUserId(userId);
        userAccessPo.setToken(token);
        userAccessPo.setCreateTime(new Date());
        userAccessPo.setTokenExpiredTime(TokenUtils.getTokenExpiredTime(refreshConfigManager.getExpiration()));
        userAccessPo.setLoginIp(loginIp);
        userAccessPo.setUpdateTime(new Date());
        userAccessMapper.insert(userAccessPo);
    }

    private void saveUserLoginAccessLog(Long userId, UserOption userOption, String loginIp) {
        UserAccessLogPo userAccessLogPo = new UserAccessLogPo();
        userAccessLogPo.setUserId(userId);
        userAccessLogPo.setCreateTime(new Date());
        userAccessLogPo.setUserOption(userOption.getType());
        userAccessLogPo.setLoginIp(loginIp);
        userAccessLogPo.setUpdateTime(new Date());
        userAccessLogMapper.insert(userAccessLogPo);
    }

    private String getIpFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-Ip");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
