package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class UpdateProduct implements Command{
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public UpdateProduct(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus;

        HttpSession session = request.getSession(true);
        int productId = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("name");
        String categoryStr = request.getParameter("category");
        String details = request.getParameter("details");
        String priceStr = request.getParameter("price");
        String stockStr = request.getParameter("stock");
        String image = request.getParameter("image");

        try {
            ProductRepositories productRep = new ProductRepositories(factory);
            Product product = productRep.findProductByID(productId);
            ProductCategory category;
            double price;
            int stock;

            if(name != null && !name.isEmpty()){
                product.setName(name);
            }

            if(details != null && !details.isEmpty()){
                product.setDetails(details);
            }

            if(image != null && !image.isEmpty()){
                product.setImage(image);
            }

            if(categoryStr != null && !categoryStr.isEmpty()){
                category = ProductCategory.valueOf(categoryStr);
                product.setCategory(category);
            }

            if(priceStr != null && !priceStr.isEmpty()){
                price = Double.parseDouble(priceStr);
                product.setPrice(price);
            }

            if(stockStr != null && !stockStr.isEmpty()){
                stock = Integer.parseInt(stockStr);
                product.setStock(stock);
            }

            boolean isUpdated = productRep.updateProduct(product);
            if(isUpdated){
                List<Product> productList = productRep.getAllProducts();
                session.setAttribute("productList", productList);
                terminus = "product-page.jsp";
            } else {
                session.setAttribute("errorMessage", "Failed to update product, please try again later");
                terminus = "error.jsp";
            }

        } catch (Exception e) {
            System.err.println("An Exception occurred while updating product: " + e.getMessage());
            terminus = "error.jsp";
        }

        return terminus;
    }
}
