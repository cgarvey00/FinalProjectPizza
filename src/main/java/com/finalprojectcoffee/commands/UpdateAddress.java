package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class UpdateAddress implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public UpdateAddress(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
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
        Address addressEcho = (Address) session.getAttribute("addressEcho");

        if(street != null && !street.isEmpty()){
            addressEcho.setStreet(street);
        }
        if(town != null && !town.isEmpty()){
            addressEcho.setTown(town);
        }
        if(county != null && !county.isEmpty()){
            addressEcho.setCounty(county);
        }
        if(eirCode != null && !eirCode.isEmpty()){
            addressEcho.setEirCode(eirCode);
        }

        try {
            UserRepositories userRep = new UserRepositories(factory);

            Boolean isUpdated = userRep.updateAddress(addressEcho);
            if (isUpdated) {
                List<Address> addressList = userRep.getAddressesByUserId(activeUserId);
                session.setAttribute("addressList", addressList);
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while updating address: " + e.getMessage());
        }

        return terminus;
    }
}
