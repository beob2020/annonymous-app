package org.beob2020.user.resource;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.beob2020.user.dtos.CreateUserRequestDto;
import org.beob2020.user.dtos.Role;
import org.beob2020.user.dtos.UpdateUserRequestDto;
import org.beob2020.user.entity.EntityPage;
import org.beob2020.user.entity.UserEntity;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

import static org.beob2020.user.entity.UserEntity.getAllUsersInPages;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient
@Slf4j
public class UserResource {

    @Path("/getAllUsers")
    @GET
    public EntityPage<UserEntity> getUser(@DefaultValue("0") @QueryParam("page") int page,
                                          @DefaultValue("10") @QueryParam("size") int size,
                                          @DefaultValue("id") @QueryParam("sort") String sort) {
        //TODO: SECURITY: Check if user is logged in
        log.info("Get all users Endpoint called");
        return getAllUsersInPages(page, size, sort);
    }

    @Path("/getUserById/{id}")
    @GET
    public UserEntity getUserById(@PathParam("id") UUID id) {
        //TODO: SECURITY: Check if user is logged in
        PanacheEntityBase byId = UserEntity.findById(id);
        if (byId == null) {
            throw new NotFoundException("User with id " + id + " not found");
        }
        return (UserEntity) byId;
    }

    @POST
    @Path("/createUser")
    @Transactional
    public Response createUser(CreateUserRequestDto createUserDto) {
        log.info("createUser Endpoint called");
        //Todo: SECURITY: Check if user is admin
        if (createUserDto != null) {
            UserEntity user = new UserEntity();
            user.setEmail(createUserDto.getEmail());
            user.setFirstName(createUserDto.getFirstName());
            user.setLastName(createUserDto.getLastName());
            user.setRole(Role.ADMIN);

            UserEntity.persist(user);
            return Response.ok("User is Created").status(Response.Status.CREATED).build();
        }
        throw new BadRequestException("User is not created");
    }

    @PUT
    @Path("/updateUser/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") UUID id, UpdateUserRequestDto updateUserDto) {
        //TODO: SECURITY: Check if user logged in
        log.info("updateUser Endpoint called");
        UserEntity user = UserEntity.findById(id);

        return Response.ok("User is Created").status(Response.Status.OK).build();
    }
}
