import com.finalprojectcoffee.entities.Carts;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.CartsRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
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

public class CartIntegrationTesting {
    private EntityManagerFactory factory;
    private ProductRepositories productRep;
    private CartsRepositories cartsRep;
    private UserRepositories userRep;

    EntityManager manager;

    /**
     * Setting up the Test Database in order to facilitate
     * all categories of testing
     */
    @BeforeEach
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("testpizzadeliveryshop");
        manager = factory.createEntityManager();
        userRep = new UserRepositories(factory);
        cartsRep = new CartsRepositories(factory);
        productRep = new ProductRepositories(factory);

    }

    /**
     * Tearing down the Test Environment once complete
     */
    @AfterEach
    public void tearDown() {
        if (manager != null && manager.isOpen()) {
            manager.close();
        }
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    private Product sampleProduct1() {
        return new Product("Curly Crispy Fries ", ProductCategory.Sides, "Our New Signature Curly Fries", 3.00, 300, "curly.png");
    }

    private Product sampleProduct2() {
        return new Product("Ice-Cream", ProductCategory.Desserts, "Chocolate Chip Ice Cream", 2.00, 300, "icecream.png");
    }

    @Test
    public void testAddCartItemWithRegisteredUserAndAvailableProduct() {
        //Value IDs
        int customerId = 1;
        int productId = 2;
        int quantity = 3;


        // Customer Logs in
        Customer cust = (Customer) userRep.findUserById(customerId);

        Product product = productRep.findProductByID(productId);

        assertNotNull(product);


        assertTrue(cartsRep.addCartItem(cust.getId(), product.getId(), quantity));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);

        //Ensuring the cart Item is obtained
        Carts cartItem = cartItems.get(0);
        assertNotNull(cartItem);

        assertEquals(customerId, cartItem.getCustomer().getId());
        assertEquals(productId, cartItem.getProduct().getId());
        assertEquals(quantity, cartItem.getQuantity());

        //Deleting Cart Item
        assertTrue(cartsRep.deleteCartItem(customerId, productId));


    }

    @Test
    public void testAddCartItemWithRegisteredUserMultipleItems() {
        //Value IDs
        int customerId = 1;

        int productId1 = 1;
        int productId2 = 2;

        int quantity1 = 3;
        int quantity2 = 1;


        // Customer Logs in
        Customer cust = (Customer) userRep.findUserById(customerId);

        //Retrieving the two Products
        Product product1 = productRep.findProductByID(productId1);
        Product product2 = productRep.findProductByID(productId2);

        assertNotNull(product1);
        assertNotNull(product2);


        //Adding Items to the Cart
        assertTrue(cartsRep.addCartItem(cust.getId(), product1.getId(), quantity1));
        assertTrue(cartsRep.addCartItem(cust.getId(), product2.getId(), quantity2));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);


        assertNotNull(cartItems);
        //Checking the Size of the Cart is Equal to 2
        assertEquals(2, cartItems.size());

        System.out.println("Cart Size: " + cartItems.size());

        //Ensuring the cart Item is obtained
        Carts cartItem1 = cartItems.get(0);
        Carts cartItem2 = cartItems.get(1);

        //Cart Item 1
        assertEquals(customerId, cartItem1.getCustomer().getId());
        assertEquals(productId1, cartItem1.getProduct().getId());
        assertEquals(quantity1, cartItem1.getQuantity());

        //Cart Item 2
        assertEquals(customerId, cartItem2.getCustomer().getId());
        assertEquals(productId2, cartItem2.getProduct().getId());
        assertEquals(quantity2, cartItem2.getQuantity());

        //Deleting Cart Item
        assertTrue(cartsRep.deleteCartItem(customerId, productId1));
        assertTrue(cartsRep.deleteCartItem(customerId, productId2));

    }

    @Test
    public void testAddCartItemWithUnsuccessfulTest() {
        //Value IDs
        int customerId = 1;
        int productId = 2;
        int quantity = 3;


        // Customer Logs in
        Customer cust = (Customer) userRep.findUserById(customerId);

        Product product = productRep.findProductByID(productId);

        assertNotNull(product);


        assertTrue(cartsRep.addCartItem(cust.getId(), product.getId(), quantity));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);
        assertNotNull(cartItems);
        assertFalse(cartItems.isEmpty());
        //Ensuring the cart Item is obtained
        int nonExistentProductID = 999;
        assertFalse(cartsRep.deleteCartItem(customerId, nonExistentProductID));

        List<Carts> updatedCartItems = cartsRep.getCartsByCustomerId(customerId);
        assertNotNull(updatedCartItems);
        assertFalse(updatedCartItems.isEmpty());

        //Deleting Cart Item
        assertTrue(cartsRep.deleteCartItem(customerId, productId));


    }

    @Test
    public void testAddCartItemsWithRegisteredUserMultipleItemsClearingCart() {
        //Value IDs
        int customerId = 1;

        int productId1 = 1;
        int productId2 = 2;

        int quantity1 = 3;
        int quantity2 = 1;

        // Customer Logs in
        Customer cust = (Customer) userRep.findUserById(customerId);

        //Retrieving the two Products
        Product product1 = productRep.findProductByID(productId1);
        Product product2 = productRep.findProductByID(productId2);

        assertNotNull(product1);
        assertNotNull(product2);

        //Adding Items to the Cart
        assertTrue(cartsRep.addCartItem(cust.getId(), product1.getId(), quantity1));
        assertTrue(cartsRep.addCartItem(cust.getId(), product2.getId(), quantity2));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);


        assertNotNull(cartItems);
        //Checking the Size of the Cart is Equal to 2
        assertEquals(2, cartItems.size());

        System.out.println("Cart Size: " + cartItems.size());

        //Ensuring the cart Item is obtained
        Carts cartItem1 = cartItems.get(0);
        Carts cartItem2 = cartItems.get(1);

        //Cart Item 1
        assertEquals(customerId, cartItem1.getCustomer().getId());
        assertEquals(productId1, cartItem1.getProduct().getId());
        assertEquals(quantity1, cartItem1.getQuantity());

        //Cart Item 2
        assertEquals(customerId, cartItem2.getCustomer().getId());
        assertEquals(productId2, cartItem2.getProduct().getId());
        assertEquals(quantity2, cartItem2.getQuantity());

        //Displaying Cart Items
        for (Carts cart : cartItems) {
            System.out.println(cart.toString());
        }

        //Clearing the Entire Cart
        assertTrue(cartsRep.clearCart(customerId));


    }

    @Test
    public void testUpdateCartItemsSuccessfulWithRegisteredUserMultipleItems() {
        //Value IDs
        int customerId = 1;

        int productId = 1;
        int oldQuantity = 2;
        int newQuantity = 10;

        // Customer Logs in
        Customer cust = (Customer) userRep.findUserById(customerId);

        //Retrieving the Product
        Product product1 = productRep.findProductByID(productId);

        assertNotNull(product1);

        //Adding Item to the Cart with initial quantity
        assertTrue(cartsRep.addCartItem(cust.getId(), product1.getId(), oldQuantity));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);

        assertNotNull(cartItems);
        //Checking the Size of the Cart is Equal to 1
        assertEquals(1, cartItems.size());

        System.out.println("Cart Size: " + cartItems.size());

        //Ensuring the cart Item is obtained
        Carts cartItem1 = cartItems.get(0);

        //Cart Item 1
        assertEquals(customerId, cartItem1.getCustomer().getId());
        assertEquals(productId, cartItem1.getProduct().getId());
        assertEquals(oldQuantity, cartItem1.getQuantity());


        //Displaying Cart Items
        for (Carts cart : cartItems) {
            System.out.println(cart.toString());
        }

        //Updating Quantity
        assertTrue(cartsRep.updateCartItem(customerId, productId, newQuantity));

        List<Carts> newCartItems = cartsRep.getCartsByCustomerId(customerId);

        assertNotNull(newCartItems);

        //Displaying Updated Cart Items
        for (Carts cart : newCartItems) {
            System.out.println(cart.toString());
        }

        //Ensuring the cart Item is obtained from the updated Cart
        Carts newCartItem = newCartItems.get(0);

        assertEquals(newQuantity, newCartItem.getQuantity());

        assertTrue(cartsRep.clearCart(customerId));

    }

    @Test
    public void testUpdateCartItemsFailureWithRegisteredUserMultipleItems() {
        //Value IDs
        int customerId = 1;

        int productId = 1;
        int oldQuantity = 2;

        //Invalid Quantity
        int newQuantity = 1030;

        // Customer Logs in
        Customer cust = (Customer) userRep.findUserById(customerId);

        //Retrieving the Product
        Product product1 = productRep.findProductByID(productId);

        assertNotNull(product1);

        //Adding Item to the Cart with initial quantity
        assertTrue(cartsRep.addCartItem(cust.getId(), product1.getId(), oldQuantity));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);

        assertNotNull(cartItems);
        //Checking the Size of the Cart is Equal to 1
        assertEquals(1, cartItems.size());

        System.out.println("Cart Size: " + cartItems.size());

        //Ensuring the cart Item is obtained
        Carts cartItem1 = cartItems.get(0);

        //Cart Item 1
        assertEquals(customerId, cartItem1.getCustomer().getId());
        assertEquals(productId, cartItem1.getProduct().getId());
        assertEquals(oldQuantity, cartItem1.getQuantity());


        //Displaying Cart Items
        for (Carts cart : cartItems) {
            System.out.println(cart.toString());
        }

        //Failure to Updating Quantity, as quantity is too high
        assertFalse(cartsRep.updateCartItem(customerId, productId, newQuantity));

        assertTrue(cartsRep.clearCart(customerId));
    }

    @Test
    public void testUpdateCartItemsFailureAsQuantityIsNegativeWithRegisteredUserMultipleItems() {
        //Value IDs
        int customerId = 1;

        int productId = 1;
        int oldQuantity = 2;

        //Invalid Quantity
        int newQuantity = -10;

        // Customer Logs in
        Customer cust = (Customer) userRep.findUserById(customerId);

        //Retrieving the Product
        Product product1 = productRep.findProductByID(productId);

        assertNotNull(product1);

        //Adding Item to the Cart with initial quantity
        assertTrue(cartsRep.addCartItem(cust.getId(), product1.getId(), oldQuantity));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);

        assertNotNull(cartItems);
        //Checking the Size of the Cart is Equal to 1
        assertEquals(1, cartItems.size());

        System.out.println("Cart Size: " + cartItems.size());

        //Ensuring the cart Item is obtained
        Carts cartItem1 = cartItems.get(0);

        //Cart Item 1
        assertEquals(customerId, cartItem1.getCustomer().getId());
        assertEquals(productId, cartItem1.getProduct().getId());
        assertEquals(oldQuantity, cartItem1.getQuantity());


        //Displaying Cart Items
        for (Carts cart : cartItems) {
            System.out.println(cart.toString());
        }

        //Failure to Updating Quantity, as quantity is too high
        assertFalse(cartsRep.updateCartItem(customerId, productId, newQuantity));

        assertTrue(cartsRep.clearCart(customerId));
    }


    @Test
    public void testDeleteCartItemsSuccessfulWithRegisteredUserMultipleItems() {
        //Value IDs
        int customerId = 1;

        int productId = 1;
        int oldQuantity = 2;
        int newQuantity = 10;

        // Customer Logs in
        Customer cust = (Customer) userRep.findUserById(customerId);

        //Retrieving the Product
        Product product1 = productRep.findProductByID(productId);

        assertNotNull(product1);

        //Adding Item to the Cart with initial quantity
        assertTrue(cartsRep.addCartItem(cust.getId(), product1.getId(), oldQuantity));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);

        assertNotNull(cartItems);
        //Checking the Size of the Cart is Equal to 1
        assertEquals(1, cartItems.size());

        System.out.println("Cart Size: " + cartItems.size());

        //Ensuring the cart Item is obtained
        Carts cartItem1 = cartItems.get(0);

        //Cart Item 1
        assertEquals(customerId, cartItem1.getCustomer().getId());
        assertEquals(productId, cartItem1.getProduct().getId());
        assertEquals(oldQuantity, cartItem1.getQuantity());


        //Displaying Cart Items
        for (Carts cart : cartItems) {
            System.out.println(cart.toString());
        }

        assertTrue(cartsRep.deleteCartItem(customerId, productId));


    }

    @Test
    public void testDeleteCartItemsWhereCustomerIsActiveAndProductIDIsFalse() {
        //Value IDs
        int customerId = 1;

        int productId = 1;
        int quantity = 2;


        // Customer Logs in
        Customer cust = (Customer) userRep.findUserById(customerId);

        //Retrieving the Product
        Product product1 = productRep.findProductByID(productId);

        assertNotNull(product1);

        //Adding Item to the Cart with initial quantity
        assertTrue(cartsRep.addCartItem(cust.getId(), product1.getId(), quantity));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);

        assertNotNull(cartItems);
        //Checking the Size of the Cart is Equal to 1
        assertEquals(1, cartItems.size());

        System.out.println("Cart Size: " + cartItems.size());

        //Ensuring the cart Item is obtained
        Carts cartItem1 = cartItems.get(0);

        //Cart Item 1
        assertEquals(customerId, cartItem1.getCustomer().getId());
        assertEquals(productId, cartItem1.getProduct().getId());
        assertEquals(quantity, cartItem1.getQuantity());


        //Displaying Cart Items
        for (Carts cart : cartItems) {
            System.out.println(cart.toString());
        }

        //Supplying an invalid ProductID
        assertFalse(cartsRep.deleteCartItem(customerId, -1000));

        assertTrue(cartsRep.deleteCartItem(customerId, productId));


    }

    @Test
    public void testDeleteCartItemsWhereCustomerIsActiveAndProductIDWhereAllIsDeleted() {
        //Value IDs
        int customerId = 1;

        int productId1 = 1;
        int productId2 = 2;

        int quantity1 = 3;
        int quantity2 = 1;

        // Customer Logs in
        Customer cust = (Customer) userRep.findUserById(customerId);

        //Retrieving the two Products
        Product product1 = productRep.findProductByID(productId1);
        Product product2 = productRep.findProductByID(productId2);

        assertNotNull(product1);
        assertNotNull(product2);

        //Adding Items to the Cart
        assertTrue(cartsRep.addCartItem(cust.getId(), product1.getId(), quantity1));
        assertTrue(cartsRep.addCartItem(cust.getId(), product2.getId(), quantity2));

        List<Carts> cartItems = cartsRep.getCartsByCustomerId(customerId);


        assertNotNull(cartItems);
        //Checking the Size of the Cart is Equal to 2
        assertEquals(2, cartItems.size());

        System.out.println("Cart Size: " + cartItems.size());

        //Ensuring the cart Item is obtained
        Carts cartItem1 = cartItems.get(0);
        Carts cartItem2 = cartItems.get(1);

        //Cart Item 1
        assertEquals(customerId, cartItem1.getCustomer().getId());
        assertEquals(productId1, cartItem1.getProduct().getId());
        assertEquals(quantity1, cartItem1.getQuantity());

        //Cart Item 2
        assertEquals(customerId, cartItem2.getCustomer().getId());
        assertEquals(productId2, cartItem2.getProduct().getId());
        assertEquals(quantity2, cartItem2.getQuantity());

        //Displaying Cart Items
        for (Carts cart : cartItems) {
            System.out.println(cart.toString());
        }

        //Clearing the Entire Cart
        assertTrue(cartsRep.removeItemsFromCart(customerId));


    }
}
