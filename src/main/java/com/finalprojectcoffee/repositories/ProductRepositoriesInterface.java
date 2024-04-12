package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;

import java.util.List;

/**
 * @author Yutang
 */
public interface ProductRepositoriesInterface {
     Product findProductByID(int productId);
     List<Product> getAllProducts();
     List<Product> findProductsByCategory(ProductCategory category);
     List<Product> findProductsByKeyword(String keyword);
     boolean addProduct(Product product);
     boolean updateProduct(Product product);
     boolean deleteProduct(int productId);
}
