package io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.follower.FollowersPerUserResponse;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;


public interface FollowerUseCase {

    boolean followUser(User follower, User user);

    boolean isFollowerFollowUser(User follower, User user);

    FollowersPerUserResponse listFollowers(Long userId);

    void unfollowUser(Long userId, Long followerId);
}
