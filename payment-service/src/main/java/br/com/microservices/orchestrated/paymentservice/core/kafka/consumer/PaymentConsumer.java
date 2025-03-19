package br.com.microservices.orchestrated.paymentservice.core.kafka.consumer;

import io.github.javawinds.dto.services.Event;
import io.github.javawinds.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static io.github.javawinds.enums.Microservice.PAYMENT_SERVICE;
import static io.github.javawinds.enums.Topic.PAYMENT_FAIL;
import static io.github.javawinds.enums.Topic.PAYMENT_SUCCESS;
import static io.github.javawinds.log.CustomLog.RECEIVED_EVENT_LOG;

@Component
public class PaymentConsumer {

    public static final Logger LOG = LoggerFactory.getLogger(PaymentConsumer.class);

    private final JsonUtils<Event> jsonUtils;

    public PaymentConsumer(JsonUtils<Event> jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.payment-success}"
    )
    public void consumeSuccessEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), PAYMENT_SERVICE.getMicroservice(), PAYMENT_SUCCESS.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(e -> LOG.info("Event received successfully: {}", e));
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.payment-fail}"
    )
    public void consumeFailEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), PAYMENT_SERVICE.getMicroservice(), PAYMENT_FAIL.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(e -> LOG.info("Event received with failure: {}", e));
    }
}
