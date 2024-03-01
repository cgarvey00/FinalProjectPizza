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
     boolean addProducts(List<Product> products);
     boolean updateProduct(Product product);
     boolean deleteProduct(List<Product> products);
}
