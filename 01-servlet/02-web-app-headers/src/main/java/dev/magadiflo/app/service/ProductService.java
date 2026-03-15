package dev.magadiflo.app.service;

import dev.magadiflo.app.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();
}
