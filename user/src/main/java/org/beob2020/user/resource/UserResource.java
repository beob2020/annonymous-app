package org.beob2020.user.resource;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.beob2020.user.entity.UserEntity;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient
@Slf4j
public class UserResource {

    @GET
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @Path("/all")
    @GET
    public UserEntity getUser() {
        log.info("Get all users Endpoint called");
        PanacheQuery<PanacheEntityBase> allUsers = UserEntity.findAll();
        if (allUsers.list().isEmpty()) {
            log.info("No users found");
            throw new NotFoundException("No users found");
        }
        return new UserEntity();
    }



}
