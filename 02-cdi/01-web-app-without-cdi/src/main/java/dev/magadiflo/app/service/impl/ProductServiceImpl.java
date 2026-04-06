package dev.magadiflo.app.service.impl;

import dev.magadiflo.app.dao.GenericDAO;
import dev.magadiflo.app.dao.impl.CategoryDAOImpl;
import dev.magadiflo.app.dao.impl.ProductDAOImpl;
import dev.magadiflo.app.exception.DatabaseException;
import dev.magadiflo.app.model.Category;
import dev.magadiflo.app.model.Product;
import dev.magadiflo.app.service.ProductService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final GenericDAO<Product> productDao;
    private final GenericDAO<Category> categoryDao;

    // Recibe la conexión por constructor y crea sus propios DAOs
    public ProductServiceImpl(Connection connection) {
        this.productDao = new ProductDAOImpl(connection);
        this.categoryDao = new CategoryDAOImpl(connection);
    }

    @Override
    public List<Product> getAllProducts() {
        try {
            return this.productDao.getAll();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Product> getProduct(Long productId) {
        try {
            return Optional.ofNullable(this.productDao.findById(productId));
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void saveProduct(Product product) {
        try {
            this.productDao.save(product);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        try {
            this.productDao.deleteById(productId);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            return this.categoryDao.getAll();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Category> getCategory(Long categoryId) {
        try {
            return Optional.ofNullable(this.categoryDao.findById(categoryId));
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage(), e.getCause());
        }
    }
}
