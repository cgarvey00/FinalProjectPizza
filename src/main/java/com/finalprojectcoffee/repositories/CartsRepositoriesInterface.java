package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Carts;

import java.util.List;

/**
 * @author cgarvey00
 */
public interface CartsRepositoriesInterface {
    Carts addCart(Carts cart);
    Boolean addCartItem(int customerId,int productId,int quantity);
    Carts getCartById(int cartId);
    List<Carts> getCartsByCustomerId(int customerId);
    Boolean removeItemsFromCart(int customerId);
    Boolean deleteCartItem(int customerId,int productId);
    Boolean clearCart(int customerId);
    public Boolean updateCartItem(int customerId, int productId, int quantity);

}
