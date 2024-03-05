package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewOrderCustomer implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewOrderCustomer(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-orders-customer";

        HttpSession session = request.getSession(true);
        Customer activeCustomer = (Customer) session.getAttribute("LoggedInUser");

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            List<Order> orders = orderRep.getAllOrdersByCustomerId(activeCustomer.getId());
            if(!orders.isEmpty()){
                session.setAttribute("vos-msg", orders);
            } else {
                session.setAttribute("voe-msg", "Failed to view orders");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing orders: " + e.getMessage());
        }
        return terminus;
    }
}
