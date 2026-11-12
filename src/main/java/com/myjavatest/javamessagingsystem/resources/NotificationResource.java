package com.myjavatest.javamessagingsystem.resources;

import com.user.RequestDTO.NotificationRequestDTO;
import com.user.ResponseDTO.NotificationResponseDTO;
import com.user.Service.NotificationService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    @Inject
    NotificationService notificationService;

    // POST /notifications  -> create
    @POST
    public Response create(NotificationRequestDTO request) {
        NotificationResponseDTO created = notificationService.saveNotification(request);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    // GET /notifications/1  -> get one
    @GET
    @Path("/{id}")
    public NotificationResponseDTO getById(@PathParam("id") Integer id) {
        return notificationService.getById(id);
    }

    // GET /notifications?userId=3  -> get all for a user
    @GET
    public List<NotificationResponseDTO> getByUser(@QueryParam("userId") Integer userId) {
        if (userId == null) {
            return notificationService.getAll();
        }
        return notificationService.getByUserId(userId);
    }

    // DELETE /notifications/1  -> delete
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        notificationService.delete(id);
        return Response.noContent().build();
    }
}
