package br.com.microservices.orchestrated.orchestratorservice.utils;

import br.com.microservices.dto.orchestrated.Event;
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
