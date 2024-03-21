package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.dto.OrderItemDTO;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Iterator;
import java.util.List;

public class DecrementQuantity implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public DecrementQuantity(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        HttpSession session = request.getSession(true);
        int productId = Integer.parseInt(request.getParameter("productId"));
        @SuppressWarnings("unchecked")
        List<OrderItemDTO> orderItems = (List<OrderItemDTO>) session.getAttribute("orderItems");
        boolean isDecremented = false;

        Iterator<OrderItemDTO> iterator = orderItems.iterator();
        while (iterator.hasNext()) {
            OrderItemDTO orderItem = iterator.next();
            if (orderItem.getProductId() == productId) {

                int currentQuantity = orderItem.getQuantity();
                if(currentQuantity > 0){
                    orderItem.setQuantity(currentQuantity - 1);
                }

                if(orderItem.getQuantity() == 0){
                    iterator.remove();
                }
                isDecremented = true;
                break;
            }
        }

        if (isDecremented) {
            session.setAttribute("orderItems", orderItems);
            return "{\"success\": true, \"decrementMessage\": \"Decrement successfully!\"}";
        } else {
            return "{\"success\": false, \"decrementMessage\": \"Failed to decrement.\"}";
        }
    }
}
