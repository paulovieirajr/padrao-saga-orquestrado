package br.com.microservices.orchestrated.productvalidationservice.core.dto;

public record Product(
        String code,
        Double unitValue
) {
}
