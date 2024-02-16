package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.util.Collections;
import java.util.List;

public class ReviewRepository implements ReviewRepositoryInterface {

    private final EntityManagerFactory factory;

    public ReviewRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean addReview(Review review) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (hasProfanity(review.getComment())) {
                return false;
            }
            entityManager.persist(review);
            transaction.commit();
            return true;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while adding a review: " + e.getMessage());
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
            System.err.println("An Exception occurred while retrieving all reviews: " + e.getMessage());
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
            System.err.println("An Exception occurred while retrieving reviews by user ID: " + e.getMessage());
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
            if (review != null) {
                entityManager.remove(review);
                transaction.commit();
                return true;
            }
            return false;
        } catch (PersistenceException e) {
            System.err.println("A PersistenceException occurred while removing a review: " + e.getMessage());
            transaction.rollback();
            return false;
        } finally {
            entityManager.close();
        }
    }

    private boolean hasProfanity(String comment) {

        List<String> profaneWords = List.of("fuck", "retard");

        for (String word : profaneWords) {
            if (comment.toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }
}
