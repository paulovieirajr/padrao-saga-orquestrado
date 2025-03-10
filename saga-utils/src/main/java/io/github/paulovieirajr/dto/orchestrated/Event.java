package io.github.paulovieirajr.dto.orchestrated;

import io.github.paulovieirajr.dto.Order;
import io.github.paulovieirajr.enums.EventSource;
import io.github.paulovieirajr.enums.SagaStatus;

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
