package wethinkcode.loadshed.spikes.queue;

import wethinkcode.loadshed.spikes.Runner;

import static wethinkcode.loadshed.spikes.enums.ConnectionType.PUBLISHER;
import static wethinkcode.loadshed.spikes.enums.DestinationType.QUEUE;

public class QueueSender {
    public static void main(String[] args) throws Exception {
        Runner.run(PUBLISHER, QUEUE, "stage");
    }
}
