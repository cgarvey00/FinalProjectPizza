package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class DeliverOrderToCustomer implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public DeliverOrderToCustomer(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "employee-order-history.jsp";
        HttpSession session = request.getSession(true);

        User activeUser = (User) session.getAttribute("loggedInUser");

        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            Boolean orderAccepted = orderRep.finishOrder(orderId);
            if (orderAccepted != false && orderAccepted != null) {
                List<Order> orderListEmployee = orderRep.getAllOrdersByEmployeeId(activeUser.getId());
                if (!orderListEmployee.isEmpty() && orderListEmployee != null) {
                    session.setAttribute("deliveredOrders", orderRep.getAllOrdersByEmployeeId(activeUser.getId()));
                    session.setAttribute("orderDelivered", "Order List is empty");
                    terminus = "employee-order-history.jsp";
                } else {
                    session.setAttribute("deliveredOrders", "Order List is empty");
                    terminus = "employee-order-history.jsp";
                }
            } else {
                session.setAttribute("deliveredOrders", "Order cannot be delivered");
                terminus = "employee-order-list.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An Exception occurred while viewing Orders: " + e.getMessage());
        }

        return terminus;

    }
}