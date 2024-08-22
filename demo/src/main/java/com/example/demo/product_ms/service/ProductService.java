package com.example.demo.product_ms.service;

import com.example.demo.product_ms.entity.Product;
import com.example.demo.product_ms.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  public List<Product> getProducts() {
    return productRepository.findAll();
  }

  public Optional<Product> getProductById(Long id) {
    return productRepository.findById(id);
  }

  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  public Product updateProduct(Product currrentProduct) {
    Product product =
        productRepository
            .findById(currrentProduct.getId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
    product.setName(currrentProduct.getName());
    product.setPrice(currrentProduct.getPrice());
    product.setDescription(currrentProduct.getDescription());
    return productRepository.save(product);
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
}
