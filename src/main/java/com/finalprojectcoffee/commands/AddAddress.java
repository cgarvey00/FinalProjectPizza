package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

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
        String terminus = "view-address.jsp";

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

            //Set default address
            List<Address> addressList = userRep.getAddressesByUserId(activeUserId);
            if(addressList == null || addressList.isEmpty()){
                address.setIsDefault(1);
            }

            address.setUser(activeUser);
            Boolean isAdded = userRep.addAddress(activeUserId, address);
            if(isAdded){
                session.setAttribute("addressed", isAdded);
                addressList = userRep.getAddressesByUserId(activeUserId);
                session.setAttribute("addressList", addressList);
            } else {
                session.setAttribute("errorMessage", "Whoops! Something went wrong. Failed to add address, Please try again later.");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred: " + e.getMessage());
        }

        return terminus;
    }
}
