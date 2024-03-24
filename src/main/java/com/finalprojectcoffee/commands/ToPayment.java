package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ToPayment implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ToPayment(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus;
        HttpSession session = request.getSession(true);
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            Order order = orderRep.findOrderById(orderId);
            if (order != null) {
                double balance = order.getBalance();
                session.setAttribute("balance", balance);
                session.setAttribute("orderId", orderId);
                terminus = "payment.jsp";
            } else {
                session.setAttribute("errorMessage", "Failed to go to payment page. Please try again later.");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while redirect to payment: " + e.getMessage());
            session.setAttribute("errorMessage", "Something went wrong");
            return "error.jsp";
        }

        return terminus;
    }
}
