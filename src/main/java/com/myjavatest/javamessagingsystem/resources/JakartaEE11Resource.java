package com.myjavatest.javamessagingsystem.resources;

import com.user.RequestDTO.UserRequestDTO;
import com.user.ResponseDTO.UserResponseDTO;
import com.user.Service.UserService;
import com.user.producer.UserEventProducer;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jakarta.ejb.EJB;

@Path("/users")
public class JakartaEE11Resource {

    @Inject
    UserService messageService;

    @EJB
    private UserEventProducer producer;

    // CREATE USER
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserRequestDTO request) {

        UserResponseDTO response = messageService.createUser(request);

        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    // GET USER BY ID
    @GET
    @Path("getOne/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerUser(@PathParam("id") int id) {

        UserResponseDTO response = messageService.getPerUser(id);

        if (response == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found")
                    .build();
        }

        return Response.ok(response).build();
    }

    // GET ALL USERS
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        List<UserResponseDTO> response = messageService.getAll();

        return Response.ok(response).build();
    }

    // UPDATE USER
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(
            @PathParam("id") int id,
            UserRequestDTO request) {

        UserResponseDTO response = messageService.update(id, request);

        if (response == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found or update failed")
                    .build();
        }

        return Response.ok(response).build();
    }

    // DELETE USER
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {

        boolean deleted = messageService.delete(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found")
                    .build();
        }

        return Response.noContent().build();
    }
//
//    @POST
//    @Path("/send")
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response sendMessage() {
//
//        producer.sendMessage(
//                "Hello from Java Messaging System!"
//        );
//
//        return Response.ok(
//                "Message submitted successfully!"
//        ).build();
//    }
}
