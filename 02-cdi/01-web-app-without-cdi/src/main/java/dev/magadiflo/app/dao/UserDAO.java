package dev.magadiflo.app.dao;

import dev.magadiflo.app.model.User;

import java.sql.SQLException;

public interface UserDAO extends GenericDAO<User> {
    User findByUsername(String username) throws SQLException;
}
