package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DeleteUser implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public DeleteUser(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "login.jsp";

        HttpSession session = request.getSession(true);
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();

        try {
            UserRepositories userRep = new UserRepositories(factory);

            Boolean isDeleted = userRep.deleteUser(activeUserId);
            if (isDeleted) {
                session.removeAttribute("loggedInUser");
            } else {
                session.setAttribute("errorMessage", "Failed to delete account, please try again later");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while deleting user: " + e.getMessage());
            session.setAttribute("errorMessage", "Failed to delete account, please try again later");
            terminus = "error.jsp";
        }
        return terminus;
    }
}
