package com.simple.cloud.gateway.filter;

import com.simple.cloud.gateway.config.GatewaySwaggerResourcesProvider;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            System.out.println("path:" + path);
            if (!StringUtils.endsWithIgnoreCase(path, GatewaySwaggerResourcesProvider.API_URI)) {
                return chain.filter(exchange);
            }
            ServerHttpRequest newRequest = request.mutate().path(GatewaySwaggerResourcesProvider.API_URI).build();
            ServerWebExchange newWebExchange = exchange.mutate().request(newRequest).build();
            return chain.filter(newWebExchange);
        };
    }
}