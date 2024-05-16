package io.github.lucasbarbosarocha.quarkussocial.core.usecase.post.impl;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.Post;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.post.CreatePostRequest;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.post.PostResponse;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.post.PostUseCase;
import io.github.lucasbarbosarocha.quarkussocial.dataprovider.database.repository.PostRepository;
import io.quarkus.panache.common.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class PostUseCaseImpl implements PostUseCase {

    private final PostRepository repository;

    public PostUseCaseImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post savePost(User user, CreatePostRequest request) {
        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        repository.persist(post);
        return post;
    }

    @Override
    public List<PostResponse> listPosts(User user) {
        var query = repository.find(
                "user", Sort.by("dateTime", Sort.Direction.Descending) , user);
        var list = query.list();

        return list.stream()
                .map(PostResponse::fromEntity)
                .collect(Collectors.toList());

    }
}
