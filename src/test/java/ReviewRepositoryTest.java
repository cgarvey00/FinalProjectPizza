import com.finalprojectcoffee.entities.Review;
import com.finalprojectcoffee.entities.User; // Import User entity
import com.finalprojectcoffee.repositories.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewRepositoryTest {

    private static EntityManagerFactory factory;
    private ReviewRepository reviewRepository;

    @BeforeAll
    public static void setUpClass() {
        factory = Persistence.createEntityManagerFactory("testpizzadeliveryshop");
    }

    @AfterAll
    public static void tearDownClass() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    @BeforeEach
    public void setUp() {
        reviewRepository = new ReviewRepository(factory);
    }

    @Test
    public void addReview_Success() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            // Fetch a user from the database
            User user = entityManager.find(User.class, 1); // Change the ID as per your requirement

            // Create a test review
            Review review = new Review();
            review.setUser(user); // Set the fetched user
            review.setComment("Great pizza!");
            review.setCommentDate(new Date());

            // Add the review
            boolean result = reviewRepository.addReview(review);
            assertTrue(result);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            fail("Exception occurred: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Test
    public void addReview_Failure() {
        // Create a test review with a non-existent user
        Review review = new Review();
        User user = new User(); // Create a dummy user
        user.setId(1000); // Assuming user ID 1000 doesn't exist in the database
        review.setUser(user);
        review.setComment("Bad experience");
        review.setCommentDate(new Date());

        assertFalse(reviewRepository.addReview(review));
    }

    @Test
    public void getAllReviews() {
        List<Review> reviews = reviewRepository.getAllReviews();
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
    }

    @Test
    public void getReviewsByUserId() {
        List<Review> reviews = reviewRepository.getReviewsByUserId(1); // Assuming user ID 1 exists
        assertNotNull(reviews);
        assertFalse(reviews.isEmpty());
    }

    @Test
    public void removeReview_Success() {
        // Assuming there's a review with ID 1 in the database
        assertTrue(reviewRepository.removeReview(1));
    }

    @Test
    public void removeReview_Failure() {
        // Assuming there's no review with ID 1000 in the database
        assertFalse(reviewRepository.removeReview(1000));
    }
}
