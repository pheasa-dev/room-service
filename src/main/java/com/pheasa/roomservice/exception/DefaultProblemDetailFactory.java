package com.pheasa.roomservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

@Component
public class DefaultProblemDetailFactory implements ProblemDetailFactory {

    @Override
    public ProblemDetail create(HttpStatus status, String message, ServerWebExchange exchange) {
        return create(status, message, status.name(), exchange);
    }

    @Override
    public ProblemDetail create(HttpStatus status, String message, ServerWebExchange exchange, Map<String, Object> properties) {
        ProblemDetail pd = create(status, message, exchange);
        if (Objects.nonNull(properties)) {
            properties.forEach(pd::setProperty);
        }
        return pd;
    }

    @Override
    public ProblemDetail create(HttpStatus status, String message, String errorCode, ServerWebExchange exchange) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", exchange.getRequest().getPath().value());
        problemDetail.setProperty("errorCode", errorCode);
        problemDetail.setProperty("service", "room-service");
        return problemDetail;
    }
}
