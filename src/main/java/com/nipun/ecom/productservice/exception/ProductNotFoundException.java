package com.nipun.ecom.productservice.exception;

/**
 * @author Nipun on 3/6/22
 */

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

}
