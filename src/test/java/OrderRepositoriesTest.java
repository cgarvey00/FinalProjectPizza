import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.Status;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoriesTest {
    private EntityManagerFactory factory;
    private OrderRepositories orderRep;

    @BeforeEach
    public void setUp(){
        factory = Persistence.createEntityManagerFactory("testpizzashop");
        orderRep = new OrderRepositories(factory);
    }

    @AfterEach
    public void destroy(){
        if(factory != null && factory.isOpen()){
            factory.close();
        }
    }

    @Test
    public void findOrderByIdTest(){
        Order order = orderRep.findOrderById(1);
        assertNotNull(order);
        Order notExistResult = orderRep.findOrderById(5);
        assertNull(notExistResult);
    }

    @Test
    public void getAllOrdersTest(){
        List<Order> orders = orderRep.getAllOrders();
        assertNotNull(orders);
        assertEquals(2,orders.size());
    }

    @Test
    public void getAllOrdersByCustomerIdTest(){
        List<Order> orders = orderRep.getAllOrdersByCustomerId(1);
        assertNotNull(orders);
        assertEquals(1,orders.size());
    }

    @Test
    public void addOrderTest(){
        Boolean expectedResult = orderRep.addOrder(3,3,1);
        assertTrue(expectedResult);
        Order order = orderRep.findOrderById(3);
        assertNotNull(order);
    }

    @Test
    public void payOrderTest(){
        Boolean expectedResult = orderRep.payOrder(3,23.96);
        assertTrue(expectedResult);
        Order order = orderRep.findOrderById(3);
        assertEquals(0.0,order.getBalance());
        assertEquals(Status.Paid, order.getPaymentStatus());
    }

    @Test
    public void acceptOrdersTest(){
        Integer orderId = 3;
        List<Integer> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        Boolean expectedResult = orderRep.acceptOrders(orderIds);
        Order order = orderRep.findOrderById(3);
        assertTrue(expectedResult);
        assertEquals(Status.Accepted, order.getStatus());
    }

    @Test
    public void deliverOrdersTest(){
        Integer orderId = 3;
        List<Integer> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        Boolean expectedResult = orderRep.deliverOrders(orderIds, 2);
        Order order = orderRep.findOrderById(3);
        assertTrue(expectedResult);
        assertEquals(Status.Delivering, order.getStatus());
    }

    @Test
    public void getAllOrdersByEmployeeTest(){
        List<Order> orders = orderRep.getAllOrdersByEmployeeId(2);
        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    public void finishOrderTest(){
        Boolean expectedResult = orderRep.finishOrder(3);
        assertTrue(expectedResult);
        Order order = orderRep.findOrderById(3);
        assertEquals(Status.Finished, order.getStatus());
    }

    @Test
    public void cancelOrdersTest(){
        Integer orderId = 1;
        List<Integer> orderIds = new ArrayList<>();
        orderIds.add(orderId);
        Boolean expectedResult = orderRep.cancelOrders(orderIds);
        assertTrue(expectedResult);
        Order order = orderRep.findOrderById(1);
        assertEquals(Status.Cancelled, order.getStatus());
        assertEquals(Status.Refunded, order.getPaymentStatus());
    }
}
