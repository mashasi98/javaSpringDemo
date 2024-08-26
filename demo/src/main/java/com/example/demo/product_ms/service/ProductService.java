package com.example.demo.product_ms.service;

import com.example.demo.product_ms.model.DTO.ProductDTO;
import com.example.demo.product_ms.model.DTO.ProductRequestDTO;
import com.example.demo.product_ms.model.ProductFilter;
import com.example.demo.product_ms.model.entity.ProductPageable;
import com.example.demo.product_ms.model.entity.Product;
import com.example.demo.product_ms.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.product_ms.model.ProductSorting.getSortingFilterFromString;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  public List<Product> getProducts(ProductRequestDTO requestFilterDTO) {
    if (isNull(requestFilterDTO)) {
      return productRepository.findAll();
    }
    if (isRequestParametersCorrect(requestFilterDTO)) {
      throw new IllegalArgumentException("Only one of filter or sort can be set");
    }
    if (isNotEmpty(requestFilterDTO.getSort())) {
      return getSortedProductList(requestFilterDTO);
    }

    if (isNotEmpty(requestFilterDTO.getFilter())) {
      String filterType = getFilter(requestFilterDTO);
      return getFilteredProductList(requestFilterDTO, filterType);
    }
    return null;
  }

  private List<Product> getFilteredProductList(
      ProductRequestDTO requestFilterDTO, String filterType) {
    return switch (filterType) {
      case "name" -> {
        Page<Product> products =
            productRepository.findAllByNameContaining(
                requestFilterDTO.getFilter().getName(), getPageable(requestFilterDTO, null));
        yield (isNull(products) ? null : products.getContent());
      }

      case "price" -> {
        if (requestFilterDTO.getFilter().getPrice().getPriceMax()
            < requestFilterDTO.getFilter().getPrice().getPriceMin())
          throw new IllegalArgumentException("Max price cannot be less than min price");
        Page<Product> products =
            productRepository.findAllByPriceBetween(
                requestFilterDTO.getFilter().getPrice().getPriceMin(),
                requestFilterDTO.getFilter().getPrice().getPriceMax(),
                getPageable(requestFilterDTO, null));
        yield (isNull(products) ? null : products.getContent());
      }
      default -> null;
    };
  }

  private List<Product> getSortedProductList(ProductRequestDTO requestFilterDTO) {
    String field = requestFilterDTO.getSort().getField();
    String sortType = getSortingFilterFromString(field);
    Sort productSorting =
        Sort.by(Sort.Direction.fromString(requestFilterDTO.getSort().getDirection()), sortType);
    Pageable pageable = getPageable(requestFilterDTO, productSorting);
    Page<Product> productPage = productRepository.findAll(pageable);
    return isNull(productPage) ? null : productPage.getContent();
  }

  private static String getFilter(ProductRequestDTO requestFilterDTO) {
    String filterType = null;
    if (isNotEmpty(requestFilterDTO.getFilter().getName())) {
      filterType = ProductFilter.BY_NAME.getFilterType();
    }
    if (isNotEmpty(requestFilterDTO.getFilter().getPrice())) {
      filterType = ProductFilter.BY_PRICE.getFilterType();
    }
    if (isNull(filterType)) {
      throw new IllegalArgumentException("Filter type is not set");
    }
    return filterType;
  }

  private static boolean isRequestParametersCorrect(ProductRequestDTO requestFilterDTO) {
    return isNotEmpty(requestFilterDTO.getFilter()) && isNotEmpty(requestFilterDTO.getSort());
  }

  private static Pageable getPageable(ProductRequestDTO requestFilterDTO, Sort productSorting) {

    ProductPageable pageable =
        ProductPageable.builder().pageNumber(0).pageSize(0).sort(Sort.unsorted()).offset(0).build();

    if (isNotEmpty(requestFilterDTO.getSort())) {
      pageable.setSort(productSorting);
    }
    if (isNotEmpty(requestFilterDTO.getPage())) {
      pageable.setPageNumber(requestFilterDTO.getPage());
    }

    if (isNotEmpty(requestFilterDTO.getSize())) {
      pageable.setPageSize(requestFilterDTO.getSize());
    }
    return pageable;
  }

  public Product getProductById(Long id) {

    return productRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found"));
  }

  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  public Product updateProduct(Long id, Product currrentProduct) {

    Product product =
        productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    product.setName(currrentProduct.getName());
    product.setPrice(currrentProduct.getPrice());
    product.setDescription(currrentProduct.getDescription());
    return productRepository.save(product);
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }

  @Transactional
  public void performComplexTransaction(ProductDTO firstProductDTO, ProductDTO secondProductDTO) {

    Product firstProductEntity =
        Product.builder()
            .name(firstProductDTO.getName())
            .price(firstProductDTO.getPrice())
            .description(firstProductDTO.getDescription())
            .build();

    productRepository.save(firstProductEntity);

    Product secondProductEntity =
        Product.builder()
            .name(secondProductDTO.getName())
            .price(secondProductDTO.getPrice())
            .description(secondProductDTO.getDescription())
            .build();
    if (secondProductDTO.getPrice() < 0) {
      throw new IllegalArgumentException("Negative Price");
    }
    productRepository.save(secondProductEntity);
  }
}
