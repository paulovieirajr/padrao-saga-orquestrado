package br.com.microservices.orchestrated.paymentservice.core.dto;

import br.com.microservices.orchestrated.paymentservice.core.enums.SagaStatus;

import java.time.LocalDateTime;
import java.util.List;

public record Event(
        String id,
        String transactionId,
        String orderId,
        Order payload,
        String source,
        SagaStatus status,
        List<History> eventHistory,
        LocalDateTime createdAt
) {
}
