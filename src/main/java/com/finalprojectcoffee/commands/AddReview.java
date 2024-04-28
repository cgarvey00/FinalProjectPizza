package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Review;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.ReviewRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

public class AddReview implements Command {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final EntityManagerFactory factory;

    public AddReview(HttpServletRequest request, HttpServletResponse response, EntityManagerFactory factory) {
        this.request = request;
        this.response = response;
        this.factory = factory;
    }

    @Override
    public String execute() throws ServletException, IOException {
        String terminus = "review.jsp";
        HttpSession session = request.getSession(true);
        try {
            ReviewRepository reviewRep = new ReviewRepository(factory);
            User loggedInUser = (User) session.getAttribute("loggedInUser");
            String comment = request.getParameter("comment");
            int stars = Integer.parseInt(request.getParameter("stars"));
            Date commentDate = new Date();
            if (loggedInUser != null && loggedInUser.getUserType().equals("Customer")) {
                boolean isReviewed = reviewRep.addReview(loggedInUser.getId(), comment,
                        commentDate, stars);
                if (isReviewed) {
                    session.setAttribute("aps-message", "Review successfully added");

                } else {
                    session.setAttribute("log-message", "User logged out");
                }

            }
        } catch (Exception e) {
            System.err.println("An Exception occured while adding review:" + e.getMessage());
        }

        return terminus;
    }
}
