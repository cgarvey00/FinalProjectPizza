package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.*;

public class ViewMenu implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public ViewMenu(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "view-menu.jsp";
        HttpSession session = request.getSession(true);

        try {
            ProductRepositories productRep = new ProductRepositories(factory);
            List<Product> productList = productRep.getAllProducts();
            Map<ProductCategory, List<Product>> productListByCategory = new LinkedHashMap<>();
            for (Product product : productList) {
                productListByCategory
                        .computeIfAbsent(product.getCategory(), k -> new ArrayList<>())
                        .add(product);
            }
            session.removeAttribute("order");
            if (!productListByCategory.isEmpty()) {
                session.setAttribute("productListByCategory", productListByCategory);
            } else {
                session.setAttribute("errorMessage", "Product list is empty. Please try again later.\"");
                terminus = "error.jsp";
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while viewing menu: " + e.getMessage());
            session.setAttribute("errorMessage", "Something went wrong");
            terminus = "error.jsp";
        }

        return terminus;
    }
}