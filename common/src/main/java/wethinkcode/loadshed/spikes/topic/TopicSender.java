package wethinkcode.loadshed.spikes.topic;

import wethinkcode.loadshed.spikes.Runner;

import static wethinkcode.loadshed.spikes.enums.ConnectionType.PUBLISHER;
import static wethinkcode.loadshed.spikes.enums.DestinationType.TOPIC;

public class TopicSender {
    public static void main(String[] args) throws Exception {
        Runner.run(PUBLISHER, TOPIC, "stage");
    }
}
