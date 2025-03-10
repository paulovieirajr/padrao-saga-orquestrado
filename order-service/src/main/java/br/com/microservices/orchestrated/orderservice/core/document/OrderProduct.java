package br.com.microservices.orchestrated.orderservice.core.document;

public record OrderProduct(
        Product product,
        Integer quantity
) {
}
