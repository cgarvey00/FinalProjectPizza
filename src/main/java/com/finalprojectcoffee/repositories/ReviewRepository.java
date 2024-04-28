package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Customer;
import com.finalprojectcoffee.entities.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ReviewRepository implements ReviewRepositoryInterface {

    private final EntityManagerFactory factory;

    public ReviewRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean addReview(int customer_id, String comment, Date commentDate, int stars) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            if (customer_id <= 0 || comment == (null)  || stars < 0 || commentDate.equals(null)) {
                return false;
            }
            transaction.begin();

            Customer customer = entityManager.find(Customer.class, customer_id);
            Review review = new Review();
            review.setUser(customer);
            review.setComment(comment);
            review.setCommentDate(commentDate);
            review.setStars(stars);
            entityManager.persist(review);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("An error occurred while adding a review: " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Review> getAllReviews() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT r FROM Review r", Review.class).getResultList();
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving all reviews: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Review> getReviewsByUserId(int userId) {
        EntityManager entityManager = factory.createEntityManager();
        try {
            return entityManager.createQuery("SELECT r FROM Review r WHERE r.user.id = :userId", Review.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving reviews by user ID: " + e.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean removeReview(int reviewId) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Review review = entityManager.find(Review.class, reviewId);
            if (review == null) {
                // Review with the given ID does not exist
                System.err.println("Review with ID " + reviewId + " does not exist.");
                return false;
            }

            entityManager.remove(review);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("An error occurred while removing a review: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
}
