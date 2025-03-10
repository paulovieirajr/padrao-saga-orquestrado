package br.com.microservices.dto.services;

import java.time.LocalDateTime;

public record History(
        String source,
        String status,
        String message,
        LocalDateTime createdAt
) {
}
