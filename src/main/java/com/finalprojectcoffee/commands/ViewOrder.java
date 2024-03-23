package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.OrderItem;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "order-page.jsp";

        HttpSession session = request.getSession(true);
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            List<OrderItem> orderItemsInOrder = orderRep.getOrderItemByOrderId(orderId);
            if (orderItemsInOrder != null && !orderItemsInOrder.isEmpty()) {
                session.setAttribute("orderId", orderId);
                session.setAttribute("orderItemsInOrder", orderItemsInOrder);
            } else {
                session.setAttribute("errorMessage", "Whoops! Something went wrong.");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing orders: " + e.getMessage());
        }
        return terminus;
    }
}
