package com.example.demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import service.ServicesConfig;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:secrets.properties")
})
@Import(ServicesConfig.class)
@ComponentScan(basePackages = {"com"})
public class AviaBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AviaBackApplication.class, args);
    }
    @Bean
    public Docket aviaApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.api")).build();
    }
}
