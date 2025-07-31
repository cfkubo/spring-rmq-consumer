// package rmqconsumer.example.rmqconsumer.views;

// import com.vaadin.flow.component.html.H1;
// import com.vaadin.flow.component.html.Paragraph;
// import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// import com.vaadin.flow.router.Route;
// import org.springframework.beans.factory.annotation.Autowired;
// import rmqconsumer.example.rmqconsumer.service.RabbitMQStatsService;
// import rmqconsumer.example.rmqconsumer.service.TransactionConsumerService;

// @Route("")
// public class HomeView extends VerticalLayout {

//     @Autowired
//     public HomeView(RabbitMQStatsService statsService) {
//         add(new H1("RabbitMQ Consumer Dashboard"));

//         add(new Paragraph("Consuming from queues:"));

//         String[] queues = {"classic.transactions", "quorum.transactions", "stream.transactions"};
//         for (String queue : queues) {
//             int count = statsService.getMessageCount(queue);
//             int consumed = 0;
//             if (queue.equals("classic.transactions")) consumed = TransactionConsumerService.classicConsumed.get();
//             if (queue.equals("quorum.transactions")) consumed = TransactionConsumerService.quorumConsumed.get();
//             if (queue.equals("stream.transactions")) consumed = TransactionConsumerService.streamConsumed.get();

//             add(new Paragraph(queue + " - Messages in queue: " + count + ", Consumed: " + consumed));
//         }
//     }
// }
