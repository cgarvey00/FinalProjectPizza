//import com.finalprojectcoffee.entities.Product;
//import com.finalprojectcoffee.entities.ProductCategory;
//import com.finalprojectcoffee.repositories.ProductRepositories;
//import jakarta.persistence.*;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductRepositoriesMockingTest {
//
//    @Mock
//    private EntityManagerFactory mockFactory;
//
//    @Mock
//    private EntityManager mockManager;
//
//    @Mock
//    private EntityTransaction mockTransaction;
//    @InjectMocks
//    private ProductRepositories productRepositories;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        lenient().when(mockFactory.createEntityManager()).thenReturn(mockManager);
//        lenient().when(mockManager.getTransaction()).thenReturn(mockTransaction);
//        productRepositories = new ProductRepositories(mockFactory);
//    }
//
//    private Product sampleProduct1() {
//        return new Product("Pepperoni Deluxe", ProductCategory.Pizzas, "Pepperoni and Cheese 10`", 5.99, 100, "pizza1.jpg");
//    }
//
//    private Product sampleProduct1WithID(int id) {
//        Product sampleProduct1 = sampleProduct1();
//        sampleProduct1.setId(id);
//        return sampleProduct1;
//    }
//
//    private Product sampleProduct2WithID(int id) {
//        Product sampleProduct2 = sampleProduct2();
//        sampleProduct2.setId(id);
//        return sampleProduct2;
//    }
//
//    private Product sampleProduct2() {
//        return new Product("Ice-Cream", ProductCategory.Desserts, "Chocolate Chip Ice Cream", 2.00, 300, "icecream.png");
//    }
//
//    @Test
//    public void testFindProductByIDWhereIsValid() {
//        when(mockManager.find(Product.class, 1)).thenReturn((sampleProduct1WithID(1)));
//
//        Product result = productRepositories.findProductByID(1);
//
//        assertEquals("Pepperoni Deluxe", result.getName());
//        assertEquals(ProductCategory.Pizzas, result.getCategory());
//        assertEquals("Pepperoni and Cheese 10`", result.getDetails());
//        assertEquals(5.99, result.getPrice());
//        assertEquals(100, result.getStock());
//        assertEquals("pizza1.jpg", result.getImage());
//        assertEquals(1, result.getId());
//        System.out.println("Test Passed: " + result);
//        verify(mockManager, times(1)).close();
//    }
//
//    @Test
//    public void testFindProductByIDWhereProductIsNotAvailable() {
//        when(mockManager.find(Product.class, 3)).thenReturn(null);
//
//        Product p = productRepositories.findProductByID(3);
//
//        assertNull(p, "Product does not exist in this instance");
//
//        verify(mockManager, times(1)).close();
//
//
//    }
//    @Test
//    public void testAddProductWhereSuccessful() {
//        TypedQuery<Long> mockQuery = mock(TypedQuery.class);
//        when(mockManager.find(Product.class, 1)).thenReturn(sampleProduct2WithID(1));
//        when(mockManager.createQuery("SELECT COUNT(p) FROM Product p WHERE p.name=:name", Long.class)).thenReturn(mockQuery);
//        when(mockQuery.setParameter(eq("name"), anyString())).thenReturn(mockQuery);
//
//        //Updating Stubbing in order to use Optional
//        when(mockQuery.getSingleResult()).thenReturn(0L);
//
//        boolean addResult = productRepositories.addProduct(sampleProduct1());
//        assertTrue(addResult, "Product is added to the Shop");
//
//        //Testing to ensure the Product has been added to the mock repository
//        Product result = productRepositories.findProductByID(1);
//
//
//        assertNotNull(productRepositories.findProductByID(1));
//        assertEquals("Ice-Cream", result.getName());
//        assertEquals(ProductCategory.Desserts, result.getCategory());
//        assertEquals("Chocolate Chip Ice Cream", result.getDetails());
//        assertEquals(2.00, result.getPrice());
//        assertEquals(300, result.getStock());
//        assertEquals("icecream.png", result.getImage());
//        assertEquals(1, result.getId());
//        System.out.println("Test Passed: " + result);
//
//        InOrder inOrder = inOrder(mockManager);
//        inOrder.verify(mockManager, times(1)).persist(any(Product.class));
//        verify(mockManager, times(1)).createQuery("SELECT COUNT(p) FROM Product p WHERE p.name=:name", Long.class);
//    }
//
//    @Test
//    public void testDeleteProductWhereProductIsAvailable() {
//        when(mockManager.find(Product.class, 1)).thenReturn((sampleProduct1WithID(1)));
//
//        boolean delete = productRepositories.deleteProduct(1);
//
//        assertTrue(delete, "Product will be deleted");
//
//        verify(mockManager, times(1)).remove(any(Product.class));
//
//        verify(mockManager, times(1)).close();
//    }
//
//    @Test
//    public void testDeleteProductByIDWhereProductIsNotAvailable() {
//        when(mockManager.find(Product.class, 3)).thenReturn(null);
//
//        boolean deleteResult = productRepositories.deleteProduct(3);
//
//        assertFalse(deleteResult, "Product should not be deleted");
//
//        verify(mockManager, never()).remove(any(Product.class));
//        verify(mockManager, times(1)).close();
//
//
//    }
//}
//