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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public String execute() throws ServletException, IOException {
        String terminus = "product-page.jsp";
        HttpSession session = request.getSession(true);

        Part partFile = request.getPart("image");
        String fileName = Paths.get(partFile.getSubmittedFileName()).getFileName().toString();
        String extension = fileName.lastIndexOf(".") > 0 ? fileName.substring(fileName.lastIndexOf(".")) : "";
        String uniqueName = UUID.randomUUID().toString() + extension;

        File uploadsDir = new File(request.getServletContext().getRealPath("/uploaded-images"));
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }
        File fileSaveDir = new File(uploadsDir,uniqueName);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        File file = new File(uploadsDir, uniqueName);

        try (InputStream fileContent = partFile.getInputStream()) {
            Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        ProductCategory category = ProductCategory.valueOf(request.getParameter("category"));
        String details = request.getParameter("details");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String name = request.getParameter("name");

        try {
            ProductRepositories productRep = new ProductRepositories(factory);

            Product product = new Product(name, category, details, price, stock, uniqueName);
            List<Product> products = new ArrayList<>();
            products.add(product);

            boolean isAdded = productRep.addProducts(products);
            if (isAdded) {
                session.setAttribute("aps-message", "Add product successfully");
                List<Product> productList = productRep.getAllProducts();
                session.setAttribute("productList", productList);
            } else {
                session.setAttribute("ape-message", "Failed to add product");
            }

            if (session.getAttribute("aps-message") != null) {
                session.setAttribute("add-product-success", true);
            } else {
                session.setAttribute("add-product-success", false);
            }
        } catch (Exception e) {
            System.err.println("An Exception occurred while adding products: " + e.getMessage());
        }
        return terminus;
    }
}