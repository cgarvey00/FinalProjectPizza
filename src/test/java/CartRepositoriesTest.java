import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.CartItem;
import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.CartRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartRepositoriesTest {

    private EntityManagerFactory factory;
    private CartRepositories cartRepositories;

    @BeforeEach
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("testpizzashop");
        cartRepositories = new CartRepositories(factory);
    }

    @AfterEach
    public void tearDown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    @Test
    public void addCart_Success() {
        Cart cart = cartRepositories.addCart();
        assertNotNull(cart);
        assertNotNull(cart.getId());
    }

    @Test
    public void addItem_Success() {
        Product product = new Product();
        int cartId = 1;
        int quantity = 2;

        CartItem cartItem = cartRepositories.addItem(cartId, product.getId(), quantity);
        assertNotNull(cartItem);
        assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    public void createCart_Success() {
        List<CartItem> cartItems = new ArrayList<>();

        Cart cart = cartRepositories.createCart(cartItems);
        assertNotNull(cart);
        assertNotNull(cart.getId());
    }

    @Test
    public void removeItemsFromCart_Success() {
        List<Integer> cartItemIds = new ArrayList<>();

        boolean result = cartRepositories.removeItemsFromCart(cartItemIds);
        assertTrue(result);
    }

    @Test
    public void getAllCartItemsByCartId_Success() {
        int cartId = 1;

        List<CartItem> cartItems = cartRepositories.getAllCartItemsByCartId(cartId);
        assertNotNull(cartItems);
    }

    @Test
    public void clearCart_Success() {
        int cartId = 1;

        boolean result = cartRepositories.clearCart(cartId);
        assertTrue(result);
    }

    @Test
    public void addItem_InvalidCartId() {
        int cartId = -1;
        int productId = 1;
        int quantity = 2;

        CartItem cartItem = cartRepositories.addItem(cartId, productId, quantity);
        assertNull(cartItem);
    }

    @Test
    public void addItem_InvalidProductId() {
        int cartId = 1;
        int productId = -1;
        int quantity = 2;

        CartItem cartItem = cartRepositories.addItem(cartId, productId, quantity);
        assertNull(cartItem);
    }

    @Test
    public void addItem_InsufficientStock() {
        int cartId = 1;
        int productId = 1;
        int quantity = 1000;

        CartItem cartItem = cartRepositories.addItem(cartId, productId, quantity);
        assertNull(cartItem);
    }

    @Test
    public void createCart_EmptyCartItemsList() {
        List<CartItem> cartItems = new ArrayList<>();

        Cart cart = cartRepositories.createCart(cartItems);
        assertNull(cart);
    }

    @Test
    public void removeItemsFromCart_InvalidCartItemId() {
        List<Integer> cartItemIds = new ArrayList<>();
        cartItemIds.add(-1);

        boolean result = cartRepositories.removeItemsFromCart(cartItemIds);
        assertFalse(result);
    }

    @Test
    public void getAllCartItemsByCartId_InvalidCartId() {
        int cartId = -1;

        List<CartItem> cartItems = cartRepositories.getAllCartItemsByCartId(cartId);
        assertNotNull(cartItems);
        assertTrue(cartItems.isEmpty());
    }

    @Test
    public void clearCart_InvalidCartId() {
        int cartId = -1;

        boolean result = cartRepositories.clearCart(cartId);
        assertFalse(result);
    }
}
