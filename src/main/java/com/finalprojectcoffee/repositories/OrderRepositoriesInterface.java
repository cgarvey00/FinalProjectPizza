package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Carts;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.OrdersItem;

import java.util.List;

public interface OrderRepositoriesInterface {
    Order findOrderById(int orderId);

    List<OrdersItem> getOrderItemsByOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getAllOrdersByCustomerId(int CustomerId);

    List<Order> getAllOrdersByEmployeeId(int EmployeeId);

    Order addOrder(int customerId, int addressId, List<Carts> cartItems);

    Boolean payOrder(int orderId, double payment);

    Boolean deleteOrderDetails(int orderId);

    Boolean acceptOrders(int orderId);

    Boolean deliverOrders(int orderId, int employeeId);

    Boolean finishOrder(int orderId);

    Boolean cancelOrder(int orderId);
}

