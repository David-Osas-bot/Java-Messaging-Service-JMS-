package com.user.Service;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Priorities;
import jakarta.annotation.Priority;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter implements ContainerRequestFilter {

    @Inject
    JwtService jwtService;

    @Override
    public void filter(ContainerRequestContext request) {

        String path = request.getUriInfo().getPath();

        // Allow authentication endpoints
        if (path.contains("auth")) {
            return;
        }

        String header = request.getHeaderString("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            request.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("{\"message\":\"Missing token\"}")
                            .build()
            );
            return;
        }

        String token = header.substring(7);

        if (!jwtService.isValid(token)) {
            request.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity("{\"message\":\"Invalid or expired token\"}")
                            .build()
            );
        }
    }
}