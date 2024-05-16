package io.github.lucasbarbosarocha.quarkussocial.core.usecase.post;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.Post;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.post.CreatePostRequest;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.post.PostResponse;

import java.util.List;

public interface PostUseCase {

    Post savePost(User user, CreatePostRequest request);

    List<PostResponse> listPosts(User user);

}
