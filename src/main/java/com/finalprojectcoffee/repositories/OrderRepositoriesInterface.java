package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.OrderItem;

import java.util.List;

public interface OrderRepositoriesInterface {
    Order findOrderById(int orderId);
    List<Order> getAllOrders();
    List<Order> getAllOrdersByCustomerId(int customerId);
    List<Order> getAllOrdersByEmployeeId(int employeeId);
    List<OrderItem> getOrderItemByOrderId(int orderId);
    Order addOrder(int customerId, int addressId);
    Boolean addOrderItem(List<OrderItem> orderItems);
    Boolean updateAddressInOrder(int orderId, int addressId);
    Boolean updateEmployeeInOrder(int orderId, int employeeId);
    Boolean payOrder(int orderId);
    void deliverOrder(int orderId);
    Boolean finishOrder(int orderId);
    Boolean cancelOrder(int orderID);
}
