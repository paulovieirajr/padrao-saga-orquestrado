package br.com.microservices.dto.services;

import br.com.microservices.dto.Order;

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
