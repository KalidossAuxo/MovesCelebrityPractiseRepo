package com.moves.movesCelebrity.resources;

import com.moves.movesCelebrity.models.api.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/health")
@Api("/Health Check")
public class HealthCheckResource {
    public static final String PONG = "PONG";

    @Path("/ping")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("Health Check endpoint")
    public APIResponse replyToPing(){
        APIResponse response = new APIResponse();
        response.setData(PONG);
        return response;
    }
}
