package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.dto.OrderItemDTO;
import com.finalprojectcoffee.entities.*;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class AddToCart implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AddToCart(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {

        HttpSession session = request.getSession(true);
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        ProductRepositories ProductRep = new ProductRepositories(factory);
        Product product = ProductRep.findProductByID(productId);
        @SuppressWarnings("unchecked")
        List<OrderItemDTO> orderItems = (List<OrderItemDTO>) session.getAttribute("orderItems");

        if(orderItems == null){
            orderItems = new ArrayList<>();
        }

        if(quantity > product.getStock()){
            return "{\"success\": false, \"addCartMessage\": \"Requested quantity exceeds stock available.\"}";
        }

        OrderItemDTO cartItem = new OrderItemDTO();

        cartItem.setProductId(productId);
        cartItem.setName(product.getName());
        cartItem.setQuantity(quantity);
        cartItem.setCost(quantity * product.getPrice());
        cartItem.setPrice(product.getPrice());

        boolean found = false;
        for(OrderItemDTO existingItem : orderItems){
            if(existingItem.getProductId() == productId){
                int newQuantity = existingItem.getQuantity() + quantity;
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                existingItem.setCost(newQuantity * product.getPrice());
                found = true;
                break;
            }
        }

        if (!found) {
            orderItems.add(cartItem);
        }

        session.setAttribute("orderItems", orderItems);
        return "{\"success\": true, \"addCartMessage\": \"Add successfully!\"}";
    }
}
