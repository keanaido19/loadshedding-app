package wethinkcode.web;

import com.google.common.annotations.VisibleForTesting;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.ServiceUnavailableResponse;
import io.javalin.http.staticfiles.Location;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

import static io.javalin.apibuilder.ApiBuilder.get;

/**
 * I am the front-end web server for the LightSched project.
 * <p>
 * Remember that we're not terribly interested in the web front-end part of this
 * server, more in the way it communicates and interacts with the back-end
 * services.
 */
public class WebService
{
    private final String domain = "http://localhost:";

    private final String stageServiceUrl = domain + 7001;
    private final String placeNameServiceUrl = domain + 7000;
    private final String scheduleServiceUrl = domain + 7002;

    public static final int DEFAULT_PORT = 5000;

    public static void main( String[] args ){
        final WebService svc = new WebService().initialise();
        svc.start();
    }

    private Javalin server;

    private int servicePort;

    @VisibleForTesting
    WebService initialise(){
        server = configureHttpServer();
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

    private void configureHttpClient(){
    }

    private Javalin configureHttpServer(){
        return Javalin
                .create(config ->
                        config.addStaticFiles("/templates", Location.CLASSPATH)
                )
                .routes(() -> {
                    get("/stage", this::getLoadSheddingStage);
                    get( "/towns/{province}", this::getTowns);
                    get("/{province}/{place}/{loadsheddingstage}",
                            this::getSchedule);
                });
    }

    private void getLoadSheddingStage(Context context) {
        try {
            HttpResponse<JsonNode> response =
                    Unirest.get(stageServiceUrl + "/stage").asJson();

            context.header("Access-Control-Allow-Origin","*");
            context.status(response.getStatus());
            context.json(response.getBody().toString());
        } catch (UnirestException e) {
            throw new ServiceUnavailableResponse();
        }
    }

    private void getTowns(Context context) {
        final String province = context.pathParam( "province" );
        try {
            HttpResponse<JsonNode> response =
                    Unirest.get(
                            placeNameServiceUrl + "/towns/" + province
                    ).asJson();

            context.header("Access-Control-Allow-Origin","*");
            context.status(response.getStatus());
            context.json(response.getBody().toString());
        } catch (UnirestException e) {
            throw new ServiceUnavailableResponse();
        }
    }

    private void getSchedule(Context context) {
        String province =
                context.pathParamAsClass("province", String.class).get();
        String place =
                context.pathParamAsClass("place", String.class).get();
        int stage =
                context.pathParamAsClass("loadsheddingstage", Integer.class)
                        .get();
        try {
            HttpResponse<JsonNode> response =
                    Unirest.get(
                            scheduleServiceUrl + "/" +
                                    province + "/" +
                                    place + "/" +
                                    stage
                    ).asJson();

            context.header("Access-Control-Allow-Origin","*");
            context.status(response.getStatus());
            context.json(response.getBody().toString());
        } catch (UnirestException e) {
            throw new ServiceUnavailableResponse();
        }
    }
}
