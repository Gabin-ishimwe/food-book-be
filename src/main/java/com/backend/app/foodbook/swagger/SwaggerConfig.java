package com.backend.app.foodbook.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket configuration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(List.of(apiKey()))
                .securityContexts(List.of(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "FoodBook Backend APIs",
                "APIs for MyApp.",
                "1.0",
                "Terms of service",
                new springfox.documentation.service.Contact("Team Food Book", "#", "g.ishimwe@alustudent.com"),
                "License of API",
                "API license URL",
                Collections.emptyList()
        );
    }

    private ApiKey apiKey() {
        return new ApiKey(AUTHORIZATION_HEADER, "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes));
    }

}
