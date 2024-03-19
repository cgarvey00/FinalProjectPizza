package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.CartsRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddCart implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AddCart(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "customer-home.jsp";
        HttpSession session = request.getSession(true);

        int productID = Integer.parseInt(request.getParameter("product_id"));
        int quantity = Integer.parseInt(request.getParameter("qty"));

        try {
            CartsRepositories cartRep = new CartsRepositories(factory);

            User loggedInUser=(User)session.getAttribute("loggedInUser");

            if (loggedInUser!=null) {
                boolean isAdded = cartRep.addCartItem(loggedInUser.getId(),productID,quantity);
                if (isAdded) {
                    session.setAttribute("aps-message", "Item Added to Cart successfully");
                } else {
                    session.setAttribute("ape-message", "Failed to add to Cart");
                }
            }else{
                session.setAttribute("log-message","User logged out");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while adding products: " + e.getMessage());
        }
        return terminus;
    }
}