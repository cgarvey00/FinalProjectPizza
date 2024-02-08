package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import java.util.List;
public interface CartRepositoriesInterface {

    // Add a product to the cart
    boolean addToCart(int userId, int productId, int quantity);

    // Remove a product from the cart
    void removeFromCart(int cartId);

    // Update the quantity of a product in the cart
    void updateQuantity(int cartId, int quantity);

    // Get all items in the cart for a specific user
    List<Cart> getCartItems(int userId);

    // Get the total cost of items in the cart for a specific user
    double getTotalCost(int userId);

    // Clear the cart for a specific user after order finalization
    void clearCart(int userId);

    Cart findByUserIdAndProductId(int userId, int productId);
}
