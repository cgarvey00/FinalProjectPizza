import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.ProductRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;

import static org.junit.jupiter.api.Assertions.*;

public class ProductRepositoriesTest {
    private EntityManagerFactory factory;
    private ProductRepositories productRep;
    EntityManager manager;

    @BeforeEach
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("testpizzashop");
        manager = factory.createEntityManager();
        productRep = new ProductRepositories(factory);

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

    private Product sampleProduct1() {
        return new Product("Curly Crispy Fries ", ProductCategory.Sides, "Our New Signature Curly Fries", 3.00, 300, "curly.png");
    }

    private Product sampleProduct2() {
        return new Product("Ice-Cream", ProductCategory.Desserts, "Chocolate Chip Ice Cream", 2.00, 300, "icecream.png");
    }

    @Test
    public void test_findProductByIDWhereItIsValid() {
        Product p = productRep.findProductByID(1);
        assertNotNull(p);
        assertEquals(1, p.getId());
    }

    @Test
    public void test_findProductByIDWhereItIsInValid() {
        Product p = productRep.findProductByID(0);
        assertNull(p);

    }

    @Test
    public void addProduct() {
        Product product1 = sampleProduct1();

        assertTrue(productRep.addProduct(product1));

        Product retrievedProduct = productRep.findProductByID(3);

        assertNotNull(retrievedProduct);

        assertEquals(product1.getName(), retrievedProduct.getName());
        assertEquals(product1.getCategory(), retrievedProduct.getCategory());
        assertEquals(product1.getDetails(), retrievedProduct.getDetails());
        assertEquals(product1.getPrice(), retrievedProduct.getPrice());
        assertEquals(product1.getStock(), retrievedProduct.getStock());
        assertEquals(product1.getImage(), retrievedProduct.getImage());

        productRep.deleteProduct(3);
        productRep.resetAutoIncrement("products", 2);
    }

    @Test
    void test_findProductsByKeywordWhereKeywordIsValid() {
        List<Product> products = productRep.findProductsByKeyword("Pepperoni");
        assertNotNull(products);
        assertFalse(products.isEmpty());
        for (Product p : products) {
            assertTrue(p.getName().toLowerCase().contains("pepperoni"));
        }

    }

    @Test
    void test_findProductsByKeywordWhereKeywordIsValid2() {
        productRep.addProduct(sampleProduct2());
        List<Product> products = productRep.findProductsByKeyword("Ice Cream");
        assertNotNull(products);
        assertFalse(products.isEmpty());
        for (Product p : products) {
            assertTrue(p.getName().toLowerCase().contains("ice cream"));
        }

        productRep.deleteProduct(3);
        productRep.resetAutoIncrement("products", 2);

    }


}