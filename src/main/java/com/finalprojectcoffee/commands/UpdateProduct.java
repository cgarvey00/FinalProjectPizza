package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

public class UpdateProduct implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public UpdateProduct(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }
    @Override
    public String execute() throws ServletException, IOException {
        String terminus = "product-page.jsp";

        HttpSession session = request.getSession(true);

        int productId = Integer.parseInt(request.getParameter("productId"));
        ProductCategory category = ProductCategory.valueOf(request.getParameter("category"));
        String details = request.getParameter("details");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String name = request.getParameter("name");

        String uniqueName = null;
        Part partFile = request.getPart("image");
        if (partFile != null && partFile.getSize() > 0) {
            String fileName = Paths.get(partFile.getSubmittedFileName()).getFileName().toString();
            String extension = fileName.lastIndexOf(".") > 0 ? fileName.substring(fileName.lastIndexOf(".")) : "";
            uniqueName = UUID.randomUUID().toString() + extension;

            File uploadsDir = new File(request.getServletContext().getRealPath("/uploaded-images"));
            if (!uploadsDir.exists() && !uploadsDir.mkdirs()) {
                throw new IOException("Unable to create a new directory");
            }
            File file = new File(uploadsDir, uniqueName);

            try (InputStream fileContent = partFile.getInputStream()) {
                Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        try {
            ProductRepositories productRep = new ProductRepositories(factory);

            Product product = productRep.findProductByID(productId);

            if (name != null && !name.isEmpty()) {
                product.setName(name);
            }

            if (details != null && !details.isEmpty()) {
                product.setDetails(details);
            }

            if (uniqueName != null && !uniqueName.isEmpty()) {
                product.setImage(uniqueName);
            }

            product.setCategory(category);
            product.setPrice(price);
            product.setStock(stock);

            boolean isUpdated = productRep.updateProduct(product);
            if (isUpdated) {
                session.setAttribute("pus-msg", "Update successfully");

                List<Product> productList = productRep.getAllProducts();
                session.setAttribute("productList", productList);
            } else {
                session.setAttribute("pue-msg", "Failed to update product");
            }

        } catch (Exception e) {
            System.err.println("An Exception occurred while updating product: " + e.getMessage());
        }
        return terminus;
    }
}
