package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.Order;

import java.util.List;

public interface OrdersRepositoriesInterface {
    Order findOrderById(int orderId);
    List<Order> getAllOrders();
    Order findOrderByCustomer(Customer customer);
    Order findOrderByEmployee(Employee employee);
    Boolean makeOrder(int cartId, int customerId, int temporaryAddressId);
    Boolean payOrder(int orderId, int customerId, double payment);
    List<Order> acceptOrders(List<Integer> orderIds);
    List<Order> deliverOrders(List<Integer> orderIds, int employeeId);
    Boolean cancelOrders(List<Integer> orderIds);
}
