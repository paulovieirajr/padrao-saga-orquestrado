package br.com.microservices.orchestrated.productvalidationservice.core.service;

import br.com.microservices.orchestrated.productvalidationservice.core.kafka.producer.KafkaProducer;
import br.com.microservices.orchestrated.productvalidationservice.core.model.Validation;
import br.com.microservices.orchestrated.productvalidationservice.core.repository.ProductRepository;
import br.com.microservices.orchestrated.productvalidationservice.core.repository.ValidationRepository;
import io.github.javawinds.dto.OrderProduct;
import io.github.javawinds.dto.services.Event;
import io.github.javawinds.dto.services.History;
import io.github.javawinds.exception.ValidationException;
import io.github.javawinds.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static io.github.javawinds.enums.SagaStatus.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductValidationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductValidationService.class);

    private static final String CURRENT_SOURCE = "PRODUCT_VALIDATION_SERVICE";

    private final JsonUtils<Event> jsonUtils;
    private final KafkaProducer kafkaProducer;
    private final ProductRepository productRepository;
    private final ValidationRepository validationRepository;

    public ProductValidationService(JsonUtils<Event> jsonUtils, KafkaProducer kafkaProducer, ProductRepository productRepository, ValidationRepository validationRepository) {
        this.jsonUtils = jsonUtils;
        this.kafkaProducer = kafkaProducer;
        this.productRepository = productRepository;
        this.validationRepository = validationRepository;
    }

    public void validateExistingProducts(Event event) {
        try {
            checkCurrentValidation(event);
            createValidation(event, true);
            handleSuccess(event);
        } catch (Exception e) {
            LOGGER.error("Error trying to validate product", e);
            handleFailCurrentNotExecuted(event, e.getMessage());
        }
        jsonUtils.toJson(event).ifPresent(kafkaProducer::sendEvent);
    }

    private void checkCurrentValidation(Event event) {
        validateProductsInformed(event);
        if (validationRepository.existsByOrderIdAndTransactionId(event.orderId(), event.transactionId())) {
            throw new ValidationException("There is another transactionId for this validation");
        }
        event.payload().products()
                .forEach(product -> {
                    validateProductInformed(product);
                    validateExistingProduct(product.product().code());
                });

    }

    private void validateProductsInformed(Event event) {
        if (isEmpty(event.payload()) || isEmpty(event.payload().products())) {
            throw new ValidationException("Product list is empty");
        }
        if (isEmpty(event.payload().id()) || isEmpty(event.transactionId())) {
            throw new ValidationException("OrderID and TransactionID must be informed");
        }
    }

    private void validateProductInformed(OrderProduct product) {
        if (isEmpty(product.product()) || isEmpty(product.product().code())) {
            throw new ValidationException("Product must be informed");
        }
    }

    private void validateExistingProduct(String code) {
        if (!productRepository.existsByCode(code)) {
            throw new ValidationException("Product does not exists in database");
        }
    }

    private void createValidation(Event event, boolean success) {
        var validation = new Validation();
        validation.setOrderId(event.payload().id());
        validation.setTransactionId(event.transactionId());
        validation.setSuccess(success);
        validationRepository.save(validation);
    }

    private void handleSuccess(Event event) {
        var successEvent = new Event(
                event.id(),
                event.transactionId(),
                event.orderId(),
                event.payload(),
                CURRENT_SOURCE,
                SUCCESS,
                event.eventHistory(),
                event.createdAt()
        );
        addHistory(successEvent, "Products has been validated successfully");
    }

    private void addHistory(Event event, String message) {
        var history = new History(
                event.source(),
                event.status(),
                message,
                LocalDateTime.now()
        );
        event.addToHistory(history);
    }

    private void handleFailCurrentNotExecuted(Event event, String message) {
        var failEvent = new Event(
                event.id(),
                event.transactionId(),
                event.orderId(),
                event.payload(),
                CURRENT_SOURCE,
                FAIL,
                event.eventHistory(),
                event.createdAt()
        );
        addHistory(failEvent, "Fail to validate products: ".concat(message));
    }

    public void rollbackEvent(Event event) {
        changeValidationToFail(event);
        var rollbackEvent = new Event(
                event.id(),
                event.transactionId(),
                event.orderId(),
                event.payload(),
                CURRENT_SOURCE,
                ROLLBACK_PENDING,
                event.eventHistory(),
                event.createdAt()
        );
        addHistory(rollbackEvent, "Rollback executed on product validation!");
        jsonUtils.toJson(rollbackEvent).ifPresent(kafkaProducer::sendEvent);
    }

    private void changeValidationToFail(Event event) {
        validationRepository
                .findByOrderIdAndTransactionId(event.orderId(), event.transactionId())
                .ifPresentOrElse(validation -> {
                            validation.setSuccess(false);
                            validationRepository.save(validation);
                        },
                        () -> createValidation(event, false));
    }
}
