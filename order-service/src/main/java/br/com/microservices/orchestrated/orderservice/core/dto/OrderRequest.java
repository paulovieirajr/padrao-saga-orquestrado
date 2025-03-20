package br.com.microservices.orchestrated.orderservice.core.dto;

import br.com.microservices.orchestrated.orderservice.core.document.OrderProduct;

import java.util.List;

public record OrderRequest(
        List<OrderProduct> products
) {
}
