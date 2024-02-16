import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.repositories.CartRepositories;
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
        List<Cart> cartItems = cartRepositories.getCartItems(1);
        if (cartItems != null && !cartItems.isEmpty()) {
            for (Cart cartItem : cartItems) {
                cartRepositories.removeFromCart(cartItem.getId());
            }
        }

        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }


    @Test
    public void testAddToCart_AddingItemToEmptyCart() {
        assertTrue(cartRepositories.addToCart(1,1,2));
        List<Cart> cartItems = cartRepositories.getCartItems(1);
        assertEquals(1,cartItems.size());
        assertEquals(2,cartItems.get(0).getQuantity());

        double expectedCost = 5.99*2;
        assertEquals(expectedCost, cartItems.get(0).getCost(), 0.01);


    }

    @Test
    public void testAddToCart_AddingMultipleItemsToCart() {
        assertTrue(cartRepositories.addToCart(1, 1, 2));
        assertTrue(cartRepositories.addToCart(1, 2, 1));

        List<Cart> cartItems = cartRepositories.getCartItems(1);
        assertEquals(2, cartItems.size());
        assertEquals(2, cartItems.get(0).getQuantity());
        assertEquals(1, cartItems.get(1).getQuantity());
    }

    @Test
    public void testRemoveFromCart_RemovingItemFromCart() {
        assertTrue(cartRepositories.addToCart(1, 1, 2));

        List<Cart> cartItemsBeforeRemoval = cartRepositories.getCartItems(1);
        assertFalse(cartItemsBeforeRemoval.isEmpty());
        cartRepositories.removeFromCart(cartItemsBeforeRemoval.get(0).getId());

        List<Cart> cartItemsAfterRemoval = cartRepositories.getCartItems(1);
        assertTrue(cartItemsAfterRemoval.isEmpty());
    }

    @Test
    public void testUpdateQuantity_UpdateItemQuantityInCart() {
        assertTrue(cartRepositories.addToCart(1, 1, 2));

        List<Cart> cartItemsBeforeUpdate = cartRepositories.getCartItems(1);
        assertEquals(2, cartItemsBeforeUpdate.get(0).getQuantity());
        cartRepositories.updateQuantity(cartItemsBeforeUpdate.get(0).getId(), 3);

        List<Cart> cartItemsAfterUpdate = cartRepositories.getCartItems(1);
        assertEquals(3, cartItemsAfterUpdate.get(0).getQuantity());
    }

    @Test
    public void testGetTotalCost_CalculateTotalCostOfCartItems() {
        assertTrue(cartRepositories.addToCart(1, 1, 2));
        assertTrue(cartRepositories.addToCart(1, 2, 1));

        double totalCost = cartRepositories.getTotalCost(1);

        double expectedTotalCost = (5.99 * 2) + 18.0; // Cost of two pizzas and one meal deal
        assertEquals(expectedTotalCost, totalCost, 0.01);
    }




    @Test
    public void testClearCart_ClearCartForUser() {

        cartRepositories.addToCart(7, 1, 1);
        cartRepositories.addToCart(7, 2, 2);
        cartRepositories.clearCart(7);
        List<Cart> cartItems = cartRepositories.getCartItems(7);
        assertTrue(cartItems.isEmpty());
    }


}
