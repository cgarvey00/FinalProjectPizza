package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Product;
import com.finalprojectcoffee.entities.ProductCategory;

import java.util.List;

/**
 * @author cgarvey00
 */
public interface ProductRepositoriesInterface {
    public Product findProductByID(int productID);
    public List<Product> getAllProducts();
    public List<Product> findProductsByCategory(ProductCategory category);
    public List<Product> findProductsByKeyword(String keyword);
    public boolean addProduct(Product p);
    public boolean updateProduct(int id, String name, ProductCategory category, String details, double price, int stock, String image);
    public boolean deleteProduct(int pID);
}
