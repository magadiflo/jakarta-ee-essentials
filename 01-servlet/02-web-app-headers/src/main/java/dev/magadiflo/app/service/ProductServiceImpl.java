package dev.magadiflo.app.service;

import dev.magadiflo.app.model.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> findAllProducts() {
        return List.of(
                new Product(1L, "Notebook", "Informática", new BigDecimal("2700")),
                new Product(2L, "Mesa de escritorio", "Oficina", new BigDecimal("996.50")),
                new Product(3L, "Teclado mecánico", "Informática", new BigDecimal("56.70"))
        );
    }
}
