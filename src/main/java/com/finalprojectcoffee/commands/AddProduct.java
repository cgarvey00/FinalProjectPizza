package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;


public class AddProduct implements Command {
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
        String terminus;
        HttpSession session = request.getSession(true);

        ProductCategory category = ProductCategory.valueOf(request.getParameter("category"));
        String details = request.getParameter("details");
        String priceStr = request.getParameter("price");
        double price = 0.0;
        if(priceStr != null && !priceStr.isEmpty()){
            price = Double.parseDouble(priceStr);
        }
        String stockStr = request.getParameter("stock");
        int stock = 0;
        if(stockStr != null && !stockStr.isEmpty()){
            stock = Integer.parseInt(stockStr);
        }
        String name = request.getParameter("name");
        String image = request.getParameter("image");

        try {
            ProductRepositories productRep = new ProductRepositories(factory);

            Product product = new Product(name, category, details, price, stock, image);
            boolean isAdded = productRep.addProduct(product);
            if (isAdded) {
                List<Product> productList = productRep.getAllProducts();
                session.setAttribute("productList", productList);
                terminus = "product-page.jsp";
            } else {
                session.setAttribute("errorMessage", "Failed to add product, please try again later");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while adding products: " + e.getMessage());
            terminus = "error.jsp";
        }
        return terminus;
    }
}
