package wethinkcode.web;

import io.javalin.http.Context;
import io.javalin.http.ServiceUnavailableResponse;
import io.javalin.plugin.openapi.annotations.*;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import wethinkcode.places.model.Town;
import wethinkcode.responses.error.ErrorResponse;
import wethinkcode.schedule.transfer.ScheduleDO;
import wethinkcode.stage.StageDO;

public class EndPointHandler {
    private static final String domain = "http://localhost:";

    public static final String stageServiceUrl = domain + 7001;
    private static final String placeNameServiceUrl = domain + 7000;
    private static final String scheduleServiceUrl = domain + 7002;

    @OpenApi(
            summary = "Get current load shedding stage",
            operationId = "getLoadSheddingStage",
            path = "/stage",
            method = HttpMethod.GET,
            tags = {"Stage Service"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            content = {
                                    @OpenApiContent( from = StageDO.class)
                            }
                    ),
                    @OpenApiResponse(
                            status = "503",
                            content = {
                                    @OpenApiContent( from = String.class)
                            }
                    )
            }
    )
    public static void getLoadSheddingStage(Context context) {
        context.header("Access-Control-Allow-Origin","*");
        if (null == WebService.stage){
            throw new ServiceUnavailableResponse();
        }
        context.json(WebService.stage);
    }

    @OpenApi(
            summary = "Get the name of the towns in a given province",
            operationId = "getTowns",
            path = "/towns/{province}",
            method = HttpMethod.GET,
            pathParams = {
                    @OpenApiParam(
                            name = "province",
                            description = "The name of the province",
                            required = true
                    )
            },
            tags = {"PlaceName Service"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            content = {
                                    @OpenApiContent( from = Town[].class)
                            }
                    ),
                    @OpenApiResponse(
                            status = "503",
                            content = {
                                    @OpenApiContent( from = String.class)
                            }
                    )
            }
    )
    public static void getTowns(Context context) {
        context.header("Access-Control-Allow-Origin","*");
        final String province = context.pathParam( "province" );
        try {
            HttpResponse<JsonNode> response =
                    Unirest.get(
                            placeNameServiceUrl + "/towns/" + province
                    ).asJson();

            context.status(response.getStatus());
            context.json(response.getBody().toString());
        } catch (UnirestException e) {
            throw new ServiceUnavailableResponse();
        }
    }

    @OpenApi(
            summary = "Get the load shedding schedule for a given load " +
                    "shedding stage in a give town",
            operationId = "getSchedule",
            path = "/{province}/{place}",
            method = HttpMethod.GET,
            pathParams = {
                    @OpenApiParam(
                            name = "province",
                            description = "The name of the province",
                            required = true
                    ),
                    @OpenApiParam(
                            name = "place",
                            description = "The name of the place",
                            required = true
                    ),
            },
            tags = {"Schedule Service"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            content = {
                                    @OpenApiContent( from = ScheduleDO.class)
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            content = {
                                    @OpenApiContent( from = ErrorResponse.class)
                            }
                    ),
                    @OpenApiResponse(
                            status = "503",
                            content = {
                                    @OpenApiContent( from = String.class)
                            }
                    )
            }
    )
    public static void getSchedule(Context context) {
        String province =
                context.pathParamAsClass("province", String.class).get();
        String place =
                context.pathParamAsClass("place", String.class).get();

        context.header("Access-Control-Allow-Origin","*");
        try {
            HttpResponse<JsonNode> response =
                    Unirest.get(
                            scheduleServiceUrl + "/" +
                                    province + "/" +
                                    place
                    ).asJson();

            context.status(response.getStatus());
            context.json(response.getBody().toString());
        } catch (UnirestException e) {
            throw new ServiceUnavailableResponse();
        }
    }
}
