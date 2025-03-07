package br.com.microservices.orchestrated.paymentservice.core.dto;

import br.com.microservices.orchestrated.paymentservice.core.enums.SagaStatus;

import java.time.LocalDateTime;

public record History(
        String source,
        SagaStatus status,
        String message,
        LocalDateTime createdAt
) {
}
