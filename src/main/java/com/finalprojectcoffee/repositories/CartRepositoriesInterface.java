package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.CartItem;

import java.util.List;
public interface CartRepositoriesInterface {
    //Add an empty cart
    Cart addCart();

    // Add a product to the cart item
    CartItem addItem(int cartId, int productId, int quantity);

    // Add items to the cart
    Cart createCart(List<CartItem> cartItems);

    // Remove items from the cart
    Boolean removeItemsFromCart(List<Integer> cartItemIds);

    // Get all items in the cart
    List<CartItem> getAllCartItemsByCartId(int cartId);

    // Clear a cart
    Boolean clearCart(int cartId);
}