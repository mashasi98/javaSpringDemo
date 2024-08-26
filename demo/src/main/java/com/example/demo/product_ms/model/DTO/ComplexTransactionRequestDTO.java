package com.example.demo.product_ms.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplexTransactionRequestDTO {
    private ProductDTO firstProduct;
    private ProductDTO secondProduct;
}
