package io.github.lucasbarbosarocha.quarkussocial.core.usecase;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.follower.FollowersPerUserResponse;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.Follower;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.FollowerUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.impl.FollowerUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.dataprovider.database.repository.FollowerRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FollowerUseCaseImplTest {

    private final FollowerRepository repository = mock(FollowerRepository.class);
    private final FollowerUseCase followerUseCase = new FollowerUseCaseImpl(repository);

    @Test
    void shouldFollowUserWhenUserNotAlreadyFollowingThenReturnsTrue() {
        // GIVEN
        User follower = new User();
        User user = new User();
        when(repository.follows(any(), any())).thenReturn(false);

        // WHEN
        boolean result = followerUseCase.followUser(follower, user);

        // THEN
        assertTrue(result);
    }

    @Test
    void shouldFollowUserWhenUserAlreadyFollowingThenReturnsFalse() {
        // GIVEN
        User follower = new User();
        User user = new User();
        when(repository.follows(any(), any())).thenReturn(true);

        // WHEN
        boolean result = followerUseCase.followUser(follower, user);

        // THEN
        assertFalse(result);
    }

    @Test
    void shouldIsFollowerFollowUserWhenUserNotAlreadyFollowingThenReturnsTrue() {
        // GIVEN
        User follower = new User();
        User user = new User();
        when(repository.follows(any(), any())).thenReturn(false);

        // WHEN
        boolean result = followerUseCase.isFollowerFollowUser(follower, user);

        // THEN
        assertTrue(result);
    }

    @Test
    void shouldIsFollowerFollowUserWhenUserAlreadyFollowingThenReturnsFalse() {
        // GIVEN
        User follower = new User();
        User user = new User();
        when(repository.follows(any(), any())).thenReturn(true);

        // WHEN
        boolean result = followerUseCase.isFollowerFollowUser(follower, user);

        // THEN
        assertFalse(result);
    }

    @Test
    void shouldListFollowersWhenReturnsCorrectFollowersPerUserResponse() {
        // GIVEN
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("user");

        User userFollower = new User();
        userFollower.setId(userId);
        userFollower.setName("userFollower");

        List<Follower> followers = new ArrayList<>();

        Follower follower = new Follower();
        follower.setId(1L);
        follower.setUser(user);
        followers.add(follower);
        follower.setFollower(userFollower);

        when(repository.findByUser(userId)).thenReturn(followers);

        // WHEN
        FollowersPerUserResponse result = followerUseCase.listFollowers(userId);

        // THEN
        assertNotNull(result);
        assertEquals(followers.size(), result.getFollowersCount());
        assertEquals(followers.size(), result.getContent().size());
    }

    @Test
    void shouldUnfollowUserThenDeletesFollower() {
        // GIVEN
        Long userId = 1L;
        Long followerId = 2L;

        // WHEN THEN
        followerUseCase.unfollowUser(userId, followerId);
    }
}
