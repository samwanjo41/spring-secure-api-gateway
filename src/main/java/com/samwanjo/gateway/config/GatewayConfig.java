package com.samwanjo.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.addRequestHeader;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class GatewayConfig {
    @Bean
    public RouterFunction<ServerResponse> getRoute(){
        return route("service1")
                .GET("/api/v1/service1/**", http("http://localhost:8001"))
                .before(addRequestHeader("X-Request-Id", "1234"))
                .build().
                and(route("service2")
                                .GET("/api/v1/service2/**", http("http://localhost:8002"))
                                .before(addRequestHeader("X-Request-Id", "1234")).build());
    }






}
