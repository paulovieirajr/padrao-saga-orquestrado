package br.com.microservices.dto;

public record OrderProduct(
        Product product,
        Integer quantity
) {
}
