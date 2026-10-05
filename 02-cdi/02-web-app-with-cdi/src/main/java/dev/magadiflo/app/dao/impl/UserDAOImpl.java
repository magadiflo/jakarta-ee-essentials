package dev.magadiflo.app.dao.impl;

import dev.magadiflo.app.dao.UserDAO;
import dev.magadiflo.app.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class UserDAOImpl implements UserDAO {

    @Inject
    @Named("connection")
    private Connection connection;

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }

    @Override
    public User findById(Long id) throws SQLException {
        return null;
    }

    @Override
    public void save(User category) throws SQLException {
    }

    @Override
    public void deleteById(Long id) throws SQLException {

    }

    @Override
    public User findByUsername(String username) throws SQLException {
        User user = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM users AS u WHERE u.username = ?")) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                }
            }
        }
        return user;
    }
}
