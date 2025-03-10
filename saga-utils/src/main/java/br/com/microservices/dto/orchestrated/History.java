package br.com.microservices.dto.orchestrated;

import br.com.microservices.enums.SagaStatus;

import java.time.LocalDateTime;

public record History(
        String source,
        SagaStatus status,
        String message,
        LocalDateTime createdAt
) {
}
