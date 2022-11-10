package wethinkcode.web;

import com.google.common.annotations.VisibleForTesting;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;

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
                .create(config -> {
                            config.registerPlugin(getConfiguredOpenApiPlugin());
                            config.addStaticFiles("/templates", Location.CLASSPATH);
                })
                .routes(() -> {
                    get("/stage", EndPointHandler::getLoadSheddingStage);
                    get( "/towns/{province}", EndPointHandler::getTowns);
                    get("/{province}/{place}/{loadsheddingstage}",
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
}
