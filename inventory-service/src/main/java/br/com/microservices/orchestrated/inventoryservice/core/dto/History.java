package br.com.microservices.orchestrated.inventoryservice.core.dto;

import br.com.microservices.orchestrated.inventoryservice.core.enums.SagaStatus;

import java.time.LocalDateTime;

public record History(
        String source,
        SagaStatus status,
        String message,
        LocalDateTime createdAt
) {
}
