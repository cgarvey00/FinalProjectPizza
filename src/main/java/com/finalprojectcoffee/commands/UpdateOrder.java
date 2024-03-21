package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.dto.OrderItemDTO;
import com.finalprojectcoffee.entities.OrderItem;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class UpdateOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public UpdateOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-order.jsp";

        HttpSession session = request.getSession(true);
        @SuppressWarnings("unchecked")
        List<OrderItem> currentOrderItems = (List<OrderItem>) session.getAttribute("orderItems");
        List<OrderItemDTO> orderItems = new ArrayList<>();

        if(currentOrderItems != null && !currentOrderItems.isEmpty()){

            OrderItemDTO orderItem = new OrderItemDTO();
            for(OrderItem currentOrderItem : currentOrderItems){
                orderItem.setName(currentOrderItem.getProduct().getName());
                orderItem.setQuantity(currentOrderItem.getQuantity());
                orderItem.setPrice(currentOrderItem.getProduct().getPrice());
                orderItem.setCost(currentOrderItem.getCost());
                orderItem.setProductId(currentOrderItem.getProduct().getId());
                orderItems.add(orderItem);
            }
        }

        if(!orderItems.isEmpty()){
            session.setAttribute("orderItems", orderItems);
            terminus = "view-cart.jsp";
        }
        return terminus;
    }
}
