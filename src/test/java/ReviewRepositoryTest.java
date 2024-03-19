//package epositories;
//
//import com.finalprojectcoffee.entities.Review;
//import com.finalprojectcoffee.entities.User;
//import com.finalprojectcoffee.repositories.ReviewRepository;
//import com.finalprojectcoffee.repositories.UserRepositories;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import org.junit.jupiter.api.*;
//
//import jakarta.persistence.Persistence;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ReviewRepositoryTest {
//
//    private EntityManagerFactory factory;
//    private ReviewRepository reviewRepository;
//    private UserRepositories userRepositories;
//
//    @BeforeEach
//    public void setUp() {
//        factory = Persistence.createEntityManagerFactory("testpizzashop");
//        reviewRepository = new ReviewRepository(factory);
//    }
//
//    @AfterEach
//    public void tearDown() {
//        if (factory != null && factory.isOpen()) {
//            factory.close();
//        }
//    }
//
//    @Test
//    public void addReview_Success() {
//        EntityManager entityManager = factory.createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//
//        try {
//            transaction.begin();
//
//            // Fetch a user from the database
//            User user = entityManager.find(User.class, 1); // Change the ID as per your requirement
//
//            // Create a test review
//            Review review = new Review();
//            review.setUser(user); // Set the fetched user
//            review.setComment("Test comment");
//            review.setCommentDate(new Date());
//
//            // Add the review
//            boolean result = reviewRepository.addReview(review);
//            assertTrue(result);
//
//            // Ensure the review is persisted
//            Review persistedReview = entityManager.find(Review.class, review.getId());
//            assertNotNull(persistedReview);
//            assertEquals(review.getComment(), persistedReview.getComment());
//            assertEquals(review.getCommentDate(), persistedReview.getCommentDate());
//            assertEquals(review.getUser(), persistedReview.getUser());
//
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null && transaction.isActive()) {
//                transaction.rollback();
//            }
//            fail("Exception occurred: " + e.getMessage());
//        } finally {
//            entityManager.close();
//        }
//    }
//
//
//    @Test
//    public void addReview_Failure() {
//        // Try adding a review with null comment (which should fail)
//        Review review = new Review();
//        review.setComment(null);
//        review.setCommentDate(new Date());
//
//        boolean result = reviewRepository.addReview(review);
//        assertFalse(result);
//    }
//}
