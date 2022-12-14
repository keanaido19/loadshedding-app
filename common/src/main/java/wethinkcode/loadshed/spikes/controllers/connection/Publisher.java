package wethinkcode.loadshed.spikes.controllers.connection;

import jakarta.jms.*;

import java.util.Scanner;

public class Publisher extends Thread {
    private final ConnectionController connectionController;
    private final Session session;

    public Publisher(ConnectionController connectionController) {
        this.connectionController = connectionController;
        this.session = connectionController.getSession();
    }

    @Override
    public void run() {
        try {
            connectionController.start();

            MessageProducer producer =
                    connectionController.getMessageProducer();

            Scanner input = new Scanner(System.in);

            String response;
            do {
                System.out.println("Enter message: ");
                response = input.nextLine();
                TextMessage msg = session.createTextMessage(response);

                producer.send(msg);

            } while (!response.equalsIgnoreCase("Shutdown"));

            connectionController.close();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
