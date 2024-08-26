package com.example.demo.product_ms.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Фильтр для продукта по имени и цене")
public class FilterDTO {

  private String name;
  private PriceDTO price;
}
