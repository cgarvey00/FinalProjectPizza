import com.finalprojectcoffee.entities.Review;
import com.finalprojectcoffee.entities.User;
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

            User user = entityManager.find(User.class, 1);

            Review review = new Review();
            review.setUser(user);
            review.setComment("Great pizza!");
            review.setCommentDate(new Date());
            review.setStars(5);

            boolean result = reviewRepository.addReview(review.getId(), review.getComment(), review.getCommentDate(), review.getStars());
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
        Review review = new Review();
        User user = new User();
        user.setId(1000);
        review.setUser(user);
        review.setComment("Bad experience");
        review.setCommentDate(new Date());
        review.setStars(1);

        assertFalse(reviewRepository.addReview(review.getId(), review.getComment(),review.getCommentDate(), review.getStars()));
    }

    @Test
    public void getAllReviews() {
        List<Review> reviews = reviewRepository.getAllReviews();
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
    }

    @Test
    public void getReviewsByUserId() {
        List<Review> reviews = reviewRepository.getReviewsByUserId(1);
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
    }

    @Test
    public void removeReview_Success() {
        assertFalse(reviewRepository.removeReview(1));
    }

    @Test
    public void removeReview_Failure() {
        assertFalse(reviewRepository.removeReview(1000));
    }
}
