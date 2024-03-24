package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.*;

public class CancelOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public CancelOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus ;
        HttpSession session = request.getSession(true);
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        User activeCustomer = (User) session.getAttribute("loggedInUser");
        int activeCustomerId = activeCustomer.getId();

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            Boolean isCancelled = orderRep.cancelOrder(orderId);
            if (isCancelled) {
                List<Order> orderListCustomer = orderRep.getAllOrdersByCustomerId(activeCustomerId);
                session.setAttribute("orderListCustomer", orderListCustomer);
                terminus = "view-order-customer.jsp";
            } else {
                session.setAttribute("errorMessage", "Failed to cancel order. Please try again later.");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while cancelling order: " + e.getMessage());
            session.setAttribute("errorMessage", "Something went wrong.");
            terminus = "error.jsp";
        }

        return terminus;
    }
}
