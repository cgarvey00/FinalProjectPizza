package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PayOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public PayOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "payment.jsp";

        HttpSession session = request.getSession(true);
        Order order = (Order) session.getAttribute("order");
        double payment = Double.parseDouble(request.getParameter("payment"));

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            if(payment >= order.getBalance()){
                Boolean isPaid = orderRep.payOrder(order.getId());
                if(isPaid){
                    session.setAttribute("pos-msg", "Pay order successfully");
                    terminus = "orders.jsp";
                } else {
                    session.setAttribute("poe-msg", "Failed to pay order");
                }
            } else {
                session.setAttribute("pme-msg", "The amount entered is incorrect, please enter again");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while paying order: " + e.getMessage());
        }

        return terminus;
    }
}
