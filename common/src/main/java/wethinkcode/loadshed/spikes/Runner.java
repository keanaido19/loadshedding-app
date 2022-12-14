package wethinkcode.loadshed.spikes;

import wethinkcode.loadshed.spikes.controllers.connection.ConnectionController;
import wethinkcode.loadshed.spikes.controllers.connection.Publisher;
import wethinkcode.loadshed.spikes.controllers.connection.Receiver;
import wethinkcode.loadshed.spikes.enums.ConnectionType;
import wethinkcode.loadshed.spikes.enums.DestinationType;

import static wethinkcode.loadshed.spikes.enums.ConnectionType.PUBLISHER;

public class Runner {
    public static void run(
            ConnectionType connectionType,
            DestinationType destinationType,
            String destination
    )
            throws Exception
    {
        ConnectionController connectionController =
                new ConnectionController(destinationType, destination);

        Thread t;

        switch (connectionType) {
            case PUBLISHER:
                t = new Publisher(connectionController);
                break;
            case RECEIVER:
            default:
                t = new Receiver(connectionController);

        }

        t.start();

        if (connectionType == PUBLISHER) return;

        while (t.isAlive()) {
            System.out.println("I'm alive!");
            Thread.sleep(1000);
        }
    }
}
