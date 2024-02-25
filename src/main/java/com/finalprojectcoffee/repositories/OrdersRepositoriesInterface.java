package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.Order;

import java.util.List;

public interface OrdersRepositoriesInterface {
    Order findOrderById(int orderId);
    List<Order> getAllOrders();
    List<Order> getAllOrdersByCustomerId(int CustomerId);
    List<Order> getAllOrdersByEmployeeId(int EmployeeId);
    Boolean addOrder(int customerId, int cartId, int temporaryAddressId);
    Boolean payOrder(int orderId, double payment);
    Boolean acceptOrders(List<Integer> orderIds);
    Boolean deliverOrders(List<Integer> orderIds, int employeeId);
    Boolean finishOrder(int orderId);
    Boolean cancelOrders(List<Integer> orderIds);
}
