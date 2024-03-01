package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewAllUsers implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewAllUsers(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "";

        HttpSession session = request.getSession(true);

        try {
            UserRepositories userRep = new UserRepositories(factory);

            List<User> userList = userRep.getAllUsers();
            if(!userList.isEmpty()){
                session.setAttribute("users", userList);
            } else {
                session.setAttribute("vue-message", "User list is empty");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing user list: " + e.getMessage());
        }
        return terminus;
    }
}
