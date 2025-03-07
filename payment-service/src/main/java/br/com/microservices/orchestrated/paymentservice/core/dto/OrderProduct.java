package br.com.microservices.orchestrated.paymentservice.core.dto;

import br.com.microservices.orchestrated.orchestratorservice.core.dto.Product;

public record OrderProduct(
        Product product,
        Integer quantity
) {
}
