package io.github.lucasbarbosarocha.quarkussocial.controller;

import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.FollowerUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.UserUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.follower.FollowerRequest;
import io.github.lucasbarbosarocha.quarkussocial.infrastructure.utils.ResponseUtils;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerController {

    private final FollowerUseCase followerUseCase;
    private final UserUseCase userUseCase;
    private final ResponseUtils utils;

    @Inject
    public FollowerController(FollowerUseCase followerUseCase, UserUseCase userUseCase, ResponseUtils utils){
        this.followerUseCase = followerUseCase;
        this.userUseCase = userUseCase;
        this.utils = utils;
    }

    @PUT
    @Transactional
    public Response followUser(
            @PathParam("userId") Long userId, FollowerRequest request){

        if(userId.equals(request.getFollowerId())){
            return utils.getGenericErrorResponse(Response.Status.CONFLICT,
                    "You can't follow yourself", userId.toString());
        }

        final var user = userUseCase.findUserById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final var follower = userUseCase.findUserById(request.getFollowerId());

        if(followerUseCase.followUser(user, follower)){
            return utils.getGenericResponseSuccess(Response.Status.OK,
                    "Now you are following a new user {}", userId.toString());
        }

        return utils.getGenericErrorResponse(Response.Status.NOT_ACCEPTABLE,
                "You can not follower the user {}", userId.toString());
    }

    @GET
    @Transactional
    public Response listFollowers(@PathParam("userId") Long userId){

        final var user = userUseCase.findUserById(userId);
        if(user == null){
            return utils.getGenericErrorResponse(Response.Status.NOT_FOUND,
                    "User {} not found", userId.toString());
        }

        var responseObject = followerUseCase.listFollowers(userId);
        return Response.ok(responseObject).build();
    }

    @DELETE
    @Transactional
    public Response unfollowUser(
            @PathParam("userId") Long userId,
            @QueryParam("followerId")  Long followerId){
        var user = userUseCase.findUserById(userId);
        if(user == null){
            return utils.getGenericErrorResponse(Response.Status.NOT_FOUND,
                    "User {} not found", userId.toString());
        }

        followerUseCase.unfollowUser(followerId, userId);

        return utils.getGenericResponseSuccess(Response.Status.OK,
                "You are no longer following the user {}", userId.toString());
    }

}
