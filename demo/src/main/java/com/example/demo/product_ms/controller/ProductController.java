package com.example.demo.product_ms.controller;

import com.example.demo.product_ms.model.DTO.ComplexTransactionRequestDTO;
import com.example.demo.product_ms.model.DTO.ProductRequestDTO;
import com.example.demo.product_ms.model.entity.Product;
import com.example.demo.product_ms.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @Operation(
      summary = "get all products list",
      description = "this api is used to get list of products")
  @GetMapping()
  public List<Product> getProducts() {
    return productService.getProducts(null);
  }

  @Operation(summary = "get product by id")
  @GetMapping("/{id}")
  public Product getProduct(@PathVariable Long id) {
    return productService.getProductById(id);
  }

  @Operation(summary = "create new product")
  @PostMapping("/create")
  public Product createProduct(@RequestBody Product product) {
    return productService.createProduct(product);
  }

  @Operation(summary = "update product info")
  @PostMapping("/update/{id}")
  public ResponseEntity<Product> updateProduct(
      @PathVariable Long id, @RequestBody Product product) {

    return ResponseEntity.ok(productService.updateProduct(id, product));
  }

  @Operation(summary = "delete product")
  @PostMapping("/delete/{id}")
  public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Operation(summary = "perform complex transaction")
  @PostMapping("/perform-transaction")
  public void performComplexTransaction(
      @RequestBody ComplexTransactionRequestDTO complexTransactionRequestDTO) {
    productService.performComplexTransaction(
        complexTransactionRequestDTO.getFirstProduct(),
        complexTransactionRequestDTO.getSecondProduct());
  }

  @GetMapping("/sort")
  @Operation(
      summary = "search products by filter",
      description = "this api is used to search products by filter: BY_NAME, BY_PRICE")
  public List<Product> sortProductsByFilter(@RequestBody() ProductRequestDTO productRequestDTO) {
    return productService.getProducts(productRequestDTO);
  }

  @GetMapping("/search")
  @Operation(
      summary = "search products by filter",
      description = "this api is used to search products by filter: name, price(min, max)")
  public List<Product> searchProductsByFilter(@RequestBody() ProductRequestDTO productRequestDTO) {
    return productService.getProducts(productRequestDTO);
  }
}
