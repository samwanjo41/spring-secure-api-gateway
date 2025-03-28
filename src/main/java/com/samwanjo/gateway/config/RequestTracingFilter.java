package com.samwanjo.gateway.config;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class RequestTracingFilter implements GlobalFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestTracingFilter.class);
    private final Tracer tracer;

    public RequestTracingFilter(Tracer tracer) {
        this.tracer = tracer;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Span span = tracer.currentSpan();
        if (span != null) {
            log.info("Request Trace ID: {}", span.context().traceId());
            exchange.getRequest().mutate().headers(httpHeaders -> httpHeaders.add("X-Trace-Id", span.context().traceId()));
        }
        return chain.filter(exchange);
    }
}
