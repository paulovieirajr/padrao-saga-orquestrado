package br.com.microservices.orchestrated.orchestratorservice.core.dto;

public record OrderProduct(
        Product product,
        Integer quantity
) {
}
