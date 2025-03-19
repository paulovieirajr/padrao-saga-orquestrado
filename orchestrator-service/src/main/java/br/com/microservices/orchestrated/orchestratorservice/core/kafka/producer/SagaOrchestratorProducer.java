package br.com.microservices.orchestrated.orchestratorservice.core.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static io.github.javawinds.enums.Microservice.ORCHESTRATOR;
import static io.github.javawinds.log.CustomLog.FAILED_EVENT_LOG;
import static io.github.javawinds.log.CustomLog.SEND_EVENT_LOG;

@Component
public class SagaOrchestratorProducer {

    private final static Logger LOG = LoggerFactory.getLogger(SagaOrchestratorProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public SagaOrchestratorProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(String payload, String topic) {
        try {
            LOG.info(SEND_EVENT_LOG.getMessage(), ORCHESTRATOR.getMicroservice(), topic, payload);
            kafkaTemplate.send(topic, payload);
        } catch (Exception e) {
            LOG.error(FAILED_EVENT_LOG.getMessage(), ORCHESTRATOR.getMicroservice(), topic, payload, e);
        }
    }
}
