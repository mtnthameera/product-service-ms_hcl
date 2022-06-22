package com.nipun.ecom.productservice.service;

import com.nipun.ecom.productservice.dto.ProductDTO;
import com.nipun.ecom.productservice.exception.ProductNotFoundException;
import com.nipun.ecom.productservice.model.Product;
import com.nipun.ecom.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * @author Nipun on 22/6/22
 */

@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl service;

    @Mock
    ProductRepository repository;

    @Mock
    ModelMapper mapper;

    private ProductDTO productDTO;

    private Product product;


    @BeforeEach
    public void setUp() {
        productDTO = ProductDTO.builder()
                .productCode("ab12")
                .stockQuantity(3)
                .productId(1L)
                .description("Test desc")
                .name("Test Product")
                .price(12.0)
                .build();

        product = Product.builder()
                .id(1L)
                .description("Test desc")
                .name("Test Product")
                .price(12.0)
                .productCode("ab12")
                .stockQuantity(3)
                .build();
    }


    @Test
    @DisplayName("product should return when ID is given")
    public void testGetProductById() {
        //Given
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(mapper.map(any(), any())).thenReturn(productDTO);
        //When
        ProductDTO actual = service.getProductById(1L);
        //Then
        assertNotNull(actual);
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getProductCode(), productDTO.getProductCode());
        assertEquals(product.getStockQuantity(), productDTO.getStockQuantity());
        assertEquals(product.getPrice(), productDTO.getPrice());
        assertEquals(product.getDescription(), productDTO.getDescription());
        assertEquals(product.getId(), productDTO.getProductId());
    }


    @Test
    @DisplayName("throw ProductNotFoundException when no product found for name")
    public void getProductByNameLikeNotFoundThrowEx() {
        //Given
        Mockito.when(repository.findByNameLikeIgnoreCase(anyString()))
                .thenReturn(Optional.empty());
        try {
            //When
            service.getProductByNameLike(anyString());
        } catch (Exception e) {
            //Then
            assertEquals(ProductNotFoundException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("productDTO should return when product is found by name")
    public void getProductByNameLikeSuccess() {
        //Given
        Mockito.when(repository.findByNameLikeIgnoreCase(anyString()))
                .thenReturn(Optional.of(List.of(product)));
        Mockito.when(mapper.map(any(), any())).thenReturn(productDTO);
        //When
        List<ProductDTO> actual = service.getProductByNameLike(anyString());
        //Then
        assertEquals(1, actual.size());
        assertEquals(product.getName(), actual.get(0).getName());
        assertEquals(product.getProductCode(), actual.get(0).getProductCode());

    }

    @Test
    @DisplayName("throw ProductNotFoundException when no product found for product code")
    public void testGetAvailableUnitsNotFoundThrowEx() {
        //Given
        Mockito.when(repository.findByProductCode(anyString()))
                .thenReturn(Optional.empty());
        try {
            //When
            service.getAvailableUnits(anyString());
        } catch (Exception e) {
            //Then
            assertEquals(ProductNotFoundException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("Return available units if product is found")
    public void testGetAvailableUnits_success() {
        //Given
        Mockito.when(repository.findByProductCode(anyString()))
                .thenReturn(Optional.of(product));
        //When
        Integer actual = service.getAvailableUnits(anyString());
        //Then
        assertEquals(3, actual);
    }

}