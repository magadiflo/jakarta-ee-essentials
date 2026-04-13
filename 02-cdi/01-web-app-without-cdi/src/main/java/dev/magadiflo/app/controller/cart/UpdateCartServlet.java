package dev.magadiflo.app.controller.cart;

import dev.magadiflo.app.model.ShoppingCart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

@WebServlet("/carts/update")
public class UpdateCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (Objects.nonNull(session.getAttribute("shopping-cart"))) {
            ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shopping-cart");
            this.updateProducts(req, shoppingCart);
            this.updateQuantities(req, shoppingCart);
        }

        resp.sendRedirect(req.getContextPath() + "/carts/view");
    }

    private void updateProducts(HttpServletRequest req, ShoppingCart shoppingCart) {
        String[] productIds = req.getParameterValues("id-of-products-to-delete");

        if (Objects.nonNull(productIds) && productIds.length > 0) {
            List<Long> productIdsList = Arrays.stream(productIds)
                    .map(Long::valueOf)
                    .toList();
            shoppingCart.removeItemsByProductId(productIdsList);
        }
    }

    private void updateQuantities(HttpServletRequest req, ShoppingCart shoppingCart) {
        Enumeration<String> names = req.getParameterNames();
        // Iteramos a traves de los parámetros y buscamos los que empiezan con
        // "quantity_". El campo cant en la vista fueron nombrados "quantity_" + productoId.
        // Obtenemos el id de cada producto y su correspondiente cantidad.
        while (names.hasMoreElements()) {
            String parameterName = names.nextElement();
            if (parameterName.startsWith("quantity_")) {
                long productId = Long.parseLong(parameterName.substring(9));
                String quantity = req.getParameter(parameterName);
                if (Objects.nonNull(quantity)) {
                    shoppingCart.increaseQuantityOfItem(productId, Integer.parseInt(quantity));
                }
            }
        }
    }
}
