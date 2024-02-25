package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.Order;

import java.util.List;

public interface OrdersRepositoriesInterface {
    Order findOrderById(int orderId);
    List<Order> getAllOrders();
    List<Order> getAllOrdersByUserId(int userId);
    Boolean addOrder(int userId, int cartId, int temporaryAddressId);
    Boolean payOrder(int orderId, int userId, double payment);
    List<Order> acceptOrders(List<Integer> orderIds);
    List<Order> deliverOrders(List<Integer> orderIds, int userId);
    Boolean cancelOrders(List<Integer> orderIds);
}
