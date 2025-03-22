package br.com.microservices.orchestrated.productvalidationservice.core.kafka.consumer;

import br.com.microservices.orchestrated.productvalidationservice.core.service.ProductValidationService;
import io.github.javawinds.dto.services.Event;
import io.github.javawinds.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static io.github.javawinds.enums.Microservice.PRODUCT_VALIDATION_SERVICE;
import static io.github.javawinds.enums.Topic.PRODUCT_VALIDATION_FAIL;
import static io.github.javawinds.enums.Topic.PRODUCT_VALIDATION_SUCCESS;
import static io.github.javawinds.log.CustomLog.RECEIVED_EVENT_LOG;

@Component
public class ProductValidationConsumer {

    public static final Logger LOG = LoggerFactory.getLogger(ProductValidationConsumer.class);

    private final JsonUtils<Event> jsonUtils;
    private final ProductValidationService productValidationService;

    public ProductValidationConsumer(JsonUtils<Event> jsonUtils, ProductValidationService productValidationService) {
        this.jsonUtils = jsonUtils;
        this.productValidationService = productValidationService;
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.product-validation-success}"
    )
    public void consumeSuccessEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), PRODUCT_VALIDATION_SERVICE.getMicroservice(), PRODUCT_VALIDATION_SUCCESS.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(productValidationService::validateExistingProducts);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.product-validation-fail}"
    )
    public void consumeFailEvent(String payload) {
        LOG.info(RECEIVED_EVENT_LOG.getMessage(), PRODUCT_VALIDATION_SERVICE.getMicroservice(), PRODUCT_VALIDATION_FAIL.getTopic(), payload);
        var event = jsonUtils.toType(payload);
        event.ifPresent(productValidationService::rollbackEvent);
    }
}
