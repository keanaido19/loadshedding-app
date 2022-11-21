package wethinkcode.loadshed.spikes.controllers.connection;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.TextMessage;

public class Receiver extends Thread {

    private final ConnectionController connectionController;

    public Receiver(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    @Override
    public void run() {
        try {
            connectionController.start();

            MessageConsumer consumer =
                    connectionController.getMessageConsumer();

            System.out.println("Waiting for messages...");

            String response;
            do {
                Message msg = consumer.receive();
                response = ((TextMessage) msg).getText().trim();

                System.out.println("Received = "+response);

            } while (!response.equalsIgnoreCase("Shutdown"));

            connectionController.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
