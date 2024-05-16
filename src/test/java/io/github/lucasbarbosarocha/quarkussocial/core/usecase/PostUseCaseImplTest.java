package io.github.lucasbarbosarocha.quarkussocial.core.usecase;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.Post;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.post.CreatePostRequest;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.post.PostUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.post.impl.PostUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.dataprovider.database.repository.PostRepository;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class PostUseCaseImplTest {

    private final PostRepository repository = mock(PostRepository.class);
    private final PostUseCase postUseCase = new PostUseCaseImpl(repository);

    @Test
    void shouldSavePostThenReturnsSavedPost() {
        // GIVEN
        User user = new User();
        CreatePostRequest request = new CreatePostRequest();
        request.setText("Test post text");
        Post savedPost = new Post();
        savedPost.setText(request.getText());
        savedPost.setUser(user);

        // WHEN
        Post result = postUseCase.savePost(user, request);

        // THEN
        assertNotNull(result);
        assertEquals(request.getText(), result.getText());
        assertEquals(user, result.getUser());
    }
}
