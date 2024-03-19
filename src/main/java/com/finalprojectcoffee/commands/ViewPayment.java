package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.*;
import com.finalprojectcoffee.repositories.CartsRepositories;
import com.finalprojectcoffee.repositories.ProductRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewPayment implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewPayment(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "payment.jsp";

        HttpSession session = request.getSession(true);

        try {
            User activeUser = (User) session.getAttribute("loggedInUser");

            UserRepositories userRep = new UserRepositories(factory);
            CartsRepositories cartRep = new CartsRepositories(factory);
            User u = userRep.findUserById(activeUser.getId());
            List<Carts> cartsList = cartRep.getCartsByCustomerId(u.getId());
            List<Address> addressList = userRep.getAddressesByUserId(u.getId());
            if (u != null) {
                session.setAttribute("loggedInUser", u);
                session.setAttribute("cartsList", cartsList);
                session.setAttribute("addressList", addressList);
                terminus = "payment.jsp";

            } else {
                session.setAttribute("emptyCart", "Cart Not Found");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing payment: " + e.getMessage());
        }
        return terminus;
    }
}
