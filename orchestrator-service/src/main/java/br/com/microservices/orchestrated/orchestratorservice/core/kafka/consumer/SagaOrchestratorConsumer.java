package br.com.microservices.orchestrated.orchestratorservice.core.kafka.consumer;

import br.com.microservices.dto.orchestrated.Event;
import br.com.microservices.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static br.com.microservices.enums.Microservice.ORCHESTRATOR;
import static br.com.microservices.enums.Topic.*;
import static br.com.microservices.log.CustomLog.RECEIVED_EVENT_LOG;

@Component
public class SagaOrchestratorConsumer {

    public static final Logger LOG = LoggerFactory.getLogger(SagaOrchestratorConsumer.class);

    private final JsonUtils<Event> jsonUtils;

    public SagaOrchestratorConsumer(JsonUtils<Event> jsonUtils) {
        this.jsonUtils = jsonUtils;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.start-saga}"
    )
    public void consumeStartSagaEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), ORCHESTRATOR.getMicroservice(), START_SAGA.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(e -> LOG.info("Event received successfully for start-saga: {}", e));
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.orchestrator}"
    )
    public void consumeOrchestratorEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), ORCHESTRATOR.getMicroservice(), BASE_ORCHESTRATOR.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(e -> LOG.info("Event received successfully for orchestrator: {}", e));
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.finish-success}"
    )
    public void consumeFinishSuccessEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), ORCHESTRATOR.getMicroservice(), FINISH_SUCCESS.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(e -> LOG.info("Event received successfully for finish-success: {}", e));
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.finish-fail}"
    )
    public void consumeFinishFailEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), ORCHESTRATOR.getMicroservice(), FINISH_FAIL.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(e -> LOG.info("Event received successfully for finish-fail: {}", e));
    }
}
