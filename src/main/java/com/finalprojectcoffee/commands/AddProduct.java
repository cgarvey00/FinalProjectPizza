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

public class AddProduct implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AddProduct(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "";
        HttpSession session = request.getSession(true);

        Object productsObj = session.getAttribute("products");
        List<Product> products = new ArrayList<>();

        if(productsObj instanceof Product[]){
            Product[] productsArray = (Product[]) productsObj;
            //Filter null elements
            products = Arrays.stream(productsArray).filter(Objects::nonNull).collect(Collectors.toList());
        }

        try {
            ProductRepositories productRep = new ProductRepositories(factory);

            if(!products.isEmpty()){
                boolean isAdded = productRep.addProducts(products);
                if (isAdded){
                    session.setAttribute("aps-message", "Add products successfully");
                } else {
                    session.setAttribute("ape-message", "Failed to add products");
                }
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while adding products: " + e.getMessage());
        }
        return terminus;
    }
}
