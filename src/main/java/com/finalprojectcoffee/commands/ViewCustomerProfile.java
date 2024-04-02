package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewCustomerProfile implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewCustomerProfile(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "customer-profile.jsp";
        HttpSession session = request.getSession(true);
        //Active user
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();
        int loyaltyPoints = 0;
        if(activeUser instanceof Customer){
            Customer activeCustomer = (Customer) activeUser;
            Integer points = activeCustomer.getLoyaltyPoints();
            loyaltyPoints = (points != null) ? points : 0;
        }

        try {
            UserRepositories userRep = new UserRepositories(factory);

            List<Address> addressList = userRep.getAddressesByUserId(activeUserId);
            if(addressList != null && !addressList.isEmpty()){
                session.setAttribute("loggedInUser", activeUser);
                session.setAttribute("loyaltyPoints", loyaltyPoints);
                session.setAttribute("addressList", addressList);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return terminus;
    }
}
