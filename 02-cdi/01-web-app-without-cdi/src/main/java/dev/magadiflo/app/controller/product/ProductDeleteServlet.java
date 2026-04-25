package dev.magadiflo.app.controller.product;

import dev.magadiflo.app.model.Product;
import dev.magadiflo.app.service.ProductService;
import dev.magadiflo.app.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/products/delete")
public class ProductDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Connection connection = (Connection) req.getAttribute("connection");
        ProductService productService = new ProductServiceImpl(connection);

        long productId = Long.parseLong(req.getParameter("productId"));
        Optional<Product> optionalProduct = productService.getProduct(productId);
        if (optionalProduct.isPresent()) {
            productService.deleteProduct(productId);
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto con id [%d] no encontrado".formatted(productId));
    }
}
