package io.github.lucasbarbosarocha.quarkussocial.controller;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.user.CreateUserRequest;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.UserUseCase;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.impl.UserUseCaseImpl;
import io.github.lucasbarbosarocha.quarkussocial.infrastructure.utils.ResponseUtils;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private final UserUseCase userUseCase = mock(UserUseCaseImpl.class);
    private final Validator validator = mock(ValidatorImpl.class);
    private final ResponseUtils utils = new ResponseUtils();

    UserController controller = new UserController(userUseCase, validator, utils);

     @Test
    void shouldListAllUsersWhenValidRequestThenReturnsOkResponse() {
        // GIVEN
        PanacheQuery<User> query = mock(PanacheQuery.class);
        when(userUseCase.listAllUsers()).thenReturn(query);

        // WHEN
        Response response = controller.listAllUsers();

        // THEN
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void shouldDeleteUserWhenInvalidIdThenReturnsNotFoundResponse() {
        // GIVEN
        when(userUseCase.deleteUser(anyLong())).thenReturn(null);

        // WHEN
        Response response = controller.deleteUser(1L);

        // THEN
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }


    @Test
    void shouldUpdateUserWhenInvalidIdThenReturnsNotFoundResponse() {
        // GIVEN
        CreateUserRequest request = new CreateUserRequest();
        when(userUseCase.updateUser(anyLong(), any())).thenReturn(null);

        // WHEN
        Response response = controller.updateUser(1L, request);

        // THEN
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
