package rmqconsumer.example.rmqconsumer.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// No longer need Acknowledgment import for AUTO ack mode unless you want to use it for specific scenarios
// import org.springframework.amqp.support.Acknowledgment;
import org.springframework.amqp.core.Message; // Keep Message import if you still want to access headers
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TransactionConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionConsumerService.class);

    public static final AtomicInteger classicConsumed = new AtomicInteger();
    public static final AtomicInteger quorumConsumed = new AtomicInteger();
    public static final AtomicInteger streamConsumed = new AtomicInteger();

    /**
     * Consumes messages from the 'classic.transactions' queue.
     * In AUTO ack mode:
     * - If method completes successfully, message is ACKed.
     * - If method throws an exception, message is NACKed and, by default, requeued.
     *
     * @param payload The deserialized message payload (e.g., String or your custom DTO).
     * @param message The incoming AMQP message (optional, but useful for headers).
     */
    @RabbitListener(queues = "classic.transactions", ackMode = "AUTO") // Changed ackMode
    public void consumeClassic(String payload, Message message) { // Removed Acknowledgment ack parameter
        classicConsumed.incrementAndGet();
        logger.info("[{}] Classic Queue: Received message -> {}", Thread.currentThread().getName(), payload);

        // Process the payload here.
        // If this method completes without throwing an exception, it's ACKED automatically.

        // Example: Simulate an error for testing NACK behavior
        // if (payload.contains("error")) {
        //     logger.error("Simulating an error for payload: {}", payload);
        //     throw new RuntimeException("Simulated processing error!");
        // }

        logger.info("[{}] Classic Queue: Successfully processed message -> {}", Thread.currentThread().getName(), payload);
    }

    /**
     * Consumes messages from the 'quorum.transactions' queue.
     *
     * @param payload The deserialized message payload (e.g., String or your custom DTO).
     * @param message The incoming AMQP message (optional, but useful for headers).
     */
    @RabbitListener(queues = "quorum.transactions", ackMode = "AUTO") // Changed ackMode
    public void consumeQuorum(String payload, Message message) { // Removed Acknowledgment ack parameter
        quorumConsumed.incrementAndGet();
        logger.info("[{}] Quorum Queue: Received message -> {}", Thread.currentThread().getName(), payload);
        // Process the payload here
        logger.info("[{}] Quorum Queue: Successfully processed message -> {}", Thread.currentThread().getName(), payload);
    }

    /**
     * Consumes messages from the 'stream.transactions' queue.
     *
     * @param payload The deserialized message payload (e.g., String or your custom DTO).
     * @param message The incoming AMQP message (optional, but useful for headers).
     */
    @RabbitListener(queues = "stream.transactions", ackMode = "AUTO") // Changed ackMode
    public void consumeStream(String payload, Message message) { // Removed Acknowledgment ack parameter
        streamConsumed.incrementAndGet();
        logger.info("[{}] Stream Queue: Received message -> {}", Thread.currentThread().getName(), payload);
        // Process the payload here
        logger.info("[{}] Stream Queue: Successfully processed message -> {}", Thread.currentThread().getName(), payload);
    }
}