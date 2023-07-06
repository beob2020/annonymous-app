package org.beob2020.user.resource;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.beob2020.user.entity.EntityPage;
import org.beob2020.user.entity.UserEntity;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import static org.beob2020.user.entity.UserEntity.getAllUsersInPages;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient
@Slf4j
public class UserResource {

    @GET
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @Path("/getAllUsers")
    @GET
    public EntityPage<UserEntity> getUser(@DefaultValue("0") @QueryParam("page") int page,
                                          @DefaultValue("10") @QueryParam("size") int size,
                                          @DefaultValue("id") @QueryParam("sort") String sort) {
        log.info("Get all users Endpoint called");
        return getAllUsersInPages(page, size, sort);
    }

    @POST
    @Path("/createUser")
    @Transactional
    public Response createUser(UserEntity userEntity) {
        log.info("createUser Endpoint called");
        UserEntity.persist(userEntity);
        return Response.ok("User is Created").status(Response.Status.CREATED).build();
    }

}
