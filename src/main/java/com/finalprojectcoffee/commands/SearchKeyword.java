package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class SearchKeyword implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public SearchKeyword(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "search.jsp";
        HttpSession session = request.getSession(true);
        String keyword = request.getParameter("search_box");
        if (keyword != null && !keyword.isEmpty()) {
            EntityManager entityManager = factory.createEntityManager();
            try {
                ProductRepositories productRepos = new ProductRepositories(factory);
                List<Product> productList = productRepos.findProductsByKeyword(keyword);
                session.setAttribute("searchKeywordProducts", productList);
            } finally {
                entityManager.close();
            }
        }
        return terminus;
    }
}

