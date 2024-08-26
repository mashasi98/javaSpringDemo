package com.example.demo.product_ms.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Фильтр для поиска продуктов")
public class ProductRequestDTO {

  private FilterDTO filter;
  private SortDTO sort;
  private int size;
  private int page;
}

// class FilterDto{
//    public Filter filter;
//    public Sort sort;
//    public Pagination pagination;
// }
//
// {
//    filter{
//        name: nameValue,
//                price: {
//            min: priceMinValue,
//                    max: priceMaxValue
//        },
//    }
//
//    sort: {
//        field: 'name' || 'price',
//        direction: 'asc' || 'desc'
// },
//        pagination: {
// page: 0,
// size: 20
//        }
//
//        }
