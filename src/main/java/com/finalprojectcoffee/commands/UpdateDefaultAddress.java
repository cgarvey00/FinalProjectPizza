package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class UpdateDefaultAddress implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public UpdateDefaultAddress(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "update-address.jsp";
        HttpSession session = request.getSession(true);
        Address addressEcho = (Address) session.getAttribute("addressEcho");
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();

        try {
            UserRepositories userRep = new UserRepositories(factory);

            Address lastDefaultAddress = userRep.getDefaultAddress(activeUserId);
            Boolean isUndefault = userRep.setDefaultAddress(lastDefaultAddress);
            if(isUndefault){
                Boolean isUpdated = userRep.setDefaultAddress(addressEcho);
                if (isUpdated) {
                    List<Address> addressList = userRep.getAddressesByUserId(activeUserId);
                    if(addressList != null && !addressList.isEmpty()){
                        session.setAttribute("addressList", addressList);
                    }
                    addressEcho = userRep.getAddressById(addressEcho.getId());
                    session.setAttribute("addressEcho", addressEcho);
                } else {
                    session.setAttribute("errorMessage", "Failed to update default address. Please try again later.\"");
                    terminus = "error.jsp";
                }
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while updating default address: " + e.getMessage());
            session.setAttribute("errorMessage", "Something went wrong");
            terminus = "error.jsp";
        }
        return terminus;
    }
}
