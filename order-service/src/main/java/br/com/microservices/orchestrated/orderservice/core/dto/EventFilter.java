package br.com.microservices.orchestrated.orderservice.core.dto;

import jakarta.validation.constraints.NotBlank;

public record EventFilter(
        @NotBlank
        String orderId,
        @NotBlank
        String transactionId
) {
}
