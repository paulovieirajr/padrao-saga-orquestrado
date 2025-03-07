package br.com.microservices.orchestrated.orderservice.core.document;

import java.time.LocalDateTime;

public record History(
        String source,
        String status,
        String message,
        LocalDateTime createdAt
) {
}
