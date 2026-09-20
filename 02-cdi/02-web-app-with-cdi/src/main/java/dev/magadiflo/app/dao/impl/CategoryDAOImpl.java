package dev.magadiflo.app.dao.impl;

import dev.magadiflo.app.dao.GenericDAO;
import dev.magadiflo.app.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements GenericDAO<Category> {

    private final Connection connection;

    public CategoryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Category> getAll() throws SQLException {
        List<Category> categories = new ArrayList<>();

        try (Statement statement = this.connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM categories")) {
            while (resultSet.next()) {
                categories.add(this.buildCategory(resultSet));
            }
        }

        return categories;
    }

    @Override
    public Category findById(Long id) throws SQLException {
        Category category = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM categories AS c WHERE c.id = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    category = this.buildCategory(resultSet);
                }
            }
        }
        return category;
    }

    @Override
    public void save(Category category) throws SQLException {
        // INSERT o UPDATE según corresponda
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("DELETE FROM categories WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private Category buildCategory(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }
}
