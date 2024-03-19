package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Carts;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.CartsRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewCart implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewCart(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-cart.jsp";

        HttpSession session = request.getSession(true);
        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser != null && "Customer".equals(loggedInUser.getUserType())) {

                ProductRepositories prodRepos = new ProductRepositories(factory);
                CartsRepositories cartRepos = new CartsRepositories(factory);
                List<Carts> cartList = cartRepos.getCartsByCustomerId(loggedInUser.getId());
                session.setAttribute("cartList", cartList);

                terminus = "view-cart.jsp";

            } else {
                terminus = "index.jsp";
            }

        } else {
            terminus = "index.jsp";
        }
        return terminus;


    }
}