package br.com.microservices.orchestrated.paymentservice.core.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static io.github.javawinds.enums.Microservice.PAYMENT_SERVICE;
import static io.github.javawinds.log.CustomLog.FAILED_EVENT_LOG;
import static io.github.javawinds.log.CustomLog.SEND_EVENT_LOG;

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
            LOG.info(SEND_EVENT_LOG.getMessage(), PAYMENT_SERVICE.getMicroservice(), orchestratorTopic, payload);
            kafkaTemplate.send(orchestratorTopic, payload);
        } catch (Exception e) {
            LOG.error(FAILED_EVENT_LOG.getMessage(), PAYMENT_SERVICE.getMicroservice(), orchestratorTopic, payload, e);
        }
    }
}
