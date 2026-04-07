package com.incom.auth.service;

import com.incom.auth.persistence.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegistrationResult register(String username, String password, String confirmPassword) {
        if (username == null || username.isBlank()) {
            return RegistrationResult.error("Username is required.");
        }
        if (password == null || password.length() < 6) {
            return RegistrationResult.error("Password must be at least 6 characters.");
        }
        if (!password.equals(confirmPassword)) {
            return RegistrationResult.error("Passwords do not match.");
        }

        String trimmed = username.strip();
        if (userRepository.existsByUsername(trimmed)) {
            return RegistrationResult.error("Username already taken.");
        }

        String hashed = BcryptUtil.bcryptHash(password);
        userRepository.create(trimmed, hashed, "user");
        return RegistrationResult.success();
    }

    public record RegistrationResult(boolean ok, String errorMessage) {
        public static RegistrationResult success() {
            return new RegistrationResult(true, null);
        }

        public static RegistrationResult error(String message) {
            return new RegistrationResult(false, message);
        }
    }
}
