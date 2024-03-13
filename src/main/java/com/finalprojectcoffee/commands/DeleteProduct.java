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
//        Object productsObj = session.getAttribute("product_ids");

        int productId = Integer.parseInt(request.getParameter("product_id"));

        //        if(productsObj instanceof Product[]){
//            Product[] productIdsArray = (Product []) productsObj;
//            //Filter null elements
//            products = Arrays.stream(productIdsArray).filter(Objects::nonNull).collect(Collectors.toList());
//        }

        try {
            ProductRepositories productRep = new ProductRepositories(factory);
            Product product = productRep.findProductByID(productId);
            List<Product> products = new ArrayList<>();
            products.add(product);
            boolean isDeleted = productRep.deleteProduct(products);
            if (isDeleted) {
                session.setAttribute("pds-message", "Delete products successfully");
            } else {
                session.setAttribute("pde-message", "Failed to delete products");
            }

            if (session.getAttribute("pds-message") != null) {
                session.setAttribute("delete-product-success", true);

            } else {
                session.setAttribute("delete-product-success", false);
            }

        } catch (Exception e) {
            System.err.println("An Exception occurred while deleting products: " + e.getMessage());
        }
        return terminus;
    }
}
