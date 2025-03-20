package br.com.microservices.orchestrated.orderservice.core.document;

import br.com.microservices.orchestrated.orderservice.core.dto.OrderRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "order")
public class Order {

    @Id
    private String id;
    private List<OrderProduct> products;
    private LocalDateTime createdAt;
    private String transactionId;
    private Double totalAmount;
    private Integer totalItems;

    private static final String TRANSACTION_ID_PATTERN = "%s_%s";

    public Order() {
    }

    public Order(String id, List<OrderProduct> products, LocalDateTime createdAt, String transactionId, Double totalAmount, Integer totalItems) {
        this.id = id;
        this.products = products;
        this.createdAt = createdAt;
        this.transactionId = transactionId;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
    }

    public static Order fromRequestDto(OrderRequest orderRequest) {
        return new Order(
                null,
                orderRequest.products(),
                LocalDateTime.now(),
                String.format(TRANSACTION_ID_PATTERN, Instant.now().toEpochMilli(), UUID.randomUUID()),
                null,
                null
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }
}
