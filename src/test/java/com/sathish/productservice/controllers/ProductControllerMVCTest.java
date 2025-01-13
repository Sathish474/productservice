package com.sathish.productservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sathish.productservice.dto.products.CreateProductDto;
import com.sathish.productservice.dto.products.CreateProductRequestDto;
import com.sathish.productservice.dto.products.GetProductResponseDto;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class) // If you have a custom security config class
public class ProductControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean(name = "databaseProductService")
    private ProductService productService;

    @MockitoBean
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HttpSecurity httpSecurity;

    @Test
    public void testCreateProduct() throws Exception {
        CreateProductRequestDto requestDto = new CreateProductRequestDto();
        requestDto.setTitle("product1");
        requestDto.setDescription("description1");
        requestDto.setPrice(100.0);
        requestDto.setCategoryName("category1");

        Product product = new Product();
        product.setTitle("product1");
        product.setDescription("description1");
        product.setPrice(100.0);
        product.setId(1L);

        String requestJson = objectMapper.writeValueAsString(requestDto);
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        //when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("description1"))
                .andExpect(jsonPath("$.title").value("product1"))
                .andExpect(jsonPath("$.price").value(100.0));
    }


    @Test
    public void testGetAllProducts() throws Exception {
        // Arrange
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        product1.setTitle("product1");
        product1.setDescription("description1");
        product1.setPrice(100.0);
        product1.setId(1L);

        Product product2 = new Product();
        product2.setTitle("product2");
        product2.setDescription("description2");
        product2.setPrice(200.0);
        product2.setId(2L);

        productList.add(product1);
        productList.add(product2);

        when(productService.getAllProducts()).thenReturn(productList);
        when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(true);
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.products.size()").value(2))
                //.andExpect(jsonPath("$.products[0].class").value("com.sathish.productservice.dto.products.GetProductDto"))
                .andExpect(jsonPath("$.products[0].id").value(1L))
                .andExpect(jsonPath("$.products[0].description").value("description1"))
                .andExpect(jsonPath("$.products[0].title").value("product1"))
                .andExpect(jsonPath("$.products[0].price").value(100.0))
                .andExpect(jsonPath("$.products[1].id").value(2L))
                .andExpect(jsonPath("$.products[1].description").value("description2"))
                .andExpect(jsonPath("$.products[1].title").value("product2"))
                .andExpect(jsonPath("$.products[1].price").value(200.0));

    }

    @Test
    public void testGetAllProductsThrowsException() throws Exception {
        // Arrange
        when(productService.getAllProducts()).thenThrow(new RuntimeException("Something went wrong"));
        when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value("Something went wrong"));
    }

    @Test
    public void testGetProductById() throws Exception{
        // Arrange
        Product product = new Product();
        product.setTitle("product1");
        product.setDescription("description1");
        product.setPrice(100.0);
        product.setId(1L);

        when(productService.getProduct(1L)).thenReturn(product);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.productDto.id").value(1L))
                .andExpect(jsonPath("$.productDto.description").value("description1"))
                .andExpect(jsonPath("$.productDto.title").value("product1"))
                .andExpect(jsonPath("$.productDto.price").value(100.0));
    }

    @Test
    public void testGetProductByIdThrowsException() throws Exception{
        // Arrange
        Product product = new Product();
        product.setTitle("product1");
        product.setDescription("description1");
        product.setPrice(100.0);
        product.setId(1L);

        when(productService.getProduct(1L)).thenThrow(new RuntimeException("Product not found"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value("Product not found"));
    }


    @Test
    public void testDeleteProductById() throws Exception{
        // Arrange
        when(productService.deleteProduct(1L)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", 1L))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.ST))
                .andExpect(content().string("true"));
    }

    @Test
    public void testDeleteProductByIdThrowsException() throws Exception{
        // Arrange
        when(productService.deleteProduct(1L)).thenThrow(new RuntimeException("Product not found"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message").value("Product not found"));
    }

    @Test
    public void testUpdateProduct() throws Exception{
        CreateProductDto createProductDto = new CreateProductDto();
        createProductDto.setTitle("product1");
        createProductDto.setDescription("changing description1");
        createProductDto.setId(1L);

        Product product = new Product();
        product.setTitle("product1");
        product.setDescription("changing description1");
        product.setId(1L);

        String requestJson = objectMapper.writeValueAsString(createProductDto);
        when(productService.paritialUpdateProduct(any(Long.class), any(Product.class))).thenReturn(product);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.patch("/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dto.id").value(1L))
                .andExpect(jsonPath("$.dto.description").value("changing description1"))
                .andExpect(jsonPath("$.dto.title").value("product1"));

    }

    @Test
    public void testGetProductByName() throws Exception{
        // Arrange
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setTitle("product1");
        product.setDescription("description1");
        product.setPrice(100.0);
        product.setId(1L);

        productList.add(product);

        when(productService.getProductByCategoryName("category")).thenReturn(productList);

        // Act & Assert
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/products/name/{name}", "category"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        List<GetProductResponseDto> responseDtos = objectMapper.readValue(contentAsString, new TypeReference<List<GetProductResponseDto>>() {});

        assert responseDtos.size() == 1;
        assert responseDtos.getFirst().getProductDto().getId() == 1L;
        assert responseDtos.getFirst().getProductDto().getDescription().equals("description1");
        assert responseDtos.getFirst().getProductDto().getTitle().equals("product1");
        assert responseDtos.getFirst().getProductDto().getPrice() == 100.0;

    }



}
