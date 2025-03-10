package br.com.microservices.orchestrated.paymentservice.utils;

import br.com.microservices.dto.services.Event;
import br.com.microservices.utils.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfig {

    @Bean
    public JsonUtils<Event> jsonUtils() {
        return new JsonUtils<>(Event.class);
    }
}
