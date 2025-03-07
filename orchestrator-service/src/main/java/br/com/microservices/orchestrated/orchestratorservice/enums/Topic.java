package br.com.microservices.orchestrated.orchestratorservice.enums;

public enum Topic {
    START_SAGA("start-saga"),
    BASE_ORCHESTRATOR("orchestrator"),
    FINISH_SUCESS("finish-sucess"),
    FINISH_FAIL("finish-fail"),
    PRODUCT_VALIDATION_SUCESS("product-validation-sucess"),
    PRODUCT_VALIDATION_FAIL("product-validation-fail"),
    PAYMENT_SUCESS("payment-sucess"),
    PAYMENT_FAIL("payment-fail"),
    INVENTORY_SUCESS("inventory-sucess"),
    INVENTORY_FAIL("inventory-fail"),
    NOTIFY_ENDING("notify-ending");

    private String name;

    Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
