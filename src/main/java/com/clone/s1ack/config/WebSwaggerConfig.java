package com.clone.s1ack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class WebSwaggerConfig {
    private static final String API_NAME = "Spring 클론 코딩";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "FE: 오경민, 이현진 | BE: 이승우, 안성재";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any()) // any: 모든 로직에 대해 Swagger 문서화가 수행
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .contact(new Contact("S1ack", "https://github.com/2-S1ack/Backend","jaesa5221@gmail.com"))
                .build();
    }

}
