package dev.magadiflo.app.service.impl;

import dev.magadiflo.app.dao.UserDAO;
import dev.magadiflo.app.exception.DatabaseException;
import dev.magadiflo.app.model.User;
import dev.magadiflo.app.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Inject
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
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
