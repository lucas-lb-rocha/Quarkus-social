package io.github.lucasbarbosarocha.quarkussocial.controller;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.follower.FollowerRequest;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.follower.FollowersPerUserResponse;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.FollowerUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.impl.FollowerUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.UserUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.impl.UserUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.infrastructure.utils.ResponseUtils;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FollowerControllerTest {

    private final UserUseCase userUseCase = mock(UserUseCaseImpl.class);
    private final FollowerUseCase followerUseCase = mock(FollowerUseCaseImpl.class);
    private final ResponseUtils utils = new ResponseUtils();

    FollowerController controller = new FollowerController(followerUseCase, userUseCase, utils);


    @Test
    void shouldFollowUserWithValidRequestThenReturnsOkResponse() {
        // GIVEN
        FollowerRequest request = new FollowerRequest();
        request.setFollowerId(2L);
        when(userUseCase.findUserById(anyLong())).thenReturn(new User());
        when(followerUseCase.followUser(any(), any())).thenReturn(true);

        // WHEN
        Response response = controller.followUser(1L, request);

        // THEN
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldFollowUserWhenUserNotFoundThenReturnsNotFoundResponse() {
        // GIVEN
        FollowerRequest request = new FollowerRequest();
        request.setFollowerId(1L);
        when(userUseCase.findUserById(anyLong())).thenReturn(null);

        // WHEN
        Response response = controller.followUser(2L, request);

        // THEN
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldFollowUserWhenSelfFollowThenReturnsConflictResponse() {
        // GIVEN
        FollowerRequest request = new FollowerRequest();
        request.setFollowerId(1L);

        // WHEN
        Response response = controller.followUser(1L, request);

        // THEN
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldListFollowersWhenValidUserIdThenReturnsOkResponse() {
        // GIVEN
        when(userUseCase.findUserById(anyLong())).thenReturn(new User());
        when(followerUseCase.listFollowers(anyLong())).thenReturn(new FollowersPerUserResponse());

        // WHEN
        Response response = controller.listFollowers(1L);

        // THEN
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldListFollowersWhenUserNotFoundThenReturnsNotFoundResponse() {
        // GIVEN
        when(userUseCase.findUserById(anyLong())).thenReturn(null);

        // WHEN
        Response response = controller.listFollowers(1L);

        // THEN
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldUnfollowUserWhenValidRequestThenReturnsOkResponse() {
        // GIVEN
        when(userUseCase.findUserById(anyLong())).thenReturn(new User());

        // WHEN
        Response response = controller.unfollowUser(1L, 2L);

        // THEN
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldUnfollowUserWhenUserNotFoundThenReturnsNotFoundResponse() {
        // GIVEN
        when(userUseCase.findUserById(anyLong())).thenReturn(null);

        // WHEN
        Response response = controller.unfollowUser(1L, 2L);

        // THEN
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

}
