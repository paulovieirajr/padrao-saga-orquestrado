package br.com.microservices.orchestrated.orderservice.core.kafka.consumer;

import br.com.microservices.dto.services.Event;
import br.com.microservices.enums.Topic;
import br.com.microservices.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static br.com.microservices.enums.Microservice.ORDER_SERVICE;
import static br.com.microservices.enums.Topic.NOTIFY_ENDING;
import static br.com.microservices.log.CustomLog.RECEIVED_EVENT_LOG;

@Component
public class EventConsumer {

    public static final Logger LOG = LoggerFactory.getLogger(EventConsumer.class);

    private final JsonUtils<Event> jsonUtils;

    public EventConsumer(JsonUtils<Event> jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.notify-ending}"
    )
    public void consumeNotifyEndingEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), ORDER_SERVICE.getMicroservice(), NOTIFY_ENDING.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(e -> LOG.info("Event received successfully: {}", e));
    }
}
