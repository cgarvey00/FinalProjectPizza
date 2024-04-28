package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class EmployeeViewFinishedOrders implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public EmployeeViewFinishedOrders(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        HttpSession session = request.getSession(true);
        String terminus = "employee-order-history.jsp";

        User activeUser = (User) session.getAttribute("loggedInUser");

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            if (!activeUser.equals(null) || activeUser.getUserType().equals("Employee")) {
                List<Order> orderListEmployee = orderRep.getAllOrdersByEmployeeId(activeUser.getId());
                if (!orderListEmployee.isEmpty() && orderListEmployee != null) {
                    session.setAttribute("deliveredOrders", orderListEmployee);
                    terminus = "employee-order-history.jsp";
                } else {
                    session.setAttribute("deliveredOrders", "Order List is empty");
                    terminus = "employee-order-history.jsp";
                }
            } else {
                terminus = "index.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An Exception occurred while viewing Orders: " + e.getMessage());
        }

        return terminus;

    }
}