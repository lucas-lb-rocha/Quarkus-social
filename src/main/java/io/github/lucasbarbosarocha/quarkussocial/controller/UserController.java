package io.github.lucasbarbosarocha.quarkussocial.controller;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.error.GenericErrorResponse;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.UserUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.user.CreateUserRequest;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.error.ResponseError;
import io.github.lucasbarbosarocha.quarkussocial.infrastructure.utils.ResponseUtils;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class UserController {
    private final UserUseCase userUseCase;
    private final Validator validator;
    private final ResponseUtils utils;

    @Inject
    public UserController(UserUseCase userUseCase, Validator validator, ResponseUtils utils){
        this.userUseCase = userUseCase;
        this.validator = validator;
        this.utils = utils;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest){
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
        if(!violations.isEmpty()){
            return ResponseError
                    .createFromValidation(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        final var user = userUseCase.createUser(userRequest);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();
    }

    @GET
    @Transactional
    public Response listAllUsers(){
        PanacheQuery<User> query = userUseCase.listAllUsers();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
        if(Objects.isNull(userUseCase.deleteUser(id))){
            return utils.getGenericErrorResponse(Response.Status.NOT_FOUND,
                    "User {} not found", id.toString());
        }

        return utils.getGenericResponseSuccess(Response.Status.OK,
                "User {} deleted with success", id.toString());    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData ){
        if(Objects.isNull(userUseCase.updateUser(id, userData))) {
            return utils.getGenericErrorResponse(Response.Status.NOT_FOUND,
                    "User {} not found", id.toString());
        }

        return utils.getGenericResponseSuccess(Response.Status.OK,
                "User {} updated with success", id.toString());
    }
}
