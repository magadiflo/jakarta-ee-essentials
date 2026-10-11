package dev.magadiflo.app.controller.cart;

import dev.magadiflo.app.model.CartItem;
import dev.magadiflo.app.model.Product;
import dev.magadiflo.app.model.ShoppingCart;
import dev.magadiflo.app.service.ProductService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/carts/add")
public class AddToCartServlet extends HttpServlet {

    @Inject
    private ShoppingCart shoppingCart;

    @Inject
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long productId = Long.parseLong(req.getParameter("productId"));
        Optional<Product> optionalProduct = this.productService.getProduct(productId);

        if (optionalProduct.isPresent()) {
            CartItem cartItem = new CartItem(1, optionalProduct.get());
            this.shoppingCart.addItemToCart(cartItem);
        }

        resp.sendRedirect(req.getContextPath() + "/carts/view");
    }
}
