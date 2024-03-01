package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.CartRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RemoveItems implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public RemoveItems(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "";

        HttpSession session = request.getSession(true);
        Object cartItemIdsObj = session.getAttribute("cartItemIds");
        List<Integer> cartItemIds = new ArrayList<>();

        if(cartItemIdsObj instanceof Integer[]){
            Integer[] cartItemIdsArray = (Integer[]) cartItemIdsObj;
            //Filter null elements
            cartItemIds = Arrays.stream(cartItemIdsArray).filter(Objects::nonNull).collect(Collectors.toList());
        }

        try {
            CartRepositories cartRep = new CartRepositories(factory);

            if(!cartItemIds.isEmpty()){
                cartRep.removeItemsFromCart(cartItemIds);
                session.setAttribute("ris-message", "Remove items successfully");
            } else {
                session.setAttribute("rie-message", "Failed to remove items");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while removing items: " + e.getMessage());
        }
        return terminus;
    }
}
