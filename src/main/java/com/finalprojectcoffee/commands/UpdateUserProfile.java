package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
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
        Address activeUserAddress = new Address();
        activeUserAddress.setUser(activeUser);
        String newPassword = request.getParameter("newPassword");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
        String newPhoneNumber = request.getParameter("phoneNumber");
        String newEmail = request.getParameter("email");
        String image = request.getParameter("image");
        String street = request.getParameter("street");
        String town = request.getParameter("town");
        String county = request.getParameter("county");
        String eirCode = request.getParameter("eirCode");

        try {
            UserRepositories userRep = new UserRepositories(factory);

            if(newPassword != null && !newPassword.isEmpty() && passwordConfirmation != null && !passwordConfirmation.isEmpty()){

                if(newPassword.equals(passwordConfirmation)){
                    if(JBCriptUtil.validatePassword(newPassword)){
                        activeUser.setPassword(JBCriptUtil.getHashedPw(newPassword));
                    } else {
                        session.setAttribute("update-pwvMsg", "Password format error");
                    }
                } else {
                    session.setAttribute("update-pwcMsg", "Password inconsistency");
                }
            }

            if(newPhoneNumber != null && !newPhoneNumber.isEmpty()){
                if(PhoneNumberUtil.validationPhoneNumber(newPhoneNumber)){
                    activeUser.setPhoneNumber(newPhoneNumber);
                } else {
                    session.setAttribute("update-pnMsg", "Phone number format error");
                }
            }

            if(newEmail != null && !newEmail.isEmpty()){
                if(EmailUtil.validateEmail(newEmail)){
                    activeUser.setEmail(newEmail);
                } else {
                    session.setAttribute("update-eMsg", "Email format error");
                }
            }

            if(image != null && !image.isEmpty()){
                activeUser.setImage(image);
            }

            Boolean isUpdated = userRep.updateUser(activeUserId, activeUser.getPassword(), activeUser.getPhoneNumber(), activeUser.getEmail(), activeUser.getImage());
            if(isUpdated){
                session.setAttribute("user-profile-update-successful-msg", "Update successfully");
            } else {
                session.setAttribute("user-profile-update-error-msg", "Failed to update");
            }

            if (street != null && !street.isEmpty()) {
                activeUserAddress.setStreet(street);
            }

            if(town != null && !town.isEmpty()){
                activeUserAddress.setTown(town);
            }

            if(county != null && !county.isEmpty()){
                activeUserAddress.setCounty(county);
            }

            if(eirCode != null && !eirCode.isEmpty()){
                activeUserAddress.setEirCode(eirCode);
            }

            Boolean isUpdatedAddress = userRep.updateAddress(activeUserId, activeUserAddress.getStreet(), activeUserAddress.getTown(), activeUserAddress.getCounty(), activeUserAddress.getEirCode());
            if(isUpdatedAddress){
                session.setAttribute("user-address-update-successful-msg", "Update successfully");
            } else {
                User refreshedUser = userRep.findUserById(activeUserId);
                session.setAttribute("loggedInUser", refreshedUser);
                session.setAttribute("user-address-update-error-msg", "Failed to update");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred: " + e.getMessage());
        }
        return terminus;
    }
}