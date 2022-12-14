package wethinkcode.loadshed.spikes.topic;

import wethinkcode.loadshed.spikes.Runner;

import static wethinkcode.loadshed.spikes.enums.ConnectionType.RECEIVER;
import static wethinkcode.loadshed.spikes.enums.DestinationType.TOPIC;

public class TopicReceiver {
    public static void main(String[] args) throws Exception {
        Runner.run(RECEIVER, TOPIC, "stage");
    }
}
