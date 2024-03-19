package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Carts;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.CartsRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class CleanCart implements Command {
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
        String terminus = "view-cart.jsp";

        HttpSession session = request.getSession(true);
        int cartId = Integer.parseInt(request.getParameter("customerID"));

        try {
            CartsRepositories cartRep = new CartsRepositories(factory);
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            Boolean isCleaned = cartRep.clearCart(cartId);
            if (isCleaned) {
                List<Carts> cartsList = cartRep.getCartsByCustomerId(loggedInUser.getId());
                session.setAttribute("cartList", cartsList);
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
