package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CartRepositoriesTest {

    private EntityManagerFactory factory;
    private CartRepositories cartRepositories;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("testpizzashop");
        cartRepositories = new CartRepositories(factory);
    }

    @After
    public void tearDown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

//    @Test
//    public void testAddToCart_AddingItemToEmptyCart() {
//        assertTrue(cartRepositories.addToCart(1,1,2));
//        List<Cart> cartItems = cartRepositories.getCartItems(1);
//        assertEquals(1,cartItems.size());
//        assertEquals(2,cartItems.get(0).getQuantity());
//    }

    @Test
    public void testClearCart_ClearCartForUser() {
        // Test clearing cart for a user
        cartRepositories.addToCart(7, 1, 1);
        cartRepositories.addToCart(7, 2, 2);
        cartRepositories.clearCart(7);
        List<Cart> cartItems = cartRepositories.getCartItems(7);
        assertTrue(cartItems.isEmpty());
    }


}
