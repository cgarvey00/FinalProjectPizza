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
        User activeUser = (User) session.getAttribute("LoggedInUser");
//        int userID = Integer.parseInt(request.getParameter("userID"));
        try {
            if (activeUser.getUserType().equals("Customer") || activeUser.getUserType().equals("Employee")
                    || activeUser.getUserType().equals("Admin")) {

                session.setAttribute("loggedInUser", activeUser);
                 terminus = "user-profile.jsp";


            } else {
                session.setAttribute("vup2-msg", "Failed to view User Profile ");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while Trying to Find the User Profile Page: " + e.getMessage());
        }
        return terminus;
    }
}