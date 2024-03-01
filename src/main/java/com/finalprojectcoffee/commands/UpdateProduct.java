package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        String terminus = "";

        HttpSession session = request.getSession(true);
        int productId = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("name");
        ProductCategory category = ProductCategory.valueOf(request.getParameter("category"));
        String details = request.getParameter("details");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String image = request.getParameter("image");

        try {
            ProductRepositories productRep = new ProductRepositories(factory);

            Product product = productRep.findProductByID(productId);

            if(name != null && !name.isEmpty()){
                product.setName(name);
            }

            if(details != null && !details.isEmpty()){
                product.setDetails(details);
            }

            if(image != null && !image.isEmpty()){
                product.setImage(image);
            }

            product.setCategory(category);
            product.setPrice(price);
            product.setStock(stock);

            boolean isUpdated = productRep.updateProduct(product);
            if(isUpdated){
                session.setAttribute("pus-message", "Update successfully");
            } else {
                session.setAttribute("pue-message", "Failed to update product");
            }

        } catch (Exception e) {
            System.err.println("An Exception occurred while updating product: " + e.getMessage());
        }
        return terminus;
    }
}
