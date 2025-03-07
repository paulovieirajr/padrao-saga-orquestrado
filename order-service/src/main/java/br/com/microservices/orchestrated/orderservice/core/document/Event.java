package br.com.microservices.orchestrated.orderservice.core.document;

import java.time.LocalDateTime;
import java.util.List;

public record Event(
        String id,
        String transactionId,
        String orderId,
        Order payload,
        String source,
        String status,
        List<History> eventHistory,
        LocalDateTime createdAt
) {
}
