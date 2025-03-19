package br.com.microservices.orchestrated.orderservice.core.kafka.consumer;

import io.github.javawinds.dto.services.Event;
import io.github.javawinds.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static io.github.javawinds.enums.Microservice.ORDER_SERVICE;
import static io.github.javawinds.enums.Topic.NOTIFY_ENDING;
import static io.github.javawinds.log.CustomLog.RECEIVED_EVENT_LOG;

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
