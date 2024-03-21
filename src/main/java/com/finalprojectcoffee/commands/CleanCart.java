package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.dto.OrderItemDTO;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class CleanCart implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public CleanCart(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        HttpSession session = request.getSession(true);
        @SuppressWarnings("unchecked")
        List<OrderItemDTO> orderItems = (List<OrderItemDTO>) session.getAttribute("orderItems");

        orderItems.clear();
        return "{\"success\": true, \"cleanCartMessage\": \"Remove successfully!\"}";
    }
}
