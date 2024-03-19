package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

public class ViewOrderCustomer implements Command {
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
        String terminus = "customer-orders.jsp";

        HttpSession session = request.getSession(true);

        //Active user
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();

        try {
            UserRepositories userRep = new UserRepositories(factory);
            OrderRepositories orderRep = new OrderRepositories(factory);
            List<Order> orderList = orderRep.getAllOrdersByCustomerId(activeUserId);

            if (orderList != null && !orderList.isEmpty()) {
                session.setAttribute("orderList", orderList);
                terminus = "customer-orders.jsp";
            } else {
                session.setAttribute("emptyList", Collections.emptyList());
                terminus = "customer-home.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing Orders: " + e.getMessage());
        }
        return terminus;
    }
}
