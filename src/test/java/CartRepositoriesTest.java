package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Cart;
import com.finalprojectcoffee.entities.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//import javax.persistence.Query;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartRepositoriesTest {

    @Mock
    private static EntityManagerFactory entityManagerFactory;

    @Mock
    private static EntityManager entityManager;

    @Mock
    private static EntityTransaction transaction;

   // @Mock
  //  private static Query query;

    private static CartRepositories cartRepositories;

    @BeforeAll
    static void setUp() {
        MockitoAnnotations.openMocks(CartRepositoriesTest.class);
        cartRepositories = new CartRepositories(entityManagerFactory);
    }

    @Test
    @Order(1)
    void testAddToCart_Success() {
        // Mocking behavior
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(transaction.isActive()).thenReturn(false);

        assertTrue(cartRepositories.addToCart(1, 1, 2));
    }

    @Test
    @Order(2)
    void testAddToCart_ProductNotFound() {
        // Mocking behavior
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(transaction.isActive()).thenReturn(false);

        assertFalse(cartRepositories.addToCart(1, 999, 2));
    }

    @Test
    @Order(3)
    void testRemoveFromCart_Success() {
        // Mocking behavior
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.find(Cart.class, 1)).thenReturn(new Cart());

        cartRepositories.removeFromCart(1);
        verify(entityManager).remove(any());
    }

    @Test
    @Order(4)
    void testUpdateQuantity_Success() {
        // Mocking behavior
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        CartItem cartItem = new CartItem();
        cartItem.setId(1);
        when(entityManager.find(CartItem.class, 1)).thenReturn(cartItem);

        cartRepositories.updateQuantity(1, 5);
        assertEquals(5, cartItem.getQuantity());
    }

    @Test
    @Order(5)
    void testClearCart_Success() {
        // Mocking behavior
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(transaction.isActive()).thenReturn(false);

        cartRepositories.clearCart(1);
        verify(entityManager).createQuery(anyString());
    }

 //   @Test
  //  @Order(6)
   // void testGetCartItems_Success() {
     //   when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
    //    List<CartItems> cartItemsList = new ArrayList<>();
     //   when(entityManager.createQuery(anyString())).thenReturn(query);
    //    when(query.setParameter(anyString(), anyInt())).thenReturn(query);
    //    when(query.getResultList()).thenReturn(cartItemsList);

      //  List<CartItems> result = cartRepositories.getCartItems(1);
      //  assertEquals(cartItemsList, result);
   // }





    @AfterAll
    static void tearDown() {

    }
}
