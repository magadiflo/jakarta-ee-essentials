package dev.magadiflo.app.controller.product;

import dev.magadiflo.app.model.Product;
import dev.magadiflo.app.service.ProductService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/products/delete")
public class ProductDeleteServlet extends HttpServlet {

    @Inject
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long productId = Long.parseLong(req.getParameter("productId"));
        Optional<Product> optionalProduct = this.productService.getProduct(productId);
        if (optionalProduct.isPresent()) {
            this.productService.deleteProduct(productId);
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                "Producto con id [%d] no encontrado".formatted(productId));
    }
}
