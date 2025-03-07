package br.com.microservices.orchestrated.inventoryservice.core.dto;

public record Product(
        String code,
        Double unitValue
) {
}
