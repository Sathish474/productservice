package com.sathish.productservice.controllers;

import com.sathish.productservice.config.TestRedisConfiguration;
import com.sathish.productservice.dto.products.*;
import com.sathish.productservice.models.Category;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class ProductControllerIntegrationTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductRepository productRepository;

    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    private RestTemplate restTemplate;

    @MockitoBean
    private RedisConnectionFactory redisConnectionFactory;

    @MockitoBean
    private HashOperations<String, Object, Object> hashOperations;

    @Test
    public void testCreateProduct() {
        // Arrange
        // Create a product request dto
        CreateProductRequestDto requestDto = new CreateProductRequestDto();
        requestDto.setTitle("Test Product");
        requestDto.setDescription("Test Description");
        requestDto.setPrice(100.0);
        requestDto.setCategoryName("Test Category");

        // Act
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        doNothing().when(hashOperations).put(anyString(), anyString(), any(Product.class));

        CreateProductResponseDto savedProduct = productController.createProduct(requestDto);


        // Assert
        assert savedProduct.getId() != null;
        assert savedProduct.getTitle().equals("Test Product");
        assert savedProduct.getDescription().equals("Test Description");
        assert savedProduct.getPrice() == 100.0;

    }
    @Test
    public void testGetAllProducts() {
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        productRepository.save(product);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.entries("product")).thenReturn(null);

        // Act
        GetAllProductResponseDto getProductResponseDto = productController.getAllProducts();

        // Assert
        assert !getProductResponseDto.getProducts().isEmpty();
    }

    @Test
    public void TestGetProduct(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.entries("product")).thenReturn(null);

        // Act
        GetProductResponseDto getProductResponseDto = productController.getProduct(savedProduct.getId());

        // Assert
        assert getProductResponseDto.getProductDto().getId().equals(savedProduct.getId());
        assert getProductResponseDto.getProductDto().getTitle().equals("Test Product");
        assert getProductResponseDto.getProductDto().getDescription().equals("Test Description");
        assert getProductResponseDto.getProductDto().getPrice().equals(100.0);

    }

    @Test
    public void TestDeleteProduct(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.entries("product")).thenReturn(null);

        // Act
        Boolean result = productController.deleteProduct(savedProduct.getId());
        Exception exception = assertThrows(RuntimeException.class, () -> productController.getProduct(savedProduct.getId()));

        // Assert
        assert result;
        assert exception.getMessage().equals("Product not found");

    }

    @Test
    public void TestUpdateProduct(){
        // Arrange
        CreateProductDto productDto = new CreateProductDto();

        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.entries("product")).thenReturn(null);

        Product product1 = new Product();
        product1.setDescription("Test Description1");
        CreateProductDto createProductDto = new CreateProductDto();
        createProductDto.setId(savedProduct.getId());
        createProductDto.setDescription("Test Description1");

        // Act
        PatchProductResponseDto updatedProduct = productController.updateProduct(savedProduct.getId(), createProductDto);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.entries("product")).thenReturn(null);

        // Assert
        assert updatedProduct.getDto().getId().equals(savedProduct.getId());
        assert updatedProduct.getDto().getTitle().equals("Test Product");
        assert updatedProduct.getDto().getDescription().equals("Test Description1");
        assert updatedProduct.getDto().getPrice().equals(100.0);
    }

    @Test
    void TestGetProductByName(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.entries("product")).thenReturn(null);

        // Act
        List<GetProductResponseDto> getProductResponseDto =  productController.getProductByName("Test Category");

        // Assert
        assert getProductResponseDto.size() == 1;
        assert getProductResponseDto.getFirst().getProductDto().getId().equals(savedProduct.getId());
        assert getProductResponseDto.getFirst().getProductDto().getTitle().equals("Test Product");
        assert getProductResponseDto.getFirst().getProductDto().getDescription().equals("Test Description");


    }
}
