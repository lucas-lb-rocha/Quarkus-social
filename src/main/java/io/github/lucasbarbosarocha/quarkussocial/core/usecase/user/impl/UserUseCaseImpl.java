package io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.impl;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.github.lucasbarbosarocha.quarkussocial.core.domain.user.CreateUserRequest;
import io.github.lucasbarbosarocha.quarkussocial.core.usecase.user.UserUseCase;
import io.github.lucasbarbosarocha.quarkussocial.dataprovider.database.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.ws.rs.core.Response;

public class UserUseCaseImpl implements UserUseCase {

    private final UserRepository repository;

    public UserUseCaseImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User createUser(CreateUserRequest userRequest) {
        User user = new User();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());

        repository.persist(user);

        return user;
    }

    @Override
    public PanacheQuery<User> listAllUsers() {
       PanacheQuery<User> query = repository.findAll();
       return query;
    }

    @Override
    public Response deleteUser(Long id) {
        User user = repository.findById(id);

        if(user != null){
            repository.delete(user);
            return Response.noContent().build();
        }
        return null;
    }

    @Override
    public Response updateUser(Long id, CreateUserRequest userData) {
        User user = repository.findById(id);

        if(user != null){
            user.setName(userData.getName());
            user.setAge(userData.getAge());
            return Response.noContent().build();
        }
        return null;
    }

    @Override
    public User findUserById(Long id) {
        return repository.findById(id);
    }
}
