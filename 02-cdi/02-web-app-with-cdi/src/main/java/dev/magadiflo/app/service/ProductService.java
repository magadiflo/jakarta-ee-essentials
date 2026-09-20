package dev.magadiflo.app.service;

import dev.magadiflo.app.model.Category;
import dev.magadiflo.app.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getProduct(Long productId);

    void saveProduct(Product product);

    void deleteProduct(Long productId);

    List<Category> getAllCategories();

    Optional<Category> getCategory(Long categoryId);
}
