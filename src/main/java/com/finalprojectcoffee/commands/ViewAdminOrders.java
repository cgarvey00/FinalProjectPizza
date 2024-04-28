package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewAdminOrders implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewAdminOrders(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "admin-dashboard.jsp";
        HttpSession session = request.getSession(true);

        User activeUser = (User) session.getAttribute("loggedInUser");

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            List<Order> orderList = orderRep.getAllOrders();
            if (!orderList.isEmpty() && orderList != null) {
                session.setAttribute("orderList", orderList);
                terminus = "admin-orders.jsp";
            } else {
                session.setAttribute("orderList", null);
                terminus = "admin-dashboard.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An Exception occurred while viewing Orders: " + e.getMessage());
        }

        return terminus;

    }
}