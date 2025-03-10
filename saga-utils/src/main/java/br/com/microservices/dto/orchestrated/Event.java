package br.com.microservices.dto.orchestrated;

import br.com.microservices.dto.Order;
import br.com.microservices.enums.EventSource;
import br.com.microservices.enums.SagaStatus;

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
