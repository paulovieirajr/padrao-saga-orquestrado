package br.com.microservices.orchestrated.productvalidationservice.core.dto;


import br.com.microservices.orchestrated.productvalidationservice.core.enums.SagaStatus;

import java.time.LocalDateTime;

public record History(
        String source,
        SagaStatus status,
        String message,
        LocalDateTime createdAt
) {
}
