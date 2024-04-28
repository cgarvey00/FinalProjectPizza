package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class EmployeeOrderList implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public EmployeeOrderList(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "employee-view-orders.jsp";
        HttpSession session = request.getSession(true);

        User activeUser = (User) session.getAttribute("loggedInUser");

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            List<Order> orderList = orderRep.getAllOrdersByEmployeeId(activeUser.getId());
            if (!orderList.isEmpty() && orderList != null) {
                session.setAttribute("orderListEmployee", orderList);
                terminus = "employee-view-orders.jsp";
            } else {
                session.setAttribute("orderListEmployee", null);
                terminus = "employee-view-orders.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An Exception occurred while viewing Orders: " + e.getMessage());
        }

        return terminus;

    }
}