package br.com.microservices.orchestrated.inventoryservice.core.kafka.consumer;

import br.com.microservices.dto.services.Event;
import br.com.microservices.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static br.com.microservices.enums.Microservice.INVENTORY_SERVICE;
import static br.com.microservices.enums.Topic.INVENTORY_FAIL;
import static br.com.microservices.enums.Topic.INVENTORY_SUCCESS;
import static br.com.microservices.log.CustomLog.RECEIVED_EVENT_LOG;

@Component
public class InventoryConsumer {

    public static final Logger LOG = LoggerFactory.getLogger(InventoryConsumer.class);

    private final JsonUtils<Event> jsonUtils;

    public InventoryConsumer(JsonUtils<Event> jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.inventory-success}"
    )
    public void consumeSuccessEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), INVENTORY_SERVICE.getMicroservice(), INVENTORY_SUCCESS.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(e -> LOG.info("Event received successfully: {}", e));
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.inventory-fail}"
    )
    public void consumeFailEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), INVENTORY_SERVICE.getMicroservice(), INVENTORY_FAIL.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(e -> LOG.info("Event received with failure: {}", e));
    }
}
