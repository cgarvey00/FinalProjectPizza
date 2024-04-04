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
        try {
            ProductRepositories productRep = new ProductRepositories(factory);
            List<Product> searchProducts = productRep.findProductsByKeyword(keyword);
            session.setAttribute("searchProducts", searchProducts);
        } catch (Exception e){
            System.out.println("An Exception occurred while searching product: " + e.getMessage());
            terminus = "error.jsp";
        }

        return terminus;
    }
}

