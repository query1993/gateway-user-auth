package com.simple.cloud.gateway.filter;

import com.simple.cloud.common.clients.userservice.UserServiceClient;
import com.simple.cloud.common.constant.RequestConstants;
import com.simple.cloud.common.entity.CommonResult;
import com.simple.cloud.common.entity.ResultCode;
import com.simple.cloud.common.exception.ErrorCode;
import com.simple.cloud.common.exception.PlatformException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        if (excludeUrls(request.getPath().toString())) {
            return chain.filter(exchange);
        }
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String userToken = headers.getFirst(RequestConstants.HEADER_ACCESS_TOKEN);
        if (StringUtils.isEmpty(userToken)) {
            log.error("token is empty");
            throw new PlatformException(ErrorCode.USER_NOT_AUTH);
        }
        CommonResult<Long> result = userServiceClient.verifyToken(userToken);
        if (result.getCode() != ResultCode.SUCCESS) {
            log.error("verify token error token:{}", userToken);
            throw new PlatformException(ErrorCode.USER_AUTH_FAIL);
        }

        String value = ServerWebExchangeUtils.expand(exchange, String.valueOf(result.getData()));
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().header(RequestConstants.USER_ID, value)
                .build();
        return chain.filter(exchange.mutate().request(serverHttpRequest).build());
    }

    @Override
    public int getOrder() {
        return -5;
    }

    public boolean excludeUrls(String targetUrl) {
        List<String> excludeUrls = new ArrayList<>();
        excludeUrls.add("/api/user/users/login");
        excludeUrls.add("/api/user/users/verify-token");
        excludeUrls.add("/user-service/swagger/v3/api-docs");
        return excludeUrls.contains(targetUrl);
    }
}
