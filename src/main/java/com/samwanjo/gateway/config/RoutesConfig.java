package com.samwanjo.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.time.Duration;


@Configuration
public class RoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("service1", r -> r.path("/api/v1/service1/**")
                        .filters(f -> f
                                .retry(config -> config
                                        .setRetries(3) // Retry 3 times
                                        .setStatuses(HttpStatus.BAD_GATEWAY, HttpStatus.GATEWAY_TIMEOUT)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST)
                                        .setBackoff(Duration.ofMillis(50), Duration.ofMillis(500), 2, false)
                                )
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(new RedisRateLimiter(10, 20))
                                        .setKeyResolver(ipKeyResolver())
                                )
                        )
                        .uri("http://localhost:8001"))
                .route("service2", r -> r.path("/api/v1/service2/**")
                        .uri("http://localhost:8002"))
                .route("product_service", r -> r.path("/products/**")
                        .uri("lb://PRODUCT-SERVICE"))  // Load-balanced service
                .build();
    }

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }





}
