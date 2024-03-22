package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class DeleteAddress implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public DeleteAddress(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
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
        int addressId = Integer.parseInt(request.getParameter("addressId"));

        UserRepositories userRep = new UserRepositories(factory);
        Boolean isDeleted = userRep.deleteAddress(activeUserId, addressId);

        if(isDeleted){
            List<Address> addressList = userRep.getAddressesByUserId(activeUserId);
            session.setAttribute("addressList", addressList);
        }
        return terminus;
    }
}
