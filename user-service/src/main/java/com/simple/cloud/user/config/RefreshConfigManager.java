package com.simple.cloud.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class RefreshConfigManager {

    @Value("${user.login.expiration}")
    private Integer expiration;
    @Value("${user.initPassword}")
    private String initPassword;

    public Integer getExpiration() {
        return expiration;
    }

    public String getInitPassword() {
        return initPassword;
    }
}
