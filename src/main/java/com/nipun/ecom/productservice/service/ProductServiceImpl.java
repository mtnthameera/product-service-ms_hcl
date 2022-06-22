package com.nipun.ecom.productservice.service;

import com.nipun.ecom.productservice.dto.ProductDTO;
import com.nipun.ecom.productservice.exception.ProductNotFoundException;
import com.nipun.ecom.productservice.model.Product;
import com.nipun.ecom.productservice.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Nipun on 3/6/22
 */

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDTO getProductById(Long id) {
        LOGGER.debug("ProductServiceImpl::getProductById");
        return repository.findById(id)
                .map(product -> mapper.map(product, ProductDTO.class))
                .orElseThrow(() -> new ProductNotFoundException("No product found by Id : " + id));
    }

    @Override
    public List<ProductDTO> getProductByNameLike(String name) {
        LOGGER.debug("ProductServiceImpl::getProductByNameLike");
        return repository.findByNameLikeIgnoreCase("%".concat(name.concat("%")))
                .orElseThrow(() -> new ProductNotFoundException("No product found by name : " + name))
                .stream().map(products -> mapper.map(products, ProductDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllProducts() {
        LOGGER.debug("ProductServiceImpl::getAllProducts");
        return repository.findAll();
    }

    @Override
    public ProductDTO saveProduct(Product product) {
        LOGGER.debug("ProductServiceImpl::saveProduct");
        Optional<Product> match = repository.findByProductCode(product.getProductCode());
        if (match.isPresent()) {
            LOGGER.info("Product {} already exists, updating stock quantity.", product.getProductCode());
            Product prod = match.get();
            prod.setStockQuantity(prod.getStockQuantity() + product.getStockQuantity());
            return mapper.map(repository.save(prod), ProductDTO.class);
        }
        return mapper.map(repository.save(product), ProductDTO.class);
    }

    @Override
    public Integer getAvailableUnits(String productCode) {
        LOGGER.debug("ProductServiceImpl::isInStocks");
        return repository.findByProductCode(productCode)
                .orElseThrow(() -> new ProductNotFoundException("Invalid Product Code" + productCode))
                .getStockQuantity();

    }

    @Override
    @Transactional
    public Integer updateStocks(String productCode, Integer soldCount) {
        LOGGER.debug("ProductServiceImpl::updateStocks");
        return repository.updateByProductCode(productCode, soldCount);
    }

    @Override
    public BigDecimal getUnitPriceByProductCode(String productCode) {
        LOGGER.debug("ProductServiceImpl::getUnitPriceByProductCode");
        return repository.findUnitPriceByProductCode(productCode);
    }

}
