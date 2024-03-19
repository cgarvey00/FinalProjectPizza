//import com.finalprojectcoffee.entities.Product;
//import com.finalprojectcoffee.entities.ProductCategory;
//import com.finalprojectcoffee.repositories.ProductRepositories;
//import jakarta.persistence.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InOrder;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.never;
//
///**
// * @author cgarvey00
// */
//@ExtendWith(MockitoExtension.class)
//public class ProductMockitoTesting {
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
//    /**
//     * This test will ensure that a Mock Product is added in order to Find it through using an ID
//     */
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
//
//    @Test
//    public void testAddProductWhereSuccessful() {
//        TypedQuery<Product> mockQuery = mock(TypedQuery.class);
//        when(mockManager.find(Product.class, 1)).thenReturn(sampleProduct2WithID(1));
//        when(mockManager.createQuery(anyString(), eq(Product.class))).thenReturn(mockQuery);
//        when(mockQuery.setParameter(eq("name"), anyString())).thenReturn(mockQuery);
//        List<Product> productList = new ArrayList<>();
//        productList.add(sampleProduct2WithID(1));
//
//        boolean addResult = productRepositories.addProducts(productList);
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
//        verify(mockManager, times(1)).createQuery(anyString(), eq(Product.class));
//    }
//
//    @Test
//    public void testDeleteProductWhereProductIsAvailable() {
//        TypedQuery<Product> mockQuery = mock(TypedQuery.class);
//        when(mockManager.createQuery(anyString(), eq(Product.class))).thenReturn(mockQuery);
//        when(mockQuery.setParameter(eq("id"), anyInt())).thenReturn(mockQuery);
//        when(mockQuery.getSingleResult()).thenReturn((sampleProduct2WithID(1)));
//        List<Product> productList = new ArrayList<>();
//        productList.add(sampleProduct2WithID(1));
//
//        boolean delete = productRepositories.deleteProduct(productList);
//
//        assertTrue(delete, "Product will be deleted from the Shop");
//
//        InOrder inOrder = inOrder(mockManager, mockQuery);
//        inOrder.verify(mockQuery, times(1)).setParameter(eq("id"), anyInt());
//        inOrder.verify(mockQuery, times(1)).getSingleResult();
//        inOrder.verify(mockManager, times(1)).remove(any(Product.class));
//
//
//    }
//
//    @Test
//    public void testDeleteProductByIDWhereProductIsNotAvailable() {
//        TypedQuery<Product> mockQuery = mock(TypedQuery.class);
//        when(mockManager.createQuery(anyString(), eq(Product.class))).thenReturn(mockQuery);
//        when(mockQuery.setParameter(eq("id"), anyInt())).thenReturn(mockQuery);
//
//        when(mockQuery.getSingleResult()).thenThrow(new NoResultException());
//
//        List<Product> productList = new ArrayList<>();
//        productList.add(sampleProduct2WithID(1));
//
//        boolean delete = productRepositories.deleteProduct(productList);
//
//        assertTrue(delete, "Product will not be deleted from the Shop");
//
//        verify(mockManager, never()).remove(any(Product.class));
//
//    }
//}
