package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ViewAddressDetailsEmployeeDeliveryList implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewAddressDetailsEmployeeDeliveryList(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "employee-view-orders.jsp";
        HttpSession session = request.getSession(true);

        User activeUser = (User) session.getAttribute("loggedInUser");

        int addressId = Integer.parseInt(request.getParameter("addressId"));
        try {
            UserRepositories addressList = new UserRepositories(factory);
            Address address = addressList.getAddressesByAddressId(addressId);

            if (address != null ) {
                session.setAttribute("addressDetails", address);
                terminus="address-ajax-table.jsp";

            } else {
                session.setAttribute("addressDetails", null);
                terminus="address-ajax-table.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An Exception occurred while viewing Orders: " + e.getMessage());
        }

        return terminus;

    }
}