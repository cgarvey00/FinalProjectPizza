package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.EmailUtil;
import com.finalprojectcoffee.utils.PhoneNumberUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UpdateUserProfile implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public UpdateUserProfile(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus;

        HttpSession session = request.getSession(true);
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();
        String newPhoneNumber = request.getParameter("phoneNumber");
        String newEmail = request.getParameter("email");

        try {
            UserRepositories userRep = new UserRepositories(factory);

            if(newPhoneNumber == null || newPhoneNumber.isEmpty()){
                newPhoneNumber = activeUser.getPhoneNumber();
            } else {
                Boolean isValid = PhoneNumberUtil.validationPhoneNumber(newPhoneNumber);
                if (!isValid) {
                    session.setAttribute("errorMessage", "Phone number format is invalid, please try again later.");
                    return  "error.jsp";
                }
            }

            if(newEmail == null || newEmail.isEmpty()){
                newEmail = activeUser.getEmail();
            } else {
                Boolean isValid = EmailUtil.validateEmail(newEmail);
                if (!isValid) {
                    session.setAttribute("errorMessage", "Email format is invalid, please try again later.");
                    return  "error.jsp";
                }
            }

            Boolean isUpdated = userRep.updateUser(activeUserId, newPhoneNumber, newEmail);
            if(isUpdated){
                activeUser = userRep.findUserById(activeUserId);
                session.setAttribute("loggedInUser", activeUser);
                terminus = "customer-profile.jsp";
            } else {
                session.setAttribute("errorMessage", "Failed to update profile, please try again later.");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while updating user profile: " + e.getMessage());
            terminus = "error.jsp";
        }

        return terminus;
    }
}