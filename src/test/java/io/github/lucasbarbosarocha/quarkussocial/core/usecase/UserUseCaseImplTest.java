package io.github.lucasbarbosarocha.quarkussocial.core.usecase;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.user.CreateUserRequest;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.UserUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.impl.UserUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.dataprovider.database.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserUseCaseImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserUseCase userUseCase = new UserUseCaseImpl(userRepository);

    @Test
    void shouldCreateUserWhenValidRequestThenReturnsUser() {
        // GIVEN
        CreateUserRequest request = new CreateUserRequest();
        request.setName("John Doe");
        request.setAge(30);
        User expectedUser = new User();
        expectedUser.setName("John Doe");
        expectedUser.setAge(30);

        // WHEN
        User createdUser = userUseCase.createUser(request);

        // THEN
        assertNotNull(createdUser);
        assertEquals(expectedUser.getName(), createdUser.getName());
        assertEquals(expectedUser.getAge(), createdUser.getAge());
    }

    @Test
    void shouldListAllUsersThenReturnsPanacheQuery() {
        // GIVEN
        PanacheQuery<User> mockQuery = mock(PanacheQuery.class);
        when(userRepository.findAll()).thenReturn(mockQuery);

        // WHEN
        PanacheQuery<User> result = userUseCase.listAllUsers();

        // THEN
        assertNotNull(result);
        assertEquals(mockQuery, result);
    }

    @Test
    void shouldDeleteUserWhenExistingUserIdThenReturnsNoContentResponse() {
        // GIVEN
        long userId = 1L;
        User existingUser = new User();
        when(userRepository.findById(userId)).thenReturn(existingUser);

        // WHEN
        Response response = userUseCase.deleteUser(userId);

        // THEN
        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    void shouldDeleteUserWhenNonExistingUserIdThenReturnsNullResponse() {
        // GIVEN
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(null);

        // WHEN
        Response response = userUseCase.deleteUser(userId);

        // THEN
        assertNull(response);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void shouldUpdateUserWhenExistingUserIdThenReturnsNoContentResponse() {
        // GIVEN
        long userId = 1L;
        CreateUserRequest userData = new CreateUserRequest();
        userData.setName("Updated Name");
        userData.setAge(35);
        User existingUser = new User();
        existingUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(existingUser);

        // WHEN
        Response response = userUseCase.updateUser(userId, userData);

        // THEN
        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertEquals(userData.getName(), existingUser.getName());
        assertEquals(userData.getAge(), existingUser.getAge());
    }

    @Test
    void shouldUpdateUserWhenNonExistingUserIdThenReturnsNullResponse() {
        // GIVEN
        long userId = 1L;
        CreateUserRequest userData = new CreateUserRequest();
        userData.setName("Updated Name");
        userData.setAge(35);
        when(userRepository.findById(userId)).thenReturn(null);

        // WHEN
        Response response = userUseCase.updateUser(userId, userData);

        // THEN
        assertNull(response);
        verify(userRepository, never()).persist(any(User.class));
    }

    @Test
    void shouldFindUserByIdWhenExistingUserIdThenReturnsUser() {
        // GIVEN
        long userId = 1L;
        User expectedUser = new User();
        when(userRepository.findById(userId)).thenReturn(expectedUser);

        // WHEN
        User result = userUseCase.findUserById(userId);

        // THEN
        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void shouldFindUserByIdWhenNonExistingUserIdThenReturnsNull() {
        // GIVEN
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(null);

        // WHEN
        User result = userUseCase.findUserById(userId);

        // THEN
        assertNull(result);
    }
}
