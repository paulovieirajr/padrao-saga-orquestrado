package io.github.paulovieirajr.dto.orchestrated;

import io.github.paulovieirajr.enums.SagaStatus;

import java.time.LocalDateTime;

public record History(
        String source,
        SagaStatus status,
        String message,
        LocalDateTime createdAt
) {
}
