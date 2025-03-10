package io.github.paulovieirajr.dto.services;

import java.time.LocalDateTime;

public record History(
        String source,
        String status,
        String message,
        LocalDateTime createdAt
) {
}
