package wethinkcode.loadshed.spikes.controllers.connection;

import jakarta.jms.*;
import org.apache.qpid.jms.JmsConnectionFactory;
import wethinkcode.loadshed.spikes.enums.DestinationType;

public class ConnectionController {
    private final Connection connection;
    private final Session session;
    private final Destination d;


    public ConnectionController(
            DestinationType destinationType,
            String destination
    )
            throws JMSException
    {
        JmsConnectionFactory factory =
                new JmsConnectionFactory("amqp://localhost:5672");
        connection =
                factory.createConnection("admin", "password");

        session =
                connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        switch (destinationType) {
            case TOPIC ->
                d = session.createTopic(destination);

            case QUEUE, default ->
                d = session.createQueue(destination);

        }
    }

    public void start() throws JMSException {
        connection.start();
    }

    public Session getSession() {
        return session;
    }

    public MessageConsumer getMessageConsumer() throws JMSException {
        return session.createConsumer(d);
    }

    public MessageProducer getMessageProducer() throws JMSException {
        return session.createProducer(d);
    }

    public void close() throws JMSException {
        connection.close();
    }
}
