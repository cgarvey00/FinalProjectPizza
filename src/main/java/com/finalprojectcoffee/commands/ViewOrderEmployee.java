package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class ViewOrderEmployee implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewOrderEmployee(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }


    @Override
    public String execute() {
        String terminus = "employee-order-list.jsp";
        HttpSession session = request.getSession(true);
        Employee activeEmployee = (Employee) session.getAttribute("LoggedInUser");

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            List<Order> freeOrderList = orderRep.getAllOrders();
            List<Order> filterList = nonEmployeeOrders(freeOrderList);
            if (!filterList.isEmpty()) {
                session.setAttribute("nonEmployeeOrders", freeOrderList);
            } else {
                session.setAttribute("voe-message", "All Orders are taken, wait for Orders to be added");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing orders: " + e.getMessage());
        }
        return terminus;
    }

    private static List<Order> nonEmployeeOrders(List<Order> filterOrders) {
        List<Order> orderList = new ArrayList<>();
        for (Order orders : filterOrders) {
            if (orders.getEmployee() == null) {
                orderList.add(orders);
            }
        }
        return orderList;
    }
}
