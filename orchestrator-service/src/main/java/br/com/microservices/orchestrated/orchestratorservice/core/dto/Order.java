package br.com.microservices.orchestrated.orchestratorservice.core.dto;

import br.com.microservices.orchestrated.orderservice.core.document.OrderProduct;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public record Order(
        @Id
        String id,
        List<OrderProduct> products,
        LocalDateTime createdAt,
        String transactionId,
        Double totalAmount,
        Integer totalItems
) {
}
