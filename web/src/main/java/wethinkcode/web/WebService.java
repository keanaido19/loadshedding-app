package wethinkcode.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.TextMessage;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import wethinkcode.loadshed.spikes.controllers.connection.ConnectionController;
import wethinkcode.stage.StageDO;

import static io.javalin.apibuilder.ApiBuilder.get;
import static wethinkcode.loadshed.spikes.enums.DestinationType.TOPIC;
import static wethinkcode.web.EndPointHandler.stageServiceUrl;

/**
 * I am the front-end web server for the LightSched project.
 * <p>
 * Remember that we're not terribly interested in the web front-end part of this
 * server, more in the way it communicates and interacts with the back-end
 * services.
 */
public class WebService
{
    public static final int DEFAULT_PORT = 5000;

    public static void main( String[] args ){
        final WebService svc = new WebService().initialise();
        new Thread(svc::getLoadSheddingStage).start();
        new Thread(svc::stageTopicReceiver).start();
        svc.start();
    }

    public static StageDO stage;

    private final ObjectMapper objectMapper = new ObjectMapper();

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
                .create(config -> {
                            config.registerPlugin(getConfiguredOpenApiPlugin());
                            config.addStaticFiles("/templates", Location.CLASSPATH);
                })
                .routes(() -> {
                    get("/stage", EndPointHandler::getLoadSheddingStage);
                    get( "/towns/{province}", EndPointHandler::getTowns);
                    get("/{province}/{place}",
                            EndPointHandler::getSchedule);
                });
    }

    private static OpenApiPlugin getConfiguredOpenApiPlugin() {
        Info info =
                new Info()
                        .version("1.0")
                        .title("Load Shedding API")
                        .description("Endpoints for Load Shedding Api");

        OpenApiOptions options = new OpenApiOptions(info)
                .activateAnnotationScanningFor(
                        "wethinkcode.web"
                )
                .path("/swagger-docs")
                .swagger(new SwaggerOptions("/swagger-ui"))
                .reDoc(new ReDocOptions("/redoc"));
        return new OpenApiPlugin(options);
    }

    private void getLoadSheddingStage() {
        while (null == stage) {
            try {
                HttpResponse<JsonNode> response =
                        Unirest.get(stageServiceUrl + "/stage").asJson();
                if (response.getStatus() == 200) {
                    stage = objectMapper.readValue(
                            response.getBody().toString(),
                            StageDO.class
                    );
                }
            } catch (Exception ignored) {}
        }
    }

    private void stageTopicReceiver() {
        while (true) {
            try {
                ConnectionController connectionController =
                        new ConnectionController(TOPIC, "stage");
                connectionController.start();

                MessageConsumer consumer =
                        connectionController.getMessageConsumer();

                String response;
                while (true) {
                    Message msg = consumer.receive();
                    response = ((TextMessage) msg).getText().trim();
                    stage =  objectMapper.readValue(response, StageDO.class);
                }
            } catch (Exception ignored) {}
        }
    }
}
