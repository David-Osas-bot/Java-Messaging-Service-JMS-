package com.myjavatest.javamessagingsystem.resources;


import com.user.RequestDTO.LoginRequestDTO;
import com.user.ResponseDTO.UserResponseDTO;
import com.user.Service.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService userService;

    @POST
    @Path("/login")
    public Response login(LoginRequestDTO request) {

        UserResponseDTO user = userService.login(request);

        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\":\"Invalid email or password\"}")
                    .build();
        }

        return Response.ok(user).build();
    }
}
