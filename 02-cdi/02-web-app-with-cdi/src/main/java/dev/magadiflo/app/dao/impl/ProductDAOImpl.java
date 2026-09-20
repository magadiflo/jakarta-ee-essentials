package dev.magadiflo.app.dao.impl;

import dev.magadiflo.app.dao.GenericDAO;
import dev.magadiflo.app.model.Category;
import dev.magadiflo.app.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductDAOImpl implements GenericDAO<Product> {

    private static final String SQL_ALL_PRODUCTS = """
            SELECT p.*, c.name AS category_name
            FROM products AS p
                INNER JOIN categories AS c ON(p.category_id = c.id)
            """;
    private final Connection connection;

    public ProductDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Statement statement = this.connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_ALL_PRODUCTS + " ORDER BY p.id ASC")) {
            while (resultSet.next()) {
                products.add(this.buildProduct(resultSet));
            }
        }
        return products;
    }

    @Override
    public Product findById(Long id) throws SQLException {
        Product product = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SQL_ALL_PRODUCTS + " WHERE p.id = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = this.buildProduct(resultSet);
                }
            }
        }
        return product;
    }

    @Override
    public void save(Product product) throws SQLException {
        String sql = Objects.isNull(product.getId())
                ? "INSERT INTO products(name, price, sku, category_id, created_at) VALUES(?,?,?,?,?)"
                : """
                UPDATE products
                SET name = ?,
                    price = ?,
                    sku = ?,
                    category_id = ?
                WHERE id = ?
                """;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setString(3, product.getSku());
            preparedStatement.setLong(4, product.getCategory().getId());

            if (Objects.isNull(product.getId())) {
                preparedStatement.setTimestamp(5, Timestamp.valueOf(product.getCreatedAt()));
            } else {
                preparedStatement.setLong(5, product.getId());
            }

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private Product buildProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setSku(rs.getString("sku"));
        product.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));

        Category category = new Category();
        category.setId(rs.getLong(("category_id")));
        category.setName(rs.getString("category_name"));
        product.setCategory(category);
        return product;
    }
}
