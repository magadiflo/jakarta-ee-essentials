package dev.magadiflo.app.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class CartItem {
    private int quantity;
    private final BigDecimal unitPrice;
    private final Product product;

    public CartItem(int quantity, Product product) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio del producto es inválido");
        }

        this.quantity = quantity;
        this.product = product;
        this.unitPrice = product.getPrice();
    }

    public BigDecimal getSubtotal() {
        return this.unitPrice
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
        if (this.quantity - quantity < 1) {
            throw new IllegalArgumentException("La cantidad mínima es 1. El item debe eliminarse del carrito");
        }
        this.quantity -= quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }


    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(getProduct(), cartItem.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProduct());
    }
}
