package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.JBCriptUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChangePassword implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ChangePassword(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "change-password.jsp";

        HttpSession session = request.getSession(true);
        User activeUser = (User) session.getAttribute("loggedInUser");
        String newPassword = request.getParameter("newPassword");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        try {
            UserRepositories userRep = new UserRepositories(factory);
            if(newPassword != null && !newPassword.isEmpty() && passwordConfirmation != null && !passwordConfirmation.isEmpty()){

                if(newPassword.equals(passwordConfirmation)){
                    if(JBCriptUtil.validatePassword(newPassword)){
                        activeUser.setPassword(JBCriptUtil.getHashedPw(newPassword));
                        terminus = "customer-home.jsp";
                    } else {
                        session.setAttribute("upw-msg", "Password format error");
                    }
                } else {
                    session.setAttribute("upwc-msg", "Password inconsistency");
                }
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while changing password: " + e.getMessage());
        }
        return terminus;
    }
}
