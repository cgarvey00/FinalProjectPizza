import com.finalprojectcoffee.entities.CartItem;
import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.CartRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CartRepositoriesTest {

    private CartRepositories cartRep;
    private EntityManagerFactory factory;

    @BeforeEach
    public void setup() {
        factory = Persistence.createEntityManagerFactory("testpizzashop");
        cartRep = new CartRepositories(factory);
    }

//    @Test
//    public void addItem_success() {
//        Product product = new Product();
//        product.setName("Test Product");
//        product.setPrice(10.0);
//        product.setStock(100);
//
//        ProductRepositories productRepo = new ProductRepositories(factory);
//
//        assertTrue(productRepo.addProduct(product));
//
//        int productId = product.getId();
//
//        CartItem cartItem = cartRep.addItem(productId, 5);
//
//        assertNotNull(cartItem);
//        assertNotNull(cartItem.getProduct());
//        assertEquals(productId, cartItem.getProduct().getId());
//        assertEquals(5, cartItem.getQuantity());
//        assertEquals(50.0, cartItem.getCost());
//
//        assertTrue(cartRep.removeItemsFromCart(List.of(cartItem.getId())));
//    }








@Test
    public void addItem_invalidProductId() {
        CartItem cartItem = cartRep.addItem(9999, 5);
        assertNull(cartItem);
    }

    @Test
    public void getAllCartItemsByCartId_invalidCartId() {
        List<CartItem> cartItems = cartRep.getAllCartItemsByCartId(9999);
        assertNotNull(cartItems);
        assertEquals(0, cartItems.size());
    }

}
