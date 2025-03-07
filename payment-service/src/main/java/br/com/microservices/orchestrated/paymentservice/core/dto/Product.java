package br.com.microservices.orchestrated.paymentservice.core.dto;

public record Product(
        String code,
        Double unitValue
) {
}
