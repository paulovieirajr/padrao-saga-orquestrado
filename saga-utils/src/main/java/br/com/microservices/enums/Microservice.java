package br.com.microservices.enums;

/**
 * Enum that represents the microservices in the system.
 * This enum is used to identify the microservices in the logs.
 * @see br.com.microservices.log.CustomLog
 */
public enum Microservice {
    ORCHESTRATOR("ORCHESTRATOR"),
    ORDER_SERVICE("ORDER SERVICE"),
    PRODUCT_VALIDATION_SERVICE("PRODUCT VALIDATION SERVICE"),
    PAYMENT_SERVICE("PAYMENT SERVICE"),
    INVENTORY_SERVICE("INVENTORY SERVICE");

    private final String microservice;

    Microservice(String microservice) {
        this.microservice = microservice;
    }

    public String getMicroservice() {
        return microservice;
    }
}
