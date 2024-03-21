package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
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
        String terminus = "view-order.jsp";

        HttpSession session = request.getSession(true);
        User activeUser = (User) session.getAttribute("LoggedInUser");

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            List<Order> orderList = orderRep.getAllOrdersByCustomerId(activeUser.getId());
            if(orderList != null && !orderList.isEmpty()){
                session.setAttribute("orderList", orderList);
            } else {
                session.setAttribute("voe-msg", "Failed to view orders");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing orders: " + e.getMessage());
        }
        return terminus;
    }
}
