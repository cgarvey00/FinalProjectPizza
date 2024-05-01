import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductRepositoriesTest {
    private EntityManagerFactory factory;
    private ProductRepositories productRep;

    @BeforeEach
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("test_pizza_shop");
        productRep = new ProductRepositories(factory);
    }

    @AfterEach
    public void tearDown() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    @Test
    @Order(1)
    public void findProductByIdTest(){
        Product result = productRep.findProductByID(1);
        assertNotNull(result);
    }

    @Test
    @Order(2)
    public void getAllProducts(){
        List<Product> result = productRep.getAllProducts();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @Order(3)
    public void findProductsByCategory(){
        List<Product> result = productRep.findProductsByCategory(ProductCategory.Pizzas);
        assertNotNull(result);
        assertEquals(ProductCategory.Pizzas, result.get(0).getCategory());
    }

    @Test
    @Order(4)
    public void addProductTest(){
        Product product = new Product("test", ProductCategory.Pizzas, "testDetails", 1.0, 100, null);
        boolean result = productRep.addProduct(product);
        assertTrue(result);
        List<Product> products = productRep.getAllProducts();
        assertEquals(3, products.size());
    }

    @Test
    @Order(5)
    public void updateProductTest(){
        Product product = productRep.findProductByID(3);
        product.setCategory(ProductCategory.Drinks);
        boolean result = productRep.updateProduct(product);
        assertTrue(result);
        product = productRep.findProductByID(3);
        assertEquals(ProductCategory.Drinks, product.getCategory());
    }

    @Test
    @Order(6)
    public void deleteProductTest(){
        boolean result = productRep.deleteProduct(3);
        assertTrue(result);
        List<Product> products = productRep.getAllProducts();
        assertEquals(2, products.size());
    }
}