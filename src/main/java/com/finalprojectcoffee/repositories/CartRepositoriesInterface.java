package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.CartItem;

import java.util.List;
public interface CartRepositoriesInterface {

    // Add a product to the cart item
    Boolean addItem(int cartId, int productId, int quantity);

    // Add items to the cart
    Boolean addCart(int orderId, int cartId, List<Integer> cartItemIds);

    // Remove items from the cart
    Boolean removeItemsFromCart( List<Integer> cartItemIds);

    // Get all items in the cart
    List<CartItem> getAllCartItemsByCartId(int cartId);

    // Clear a cart
    Boolean clearCart(int cartId);
}