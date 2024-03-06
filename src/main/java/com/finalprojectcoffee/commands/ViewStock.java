package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewStock implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewStock(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-stock.jsp";

        HttpSession session = request.getSession(true);

        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser != null && "Admin".equals(loggedInUser.getUserType())) {

                ProductRepositories prodRepos = new ProductRepositories(factory);
                List<Product> productList = prodRepos.getAllProducts();
                session.setAttribute("productList", productList);

                terminus = "view-stock.jsp";

            } else {
                terminus = "index.jsp";
            }

        } else {
            terminus = "index.jsp";
        }
        return terminus;


    }
}
