package com.incloud.hcp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * Created by George on 12/06/2017.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    public Docket swaggerSpringfoxDocket() {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                //.pathMapping("/api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.incloud.hcp.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .genericModelSubstitutes(ResponseEntity.class)
                ;


        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Portal de Proveedores IPROVIDER",
                "Aplicacion SAP SCP",
                "Sistema Backend versión 2.0",
                "Derechos reservados",
                null,
                "License of API", "API license URL", Collections.emptyList());
    }

}

