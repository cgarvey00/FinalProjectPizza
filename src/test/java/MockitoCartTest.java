import com.finalprojectcoffee.entities.Carts;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.CartsRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

/**
 * @author cgarvey00
 */
@ExtendWith(MockitoExtension.class)
public class MockitoCartTest {

    @Mock
    private EntityManagerFactory mockFactory;

    @Mock
    private EntityManager mockManager;

    @Mock
    private EntityTransaction mockTransaction;
    @InjectMocks
    private ProductRepositories productRepositories;
    @InjectMocks
    private CartsRepositories cartRep;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        lenient().when(mockFactory.createEntityManager()).thenReturn(mockManager);
        lenient().when(mockManager.getTransaction()).thenReturn(mockTransaction);
        productRepositories = new ProductRepositories(mockFactory);
        cartRep = new CartsRepositories(mockFactory);
    }

    @Test
    public void testAddCartWhereSuccessful() {
        int customerId = 1;
        int productId = 2;
        int quantity = 1;

        Product product = new Product(productId, "Ice-Cream", ProductCategory.Desserts, "Chocolate Chip Ice Cream", 2.00, 300, "icecream.png");
        Customer customer = new Customer();
        Carts cartItem = new Carts(product, customer, quantity, product.getPrice() * quantity);

        when(mockManager.find(Product.class, productId)).thenReturn(product);

        TypedQuery<Carts> mockQuery = mock(TypedQuery.class);
        when(mockManager.createQuery(anyString(), eq(Carts.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("customerId"), eq(customerId))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("productId"), eq(productId))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

        boolean addResult = cartRep.addCartItem(customerId, productId, quantity);

        assertTrue(addResult, "Cart Item is added successfully");
        verify(mockManager).persist(any(Carts.class));
        verify(mockManager).createQuery(anyString(), eq(Carts.class));
    }

    @Test
    public void testAddCartWhereUnSuccessful() {
        int customerId = 1;
        int productId = 2;
        int quantity = 1;

        when(mockManager.find(Product.class, productId)).thenReturn(null);

        boolean addResult = cartRep.addCartItem(customerId, productId, quantity);

        assertFalse(addResult, "Cart Item is cannot be added");
        verify(mockManager, never()).persist(any(Carts.class));
        verify(mockManager, never()).createQuery(anyString(), eq(Carts.class));
    }

    @Test
    public void testGetCartById() {
        int cartId = 1;

        Carts expectedCart = new Carts();

        TypedQuery<Carts> mockQuery = mock(TypedQuery.class);
        when(mockManager.createQuery(anyString(), eq(Carts.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("cartId"), eq(cartId))).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(expectedCart);

        Carts result = cartRep.getCartById(cartId);

        assertEquals(expectedCart, result);
        verify(mockManager).createQuery(anyString(), eq(Carts.class));
        verify(mockQuery).setParameter(eq("cartId"), eq(cartId));
    }

    @Test
    public void testGetCartByCustomerId() {
        int customerId = 1;

        TypedQuery<Carts> mockQuery = mock(TypedQuery.class);
        when(mockManager.createQuery(anyString(), eq(Carts.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("customerId"), eq(customerId))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

        List<Carts> result = cartRep.getCartsByCustomerId(customerId);

        assertNotNull(result);
        verify(mockManager).createQuery(anyString(), eq(Carts.class));
        verify(mockQuery).setParameter(eq("customerId"), eq(customerId));
    }

    @Test
    public void testDeleteCartItem() {
        int customerId = 1;
        int productId = 2;
        TypedQuery<Carts> mockQuery = mock(TypedQuery.class);
        when(mockManager.createQuery(anyString(), eq(Carts.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("customerId"), eq(customerId))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("productId"), eq(productId))).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

        when(mockManager.getTransaction()).thenReturn(mockTransaction);

        assertFalse(cartRep.deleteCartItem(customerId, productId));

        verify(mockManager).createQuery(anyString(), eq(Carts.class));
        verify(mockQuery).setParameter(eq("customerId"), eq(customerId));
        verify(mockQuery).setParameter(eq("productId"), eq(productId));
        verify(mockTransaction).commit();
    }

    @Test
    public void testClearCart() {
        int customerId = 1;
        TypedQuery<Carts> mockQuery = mock(TypedQuery.class);
        when(mockManager.createQuery(anyString(), eq(Carts.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter(eq("customerId"), eq(customerId))).thenReturn(mockQuery);

        when(mockQuery.getResultList()).thenReturn(Collections.singletonList(new Carts()));

        when(mockManager.getTransaction()).thenReturn(mockTransaction);

        assertTrue(cartRep.clearCart(customerId));

        verify(mockManager).createQuery(anyString(), eq(Carts.class));
        verify(mockQuery).setParameter(eq("customerId"), eq(customerId));
        verify(mockTransaction).commit();
    }
}
