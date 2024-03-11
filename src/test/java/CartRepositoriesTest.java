import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.CartItem;
import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.CartRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
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
        List<Product> products = new ArrayList<>();
        products.add(new Product("Test Product 1", ProductCategory.Pizzas, "Test details", 10.0, 5, "test1.jpg"));
        products.add(new Product("Test Product 2", ProductCategory.Pizzas, "Test details", 10.0, 10, "test2.jpg"));

        ProductRepositories productRepositories = new ProductRepositories(factory);
        productRepositories.addProducts(products);

        int initialStock = products.get(0).getStock();

        int cartId = 1;
        int quantity = 2;
        CartItem cartItem = cartRepositories.addItem(cartId, products.get(0).getId(), quantity);

        assertNotNull(cartItem);
        assertEquals(quantity, cartItem.getQuantity());

        Product updatedProduct = productRepositories.findProductByID(products.get(0).getId());

        assertNotNull(updatedProduct);
        assertEquals(initialStock - quantity, updatedProduct.getStock());
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
