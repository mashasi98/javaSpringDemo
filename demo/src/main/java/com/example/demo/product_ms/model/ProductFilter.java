package com.example.demo.product_ms.model;

import lombok.Getter;

@Getter
public enum ProductFilter {
  BY_NAME("name"),
  BY_PRICE("price");

  private final String filterType;

  ProductFilter(String name) {
    this.filterType = name;
  }
}
