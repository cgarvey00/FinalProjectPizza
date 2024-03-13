package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;

public class ViewAddress implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewAddress(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-address.jsp";

        HttpSession session = request.getSession(true);
        //Active user
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();

        try {
            UserRepositories userRep = new UserRepositories(factory);
            List<Address> addressList = userRep.getAddressesByUserId(activeUserId);

            if(addressList != null && !addressList.isEmpty()){
                session.setAttribute("addressList", addressList);
            } else {
                session.setAttribute("emptyList", Collections.emptyList());
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing addresses: " + e.getMessage());
        }
        return terminus;
    }
}
