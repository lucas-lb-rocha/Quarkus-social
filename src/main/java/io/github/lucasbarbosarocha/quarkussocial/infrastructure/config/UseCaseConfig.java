package io.github.lucasbarbosarocha.quarkussocial.infrastructure.config;

import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.FollowerUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.follower.impl.FollowerUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.post.PostUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.post.impl.PostUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.UserUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.impl.UserUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.dataprovider.database.repository.FollowerRepository;
import io.github.lucasbarbosarocha.quarkussocial.dataprovider.database.repository.PostRepository;
import io.github.lucasbarbosarocha.quarkussocial.dataprovider.database.repository.UserRepository;
import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.Unremovable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.ws.rs.Produces;

@Unremovable
@ApplicationScoped
public class UseCaseConfig {

    @Produces
    @Singleton
    @DefaultBean
    public FollowerUseCase followerUseCase(final FollowerRepository followerRepository){
        return new FollowerUseCaseImpl(followerRepository);
    }

    @Produces
    @Singleton
    @DefaultBean
    public PostUseCase postUseCase(final PostRepository postRepository){
        return new PostUseCaseImpl(postRepository);
    }

    @Produces
    @Singleton
    @DefaultBean
    public UserUseCase userUseCase(final UserRepository userRepository){
        return new UserUseCaseImpl(userRepository);
    }

}
