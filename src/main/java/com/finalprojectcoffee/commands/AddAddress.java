package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddAddress implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AddAddress(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "customer-home.jsp";

        HttpSession session = request.getSession(true);
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();
        String street = request.getParameter("street");
        String town = request.getParameter("town");
        String county = request.getParameter("county");
        String eirCode = request.getParameter("eirCode");
        Address address = new Address();

        try {
            UserRepositories userRep = new UserRepositories(factory);

            if (street != null && !street.isEmpty()) {
                address.setStreet(street);
            }

            if(town != null && !town.isEmpty()){
                address.setTown(town);
            }

            if(county != null && !county.isEmpty()){
                address.setCounty(county);
            }

            if(eirCode != null && !eirCode.isEmpty()){
                address.setEirCode(eirCode);
            }

            address.setUser(activeUser);
            Boolean isAdded = userRep.addAddress(activeUserId, address);
            if(isAdded){
                session.setAttribute("aas-msg", "Update successfully");
            } else {
                session.setAttribute("aae-msg", "Failed to update");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred: " + e.getMessage());
        }

        return terminus;
    }
}
