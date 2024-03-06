package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewUpdateProduct implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewUpdateProduct(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "update-product.jsp";

        HttpSession session = request.getSession(true);
        int prodID = Integer.parseInt(request.getParameter("product_id"));
        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser != null && "Admin".equals(loggedInUser.getUserType())) {

                ProductRepositories prodRepos = new ProductRepositories(factory);
                Product product = prodRepos.findProductByID(prodID);
                session.setAttribute("product", product);

                terminus = "update-product.jsp";

            } else {
                terminus = "index.jsp";
            }

        } else {
            terminus = "index.jsp";
        }
        return terminus;


    }
}
