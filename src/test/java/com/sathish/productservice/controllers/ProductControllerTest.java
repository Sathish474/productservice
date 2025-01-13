package com.sathish.productservice.controllers;

import com.sathish.productservice.dto.products.*;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        CreateProductRequestDto requestDto = new CreateProductRequestDto();
        Product product = new Product();
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        CreateProductResponseDto responseDto = productController.createProduct(requestDto);

        assertEquals(CreateProductResponseDto.from(product).getClass(), responseDto.getClass());
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(productList);
        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(true);

        GetAllProductResponseDto responseDto = productController.getAllProducts();

        assertEquals(productList.size(), responseDto.getProducts().size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProduct() {
        Long productId = 1L;
        Product product = new Product();
        when(productService.getProduct(productId)).thenReturn(product);

        GetProductResponseDto responseDto = productController.getProduct(productId);

        assertEquals(GetProductDto.from(product).getClass(), responseDto.getProductDto().getClass());
        verify(productService, times(1)).getProduct(productId);
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        when(productService.deleteProduct(productId)).thenReturn(true);

        Boolean result = productController.deleteProduct(productId);

        assertEquals(true, result);
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        CreateProductDto productDto = new CreateProductDto();
        Product product = new Product();
        when(productService.paritialUpdateProduct(eq(productId), any(Product.class))).thenReturn(product);

        PatchProductResponseDto responseDto = productController.updateProduct(productId, productDto);

        assertEquals(GetProductDto.from(product).getClass(), responseDto.getDto().getClass());
        verify(productService, times(1)).paritialUpdateProduct(eq(productId), any(Product.class));
    }

    @Test
    void testGetProductByName() {
        String productName = "example";
        List<Product> productList = new ArrayList<>();
        when(productService.getProductByCategoryName(productName)).thenReturn(productList);

        List<GetProductResponseDto> responseDtos = productController.getProductByName(productName);

        assertEquals(productList.size(), responseDtos.size());
        verify(productService, times(1)).getProductByCategoryName(productName);
    }
}
