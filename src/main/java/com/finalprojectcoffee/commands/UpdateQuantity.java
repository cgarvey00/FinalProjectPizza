package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.dto.OrderItemDTO;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class UpdateQuantity implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public UpdateQuantity(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {

        HttpSession session = request.getSession(true);
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        @SuppressWarnings("unchecked")
        List<OrderItemDTO> orderItems = (List<OrderItemDTO>) session.getAttribute("orderItems");
        boolean isUpdated = false;

        for(OrderItemDTO orderItem : orderItems){
            if (orderItem.getProductId() == productId) {
                orderItem.setQuantity(quantity);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            session.setAttribute("orderItems", orderItems);
            return "{\"success\": true, \"updateQuantityMessage\": \"Update successfully!\"}";
        } else {
            return "{\"success\": false, \"updateQuantityMessage\": \"Failed to update\"}";
        }
    }
}
