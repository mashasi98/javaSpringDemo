package com.example.demo.product_ms.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@Setter
@Builder
public class ProductPageable implements Pageable {

  private int pageNumber;
  private int pageSize;
  private long offset;
  private Sort sort;

  @Override
  public int getPageNumber() {
    return pageNumber;
  }

  @Override
  public int getPageSize() {
    return pageSize;
  }

  @Override
  public long getOffset() {
    return offset;
  }

  @Override
  public Sort getSort() {
    return sort;
  }

  @Override
  public Pageable next() {
    return new ProductPageable(pageNumber + 1, pageSize, offset + pageSize, sort);
  }

  @Override
  public Pageable previousOrFirst() {
    if (pageNumber > 1) {
      return new ProductPageable(pageNumber - 1, pageSize, offset - pageSize, sort);
    } else {
      return this;
    }
  }

  @Override
  public Pageable first() {
    return new ProductPageable(1, pageSize, 0, sort);
  }

  @Override
  public Pageable withPage(int pageNumber) {
    return new ProductPageable(pageNumber, pageSize, (pageNumber - 1) * pageSize, sort);
  }

  @Override
  public boolean hasPrevious() {
    return pageNumber > 1;
  }
}
