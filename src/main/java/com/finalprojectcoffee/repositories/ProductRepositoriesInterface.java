package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;

import java.util.List;

/**
 * @author cgarvey00
 */
public interface ProductRepositoriesInterface {
     Product findProductByID(int productId);
     List<Product> getAllProducts();
     List<Product> findProductsByCategory(ProductCategory category);
     List<Product> findProductsByKeyword(String keyword);
     boolean addProducts(List<Product> products);
     boolean updateProduct(int id, String name, ProductCategory category, String details, double price, int stock, String image);
     boolean deleteProduct(List<Product> products);
}
