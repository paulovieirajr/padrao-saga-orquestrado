package br.com.microservices.orchestrated.orderservice.core.document;

public record Product(
        String code,
        Double unitValue
) {
}
