package br.com.microservices.log;

/**
 * Enum to store custom log messages for Kafka events.
 * <p>Example of use:</p>
 * <pre>{@code
 *              log.info(SEND_EVENT.getMessage(), ORDER_SERVICE.getMicroservice(), topic, data);
 *              }</pre>
 * <p>Output:</p>
 * <pre>{@code
 *              [ORDER SERVICE] - Sending event to Kafka Topic [start-saga] with data [payload]
 *              }</pre>
 *
 * @version 0.0.1
 * @see br.com.microservices.enums.Microservice
 * @see br.com.microservices.enums.Topic
 * @see org.slf4j.Logger
 * @see org.slf4j.LoggerFactory
 */
public enum CustomLog {
    SEND_EVENT_LOG("[{}] - Sending event to Kafka Topic [{}] with data [{}]"),
    RECEIVED_EVENT_LOG("[{}] - Received event from Kafka Topic [{}] with data [{}]"),
    FAILED_EVENT_LOG("[{}] - Failed to send event to Kafka Topic [{}] with data [{}]");

    private final String message;

    CustomLog(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
