package br.com.microservices.orchestrated.inventoryservice.core.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static br.com.microservices.enums.Microservice.INVENTORY_SERVICE;
import static br.com.microservices.log.CustomLog.FAILED_EVENT_LOG;
import static br.com.microservices.log.CustomLog.SEND_EVENT_LOG;

@Component
public class KafkaProducer {

    private final static Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.kafka.topic.orchestrator}")
    private String orchestratorTopic;

    public void sendEvent(String payload) {
        try {
            LOG.info(SEND_EVENT_LOG.getMessage(), INVENTORY_SERVICE.getMicroservice(), orchestratorTopic, payload);
            kafkaTemplate.send(orchestratorTopic, payload);
        } catch (Exception e) {
            LOG.error(FAILED_EVENT_LOG.getMessage(), INVENTORY_SERVICE.getMicroservice(), orchestratorTopic, payload, e);
        }
    }
}
