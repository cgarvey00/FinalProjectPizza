import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.OrderItem;
import com.finalprojectcoffee.entities.Status;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoriesTest {
    private EntityManagerFactory factory;
    private OrderRepositories orderRep;

    @BeforeEach
    public void setUp(){
        factory = Persistence.createEntityManagerFactory("test_pizza_shop");
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
        Order result = orderRep.findOrderById(1);
        assertNotNull(result);
        Order notExistResult = orderRep.findOrderById(5);
        assertNull(notExistResult);
    }

    @Test
    public void getAllOrdersTest(){
        List<Order> result = orderRep.getAllOrders();
        assertNotNull(result);
        assertEquals(2,result.size());
    }

    @Test
    public void getALLOrdersTodayTest(){
        List<Order> result = orderRep.getAllOrdersToday();
        assertEquals(0, result.size());
    }

    @Test
    public void getAllOrdersByCustomerIdTest(){
        List<Order> result = orderRep.getAllOrdersByCustomerId(3);
        assertNotNull(result);
        assertEquals(1,result.size());
    }

    @Test
    public void getAllOrdersByEmployeeIdTest(){
        List<Order> orders = orderRep.getAllOrdersByEmployeeId(2);
        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    public void getOrderItemByOrderIdTest(){
        List<OrderItem> result = orderRep.getOrderItemByOrderId(1);
        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    public void getCurrentOrdersForEmployeeTest(){
        List<Order> result = orderRep.getCurrentOrdersForEmployee(2);
        assertEquals(0, result.size());
    }

    @Test
    public void addOrderTest(){
        Order result = orderRep.addOrder(5,3);
        assertNotNull(result);
    }

    @Test
    public void payOrderTest(){
        Boolean result = orderRep.payOrder(1);
        assertTrue(result);
        Order order = orderRep.findOrderById(1);
        assertEquals(Status.Paid, order.getPaymentStatus());
    }

    @Test
    public void deliverOrderTest(){
        orderRep.deliverOrder(1);
        Order order = orderRep.findOrderById(1);
        assertEquals(Status.Delivering, order.getStatus());
    }

    @Test
    public void updateAddressInOrderTest(){
        Boolean result = orderRep.updateAddressInOrder(1, 2);
        assertTrue(result);
        Order order = orderRep.findOrderById(1);
        assertEquals(2, order.getAddress().getId());
    }

    @Test
    public void updateEmployeeInOrderTest(){
        Boolean result = orderRep.updateEmployeeInOrder(1, 4);
        assertTrue(result);
        Order order = orderRep.findOrderById(1);
        assertEquals(4, order.getEmployee().getId());
    }

    @Test
    public void getAllOrdersByEmployeeTest(){
        List<Order> orders = orderRep.getAllOrdersByEmployeeId(2);
        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    public void cancelOrdersTest(){
        Boolean result = orderRep.cancelOrder(2);
        assertTrue(result);
        Order order = orderRep.findOrderById(2);
        assertEquals(Status.Cancelled, order.getStatus());
    }

    @Test
    public void finishOrderTest(){
        Boolean result = orderRep.finishOrder(1);
        assertTrue(result);
        Order order = orderRep.findOrderById(1);
        assertEquals(Status.Finished, order.getStatus());
    }

    @Test
    public void filterOrderByDateTest(){
        LocalDate startDate = LocalDate.of(2024, 4, 1);
        LocalDate endDate = LocalDate.now();
        List<Order> result = orderRep.filterOrderByDate(startDate, endDate, 3);
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}