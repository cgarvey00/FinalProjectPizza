package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ToUpdateAddress implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ToUpdateAddress(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "update-address.jsp";
        HttpSession session = request.getSession(true);
        int addressId = Integer.parseInt(request.getParameter("addressId"));

        try {
            UserRepositories userRep = new UserRepositories(factory);

            Address address = userRep.getAddressById(addressId);
            if(address != null){
                session.setAttribute("addressEcho", address);
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while sending address echo: " + e.getMessage());
        }
        return terminus;
    }
}
