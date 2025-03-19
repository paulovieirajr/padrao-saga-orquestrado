package br.com.microservices.orchestrated.orderservice.core.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static io.github.javawinds.enums.Microservice.ORDER_SERVICE;
import static io.github.javawinds.log.CustomLog.FAILED_EVENT_LOG;
import static io.github.javawinds.log.CustomLog.SEND_EVENT_LOG;

@Component
public class SagaProducer {

    private final static Logger LOG = LoggerFactory.getLogger(SagaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public SagaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value("${spring.kafka.topic.start-saga}")
    private String startSagaTopic;

    public void sendEvent(String payload) {
        try {
            LOG.info(SEND_EVENT_LOG.getMessage(), ORDER_SERVICE.getMicroservice(), startSagaTopic, payload);
            kafkaTemplate.send(startSagaTopic, payload);
        } catch (Exception e) {
            LOG.error(FAILED_EVENT_LOG.getMessage(), ORDER_SERVICE.getMicroservice(), startSagaTopic, payload, e);
        }
    }
}
