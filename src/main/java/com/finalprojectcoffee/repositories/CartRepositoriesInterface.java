package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.CartItem;

import java.util.List;

public interface CartRepositoriesInterface {

    boolean addToCart(int userId, int productId, int quantity);

    void removeFromCart(int cartId);

    void updateQuantity(int cartId, int quantity);

    List<CartItem> getCartItems(int userId);

    double getTotalCost(int userId);

    void clearCart(int userId);

    Cart findByUserIdAndProductId(int userId, int productId);
}
