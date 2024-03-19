import com.finalprojectcoffee.entities.Carts;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.OrdersItem;
import com.finalprojectcoffee.entities.Status;
import com.finalprojectcoffee.repositories.CartsRepositories;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * @author Yutang and cgarvey00
 */
public class OrderIntegrationUnitTests {

    private EntityManagerFactory factory;
        private OrderRepositories orderRep;
        private CartsRepositories cartRep;
        EntityManager manager;

        @BeforeEach
        public void setUp() {
            factory = Persistence.createEntityManagerFactory("testpizzadeliveryshop");
            manager = factory.createEntityManager();
            orderRep = new OrderRepositories(factory);
            cartRep = new CartsRepositories(factory);
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

        @Test
        public void findOrderByIdTest() {
            Order order = orderRep.findOrderById(1);
            assertNotNull(order);
            Order notExistResult = orderRep.findOrderById(5);
            assertNull(notExistResult);
        }

        @Test
        public void getAllOrdersTest() {
            List<Order> orders = orderRep.getAllOrders();
            assertNotNull(orders);
            assertEquals(1, orders.size());
        }

        @Test
        public void getAllOrdersByCustomerIdTest() {
            List<Order> orders = orderRep.getAllOrdersByCustomerId(1);
            assertNotNull(orders);
            assertEquals(1, orders.size());
        }


        @Test
        public void addOrderWhereSuccessful() {
            int customerID = 3;
            int productID = 1;
            int productID2 = 2;
            int quantity = 2;
            int quantity2 = 1;
            int addressID = 1;

            assertTrue(cartRep.addCartItem(customerID, productID, quantity));
            assertTrue(cartRep.addCartItem(customerID, productID2, quantity2));

            List<Carts> cartList = cartRep.getCartsByCustomerId(customerID);

            Order order = orderRep.addOrder(customerID, addressID, cartList);
            assertNotNull(order);
            assertNotNull(order.getId());
            assertNotNull(order.getCreateTime());

            assertEquals(Status.Pending, order.getPaymentStatus());
            assertEquals(Status.Pending, order.getStatus());
            assertEquals(0.0, order.getBalance());
            assertNotNull(order.getCustomer());
            assertNotNull(order.getAddress());

            List<OrdersItem> orderItems = orderRep.getOrderItemsByOrder(order);

            assertNotNull(orderItems);
            assertFalse(orderItems.isEmpty());

            for (OrdersItem orderItem : orderItems) {
                assertEquals(order, orderItem.getOrder());
            }

            assertTrue(cartRep.clearCart(customerID));
            cartRep.resetAutoIncrement("carts");
            assertTrue(orderRep.deleteOrderDetails(order.getId()));
            orderRep.resetAutoIncrement("orders");
            orderRep.resetAutoIncrement2("order_items", 2);
        }

        @Test
        public void addOrderWhereItIsPaid() {
            int customerID = 3;
            int productID = 1;
            int productID2 = 2;
            int quantity = 2;
            int quantity2 = 1;
            int addressID = 1;

            assertTrue(cartRep.addCartItem(customerID, productID, quantity));
            assertTrue(cartRep.addCartItem(customerID, productID2, quantity2));

            List<Carts> cartList = cartRep.getCartsByCustomerId(customerID);

            Order order = orderRep.addOrder(customerID, addressID, cartList);
            assertNotNull(order);
            assertNotNull(order.getId());
            assertNotNull(order.getCreateTime());

            assertEquals(Status.Pending, order.getPaymentStatus());
            assertEquals(Status.Pending, order.getStatus());
            assertEquals(0.0, order.getBalance());
            assertNotNull(order.getCustomer());
            assertNotNull(order.getAddress());

            List<OrdersItem> orderItems = orderRep.getOrderItemsByOrder(order);

            assertNotNull(orderItems);
            assertFalse(orderItems.isEmpty());

            //Getting the total from order items
            double total = 0;
            for (OrdersItem orderItem : orderItems) {
                assertEquals(order, orderItem.getOrder());
                total += orderItem.getCost();
            }

            assertTrue(orderRep.payOrder(order.getId(), total));

            Order paidOrder = orderRep.findOrderById(order.getId());

            assertEquals(total, paidOrder.getBalance());
            assertEquals(Status.Paid, paidOrder.getPaymentStatus());

            System.out.println("Order has been Paid + " + paidOrder.toString());

            // Resetting the database changes
            assertTrue(cartRep.clearCart(customerID));
            cartRep.resetAutoIncrement("carts");
            assertTrue(orderRep.deleteOrderDetails(order.getId()));
            orderRep.resetAutoIncrement("orders");
            orderRep.resetAutoIncrement2("order_items", 2);
        }

        @Test
        public void addOrderWhereItIsAccepted() {
            int customerID = 3;
            int productID = 1;
            int productID2 = 2;
            int quantity = 5;
            int quantity2 = 1;
            int addressID = 1;

            assertTrue(cartRep.addCartItem(customerID, productID, quantity));
            assertTrue(cartRep.addCartItem(customerID, productID2, quantity2));

            List<Carts> cartList = cartRep.getCartsByCustomerId(customerID);

            Order order = orderRep.addOrder(customerID, addressID, cartList);
            assertNotNull(order);
            assertNotNull(order.getId());
            assertNotNull(order.getCreateTime());

            assertEquals(Status.Pending, order.getPaymentStatus());
            assertEquals(Status.Pending, order.getStatus());
            assertEquals(0.0, order.getBalance());
            assertNotNull(order.getCustomer());
            assertNotNull(order.getAddress());

            List<OrdersItem> orderItems = orderRep.getOrderItemsByOrder(order);

            assertNotNull(orderItems);
            assertFalse(orderItems.isEmpty());

            //Getting the total from order items
            double total = 0;
            for (OrdersItem orderItem : orderItems) {
                assertEquals(order, orderItem.getOrder());
                total += orderItem.getCost();
            }

            assertTrue(orderRep.payOrder(order.getId(), total));

            Order paidOrder = orderRep.findOrderById(order.getId());

            assertEquals(total, paidOrder.getBalance());
            assertEquals(Status.Paid, paidOrder.getPaymentStatus());

            System.out.println("Order has been Paid + " + paidOrder.toString());

            //Accepting the Order
            assertTrue(orderRep.acceptOrders(paidOrder.getId()));

            Order accepted = orderRep.findOrderById(paidOrder.getId());

            assertEquals(Status.Accepted, accepted.getStatus());

            // Resetting the database changes
            assertTrue(cartRep.clearCart(customerID));
            cartRep.resetAutoIncrement("carts");
            assertTrue(orderRep.deleteOrderDetails(order.getId()));
            orderRep.resetAutoIncrement("orders");
            orderRep.resetAutoIncrement2("order_items", 2);
        }

        @Test
        public void addOrderWhereItIsAcceptedAndAcceptedByAEmployee() {
            int customerID = 3;
            int employeeID = 2;
            int productID = 1;
            int productID2 = 2;
            int quantity = 5;
            int quantity2 = 1;
            int addressID = 1;

            assertTrue(cartRep.addCartItem(customerID, productID, quantity));
            assertTrue(cartRep.addCartItem(customerID, productID2, quantity2));

            List<Carts> cartList = cartRep.getCartsByCustomerId(customerID);

            Order order = orderRep.addOrder(customerID, addressID, cartList);
            assertNotNull(order);
            assertNotNull(order.getId());
            assertNotNull(order.getCreateTime());

            assertEquals(Status.Pending, order.getPaymentStatus());
            assertEquals(Status.Pending, order.getStatus());
            assertEquals(0.0, order.getBalance());
            assertNotNull(order.getCustomer());
            assertNotNull(order.getAddress());

            List<OrdersItem> orderItems = orderRep.getOrderItemsByOrder(order);

            assertNotNull(orderItems);
            assertFalse(orderItems.isEmpty());

            //Getting the total from order items
            double total = 0;
            for (OrdersItem orderItem : orderItems) {
                assertEquals(order, orderItem.getOrder());
                total += orderItem.getCost();
            }

            assertTrue(orderRep.payOrder(order.getId(), total));

            Order paidOrder = orderRep.findOrderById(order.getId());

            assertEquals(total, paidOrder.getBalance());
            assertEquals(Status.Paid, paidOrder.getPaymentStatus());

            System.out.println("Order has been Paid + " + paidOrder.toString());

            //Accepting the Order
            assertTrue(orderRep.acceptOrders(paidOrder.getId()));

            Order accepted = orderRep.findOrderById(paidOrder.getId());
            //Order has been Accepted
            assertEquals(Status.Accepted, accepted.getStatus());

            //Employee Delivering Orders
            assertTrue(orderRep.deliverOrders(accepted.getId(),employeeID));

            List<Order> delivered=orderRep.getAllOrdersByEmployeeId(employeeID);

            Order deliveryOrder=delivered.get(0);

            assertEquals(Status.Delivering,deliveryOrder.getStatus());

            System.out.println("Order Confirmed to be transported :"+deliveryOrder.toString());

            // Resetting the database changes
            assertTrue(cartRep.clearCart(customerID));
            cartRep.resetAutoIncrement("carts");
            assertTrue(orderRep.deleteOrderDetails(order.getId()));
            orderRep.resetAutoIncrement("orders");
            orderRep.resetAutoIncrement2("order_items", 2);
        }

        @Test
        public void addOrderWhereItIsAcceptedAndAcceptedByAEmployeeDelivered() {
            int customerID = 3;
            int employeeID = 2;
            int productID = 1;
            int productID2 = 2;
            int quantity = 5;
            int quantity2 = 1;
            int addressID = 1;

            assertTrue(cartRep.addCartItem(customerID, productID, quantity));
            assertTrue(cartRep.addCartItem(customerID, productID2, quantity2));

            List<Carts> cartList = cartRep.getCartsByCustomerId(customerID);

            Order order = orderRep.addOrder(customerID, addressID, cartList);
            assertNotNull(order);
            assertNotNull(order.getId());
            assertNotNull(order.getCreateTime());

            assertEquals(Status.Pending, order.getPaymentStatus());
            assertEquals(Status.Pending, order.getStatus());
            assertEquals(0.0, order.getBalance());
            assertNotNull(order.getCustomer());
            assertNotNull(order.getAddress());

            List<OrdersItem> orderItems = orderRep.getOrderItemsByOrder(order);

            assertNotNull(orderItems);
            assertFalse(orderItems.isEmpty());

            //Getting the total from order items
            double total = 0;
            for (OrdersItem orderItem : orderItems) {
                assertEquals(order, orderItem.getOrder());
                total += orderItem.getCost();
            }

            assertTrue(orderRep.payOrder(order.getId(), total));

            Order paidOrder = orderRep.findOrderById(order.getId());

            assertEquals(total, paidOrder.getBalance());
            assertEquals(Status.Paid, paidOrder.getPaymentStatus());

            System.out.println("Order has been Paid + " + paidOrder.toString());

            //Accepting the Order
            assertTrue(orderRep.acceptOrders(paidOrder.getId()));

            Order accepted = orderRep.findOrderById(paidOrder.getId());
            //Order has been Accepted
            assertEquals(Status.Accepted, accepted.getStatus());

            //Employee Delivering Orders
            assertTrue(orderRep.deliverOrders(accepted.getId(),employeeID));

            List<Order> delivered=orderRep.getAllOrdersByEmployeeId(employeeID);

            Order deliveryOrder=delivered.get(0);

            assertEquals(Status.Delivering,deliveryOrder.getStatus());

            System.out.println("Order Confirmed to be transported :"+deliveryOrder.toString());

            //Finishing the Order
            assertTrue(orderRep.finishOrder(paidOrder.getId()));

            Order delivered2 = orderRep.findOrderById(paidOrder.getId());
            //Order has been Accepted
            assertEquals(Status.Delivered, delivered2.getStatus());

            // Resetting the database changes
            assertTrue(cartRep.clearCart(customerID));
            cartRep.resetAutoIncrement("carts");
            assertTrue(orderRep.deleteOrderDetails(order.getId()));
            orderRep.resetAutoIncrement("orders");
            orderRep.resetAutoIncrement2("order_items", 2);
        }

        @Test
        public void addOrderWhereItIsAcceptedAndCancelled() {
            int customerID = 3;
            int employeeID = 2;
            int productID = 1;
            int productID2 = 2;
            int quantity = 5;
            int quantity2 = 1;
            int addressID = 1;

            assertTrue(cartRep.addCartItem(customerID, productID, quantity));
            assertTrue(cartRep.addCartItem(customerID, productID2, quantity2));

            List<Carts> cartList = cartRep.getCartsByCustomerId(customerID);

            Order order = orderRep.addOrder(customerID, addressID, cartList);
            assertNotNull(order);
            assertNotNull(order.getId());
            assertNotNull(order.getCreateTime());

            assertEquals(Status.Pending, order.getPaymentStatus());
            assertEquals(Status.Pending, order.getStatus());
            assertEquals(0.0, order.getBalance());
            assertNotNull(order.getCustomer());
            assertNotNull(order.getAddress());

            List<OrdersItem> orderItems = orderRep.getOrderItemsByOrder(order);

            assertNotNull(orderItems);
            assertFalse(orderItems.isEmpty());

            //Getting the total from order items
            double total = 0;
            for (OrdersItem orderItem : orderItems) {
                assertEquals(order, orderItem.getOrder());
                total += orderItem.getCost();
            }

            assertTrue(orderRep.payOrder(order.getId(), total));

            Order paidOrder = orderRep.findOrderById(order.getId());

            assertEquals(total, paidOrder.getBalance());
            assertEquals(Status.Paid, paidOrder.getPaymentStatus());

            System.out.println("Order has been Paid + " + paidOrder.toString());

            //Accepting the Order
            assertTrue(orderRep.acceptOrders(paidOrder.getId()));

            Order accepted = orderRep.findOrderById(paidOrder.getId());
            //Order has been Accepted
            assertEquals(Status.Accepted, accepted.getStatus());

            //Employee Delivering Orders
            assertTrue(orderRep.cancelOrder(accepted.getId()));

            List<Order> cancelled=orderRep.getAllOrdersByCustomerId(customerID);

            Order cancelledOrder=cancelled.get(0);

            assertEquals(Status.Cancelled,cancelledOrder.getStatus());
            assertEquals(Status.Refunded,cancelledOrder.getPaymentStatus());

            System.out.println("Order Confirmed to be Cancelled Refund Incoming :"+cancelledOrder.toString());

            // Resetting the database changes
            assertTrue(cartRep.clearCart(customerID));
            cartRep.resetAutoIncrement("carts");
            assertTrue(orderRep.deleteOrderDetails(order.getId()));
            orderRep.resetAutoIncrement("orders");
            orderRep.resetAutoIncrement2("order_items", 2);
        }

        @Test
        public void addOrderWhereItIsAcceptedAndAcceptedByAEmployeeFailure() {
            int customerID = 3;
            int employeeID = -2;
            int productID = 1;
            int productID2 = 2;
            int quantity = 5;
            int quantity2 = 1;
            int addressID = 1;

            assertTrue(cartRep.addCartItem(customerID, productID, quantity));
            assertTrue(cartRep.addCartItem(customerID, productID2, quantity2));

            List<Carts> cartList = cartRep.getCartsByCustomerId(customerID);

            Order order = orderRep.addOrder(customerID, addressID, cartList);
            assertNotNull(order);
            assertNotNull(order.getId());
            assertNotNull(order.getCreateTime());

            assertEquals(Status.Pending, order.getPaymentStatus());
            assertEquals(Status.Pending, order.getStatus());
            assertEquals(0.0, order.getBalance());
            assertNotNull(order.getCustomer());
            assertNotNull(order.getAddress());

            List<OrdersItem> orderItems = orderRep.getOrderItemsByOrder(order);

            assertNotNull(orderItems);
            assertFalse(orderItems.isEmpty());

            //Getting the total from order items
            double total = 0;
            for (OrdersItem orderItem : orderItems) {
                assertEquals(order, orderItem.getOrder());
                total += orderItem.getCost();
            }

            assertTrue(orderRep.payOrder(order.getId(), total));

            Order paidOrder = orderRep.findOrderById(order.getId());

            assertEquals(total, paidOrder.getBalance());
            assertEquals(Status.Paid, paidOrder.getPaymentStatus());

            System.out.println("Order has been Paid + " + paidOrder.toString());

            //Accepting the Order
            assertTrue(orderRep.acceptOrders(paidOrder.getId()));

            Order accepted = orderRep.findOrderById(paidOrder.getId());
            //Order has been Accepted
            assertEquals(Status.Accepted, accepted.getStatus());

            //Employee Delivering Failure
            assertFalse(orderRep.deliverOrders(accepted.getId(),employeeID),"Order cannot be delivered invalid employee id");

            // Resetting the database changes
            assertTrue(cartRep.clearCart(customerID));
            cartRep.resetAutoIncrement("carts");
            assertTrue(orderRep.deleteOrderDetails(order.getId()));
            orderRep.resetAutoIncrement("orders");
            orderRep.resetAutoIncrement2("order_items", 2);
        }

        @Test
        public void addOrderWhereNotSuccessful() {
            int customerID = 3;
            int productID = 1;
            int productID2 = 2;
            int quantity = 2;
            int quantity2 = 1;
            int addressID = 1;

            assertTrue(cartRep.addCartItem(customerID, productID, quantity));
            assertTrue(cartRep.addCartItem(customerID, productID2, quantity2));

            assertNull(orderRep.addOrder(customerID, addressID, Collections.emptyList()));

            assertTrue(cartRep.clearCart(customerID));
            cartRep.resetAutoIncrement("carts");

        }

        @Test
        public void findOrderByOrderObject() {
            Order order = orderRep.findOrderById(1);
            List<OrdersItem> orderItems = orderRep.getOrderItemsByOrder(order);
            assertNotNull(orderItems);
            assertFalse(orderItems.isEmpty());
            for (OrdersItem orderItem : orderItems) {
                System.out.println(orderItem.toString());
            }

        }

        @Test
        public void deleteOrderWhereSuccessful() {
            int customerID = 3;
            int productID = 1;
            int productID2 = 2;
            int quantity = 2;
            int quantity2 = 1;
            int addressID = 1;

            assertTrue(cartRep.addCartItem(customerID, productID, quantity));
            assertTrue(cartRep.addCartItem(customerID, productID2, quantity2));

            List<Carts> cartList = cartRep.getCartsByCustomerId(customerID);

            Order order = orderRep.addOrder(customerID, addressID, cartList);
            assertNotNull(order);
            assertTrue(orderRep.deleteOrderDetails(order.getId()));
            assertTrue(cartRep.clearCart(customerID));
            cartRep.resetAutoIncrement("carts");
            orderRep.resetAutoIncrement2("orders", 1);
            orderRep.resetAutoIncrement2("order_items", 2);

        }

//    @Test
//    public void deleteOrderWhereSuccessfulByOrder() {
//
//        assertTrue(orderRep.deleteOrderDetails(2));
//        orderRep.resetAutoIncrement2("orders", 1);
//        orderRep.resetAutoIncrement2("order_items", 2);
//
//    }
    }


