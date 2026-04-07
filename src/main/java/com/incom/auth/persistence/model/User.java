package com.incom.auth.persistence.model;

import java.util.Objects;

public final class User {
    private final Long id;
    private final String username;
    private final String role;

    public User(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Long id() {
        return id;
    }

    public String username() {
        return username;
    }

    public String role() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (User) obj;
        return Objects.equals(this.id, that.id) &&
               Objects.equals(this.username, that.username) &&
               Objects.equals(this.role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, role);
    }

    @Override
    public String toString() {
        return "User[" +
               "id=" + id + ", " +
               "username=" + username + ", " +
               "role=" + role + ']';
    }

}
