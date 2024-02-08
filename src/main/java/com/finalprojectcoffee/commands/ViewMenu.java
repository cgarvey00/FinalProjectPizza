package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewMenu implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewMenu(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "search-menu.jsp";
        HttpSession session = request.getSession(true);
        EntityManager entityManager = factory.createEntityManager();

        try {
            ProductRepositories productRepos = new ProductRepositories(factory);
            List<Product> productList = productRepos.getAllProducts();

            request.setAttribute("productList", productList);

        } finally {
            entityManager.close();
        }

        return terminus;
    }
}


