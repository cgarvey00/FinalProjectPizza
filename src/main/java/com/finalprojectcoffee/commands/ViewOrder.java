package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.OrderItem;
import com.finalprojectcoffee.repositories.OrderRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
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
        String terminus = "order-page.jsp";

        HttpSession session = request.getSession(true);
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            OrderRepositories orderRep = new OrderRepositories(factory);
            UserRepositories userRep = new UserRepositories(factory);

            List<OrderItem> orderItemsInOrder = orderRep.getOrderItemByOrderId(orderId);
            Order order = orderRep.findOrderById(orderId);
            Address addressInorder = userRep.getAddressById(order.getAddress().getId());
            if (orderItemsInOrder != null && !orderItemsInOrder.isEmpty()) {
                session.setAttribute("order", order);
                session.setAttribute("orderItemsInOrder", orderItemsInOrder);
                session.setAttribute("addressInorder", addressInorder);
            } else {
                session.setAttribute("errorMessage", "Order doesn't exist. Please try again later.");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing orders: " + e.getMessage());
            session.setAttribute("errorMessage", "Something went wrong");
            terminus = "error.jsp";
        }
        return terminus;
    }
}
