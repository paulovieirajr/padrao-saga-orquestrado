package br.com.microservices.orchestrated.orderservice.core.service;

import br.com.microservices.orchestrated.orderservice.core.document.Event;
import br.com.microservices.orchestrated.orderservice.core.document.Order;
import br.com.microservices.orchestrated.orderservice.core.dto.OrderRequest;
import br.com.microservices.orchestrated.orderservice.core.kafka.producer.SagaProducer;
import br.com.microservices.orchestrated.orderservice.core.repository.OrderRepository;
import io.github.javawinds.utils.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final EventService eventService;
    private final SagaProducer sagaProducer;
    private final JsonUtils<Event> jsonUtils;
    private final OrderRepository orderRepository;

    public OrderService(EventService eventService,
                        SagaProducer sagaProducer,
                        JsonUtils<Event> jsonUtils,
                        OrderRepository orderRepository) {
        this.eventService = eventService;
        this.sagaProducer = sagaProducer;
        this.jsonUtils = jsonUtils;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderRequest orderRequest) {
        Order savedOrder = orderRepository.save(Order.fromRequestDto(orderRequest));
        Optional<String> json = jsonUtils.toJson(createPayload(savedOrder));
        json.ifPresent(sagaProducer::sendEvent);
        return savedOrder;
    }

    private Event createPayload(Order order) {
        return eventService.save(Event.fromOrder(order));
    }
}

