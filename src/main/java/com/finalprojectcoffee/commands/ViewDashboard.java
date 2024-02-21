package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewDashboard implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    private final EntityManagerFactory factory;
    public ViewDashboard(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {

        String terminus = "admin-dashboard.jsp";
        HttpSession session = request.getSession(true);
        EntityManager entityManager = factory.createEntityManager();

        // This contains a list of attributes the Admin can Oversee such as Products,
        // Users and Reviews
        try {
            ProductRepositories productRepos = new ProductRepositories(factory);
            List<Product> productList = productRepos.getAllProducts();



            session.setAttribute("productList", productList);

        } finally {
            entityManager.close();
        }

        return terminus;

    }


}
