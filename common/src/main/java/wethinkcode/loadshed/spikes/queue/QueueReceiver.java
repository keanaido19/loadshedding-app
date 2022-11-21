package wethinkcode.loadshed.spikes.queue;

import wethinkcode.loadshed.spikes.Runner;

import static wethinkcode.loadshed.spikes.enums.ConnectionType.RECEIVER;
import static wethinkcode.loadshed.spikes.enums.DestinationType.QUEUE;

public class QueueReceiver {
    public static void main(String[] args) throws Exception {
        Runner.run(RECEIVER, QUEUE, "stage");
    }
}
