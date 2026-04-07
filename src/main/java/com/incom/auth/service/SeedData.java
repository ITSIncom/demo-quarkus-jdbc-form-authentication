package com.incom.auth.service;

import com.incom.auth.persistence.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.codejargon.fluentjdbc.api.FluentJdbc;

@ApplicationScoped
public class SeedData {

    @Inject
    FluentJdbc fluentJdbc;

    @Inject
    UserRepository userRepository;

    void onStart(@Observes StartupEvent ev) {
        fluentJdbc.query().update("""
                CREATE TABLE IF NOT EXISTS app_user (
                    id       SERIAL PRIMARY KEY,
                    username VARCHAR(255) UNIQUE NOT NULL,
                    password VARCHAR(255)        NOT NULL,
                    role     VARCHAR(50)         NOT NULL DEFAULT 'user'
                )
                """).run();

        insertIfMissing("admin", "admin", "admin");
        insertIfMissing("user", "user", "user");
    }

    private void insertIfMissing(String username, String rawPassword, String role) {
        if (!userRepository.existsByUsername(username)) {
            userRepository.create(username, BcryptUtil.bcryptHash(rawPassword), role);
        }
    }
}
