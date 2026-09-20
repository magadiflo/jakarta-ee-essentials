package dev.magadiflo.app.controller.cart;

import dev.magadiflo.app.model.CartItem;
import dev.magadiflo.app.model.Product;
import dev.magadiflo.app.model.ShoppingCart;
import dev.magadiflo.app.service.ProductService;
import dev.magadiflo.app.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/carts/add")
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long productId = Long.parseLong(req.getParameter("productId"));
        Connection connection = (Connection) req.getAttribute("connection");
        ProductService productService = new ProductServiceImpl(connection);
        Optional<Product> optionalProduct = productService.getProduct(productId);

        if (optionalProduct.isPresent()) {
            CartItem cartItem = new CartItem(1, optionalProduct.get());
            HttpSession session = req.getSession();
            ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shopping-cart");
            shoppingCart.addItemToCart(cartItem);
        }

        resp.sendRedirect(req.getContextPath() + "/carts/view");
    }
}
