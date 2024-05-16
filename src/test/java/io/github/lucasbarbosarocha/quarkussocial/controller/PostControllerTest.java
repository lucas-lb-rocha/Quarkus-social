package io.github.lucasbarbosarocha.quarkussocial.controller;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.Post;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.post.CreatePostRequest;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.FollowerUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.impl.FollowerUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.post.PostUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.post.impl.PostUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.UserUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.impl.UserUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.infrastructure.utils.ResponseUtils;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostControllerTest {

    private final UserUseCase userUseCase = mock(UserUseCaseImpl.class);
    private final PostUseCase postUseCase = mock(PostUseCaseImpl.class);
    private final FollowerUseCase followerUseCase = mock(FollowerUseCaseImpl.class);
    private final ResponseUtils utils = new ResponseUtils();

    PostController controller = new PostController(userUseCase, postUseCase, followerUseCase, utils);

    @Test
    void shouldSavePostWhenValidRequestThenReturnsCreatedResponse() {
        // GIVEN
        CreatePostRequest request = new CreatePostRequest();
        when(userUseCase.findUserById(anyLong())).thenReturn(new User());
        when(postUseCase.savePost(any(), any())).thenReturn(new Post());

        // WHEN
        Response response = controller.savePost(1L, request);

        // THEN
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldSavePostWhenUserNotFoundThenReturnsNotFoundResponse() {
        // GIVEN
        CreatePostRequest request = new CreatePostRequest();
        when(userUseCase.findUserById(anyLong())).thenReturn(null);

        // WHEN
        Response response = controller.savePost(1L, request);

        // THEN
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldListPostsWhenValidRequestThenReturnsOkResponse() {
        // GIVEN
        User user = new User();
        User follower = new User();
        when(userUseCase.findUserById(anyLong())).thenReturn(user);
        when(userUseCase.findUserById(anyLong())).thenReturn(follower);
        when(followerUseCase.isFollowerFollowUser(any(), any())).thenReturn(true);
        when(postUseCase.listPosts(any())).thenReturn(new ArrayList<>());

        // WHEN
        Response response = controller.listPosts(1L, 2L);

        // THEN
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldListPostsWhenUserNotFoundThenReturnsNotFoundResponse() {
        // GIVEN
        when(userUseCase.findUserById(anyLong())).thenReturn(null);

        // WHEN
        Response response = controller.listPosts(1L, 2L);

        // THEN
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldListPostsWhenMissingFollowerIdHeaderThenReturnsBadRequestResponse() {
        // GIVEN
        when(userUseCase.findUserById(anyLong())).thenReturn(new User());

        // WHEN
        Response response = controller.listPosts(1L, null);

        // THEN
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldListPostsWhenAllConditionsMetThenReturnsOkResponse() {
        // GIVEN
        User user = new User();
        User follower = new User();
        when(userUseCase.findUserById(anyLong())).thenReturn(user);
        when(userUseCase.findUserById(anyLong())).thenReturn(follower);
        when(followerUseCase.isFollowerFollowUser(any(), any())).thenReturn(true);

        // WHEN
        Response response = controller.listPosts(1L, 2L);

        // THEN
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

}
