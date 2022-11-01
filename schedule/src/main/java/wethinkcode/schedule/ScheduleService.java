package wethinkcode.schedule;

import com.google.common.annotations.VisibleForTesting;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import wethinkcode.schedule.transfer.DayDO;
import wethinkcode.schedule.transfer.ScheduleDO;
import wethinkcode.schedule.transfer.SlotDO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.javalin.apibuilder.ApiBuilder.get;
import static java.net.http.HttpRequest.BodyPublishers.noBody;

/**
 * I provide a REST API providing the current loadshedding schedule for a
 * given town (in a specific province) at a given loadshedding stage.
 */
public class ScheduleService
{
    public static final int DEFAULT_STAGE = 0; // no loadshedding. Ha!

    public static final int DEFAULT_PORT = 7002;

    private Javalin server;

    private int servicePort;

    public static void main( String[] args ){
        final ScheduleService svc = new ScheduleService().initialise();
        svc.start();
    }

    @VisibleForTesting
    ScheduleService initialise(){
        server = initHttpServer();
        return this;
    }

    public void start(){
        start( DEFAULT_PORT );
    }

    @VisibleForTesting
    void start( int networkPort ){
        servicePort = networkPort;
        run();
    }

    public void stop(){
        server.stop();
    }

    public void run(){
        server.start( servicePort );
    }

    private Javalin initHttpServer(){
        return Javalin.create().routes(() -> {
            get("/{province}/{place}/{loadsheddingstage}", this::getSchedule);
            get("/*", ctx -> ctx.status(HttpStatus.BAD_REQUEST));
        });
    }

    private boolean checkIfPlaceIsValid(String province, String place) {
        try {
            String p1 = province.replace(" ", "%20");
            String p2 = place.replace(" ", "%20");

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder(
                    URI.create("http://localhost:7000/" + p1 + "/" + p2)
            ).method("head", noBody()).build();

            var response =
                    client.send(request, HttpResponse.BodyHandlers.discarding());

            return HttpStatus.OK.getCode() == response.statusCode();
        } catch (IOException | InterruptedException e) {
            return List.of("Free State", "Eastern Cape", "Gauteng",
                    "North West", "Mpumalanga", "Northern Cape", "Limpopo",
                    "KwaZulu-Natal", "Western Cape").contains(province);
        }
    }

    private void getSchedule(Context context) {
        String province =
                context.pathParamAsClass("province", String.class).get();
        String place =
                context.pathParamAsClass("place", String.class).get();
        int stage =
                context.pathParamAsClass("loadsheddingstage", Integer.class)
                .check(
                        value -> List.of(0, 1, 2, 3, 4, 5, 6, 7, 8)
                                .contains(value),
                        "Invalid Load Shedding stage!"
                )
                .get();

        if (!checkIfPlaceIsValid(province, place)) {
            context.status(HttpStatus.NOT_FOUND);
            context.json(emptySchedule());
            return;
        }

        context.json(mockSchedule());
    }

    // There *must* be a better way than this...
    // See Steps 4 and 5 (the optional ones!) in the course notes.
    Optional<ScheduleDO> getSchedule( String province, String town, int stage ){
        return province.equalsIgnoreCase( "Mars" )
            ? Optional.empty()
            : Optional.of( mockSchedule() );
    }

    /**
     * Answer with a hard-coded/mock Schedule.
     * @return A non-null, slightly plausible Schedule.
     */
    private static ScheduleDO mockSchedule(){
        final List<SlotDO> slots = List.of(
            new SlotDO( LocalTime.of( 2, 0 ), LocalTime.of( 4, 0 )),
            new SlotDO( LocalTime.of( 10, 0 ), LocalTime.of( 12, 0 )),
            new SlotDO( LocalTime.of( 18, 0 ), LocalTime.of( 20, 0 ))
        );
        final List<DayDO> days = List.of(
            new DayDO( slots ),
            new DayDO( slots ),
            new DayDO( slots ),
            new DayDO( slots )
        );
        return new ScheduleDO( days );
    }

    /**
     * Answer with a non-null but empty Schedule.
     * @return The empty Schedule.
     */
    private static ScheduleDO emptySchedule(){
        final List<SlotDO> slots = Collections.emptyList();
        final List<DayDO> days = Collections.emptyList();
        return new ScheduleDO( days );
    }
}
