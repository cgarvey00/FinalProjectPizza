package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.*;
import com.finalprojectcoffee.repositories.CartRepositories;
import com.finalprojectcoffee.repositories.OrderRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        String terminus = "search-menu.jsp";
        HttpSession session = request.getSession(true);

        Object productIdsObj = session.getAttribute("product_ids");
        Object quantitiesObj = session.getAttribute("product_quantities");
        List<Integer> productIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        if(productIdsObj instanceof Integer[]){
            Integer[] productIdsArray = (Integer[]) productIdsObj;
            //Filter null elements
            productIds = Arrays.stream(productIdsArray).filter(Objects::nonNull).collect(Collectors.toList());
        }

        if(quantitiesObj instanceof Integer[]){
            Integer[] quantitiesArray = (Integer[]) quantitiesObj;
            //Filter null elements
            quantities = Arrays.stream(quantitiesArray).filter(Objects::nonNull).collect(Collectors.toList());
        }

        //Active user
        User activeCustomer = (User) session.getAttribute("loggedInUser");
        int activeCustomerId = activeCustomer.getId();

        //Address
        int addressId = (int) session.getAttribute("address_id");

        //Payment
        Double payment = (Double) session.getAttribute("payment");

        try {
            CartRepositories cartRep = new CartRepositories(factory);
            OrderRepositories orderRep = new OrderRepositories(factory);
            List<CartItem> cartItems = new ArrayList<>();

            //Add empty cart
            Cart cart = cartRep.addCart();

            if(!productIds.isEmpty()&& !quantities.isEmpty() && productIds.size() == quantities.size()){
                for (int i = 0; i< productIds.size(); i++) {
                    CartItem cartItem = cartRep.addItem(cart.getId(), productIds.get(i), quantities.get(i));

                    if (cartItem != null) {
                        cartItems.add(cartItem);
                    } else {
                        session.setAttribute("item_error", "Failed to add item");
                    }
                }
            } else {
                session.setAttribute("iqe_message", "Failed to select items");
            }

            //Call createCart function
            cart = cartRep.createCart(cartItems);
            if(cart != null){
                Order order = orderRep.addOrder(activeCustomerId, cart.getId(), addressId);

                if(order != null){
                    Boolean isPaid = orderRep.payOrder(order.getId(), payment);
                    if(isPaid){
                        session.setAttribute("aos_message", "Add order successfully");
                        terminus = "index.jsp";
                    }
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
