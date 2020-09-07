package com.simple.cloud.gateway.config;

import com.simple.cloud.common.clients.userservice.UserServiceClient;
import com.simple.cloud.common.exception.ExceptionConfig;
import com.simple.cloud.common.feign.FeignConfig;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.oas.annotations.EnableOpenApi;

@Import({ExceptionConfig.class, FeignConfig.class})
@Configuration
@EnableOpenApi
@EnableFeignClients(clients = {UserServiceClient.class})
public class GatewayConfig {

}
