package io.github.lucasbarbosarocha.quarkussocial.controller;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.error.GenericErrorResponse;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.Post;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.FollowerUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.post.PostUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.UserUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.post.CreatePostRequest;
import io.github.lucasbarbosarocha.quarkussocial.infrastructure.utils.ResponseUtils;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostController {

    private final UserUseCase userUseCase;
    private final PostUseCase postUseCase;
    private final FollowerUseCase followerUseCase;
    private final ResponseUtils utils;

    @Inject
    public PostController(UserUseCase userUseCase, PostUseCase postUseCase,
                          FollowerUseCase followerUseCase, ResponseUtils utils) {
        this.userUseCase = userUseCase;
        this.postUseCase = postUseCase;
        this.followerUseCase = followerUseCase;
        this.utils = utils;
    }

    @POST
    @Transactional
    public Response savePost(
            @PathParam("userId") Long userId, CreatePostRequest request){


        final User user = userUseCase.findUserById(userId);
        if(user == null){
            return utils.getGenericErrorResponse(Response.Status.NOT_FOUND,
                    "User {} not found", userId.toString());
        }

        final Post post = postUseCase.savePost(user, request);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(post)
                .build();
    }

    @GET
    @Transactional
    public Response listPosts(
            @PathParam("userId") Long userId,
            @HeaderParam("followerId") Long followerId ){

        final User user = userUseCase.findUserById(userId);
        if(user == null){
            return utils.getGenericErrorResponse(Response.Status.NOT_FOUND,
                    "User {} not found", userId.toString());
        }

        if(followerId == null){
            return utils.getGenericErrorResponse(Response.Status.BAD_REQUEST,
                    "You forgot the header followerId");
        }

        final User follower = userUseCase.findUserById(followerId);
        if(follower == null){
            return utils.getGenericErrorResponse(Response.Status.BAD_REQUEST,
                    "There is no user for followerId {}", followerId.toString());
        }

        boolean follows = followerUseCase.isFollowerFollowUser(follower, user);
        if(!follows){
            return utils.getGenericErrorResponse(Response.Status.FORBIDDEN, "You can't see these posts " +
                    "because you are not following the user {}", user.getName());
        }

        var postResponseList = postUseCase.listPosts(user);
        return Response.ok(postResponseList).build();
    }
}