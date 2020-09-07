package com.simple.cloud.user.config;

import com.simple.cloud.common.exception.ExceptionConfig;
import com.simple.cloud.common.feign.FeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@MapperScan("com.simple.cloud.user.mapper")
@Import({ExceptionConfig.class, FeignConfig.class})
public class UserServiceConfig {

}
