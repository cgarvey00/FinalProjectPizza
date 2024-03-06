package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Address;
import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.ProductRepositories;
import com.finalprojectcoffee.repositories.UserRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class CustomerPage implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public CustomerPage(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "customer-home.jsp";

        HttpSession session = request.getSession(true);

        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser != null && "Customer".equals(loggedInUser.getUserType())) {

                ProductRepositories prodRepos = new ProductRepositories(factory);
                UserRepositories userRepos = new UserRepositories(factory);

                List<Product> productList = prodRepos.getAllProducts();
                session.setAttribute("productList", productList);

                List<Address> addressList = userRepos.getAddressesByUserId(loggedInUser.getId());
                session.setAttribute("addressList", addressList);

                terminus = "customer-home.jsp";

            } else {
                terminus = "index.jsp";
            }

        } else {
            terminus = "index.jsp";
        }
        return terminus;

    }
}

