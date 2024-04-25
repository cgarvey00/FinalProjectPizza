package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.ProductRepositories;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class MostPopularProduct implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public MostPopularProduct(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "admin-dashboard.jsp";

        HttpSession session = request.getSession(true);
        boolean isNull = true;
        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser != null && "Admin".equals(loggedInUser.getUserType())) {

                ProductRepositories prodRepos = new ProductRepositories(factory);
                Product popular = prodRepos.getMostPopularProduct();
                popular = null;
                if (popular != null) {
                    try (PDDocument document = new PDDocument()) {
                        isNull=false;
                        PDPage page = new PDPage();
                        document.addPage(page);

                        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.HELVETICA, 16);
                            contentStream.setLeading(14.5f);
                            contentStream.newLineAtOffset(25, 700);
                            contentStream.showText("This is the most popular Product of the Week");
                            contentStream.newLine();
                            contentStream.newLine();
                            contentStream.showText("Product Name: " + popular.getName());
                            contentStream.newLine();
                            contentStream.showText("Details: " + popular.getDetails());
                            contentStream.newLine();
                            contentStream.showText("Price: " + popular.getPrice());
                            contentStream.endText();
                        }
                        response.setContentType("application/pdf");
                        String fileName = "mostPopularProduct.pdf";
                        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
                        document.save(response.getOutputStream());
                        response.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    session.setAttribute("errorPDF", isNull);
//                    return null;
                } else {
                    session.setAttribute("errorPDF", isNull);
                    terminus = "admin-dashboard.jsp";
                }
            } else {
                terminus = "index.jsp";
            }
        } else {
            terminus = "index.jsp";
        }

        return terminus;
    }
}