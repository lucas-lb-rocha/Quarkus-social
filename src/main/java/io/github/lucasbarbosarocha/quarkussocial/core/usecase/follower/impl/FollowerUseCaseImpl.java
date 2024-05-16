package io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.impl;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.follower.FollowerResponse;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.follower.FollowersPerUserResponse;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.Follower;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.FollowerUseCase;
import io.github.lucasbarbosarocha.quarkussocial.dataprovider.database.repository.FollowerRepository;

import java.util.stream.Collectors;

public class FollowerUseCaseImpl implements FollowerUseCase {

    private final FollowerRepository repository;

    public FollowerUseCaseImpl(FollowerRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean followUser(User follower, User user) {
        final var follows = repository.follows(follower, user);

        if(!follows){
            var entity = new Follower();
            entity.setUser(user);
            entity.setFollower(follower);

            repository.persist(entity);
            return true;
        }

        return false;
    }

    @Override
    public boolean isFollowerFollowUser(User follower, User user){
        final var follows = repository.follows(follower, user);
        return !follows;
    }

    @Override
    public FollowersPerUserResponse listFollowers(Long userId) {
        var list = repository.findByUser(userId);
        FollowersPerUserResponse responseObject = new FollowersPerUserResponse();
        responseObject.setFollowersCount(list.size());

        final var followerList = list.stream()
                .map( FollowerResponse::new )
                .collect(Collectors.toList());
        responseObject.setContent(followerList);
        return responseObject;
    }

    @Override
    public void unfollowUser(Long userId, Long followerId) {
        repository.deleteByFollowerAndUser(followerId, userId);
    }
}
