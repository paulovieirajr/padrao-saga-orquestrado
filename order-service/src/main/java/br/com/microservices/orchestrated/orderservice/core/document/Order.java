package br.com.microservices.orchestrated.orderservice.core.document;

import java.time.LocalDateTime;
import java.util.List;

public record Order(
        String id,
        List<OrderProduct> products,
        LocalDateTime createdAt,
        String transactionId,
        Double totalAmount,
        Integer totalItems
) {
}
