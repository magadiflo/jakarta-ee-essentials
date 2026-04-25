package dev.magadiflo.app.controller.product;

import dev.magadiflo.app.model.Category;
import dev.magadiflo.app.model.Product;
import dev.magadiflo.app.service.ProductService;
import dev.magadiflo.app.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/products/form")
public class ProductFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Connection connection = (Connection) req.getAttribute("connection");
        ProductService productService = new ProductServiceImpl(connection);

        // Si viene productId → edición, si no → creación
        Long productId = Objects.nonNull(req.getParameter("productId"))
                ? Long.parseLong(req.getParameter("productId"))
                : null;

        // Producto por defecto vacío para el formulario de creación
        Product product = new Product();
        product.setCategory(new Category());

        if (Objects.nonNull(productId)) {
            Optional<Product> optionalProduct = productService.getProduct(productId);
            if (optionalProduct.isPresent()) {
                product = optionalProduct.get();
            }
        }

        req.setAttribute("categories", productService.getAllCategories());
        req.setAttribute("product", product);
        req.setAttribute("title", req.getAttribute("title") + ": Listado de productos");

        this.getServletContext()
                .getRequestDispatcher("/form.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Connection connection = (Connection) req.getAttribute("connection");
        ProductService productService = new ProductServiceImpl(connection);

        // Lee los parámetros del formulario
        String name = req.getParameter("name");
        String sku = req.getParameter("sku");
        LocalDateTime createdAt = LocalDateTime.now();
        BigDecimal price = new BigDecimal(req.getParameter("price"));

        // Si viene productId → UPDATE, si no → INSERT
        Long productId = Objects.nonNull(req.getParameter("productId"))
                ? Long.parseLong(req.getParameter("productId"))
                : null;
        Long categoryId = Objects.nonNull(req.getParameter("categoryId"))
                ? Long.parseLong(req.getParameter("categoryId"))
                : null;

        // Construye el objeto Product con los datos del formulario
        Category category = new Category();
        category.setId(categoryId);

        Product product = new Product();
        product.setId(productId);
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setSku(sku);
        product.setCreatedAt(createdAt);

        productService.saveProduct(product);
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}
