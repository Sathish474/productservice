/*
package com.sathish.productservice;

import com.sathish.productservice.controllers.ProductController;
import com.sathish.productservice.dto.products.CreateProductRequestDto;
import com.sathish.productservice.dto.products.CreateProductResponseDto;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
@ActiveProfiles("test")
class ProductserviceApplicationTests {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Test
    public void Test_CreateProduct_With_Valid_RequestBody() {
        // Arrange
        when(productService.createProduct(any())).thenReturn(new Product());
        // Act
        CreateProductResponseDto response = productController.createProduct(new CreateProductRequestDto());
        // Assert
        assert response != null;
        assertEquals(CreateProductResponseDto.class, response.getClass());
    }

    */
/*
    public CreateProductResponseDto createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        logger.info("Creating product with name: {}", createProductRequestDto.toString());
        Product product = productService.createProduct(createProductRequestDto.toProduct());
        logger.info("Product created with id: {}", product.getId());
        return CreateProductResponseDto.from(product);
    }
     *//*

}
*/
