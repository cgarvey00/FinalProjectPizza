package com.finalprojectcoffee.commands;

import com.finalprojectcoffee.entities.Review;
import com.finalprojectcoffee.entities.User;
import com.finalprojectcoffee.repositories.ReviewRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
    public String execute() {
        User user = (User) request.getSession().getAttribute("user");
        String comment = request.getParameter("comment");
        int stars = Integer.parseInt(request.getParameter("stars"));
        Date commentDate = new Date();

        Review review = new Review(user, comment, commentDate, stars);

        ReviewRepository reviewRepository = new ReviewRepository(factory);
        boolean success = reviewRepository.addReview(review, stars);

        if (success) {
            return "review.jsp?success=true";
        } else {
            return "review.jsp?success=false";
        }
    }
}
