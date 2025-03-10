package io.github.paulovieirajr.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Utility class to serialize and deserialize objects to and from JSON.
 * There is no need to handle exceptions when using this class, as it returns an {@link Optional} object.
 *
 * @param <T> the type of the object to serialize and deserialize
 *           <p>
     *           Example:
     *           <pre>{@code
     *           JsonUtils<Product> jsonUtils = new JsonUtils<>(Product.class);
     *
     *           // Serialize object to JSON
     *           Optional<String> json = jsonUtils.toJson(product);
     *
     *           // Deserialize JSON to object
     *           Optional<Product> product = jsonUtils.toType(json);
     *           }</pre>
 *           </p>
 * @see ObjectMapper
 */
public final class JsonUtils<T> {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Class<T> type;

    public JsonUtils(Class<T> type) {
        this.type = type;
    }

    /**
     * Serialize an object to JSON.
     *
     * @param t the object to serialize
     * @return an {@link Optional} containing the JSON string if the serialization was successful, or an empty {@link Optional} otherwise
     */
    public Optional<String> toJson(T t) {
        try {
            return Optional.ofNullable(objectMapper.writeValueAsString(t));
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize object to JSON: {}", t, e);
            return Optional.empty();
        }
    }

    /**
     * Deserialize JSON to an object.
     *
     * @param json the JSON string to deserialize
     * @return an {@link Optional} containing the object if the deserialization was successful, or an empty {@link Optional} otherwise
     */
    public Optional<T> toType(String json) {
        try {
            return Optional.ofNullable(objectMapper.readValue(json, type));
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize JSON to type {}: {}", type.getSimpleName(), json, e);
            return Optional.empty();
        }
    }
}

