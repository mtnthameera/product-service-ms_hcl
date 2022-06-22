package com.nipun.ecom.productservice.controller;

import com.nipun.ecom.productservice.dto.ProductDTO;
import com.nipun.ecom.productservice.model.Product;
import com.nipun.ecom.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Nipun on 3/6/22
 */

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        LOGGER.info("Fetching product : {}", id);
        return ResponseEntity.ok()
                .body(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProductsByName(@RequestParam String productName) {
        LOGGER.info("Fetching product : {}", productName);
        return ResponseEntity.ok()
                .body(productService.getProductByNameLike(productName));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> saveNewProduct(@Valid @RequestBody Product product) {
        LOGGER.info("Saving new product.");
        return ResponseEntity.ok().body(productService.saveProduct(product));
    }

    @GetMapping("/stocks/{productCode}")
    public ResponseEntity<Integer> getAvailableUnits(@Valid @PathVariable String productCode){
        LOGGER.info("Checking product availability.");
        return ResponseEntity.ok().body(productService.getAvailableUnits(productCode));
    }

    @PutMapping("/stocks/{productCode}/update/{soldCount}")
    public ResponseEntity<Integer> updateStocks(@PathVariable String productCode, @PathVariable Integer soldCount){
        LOGGER.info("Updating product stocks.");
        return ResponseEntity.ok().body(productService.updateStocks(productCode,soldCount));

    }

    @GetMapping("/{productCode}/unit-price")
    public ResponseEntity<BigDecimal> getUnitPrice(@PathVariable String productCode){
        LOGGER.info("Updating product stocks.");
        return ResponseEntity.ok().body(productService.getUnitPriceByProductCode(productCode));

    }
}
