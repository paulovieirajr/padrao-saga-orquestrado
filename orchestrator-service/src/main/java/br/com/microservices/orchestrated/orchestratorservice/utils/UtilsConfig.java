package br.com.microservices.orchestrated.orchestratorservice.utils;

import io.github.javawinds.dto.orchestrated.Event;
import io.github.javawinds.utils.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfig {

    @Bean
    public JsonUtils<Event> jsonUtils() {
        return new JsonUtils<>(Event.class);
    }
}
