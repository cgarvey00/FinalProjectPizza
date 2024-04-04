package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewOrderListAdmin implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewOrderListAdmin(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-order-admin.jsp";
        HttpSession session = request.getSession(true);

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);

            List<Order> orderList = orderRep.getAllOrders();
            if(!orderList.isEmpty()){
                session.setAttribute("orderList", orderList);
            } else {
                session.setAttribute("errorMessage", "Order list is empty");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.out.println("An Exception occurred while getting order list: " + e.getMessage());
            terminus = "error.jsp";
        }

        return terminus;
    }
}
