package com.nipun.ecom.productservice.service;

import com.nipun.ecom.productservice.dto.ProductDTO;
import com.nipun.ecom.productservice.model.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Nipun on 3/6/22
 */

public interface ProductService {
    ProductDTO getProductById(Long id);

    List<ProductDTO> getProductByNameLike(String name);

    List<Product> getAllProducts();

    ProductDTO saveProduct(Product product);

    Integer getAvailableUnits(String productCode);

    Integer updateStocks(String productCode, Integer soldCount);

    BigDecimal getUnitPriceByProductCode(String productCode);
}
