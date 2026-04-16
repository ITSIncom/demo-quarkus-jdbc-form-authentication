package com.incom.auth.service;

import com.incom.auth.persistence.entity.User;
import com.incom.auth.persistence.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
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

        User u = new User();
        u.setUsername(trimmed);
        u.setRole("user");
        u.setPasswordHash(hashed);

        userRepository.persist(u);
        return RegistrationResult.success();
    }

    public List<User> findAll() {
        return userRepository.listAll();
    }

    public User getByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Transactional
    public Optional<String> modifyUser(String oldUsername, String newUsername, String newRole) {
        if (oldUsername == null || oldUsername.isBlank()) {
            return Optional.of("Username is required");
        }
        if (newUsername == null || newUsername.isBlank()) {
            return Optional.of("Username from form is required");
        }
        // Il ruolo deve essere o "user" oppure "admin"
        if (!"user".equals(newRole) && !"admin".equals(newRole)) {
            return Optional.of("Role must be either user or admin");
        }
        Optional<User> oldByUsername = userRepository.findByUsername(newUsername);
        if (oldByUsername.isEmpty()) {
            return Optional.of("User does not exist");
        }

        if (!oldUsername.equals(newUsername)) {
            if (userRepository.existsByUsername(newUsername)) {
                return Optional.of("Username already exists");
            }
        }

        User user = oldByUsername.get();
        user.setUsername(newUsername);
        user.setRole(newRole);
        return Optional.empty();
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
