package com.example.demo.product_ms.controller;

import com.example.demo.product_ms.entity.Product;
import com.example.demo.product_ms.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @Operation(
      summary = "get all products list",
      description = "this api is used to get list of products")
  @GetMapping("/products")
  public List<Product> getProducts() {
    return productService.getProducts();
  }

  @Operation(summary = "get product by id")
  @GetMapping("/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable Long id) {
    return productService
        .getProductById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @Operation(summary = "create new product")
  @PostMapping("/create")
  public Product createProduct(@RequestBody Product product) {
    return productService.createProduct(product);
  }

  @Operation(summary = "update product info")
  @PostMapping("/update")
  public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
    return ResponseEntity.ok(productService.updateProduct(product));
  }

  @Operation(summary = "delete product")
  @PostMapping("/delete")
  public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
