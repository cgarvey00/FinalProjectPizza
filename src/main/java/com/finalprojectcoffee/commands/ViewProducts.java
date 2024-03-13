package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewProducts implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewProducts(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "product-page.jsp";

        HttpSession session = request.getSession(true);

        try {
            ProductRepositories productRep = new ProductRepositories(factory);

            List<Product> productList = productRep.getAllProducts();
            if (productList != null && !productList.isEmpty()) {
                session.setAttribute("productList", productList);
            } else {
                session.setAttribute("vpe-message", "Product list is empty");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing product list: " + e.getMessage());
        }
        return terminus;
    }
}
