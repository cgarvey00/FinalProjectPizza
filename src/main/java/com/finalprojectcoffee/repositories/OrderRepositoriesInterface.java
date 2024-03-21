package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.OrderItem;

import java.util.List;

public interface OrderRepositoriesInterface {
    Order findOrderById(int orderId);
    List<Order> getAllOrders();
    List<Order> getAllOrdersByCustomerId(int CustomerId);
    List<Order> getAllOrdersByEmployeeId(int EmployeeId);
    Order addOrder(int customerId, int addressId);
    Boolean addOrderItem(List<OrderItem> orderItems);
    Boolean payOrder(int orderId);
    Boolean deliverOrders(List<Integer> orderIds, int employeeId);
    Boolean finishOrder(int orderId);
    Boolean cancelOrder(int orderID);
}
