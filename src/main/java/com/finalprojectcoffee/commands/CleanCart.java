package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.repositories.CartRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        String terminus = "";

        HttpSession session = request.getSession(true);
        int cartId = Integer.parseInt(request.getParameter("cartId"));

        try {
            CartRepositories cartRep = new CartRepositories(factory);

            Boolean isCleaned = cartRep.clearCart(cartId);
            if (isCleaned) {
                session.setAttribute("ccs-msg", "Clean cart successfully");
            } else {
                session.setAttribute("cce-msg", "Failed to clean cart");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while cleaning cart: " + e.getMessage());
        }
        return terminus;
    }
}
