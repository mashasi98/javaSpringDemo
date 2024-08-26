package com.example.demo.product_ms.repository;

import com.example.demo.product_ms.model.entity.Product;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Nullable
  Page<Product> findAll(Pageable pageable);

  @Nullable
  Page<Product> findAllByNameContaining(String name, Pageable pageable);

  @Nullable
  Page<Product> findAllByPriceBetween(double minPrice, double maxPrice, Pageable pageable);
}
