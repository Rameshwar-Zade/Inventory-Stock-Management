package com.gt.service;

import com.gt.dto.ProductDTO;
import com.gt.entity.Product;
import com.gt.repository.ProductRepository;
import com.gt.util.ModelMapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapperUtil modelMapperUtil;

    @InjectMocks
    private ProductServiceImpl productService; // Implementation class, not interface

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductById_Found() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(modelMapperUtil.map(product, ProductDTO.class)).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        ProductDTO result = productService.getProductById(999L);

        assertNull(result);
        verify(productRepository, times(1)).findById(999L);
    }
}
