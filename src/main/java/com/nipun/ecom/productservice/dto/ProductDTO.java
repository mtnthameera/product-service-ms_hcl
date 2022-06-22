package com.nipun.ecom.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Nipun on 20/6/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long productId;
    @NotEmpty(message = "Please provide product name")
    private String name;
    @NotNull(message = "Please provide price of the product")
    private Double price;
    @NotEmpty(message = "Please provide product Description")
    private String description;
    @NotEmpty(message = "Please provide product Code")
    private String productCode;
    @NotEmpty(message = "Please provide product Stocks")
    private Integer stockQuantity;
}
