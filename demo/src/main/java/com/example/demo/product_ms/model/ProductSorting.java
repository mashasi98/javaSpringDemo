package com.example.demo.product_ms.model;

import lombok.Getter;

import static org.springframework.util.ObjectUtils.isEmpty;

@Getter
public enum ProductSorting {
  BY_NAME("name"),
  BY_PRICE("price");

  private final String sortingType;

  ProductSorting(String name) {
    this.sortingType = name;
  }

  public static String getSortingFilterFromString(String sortingType) {

    isSortingTypeProvide(sortingType);
    for (ProductSorting value : ProductSorting.values()) {
      if (value.toString().equals(sortingType)) {
        return value.getSortingType();
      }
    }
    throw new IllegalArgumentException("No enum constant found for name: " + sortingType);
  }

  private static void isSortingTypeProvide(String name) {
    if (isEmpty(name)) {
      throw new IllegalArgumentException("Sorting type is not set");
    }
  }
}
