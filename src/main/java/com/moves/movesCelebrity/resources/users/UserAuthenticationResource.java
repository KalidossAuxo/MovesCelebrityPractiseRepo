package com.moves.movesCelebrity.resources.users;

import com.moves.movesCelebrity.models.api.APIResponse;
import com.moves.movesCelebrity.models.api.user.UserAccessTokenFB;
import com.moves.movesCelebrity.models.api.user.UserAccessTokenInsta;
import com.moves.movesCelebrity.models.api.user.UserAccessTokenTwitter;
import com.moves.movesCelebrity.resources.helpers.UserAuthenticationResourceHelper;
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

@Path("/auth")
@Api("User Authentication")
public class UserAuthenticationResource {
    private static Logger LOGGER = LoggerFactory.getLogger(UserAuthenticationResource.class);

    UserAuthenticationResourceHelper helper = new UserAuthenticationResourceHelper();
//For Saving Twitter Authentication details(User id and Access token)
    @PUT
    @Path("/twitter")
    @ApiOperation(value = "Authentication",notes="Twitter Authentication Details",
            response = APIResponse.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "UserAgent",
            value = "ios",
            dataType = "string",
            paramType = "header")})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public APIResponse saveTwitterAuthDetails(UserAccessTokenTwitter auth) throws Exception {
        try {
            return helper.addTwitterAuth(auth);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            APIResponse response = new APIResponse();
            response.setError(ERROR_UNEXPECTED);
            return response;
        }
    }

//For saving Facebook Authentication details(User id,Short access token , Extended access token)
    @PUT
    @Path("/facebook")
    @ApiOperation(value = "Authentication",notes="Facebook Authentication Details",
            response = APIResponse.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "UserAgent",
            value = "ios",
            dataType = "string",
            paramType = "header")})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public APIResponse saveFBAuthDetails(UserAccessTokenFB auth) throws Exception {
        try {
            return helper.addFBAuth(auth);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            APIResponse response = new APIResponse();
            response.setError(ERROR_UNEXPECTED);
            return response;
        }
    }

    //For saving Instagram Authentication details(User id,Access token)
    @PUT
    @Path("/instagram")
    @ApiOperation(value = "Authentication",notes="Instagram Authentication Details",
            response = APIResponse.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "UserAgent",
            value = "ios",
            dataType = "string",
            paramType = "header")})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public APIResponse saveInstaAuthDetails(UserAccessTokenInsta auth) throws Exception {
        try {
            return helper.addInstaAuth(auth);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            APIResponse response = new APIResponse();
            response.setError(ERROR_UNEXPECTED);
            return response;
        }
    }
}
