package io.github.lucasbarbosarocha.quarkussocial.dataprovider.database.repository;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
