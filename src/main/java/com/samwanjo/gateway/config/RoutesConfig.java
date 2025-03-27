package com.samwanjo.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RoutesConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("service1", r -> r.path("/api/v1/service1/**")
                        .uri("http://localhost:8001"))
                .route("service2", r -> r.path("/api/v1/service2/**")
                        .uri("http://localhost:8002"))
                .route("product_service", r -> r.path("/products/**")
                        .uri("lb://PRODUCT-SERVICE"))  // Load-balanced service
                .build();
    }






}
