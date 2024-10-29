package com.crud.code.svc.base.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi(ObjectProvider<BuildProperties> buildProperties) {
        OpenAPI openAPI = new OpenAPI();
        // add header
        Map<String, SecurityScheme> map = new HashMap<>();
        map.put(HttpHeaders.AUTHORIZATION, new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name(HttpHeaders.AUTHORIZATION));
        openAPI.components(new Components().securitySchemes(map));
        map.keySet().forEach(key -> openAPI.addSecurityItem(new SecurityRequirement().addList(key)));

        // base info
        openAPI.info(new Info().title("dashboard-backend-service")
                .description("dashboard api document")
                .version(Optional.ofNullable(buildProperties.getIfAvailable()).map(BuildProperties::getVersion).orElse("1.0.0")));
        return openAPI;
    }
}


