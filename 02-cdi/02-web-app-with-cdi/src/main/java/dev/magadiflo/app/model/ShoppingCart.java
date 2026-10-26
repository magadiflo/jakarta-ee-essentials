package dev.magadiflo.app.model;

import dev.magadiflo.app.annotations.ShoppingCartSessionBean;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ShoppingCartSessionBean
public class ShoppingCart implements Serializable {

    /**
     * El problema es que estamos inyectando un objeto Logger en una clase de sessión.
     * Es decir, el ShoppingCart es del tipo @SessionScoped que además implementa Serializable,
     * mientras que el Logger no es Serializable, no se puede guardar en la sessión, entonces
     * digamos que no es compatible directamente. Para solucionar eso, utilizamos un modificador
     * de java llamado transient. De esta forma, inyectamos el Logger pero ese Logger no forma
     * parte del ShoppingCart, simplemente lo usamos para imprimir mensajes.
     *
     * Usaremos transient en los lugares donde lo vamos a inyectar que tengan el scope de:
     * - @SessionScoped
     * - @ConversationScope
     * y que además, implementen Serializable.
     */
    @Inject
    private transient Logger log;

    private List<CartItem> items = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        log.info("Se ha completado la inicialización del bean " + this.getClass().getName());
    }

    @PreDestroy
    public void cleanup() {
        log.info("Se está ejecutando la destrucción del bean " + this.getClass().getName());
    }

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

    public void setItemQuantity(Long productId, Integer quantity) {
        this.items.stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findFirst()
                .ifPresent(cartItem -> cartItem.setQuantity(quantity));
    }

    public void decreaseQuantityOfItem(Long productId, Integer quantity) {
        this.items.stream()
                .filter(cartItem -> productId.equals(cartItem.getProduct().getId()))
                .findFirst()
                .ifPresent(cartItem -> cartItem.decreaseQuantity(quantity));
    }
}
