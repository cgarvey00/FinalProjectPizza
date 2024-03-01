package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewAllOrders implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewAllOrders(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "";

        HttpSession session = request.getSession(true);

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            List<Order> orderList = orderRep.getAllOrders();
            if(!orderList.isEmpty()){
                session.setAttribute("orderList", orderList);
            } else {
                session.setAttribute("emptyList", "Order list is empty");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing order list: " + e.getMessage());
        }
        return terminus;
    }
}
