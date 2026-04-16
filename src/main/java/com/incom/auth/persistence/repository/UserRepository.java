package com.incom.auth.persistence.repository;

import com.incom.auth.persistence.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Long> {

    public Optional<User> findByUsername(String username) {
        String query = "SELECT u FROM User u WHERE u.username = :username";
        PanacheQuery<User> userPanacheQuery = find(query, Map.of("username", username));
        return userPanacheQuery.firstResultOptional();
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }


}
