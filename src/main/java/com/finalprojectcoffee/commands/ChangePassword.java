package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.JBCryptUtil;
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
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        try {
            UserRepositories userRep = new UserRepositories(factory);
            if(!JBCryptUtil.checkPw(activeUser.getPassword(), oldPassword)){
                session.setAttribute("opiMessage", "Old password inconsistent");
                return "change-password.jsp";
            }

            if(newPassword != null && !newPassword.isEmpty() && passwordConfirmation != null && !passwordConfirmation.isEmpty()){

                if(newPassword.equals(passwordConfirmation)){
                    if(JBCryptUtil.validatePassword(newPassword)){
                        activeUser.setPassword(JBCryptUtil.getHashedPw(newPassword));
                        terminus = "customer-profile.jsp";
                    } else {
                        session.setAttribute("pwMessage", "Password format error");
                    }
                } else {
                    session.setAttribute("pwcMessage", "Password inconsistency");
                }
            } else {
                session.setAttribute("errorMessage", "Failed to change password. Please try again later. <a href='customer-profile.jsp'>Profile page<a/>");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while changing password: " + e.getMessage());
            terminus = "error.jsp";
        }
        return terminus;
    }
}
