package br.com.microservices.orchestrated.orchestratorservice.core.dto;

import br.com.microservices.orchestrated.orchestratorservice.core.enums.EventSource;
import br.com.microservices.orchestrated.orchestratorservice.core.enums.SagaStatus;

import java.time.LocalDateTime;
import java.util.List;

public record Event(
        String id,
        String transactionId,
        String orderId,
        Order payload,
        EventSource source,
        SagaStatus status,
        List<History> eventHistory,
        LocalDateTime createdAt
) {
}
