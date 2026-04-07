package com.incom.auth.persistence.repository;

import com.incom.auth.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.codejargon.fluentjdbc.api.FluentJdbc;

import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    private final FluentJdbc fluentJdbc;

    public UserRepository(FluentJdbc fluentJdbc) {
        this.fluentJdbc = fluentJdbc;
    }

    public Optional<User> findByUsername(String username) {
        return fluentJdbc.query()
                .select("SELECT id, username, role FROM app_user WHERE username = ?")
                .params(username)
                .firstResult(rs -> new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("role")
                ));
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    public void create(String username, String hashedPassword, String role) {
        fluentJdbc.query().update("INSERT INTO app_user (username, password, role) VALUES (?, ?, ?)")
                .params(username, hashedPassword, role)
                .run();
    }
}
