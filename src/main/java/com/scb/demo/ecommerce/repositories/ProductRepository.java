package com.scb.demo.ecommerce.repositories;

import com.scb.demo.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByProductName(String name);
}
