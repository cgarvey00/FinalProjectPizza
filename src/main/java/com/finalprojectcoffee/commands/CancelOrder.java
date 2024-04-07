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
        String terminus = "order-page.jsp";
        HttpSession session = request.getSession(true);
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        User activeCustomer = (User) session.getAttribute("loggedInUser");
        int activeCustomerId = activeCustomer.getId();
        String userType = request.getParameter("userType");

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            Boolean isCancelled = orderRep.cancelOrder(orderId);
            if (isCancelled) {
                if(userType.equals("customer")){
                    List<Order> orderListCustomer = orderRep.getAllOrdersByCustomerId(activeCustomerId);
                    session.setAttribute("orderListCustomer", orderListCustomer);
                    session.removeAttribute("order");
                    session.removeAttribute("orderId");
                    terminus = "view-order-customer.jsp";
                }
                if(userType.equals("admin")){
                    List<Order> orderList = orderRep.getAllOrders();
                    session.setAttribute("orderList", orderList);
                    terminus = "view-order-admin.jsp";
                }
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
