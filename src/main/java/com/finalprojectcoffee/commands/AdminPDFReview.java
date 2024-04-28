package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Review;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.ReviewRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class AdminPDFReview implements Command {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AdminPDFReview(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() {
        String terminus = "admin-dashboard.jsp";

        HttpSession session = request.getSession(true);

        if (session != null) {
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser != null && "Admin".equals(loggedInUser.getUserType())) {

                ReviewRepository reviewRep = new ReviewRepository(factory);
                if (reviewRep.getAllReviews() != null || !reviewRep.getAllReviews().isEmpty()) {
                    try (PDDocument document = new PDDocument()) {
                        PDPage page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);

                        float margin = 50;
                        float yStart = page.getMediaBox().getHeight() - margin;
                        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                        float rowHeight = 20;
                        float yPosition = yStart;
//                        float tableHeight = rowHeight * (reviewRep.getAllReviews().size() + 1);

                        //Title
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                        contentStream.newLineAtOffset(margin, yStart);
                        contentStream.showText("List of Reviews");
                        contentStream.endText();
                        yPosition -= rowHeight;

                        String[] headings = new String[]{"Customer Username", "Comment", "Stars", "Comment Date"};
                        float[] cellWidths = {
                                tableWidth/4,
                                tableWidth/4,
                                tableWidth/4,tableWidth/4
                        };
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.moveTo(margin, yPosition);
                        contentStream.lineTo(margin + tableWidth, yPosition);
                        contentStream.stroke();

                        float nextx = margin;

                        for (int i = 0; i < headings.length; i++) {
                            contentStream.beginText();
                            contentStream.newLineAtOffset(nextx + 2, yPosition - 15);
                            contentStream.showText(headings[i]);
                            contentStream.endText();

                            nextx += cellWidths[i];
                            contentStream.moveTo(nextx, yPosition);
                            contentStream.lineTo(nextx, yPosition - rowHeight);
                            contentStream.stroke();
                        }
                        yPosition -= rowHeight;
                        contentStream.moveTo(margin, yPosition);
                        contentStream.lineTo(margin + tableWidth, yPosition);
                        contentStream.stroke();

                        contentStream.setFont(PDType1Font.HELVETICA, 12);
                        for (Review review : reviewRep.getAllReviews()) {
                            String[] data = {review.getUser().getUsername(), review.getComment(), String.valueOf(review.getStars()),
                                    review.getCommentDate().toString()};
                            nextx = margin;

                            for (int i = 0; i < data.length; i++) {
                                contentStream.beginText();
                                contentStream.newLineAtOffset(nextx + 2 ,yPosition-15);
                                contentStream.showText(data[i]);
                                contentStream.endText();
                                nextx+=cellWidths[i];
                            }
                            yPosition -= rowHeight;

                            contentStream.moveTo(margin, yPosition);
                            contentStream.moveTo(margin, yPosition);
                            contentStream.lineTo(margin + tableWidth, yPosition);
                            contentStream.stroke();
                        }
                        contentStream.close();
                        response.setContentType("application/pdf");
                        String fileName = "PizzaShopReviews.pdf";
                        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
                        document.save(response.getOutputStream());
                        response.getOutputStream().flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    session.setAttribute("errorReview", "No Reviews has been completed yet!!! ");
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