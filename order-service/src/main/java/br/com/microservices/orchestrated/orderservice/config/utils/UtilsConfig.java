package br.com.microservices.orchestrated.orderservice.config.utils;

import io.github.paulovieirajr.dto.services.Event;
import io.github.paulovieirajr.utils.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfig {

    @Bean
    public JsonUtils<Event> jsonUtils() {
        return new JsonUtils<>(Event.class);
    }
}
