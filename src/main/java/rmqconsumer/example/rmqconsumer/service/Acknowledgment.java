package rmqconsumer.example.rmqconsumer.service;

public interface Acknowledgment {

    void acknowledge();

    void nack(boolean b, boolean c);

}
