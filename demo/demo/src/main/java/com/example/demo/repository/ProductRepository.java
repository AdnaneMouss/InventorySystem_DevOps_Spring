package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
@Query("SELECT p FROM Product p WHERE p.price=(SELECT MIN(p2.price)FROM Product p2)")
    Product findCheapestProduct();
    @Query("SELECT p FROM Product p WHERE p.price=(SELECT MAX(p2.price)FROM Product p2)")
    Product findPriciestProduct();
}