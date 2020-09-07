package com.simple.cloud.user.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate requestTemplate) {
    try {
      // 所有的Feign调用都需要自动增加key为userId的Header参数
      ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
          .getRequestAttributes();
      if (servletRequestAttributes != null) {
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
          String key = headerNames.nextElement();
          if(StringUtils.equals("userId", key)){
            requestTemplate.header(key, request.getHeader(key));
            break;
          }
        }
      }
    } catch (Exception e) {
      log.error("Fail to apply feign request", e);
    }
  }
}
