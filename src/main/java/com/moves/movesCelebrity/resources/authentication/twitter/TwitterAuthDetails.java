package com.moves.movesCelebrity.resources.authentication.twitter;

import com.moves.movesCelebrity.models.api.APIResponse;
import com.moves.movesCelebrity.models.api.user.UserAccessTokenTwitter;
import com.moves.movesCelebrity.resources.helpers.UserAuthenticationHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.moves.movesCelebrity.configuration.MovesAppConfiguration.ERROR_UNEXPECTED;

@Path("/twitter")
@Api("Twitter Authentication Data")
public class TwitterAuthDetails {
    private static Logger LOGGER = LoggerFactory.getLogger(TwitterAuthDetails.class);

    UserAuthenticationHelper helper = new UserAuthenticationHelper();

    @PUT
    @Path("/auth")
    @ApiOperation(value = "Authentication",notes="Twitter Authentication Details",
            response = APIResponse.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "UserAgent",
            value = "ios",
            dataType = "string",
            paramType = "header")})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public APIResponse saveAuthDetails(UserAccessTokenTwitter auth) throws Exception {
        try {
            return helper.addTwitterAuth(auth);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            APIResponse response = new APIResponse();
            response.setError(ERROR_UNEXPECTED);
            return response;
        }
    }
}
