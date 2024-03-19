package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Order;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.OrderRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewUserProfile implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewUserProfile(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "user-profile.jsp";

        HttpSession session = request.getSession(true);
        //Active user
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();

        try {
            UserRepositories userRep = new UserRepositories(factory);
            User u = userRep.findUserById(activeUserId);

            if (u != null) {
                session.setAttribute("loggedInUser", u);
                terminus = "user-profile.jsp";

            } else {
                session.setAttribute("emptyUser", "User Not Found");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing addresses: " + e.getMessage());
        }
        return terminus;
    }
}