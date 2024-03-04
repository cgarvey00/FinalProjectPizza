import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.CartItem;
import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.CartRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CartRepositoriesTest {

    private CartRepositories cartRep;
    private EntityManagerFactory factory;
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        factory = Persistence.createEntityManagerFactory("testpizzashop");
        entityManager = factory.createEntityManager();
        cartRep = new CartRepositories(factory);
    }

    @AfterEach
    public void destroy(){
        if(factory != null && factory.isOpen()){
            factory.close();
        }
    }

    @Test
    public void addItemTest(){


        entityManager.close();
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
    public void getAllCartItemsByCartId_invalidCartId() {
        List<CartItem> cartItems = cartRep.getAllCartItemsByCartId(9999);
        assertNotNull(cartItems);
        assertEquals(0, cartItems.size());
    }

}
