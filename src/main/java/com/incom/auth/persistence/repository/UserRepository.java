package com.incom.auth.persistence.repository;

import com.incom.auth.persistence.mapper.UserMapper;
import com.incom.auth.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import org.codejargon.fluentjdbc.api.FluentJdbc;
import org.codejargon.fluentjdbc.api.query.UpdateResult;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    private final FluentJdbc fluentJdbc;
    private final UserMapper userMapper;

    public UserRepository(FluentJdbc fluentJdbc, UserMapper userMapper) {
        this.fluentJdbc = fluentJdbc;
        this.userMapper = userMapper;
    }

    public Optional<User> findByUsername(String username) {
        return fluentJdbc.query()
                .select("SELECT id, username, role FROM app_user WHERE username = ?")
                .params(username)
                .firstResult(userMapper);
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    public void create(String username, String hashedPassword, String role) {
        fluentJdbc.query().update("INSERT INTO app_user (username, password, role) VALUES (?, ?, ?)")
                .params(username, hashedPassword, role)
                .run();
    }

    public List<User> findAll() {
        return fluentJdbc.query()
                .select("SELECT id, username, role FROM app_user")
                .listResult(userMapper);
    }

    public long updateUser(String oldUsername, String newUsername, String newRole) {
        UpdateResult result = fluentJdbc.query()
                .update(
                        "UPDATE app_user " +
                        "SET " +
                        "username = :username , " +
                        "role = :role " +
                        "WHERE username = :oldUsername "
                )
                .namedParam("username", newUsername)
                .namedParam("role", newRole)
                .namedParam("oldUsername", oldUsername)
                .run();
        return result.affectedRows();
    }
}
