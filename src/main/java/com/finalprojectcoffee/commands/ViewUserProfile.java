package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Employee;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewUserProfile implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewUserProfile(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "user-profile.jsp";
        HttpSession session = request.getSession(true);
        //Active user
        User activeUser = (User) session.getAttribute("loggedInUser");
        int activeUserId = activeUser.getId();

        if (activeUser instanceof Customer) {
            int loyaltyPoints;
            Customer activeCustomer = (Customer) activeUser;
            Integer points = activeCustomer.getLoyaltyPoints();
            loyaltyPoints = (points != null) ? points : 0;

            try {
                UserRepositories userRep = new UserRepositories(factory);

                List<Address> addressList = userRep.getAddressesByUserId(activeUserId);
                session.setAttribute("loggedInUser", activeUser);
                session.setAttribute("loyaltyPoints", loyaltyPoints);
                //session.setAttribute("userType", "customer");
                if (addressList != null && !addressList.isEmpty()) {
                    session.setAttribute("addressList", addressList);
                }
            } catch (Exception e) {
                System.out.println("An Exception occurred while viewing user profile: " + e.getMessage());
                terminus = "error.jsp";
            }
        }

        if (activeUser instanceof Employee) {
            Employee activeEmployee = (Employee) activeUser;

            try {
                session.setAttribute("loggedInUser", activeEmployee);
                //session.setAttribute("userType", "employee");
            } catch (Exception e) {
                System.out.println("An Exception occurred while viewing user profile: " + e.getMessage());
                terminus = "error.jsp";
            }
        }

        return terminus;
    }
}