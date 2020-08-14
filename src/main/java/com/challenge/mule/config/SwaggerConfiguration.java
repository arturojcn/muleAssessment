package com.challenge.mule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.challenge.mule.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Mule API-REST Service",
                "API-REST service where you can consult information related with worldBank",
                "1.0",
                "TERMS OF SERVICE URL",
                new Contact("Arturo Cabrera","www.linkedin.com/in/arturojcn8","Arturojcn8@gmail.com"),
                "LICENSE",
                "https://www.gnu.org/licenses/gpl-3.0.html",
                Collections.emptyList()
        );
    }
}
