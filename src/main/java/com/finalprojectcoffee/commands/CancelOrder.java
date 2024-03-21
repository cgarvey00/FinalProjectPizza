package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.*;
import java.util.stream.Collectors;

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

        HttpSession session = request.getSession(true);
        int orderId = (int) session.getAttribute("orderId");
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) session.getAttribute("orders");
        boolean isCanceled = false;

        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if(order.getId() == orderId){
                iterator.remove();
                isCanceled = true;
                break;
            }
        }

        if (isCanceled) {
            session.setAttribute("orders", orders);
            return "{\"success\": true, \"cancelOrderMessage\": \"Cancel successfully!\"}";
        } else {
            return "{\"success\": false, \"cancelOrderMessage\": \"Failed to cancel.\"}";
        }
    }
}
