package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ToUpdateProduct implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ToUpdateProduct(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus ;
        HttpSession session = request.getSession(true);
        int productId = Integer.parseInt(request.getParameter("productId"));

        try {
            ProductRepositories productRep = new ProductRepositories(factory);
            ProductCategory[] categories = ProductCategory.values();

            Product product = productRep.findProductByID(productId);
            if(product != null){
                session.setAttribute("product", product);
                session.setAttribute("categories", categories);
                terminus = "update-product.jsp";
            } else {
                session.setAttribute("errorMessage", "Product doesn't exist");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.out.println("An Exception occurred while searching product: " + e.getMessage());
            terminus = "error.jsp";
        }

        return terminus;
    }
}
