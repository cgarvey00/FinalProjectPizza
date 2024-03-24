package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.dto.OrderItemDTO;
import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class IncrementQuantity implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public IncrementQuantity(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
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
        boolean isIncremented = false;

        for(OrderItemDTO orderItem : orderItems){
            if(orderItem.getProductId() == productId){
                ProductRepositories productRep = new ProductRepositories(factory);
                Product product = productRep.findProductByID(productId);
                int currentQuantity = orderItem.getQuantity();
                orderItem.setQuantity(currentQuantity + 1);
                orderItem.setCost(product.getPrice() * orderItem.getQuantity());
                isIncremented = true;
                break;
            }
        }

        if (isIncremented) {
            session.setAttribute("orderItems", orderItems);
            return "{\"success\": true, \"incrementMessage\": \"Increment successfully!\"}";
        } else {
            return "{\"success\": false, \"incrementMessage\": \"Failed to increment.\"}";
        }
    }
}
