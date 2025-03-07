package br.com.microservices.orchestrated.orchestratorservice.core.dto;

public record Product(
        String code,
        Double unitValue
) {
}
