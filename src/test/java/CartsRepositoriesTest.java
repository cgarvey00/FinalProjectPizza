import com.finalprojectcoffee.entities.Carts;
import com.finalprojectcoffee.repositories.CartsRepositories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cgarvey00
 */
public class CartsRepositoriesTest {
    private EntityManagerFactory factory;
    private CartsRepositories cartsRep;
    EntityManager manager;

    /**
     * Setting up the Test Database in order to facilitate
     * all categories of testing
     */
    @BeforeEach
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("testpizzadeliveryshop");
        manager = factory.createEntityManager();
        cartsRep = new CartsRepositories(factory);

    }

    @AfterEach
    public void tearDown() {
        if (manager != null && manager.isOpen()) {
            manager.close();
        }
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    @Test
    public void testAddCartItem() {
        int customerId = 1;
        int productId = 1;
        int quantity = 1;

        assertTrue(cartsRep.addCartItem(customerId, productId, quantity));

        assertTrue(cartsRep.deleteCartItem(customerId, productId));
    }

    @Test
    public void testAddCartItemWhereItAlreadyExists() {
        int customerId = 1;
        int productId = 1;
        int quantity = 1;

        assertTrue(cartsRep.addCartItem(customerId, productId, quantity));

        assertFalse(cartsRep.addCartItem(customerId, productId, quantity));

        assertTrue(cartsRep.deleteCartItem(customerId, productId));
    }

    @Test
    public void testAddCartItemWhereQuantityIsTooHigh() {
        int customerId = 1;
        int productId = 1;
        int quantity = 1000;

        assertFalse(cartsRep.addCartItem(customerId, productId, quantity));

    }


    @Test
    public void testUpdateCartItemSuccessful() {
        int customerId = 1;
        int productId = 2;
        int originalQuantity = 1;
        int updatedQuantity = 5;

        assertTrue(cartsRep.addCartItem(customerId, productId, originalQuantity));

        //Checking if update is successful

        assertTrue(cartsRep.updateCartItem(customerId, productId, updatedQuantity));

        List<Carts> updatedCartItem = cartsRep.getCartsByCustomerId(customerId);

        assertTrue(updatedCartItem.get(0).getQuantity() == updatedQuantity);

        System.out.println("Updated Quantity: " + updatedCartItem.get(0).getQuantity());

        System.out.println("Cart Details: "+updatedCartItem.get(0).toString());

        assertTrue(cartsRep.deleteCartItem(customerId, productId));

    }



    @Test
    public void testUpdateCartItemUnSuccessfulWhereProductDoesntExist() {
        int customerId = 1;
        int productId = 2;
        int updatedQuantity = 2;

        assertFalse(cartsRep.updateCartItem(customerId, productId, updatedQuantity));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);
        assertTrue(cartItems.isEmpty());
    }

    @Test
    public void testGetCartByIDSuccessful() {
        int cartId = 1;
        Carts cart = cartsRep.getCartById(cartId);

        assertNotNull(cart);
        assertEquals(cartId, cart.getId());
    }

    @Test
    public void testGetCartByIDUnSuccessful() {
        int cartId = -1;
        Carts cart = cartsRep.getCartById(cartId);

        assertNull(cart);
    }

    @Test
    public void testGetCartByCustomerIDSuccessful() {
        int customerId = 5;

        List<Carts> carts = cartsRep.getCartsByCustomerId(customerId);
        assertNotNull(carts);
        assertFalse(carts.isEmpty());
    }

    @Test
    public void testGetCartByCustomerIDUnSuccessful() {
        int customerId = -5;

        List<Carts> carts = cartsRep.getCartsByCustomerId(customerId);
        assertNotNull(carts);
        assertTrue(carts.isEmpty());
    }

    @Test
    public void testRemoveItemsFromCartSuccessful() {
        int customerId = 1;

        assertTrue(cartsRep.addCartItem(customerId, 2, 4));
        assertTrue(cartsRep.addCartItem(customerId, 1, 4));

        System.out.println(cartsRep.getCartsByCustomerId(customerId).size());

        assertTrue(cartsRep.removeItemsFromCart(customerId));
    }

    @Test
    public void testRemoveItemsFromCartUnSuccessful() {
        int customerId = -1;

        assertFalse(cartsRep.removeItemsFromCart(customerId));
    }

    @Test
    public void testDeleteCartItemSuccessful() {
        int customerId = 1;

        assertTrue(cartsRep.addCartItem(customerId, 2, 4));

        System.out.println(cartsRep.getCartsByCustomerId(customerId).size());

        assertTrue(cartsRep.deleteCartItem(customerId, 2));
    }

    @Test
    public void testDeleteCartItemsFromCartUnSuccessful() {
        assertFalse(cartsRep.deleteCartItem(-1, -1));
    }

    @Test
    public void testClearCartSuccessful() {
        int customerId = 1;

        assertTrue(cartsRep.addCartItem(customerId, 2, 4));
        assertTrue(cartsRep.addCartItem(customerId, 1, 4));

        System.out.println("Cart Size : " + cartsRep.getCartsByCustomerId(customerId).size());

        assertTrue(cartsRep.clearCart(customerId));

        System.out.println("Cart Size After Clearing Cart : " + cartsRep.getCartsByCustomerId(customerId).size());
    }

    @Test
    public void testClearCartUnsuccessful() {
        int customerId = -1;

        assertFalse(cartsRep.clearCart(customerId));
    }

}
