package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.OrdersItem;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ViewOrderDetailsEmployee implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewOrderDetailsEmployee(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "employee-order-list.jsp";
        HttpSession session = request.getSession(true);

        User activeUser = (User) session.getAttribute("loggedInUser");

        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            Order order = orderRep.findOrderById(orderId);

            List<OrdersItem> orderDetailsList = orderRep.getOrderItemsByOrder(order);
            if (orderDetailsList != null && !orderDetailsList.isEmpty()) {
                session.setAttribute("orderDetailList", orderDetailsList);
                terminus="ajaxtable.jsp";

            } else {
                session.setAttribute("orderDetailList", Collections.emptyList());
                terminus="ajaxtable.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An Exception occurred while viewing Orders: " + e.getMessage());
        }

        return terminus;

    }
}