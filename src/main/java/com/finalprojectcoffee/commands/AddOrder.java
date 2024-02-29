package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.*;
import com.finalprojectcoffee.repositories.CartRepositories;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class AddOrder implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AddOrder(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "order-customer";
        HttpSession session = request.getSession(true);

        //The required values of adding cart item
        Integer[] productIds = (Integer[]) session.getAttribute("product_ids");
        Integer[] productQuantities = (Integer[]) session.getAttribute("product_quantities");

        //Active user and temporary address
        User activeCustomer = (User) session.getAttribute("loggedInUser");
        int activeCustomerId = activeCustomer.getId();
        TemporaryAddress temporaryAddress = (TemporaryAddress) session.getAttribute("tem_address");

        try {
            CartRepositories cartRep = new CartRepositories(factory);
            OrderRepositories orderRep = new OrderRepositories(factory);
            List<CartItem> cartItems = new ArrayList<>();

            if(productIds != null && productQuantities != null && productIds.length == productQuantities.length){
                for (int i = 0; i< productIds.length; i++) {
                    CartItem cartItem = cartRep.addItem(productIds[i], productQuantities[i]);

                    if (cartItem != null) {
                        cartItems.add(cartItem);
                    } else {
                        session.setAttribute("item_error", "Failed to add item");
                    }
                }
            } else {
                session.setAttribute("item_quantity_error", "Failed to select items");
            }

            Cart cart = cartRep.addCart(cartItems);
            if(cart != null){
                Boolean order = orderRep.addOrder(activeCustomerId, cart.getId(), temporaryAddress.getId());

                if(order){
                    session.setAttribute("aos_message", "Add order successfully");
                    terminus = "payment-page";
                } else {
                    session.setAttribute("aoe_message", "Failed to add order");
                }
            } else {
                session.setAttribute("cart_error", "Failed to add cart");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while adding order: " + e.getMessage());
        }

        return terminus;
    }
}
