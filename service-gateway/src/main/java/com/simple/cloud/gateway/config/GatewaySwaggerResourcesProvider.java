package com.simple.cloud.gateway.config;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@ConfigurationProperties(prefix = "open-api.swagger")
@EnableConfigurationProperties
@Component
@Primary
public class GatewaySwaggerResourcesProvider implements SwaggerResourcesProvider {

    @Setter
    @Getter
    private List<String> resources = Lists.newArrayList();

    public static final String API_URI = "/v3/api-docs";

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> swaggerResources = Lists.newArrayList(resource("default", API_URI));
        resources.forEach(s -> {
            //name,oas3Url
            String[] split = s.split(",");
            swaggerResources.add(resource(split[0], split[1] + API_URI));
        });
        return swaggerResources;
    }

    /**
     * @param name 显示的名字
     * @param oas3Url 文档格式：/user-service/swagger/v3/api-docs
     */
    private SwaggerResource resource(String name, String oas3Url) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setUrl(oas3Url);
        swaggerResource.setSwaggerVersion("3.0.0");
        return swaggerResource;
    }


}
