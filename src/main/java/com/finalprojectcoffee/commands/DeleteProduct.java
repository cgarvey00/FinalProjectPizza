package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DeleteProduct implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public DeleteProduct(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "product-page.jsp";

        HttpSession session = request.getSession(true);

        int productId = Integer.parseInt(request.getParameter("productId"));

        try {
            ProductRepositories productRep = new ProductRepositories(factory);

            boolean isDeleted = productRep.deleteProduct(productId);
            if (isDeleted) {
                List<Product> productList = productRep.getAllProducts();
                session.setAttribute("productList", productList);
                session.setAttribute("dpsMessage", "Delete successfully");
            } else {
                session.setAttribute("errorMessage", "Failed to delete products, please try again later");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while deleting products: " + e.getMessage());
            terminus = "error.jsp";
        }
        return terminus;
    }
}
