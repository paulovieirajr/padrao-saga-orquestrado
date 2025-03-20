package br.com.microservices.orchestrated.orderservice.core.controller;

import br.com.microservices.orchestrated.orderservice.core.document.Event;
import br.com.microservices.orchestrated.orderservice.core.dto.EventFilter;
import br.com.microservices.orchestrated.orderservice.core.service.EventService;
import io.github.javawinds.exception.ValidationException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@Validated
public class EventController {

    public static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public Event findByFilter(@Valid EventFilter filter) {
        LOGGER.info("Endpoint /api/event called with filter: {}", filter);
        if (filter.orderId() != null) {
            return findByOrderId(filter.orderId());
        }
        return findByTransactionId(filter.transactionId());
    }

    private Event findByOrderId(String orderId) {
        LOGGER.info("Searching for event with orderId: {}", orderId);
        return eventService.findEventByOrderId(orderId)
                .orElseThrow(() -> new ValidationException("Event not found by orderId"));
    }

    private Event findByTransactionId(String transactionId) {
        LOGGER.info("Searching for event with transactionId: {}", transactionId);
        return eventService.findEventByTransactionId(transactionId)
                .orElseThrow(() -> new ValidationException("Event not found by transactionId"));
    }

    @GetMapping("/all")
    public List<Event> findAll() {
        LOGGER.info("Endpoint /api/event/all called");
        return eventService.findAll();
    }
}
