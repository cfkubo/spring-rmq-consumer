package rmqconsumer.example.rmqconsumer.service;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitMQStatsService {

    private final RabbitAdmin rabbitAdmin;

    public RabbitMQStatsService(ConnectionFactory connectionFactory) {
        this.rabbitAdmin = new RabbitAdmin(connectionFactory);
    }

    public int getMessageCount(String queueName) {
        Map<Object props = rabbitAdmin.getQueueProperties(queueName);
        if (props != null && props.containsKey("QUEUE_MESSAGE_COUNT")) {
            return (int) props.get("QUEUE_MESSAGE_COUNT");
        }
        return -1;
    }
}