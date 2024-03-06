package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ViewProductByCategory implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewProductByCategory(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "product-category.jsp";

        HttpSession session = request.getSession(true);
        ProductCategory category = ProductCategory.valueOf(request.getParameter("category"));

        try {
            ProductRepositories productRep = new ProductRepositories(factory);

            List<Product> productList = productRep.findProductsByCategory(category);
            if(!productList.isEmpty()){
                session.setAttribute("productList", productList);
            } else {
                session.setAttribute("emptyList", "Product list is empty");
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing products: " + e.getMessage());
        }
        return terminus;
    }
}
