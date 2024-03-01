package com.finalprojectcoffee.commands;


import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class FinishOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public FinishOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
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

            Boolean isFinished = orderRep.finishOrder(orderId);
            if (isFinished) {
                session.setAttribute("ofs-message", "Order finished");
            } else {
                session.setAttribute("ofe_message", "Failed to finish order");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while finishing order: " + e.getMessage());
        }
        return terminus;
    }
}
