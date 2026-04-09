package com.incom.auth.persistence.mapper;

import com.incom.auth.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import org.codejargon.fluentjdbc.api.query.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class UserMapper implements Mapper<User> {
    @Override
    public User map(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("role")
        );
    }
}
