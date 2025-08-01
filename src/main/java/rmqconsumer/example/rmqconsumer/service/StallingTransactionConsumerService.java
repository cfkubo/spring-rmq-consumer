// package rmqconsumer.example.rmqconsumer.service;

// import org.springframework.amqp.core.AcknowledgeMode;
// import org.springframework.amqp.core.Message;
// import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.stereotype.Service;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import java.util.concurrent.TimeUnit;
// import java.util.concurrent.atomic.AtomicInteger;

// /**
//  * A consumer service designed to simulate a slow consumer that will trigger
//  * a RabbitMQ consumer timeout.
//  */
// @Service
// public class StallingTransactionConsumerService {

//     private static final Logger logger = LoggerFactory.getLogger(StallingTransactionConsumerService.class);
//     private static final long STALL_DURATION_SECONDS = 60; // 60 seconds

//     public static final AtomicInteger stallingConsumed = new AtomicInteger();

//     /**
//      * Consumes messages from the 'classic.transactions' queue.
//      * This listener is configured for manual acknowledgment and a prefetch count of 1
//      * to ensure it processes only one message at a time.
//      * It will then sleep for 60 seconds before acknowledging the message,
//      * which will exceed the 5000ms (5-second) timeout you set via policy.
//      *
//      * @param payload The deserialized message payload.
//      * @param message The incoming AMQP message.
//      * @param ack The Acknowledgment object for manual control.
//      */
//     @RabbitListener(
//         queues = "classic.transactions",
//         ackMode = "MANUAL",
//         containerFactory = "stallingRabbitListenerContainerFactory"
//     )
//     public void consumeClassic(String payload, Message message, AcknowledgeMode ack) {
//         try {
//             stallingConsumed.incrementAndGet();
//             logger.info("[{}] Classic Queue (Stalling): Received message -> {}", Thread.currentThread().getName(), payload);

//             // --- SIMULATE VERY LONG PROCESSING TO CAUSE TIMEOUT ---
//             logger.info("[{}] Classic Queue (Stalling): Starting a {} second delay...", Thread.currentThread().getName(), STALL_DURATION_SECONDS);
//             TimeUnit.SECONDS.sleep(STALL_DURATION_SECONDS);
//             logger.info("[{}] Classic Queue (Stalling): Finished the delay. Acknowledging message...", Thread.currentThread().getName());
//             // --- END SIMULATION ---

//             // This acknowledge call will likely fail because the channel will have been closed by RabbitMQ
//             ack.acknowledge();

//         } catch (InterruptedException e) {
//             Thread.currentThread().interrupt(); // Restore the interrupted status
//             logger.error("[{}] Classic Queue (Stalling): Processing interrupted for message -> {}", Thread.currentThread().getName(), payload);
//             // In a real-world scenario, you might not want to requeue on interruption.
//             // ack.nack(false);
//         } catch (Exception e) {
//             logger.error("[{}] Classic Queue (Stalling): Error processing message -> {}, Requeuing message.", Thread.currentThread().getName(), e.getMessage());
//             // This is crucial for manual ack: nack on error. In this case, we might want it to go to DLQ.
//             // ack.nack(false);
//         }
//     }
// }