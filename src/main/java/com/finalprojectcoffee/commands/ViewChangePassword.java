package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

public class ViewChangePassword implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewChangePassword(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "change-password.jsp";

        HttpSession session = request.getSession(true);

        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser != null && "Customer".equals(loggedInUser.getUserType())) {
                terminus = "change-password.jsp";

            } else {
                terminus = "index.jsp";
            }

        } else {
            terminus = "index.jsp";
        }
        return terminus;

    }
}