package com.moves.movesCelebrity.resources.users;

import com.moves.movesCelebrity.models.api.APIResponse;
import com.moves.movesCelebrity.models.api.user.UserProfile;
import com.moves.movesCelebrity.resources.helpers.UserResourceHelper;
import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.PATCH;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static com.moves.movesCelebrity.configuration.MovesAppConfiguration.ERROR_UNEXPECTED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/user")
@Api("User Profile")
public class UserResource {

    private static Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private UserResourceHelper helper = new UserResourceHelper();

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed("1")
    @ApiOperation(
            value = "Create user",
            notes = "To create a new user",
            response = APIResponse.class
    )
    @ApiImplicitParams({@ApiImplicitParam(name = "DeviceType",
            value = "ios",//android, web
            dataType = "string",
            paramType = "header")})
    public APIResponse createUser(@Valid UserProfile request) {
        try {
            return helper.addNewUser(request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            APIResponse response = new APIResponse();
            response.setError(ERROR_UNEXPECTED);
            return response;
        }
    }

    @PUT
    @Path("/signIn")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @ApiOperation(
            value = "Sign in User",
            notes = "Sign in the user and return user token",
            response = APIResponse.class
    )
    @ApiImplicitParam(name = "DeviceType",
            value = "ios",
            dataType = "string",
            paramType = "header")
    public APIResponse signInUser(@Valid UserProfile request){
        try{
            return helper.signInUser(request);
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            APIResponse response = new APIResponse();
            response.setError(ERROR_UNEXPECTED);
            return response;
        }
    }


    @PATCH
    @Path("/password")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(APPLICATION_JSON)
    @RolesAllowed("1")
    @ApiOperation(
            value = "Update Password",
            notes = "To update the current password",
            response = APIResponse.class
    )
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization",
            value = "Bearer 234df4md223m23mk2mdw",
            dataType = "string",
            paramType = "header"),@ApiImplicitParam(name = "DeviceType",
            value = "ios",//android, web
            dataType = "string",
            paramType = "header")})
    public APIResponse updatePassword(@NotNull @FormParam("currentPassword") String currentPassword,
                                      @NotNull @FormParam("newPassword") String newPassword,
                                      @ApiParam(hidden = true) @Auth UserProfile profile) {
        APIResponse response = new APIResponse();
        try {
//            EmployeeResourceHelper helper = new EmployeeResourceHelper();
//            Boolean update = helper.updatePassword(profile.getId(), profile.getEmail(),
//                    currentPassword, newPassword);
//            if (update == null) {
//                response.setError("Current Password entered is incorrect");
//            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            response.setError(ERROR_UNEXPECTED);
        }

        return response;
    }
}

