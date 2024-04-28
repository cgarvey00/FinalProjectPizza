package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class AcceptOrderEmployee implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AcceptOrderEmployee(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "employee-view-list.jsp";
        HttpSession session = request.getSession(true);

        User activeUser = (User) session.getAttribute("loggedInUser");

        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            Boolean orderAccepted = orderRep.deliverOrders(orderId, activeUser.getId());
            if (orderAccepted != false && orderAccepted != null) {
                session.setAttribute("nonEmployeeOrders",orderRep.getAllOrders());
                List<Order> orderListEmployee = orderRep.getAllOrdersByEmployeeId(activeUser.getId());
                if (!orderListEmployee.isEmpty() && orderListEmployee != null) {
                    session.setAttribute("orderListEmployee", orderRep.getAllOrdersByEmployeeId(activeUser.getId()));
                    terminus = "employee-view-orders.jsp";
                } else {
                    session.setAttribute("employeeEmptyList", "Order List is empty");
                    terminus = "employee-order-list.jsp";
                }
            } else {
                session.setAttribute("orderTaken", "Order has been taken already, try another!");
                terminus = "employee-order-list.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An Exception occurred while viewing Orders: " + e.getMessage());
        }

        return terminus;

    }
}