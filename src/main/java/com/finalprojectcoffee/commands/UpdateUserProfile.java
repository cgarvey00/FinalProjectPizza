package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import com.finalprojectcoffee.utils.EmailUtil;
import com.finalprojectcoffee.utils.JBCriptUtil;
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
        String terminus = "user-profile.jsp";

        HttpSession session = request.getSession(true);
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();
        String newPhoneNumber = request.getParameter("phoneNumber");
        String newEmail = request.getParameter("email");
        String image = request.getParameter("image");

        try {
            UserRepositories userRep = new UserRepositories(factory);

            if(newPhoneNumber != null && !newPhoneNumber.isEmpty()){
                if(PhoneNumberUtil.validationPhoneNumber(newPhoneNumber)){
                    activeUser.setPhoneNumber(newPhoneNumber);
                } else {
                    session.setAttribute("upe-msg", "Phone number format error");
                }
            }

            if(newEmail != null && !newEmail.isEmpty()){
                if(EmailUtil.validateEmail(newEmail)){
                    activeUser.setEmail(newEmail);
                } else {
                    session.setAttribute("uee-msg", "Email format error");
                }
            }

            if(image != null && !image.isEmpty()){
                activeUser.setImage(image);
            }

            Boolean isUpdated = userRep.updateUser(activeUserId, activeUser.getPassword(), activeUser.getPhoneNumber(), activeUser.getEmail(), activeUser.getImage());
            if(isUpdated){
                session.setAttribute("upus-msg", "Update successfully");
                terminus = "customer-home.jsp";
            } else {
                session.setAttribute("upue-msg", "Failed to update");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred: " + e.getMessage());
        }

        return terminus;
    }
}