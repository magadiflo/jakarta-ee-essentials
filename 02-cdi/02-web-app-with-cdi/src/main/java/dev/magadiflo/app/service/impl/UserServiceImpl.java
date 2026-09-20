package dev.magadiflo.app.service.impl;

import dev.magadiflo.app.dao.UserDAO;
import dev.magadiflo.app.dao.impl.UserDAOImpl;
import dev.magadiflo.app.exception.DatabaseException;
import dev.magadiflo.app.model.User;
import dev.magadiflo.app.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(Connection connection) {
        this.userDAO = new UserDAOImpl(connection);
    }

    @Override
    public Optional<User> login(String username, String password) {
        try {
            return Optional.ofNullable(this.userDAO.findByUsername(username))
                    .filter(user -> user.getPassword().equals(password));
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        }
    }
}
