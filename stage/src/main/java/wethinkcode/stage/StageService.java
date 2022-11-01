package wethinkcode.stage;

import com.google.common.annotations.VisibleForTesting;
import io.javalin.Javalin;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

/**
 * I provide a REST API that reports the current loadshedding "stage". I provide
 * two endpoints:
 * <dl>
 * <dt>GET /stage
 * <dd>report the current stage of loadshedding as a JSON serialisation
 *      of a {@code StageDO} data/transfer object
 * <dt>POST /stage
 * <dd>set a new loadshedding stage/level by POSTing a JSON-serialised {@code StageDO}
 *      instance as the body of the request.
 * </ul>
 */
public class StageService
{
    public static final int DEFAULT_STAGE = 0; // no loadshedding. Ha!

    public static final int DEFAULT_PORT = 7001;

    public static void main( String[] args ){
        final StageService svc = new StageService().initialise();
        svc.start();
    }

    private int loadSheddingStage;

    private Javalin server;

    private int servicePort;

    @VisibleForTesting
    StageService initialise(){
        return initialise( DEFAULT_STAGE );
    }

    @VisibleForTesting
    StageService initialise( int initialStage ){
        loadSheddingStage = initialStage;
        assert loadSheddingStage >= 0;

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
            get("/stage", this::getLoadSheddingStage);
            post("/stage", this::postLoadSheddingStage);
        });
    }

    private void getLoadSheddingStage(Context context) {
        context.json(new StageDO(loadSheddingStage));
    }

    private void postLoadSheddingStage(Context context) {
        try {
            StageDO stageDO = context.bodyAsClass(StageDO.class);
            if (stageDO.getStage() < 0 || stageDO.getStage() > 8)
                throw new IllegalArgumentException();
            loadSheddingStage = stageDO.getStage();
        } catch (Exception e) {
            throw new BadRequestResponse();
        }
    }
}
