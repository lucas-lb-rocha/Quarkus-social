package io.github.lucasbarbosarocha.quarkussocial.core.usecase.user;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.user.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface UserUseCase {

    User createUser(CreateUserRequest userRequest);

    PanacheQuery<User> listAllUsers();

    Response deleteUser(Long id);

    Response updateUser(Long id, CreateUserRequest userData);

    User findUserById(Long id);
}
