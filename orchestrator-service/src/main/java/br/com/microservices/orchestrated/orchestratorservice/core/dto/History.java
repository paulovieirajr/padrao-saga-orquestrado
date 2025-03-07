package br.com.microservices.orchestrated.orchestratorservice.core.dto;

import br.com.microservices.orchestrated.orchestratorservice.core.enums.EventSource;
import br.com.microservices.orchestrated.orchestratorservice.core.enums.SagaStatus;

import java.time.LocalDateTime;

public record History(
        EventSource source,
        SagaStatus status,
        String message,
        LocalDateTime createdAt
) {
}
