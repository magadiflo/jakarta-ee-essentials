package dev.magadiflo.app.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public BigDecimal getTotal() {
        return this.items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addItemToCart(CartItem item) {
        this.items.stream()
                .filter(cartItem -> cartItem.equals(item))
                .findFirst()
                .ifPresentOrElse(
                        cartItem -> cartItem.increaseQuantity(1),
                        () -> this.items.add(item)
                );
    }

    public void removeItemsByProductId(List<Long> productIds) {
        List<CartItem> itemsToRemove = this.items.stream()
                .filter(cartItem -> productIds.contains(cartItem.getProduct().getId()))
                .toList();
        this.items.removeAll(itemsToRemove);
    }

    public void removeItemByProductId(Long productId) {
        this.items.removeIf(cartItem -> productId.equals(cartItem.getProduct().getId()));
    }

    public void increaseQuantityOfItem(Long productId, Integer quantity) {
        this.items.stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findFirst()
                .ifPresent(cartItem -> cartItem.increaseQuantity(quantity));
    }

    public void decreaseQuantityOfItem(Long productId, Integer quantity) {
        this.items.stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findFirst()
                .ifPresent(cartItem -> cartItem.decreaseQuantity(quantity));
    }
}
