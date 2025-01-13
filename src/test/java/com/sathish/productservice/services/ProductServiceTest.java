package com.sathish.productservice.services;

import com.sathish.productservice.models.Category;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.repositories.CategoryRepository;
import com.sathish.productservice.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductServiceDBImpl productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @Test
    public void testCreateProductWithExistingCategory() {
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setPrice(100.0);
        product.setDescription("Test Description");
        product.setImageUrl("Test Image URL");
        product.setId(1L);

        Category category = new Category();
        category.setName("Test Category");
        //category.setId(1L);
        category.setDescription("Test Description");
        product.setCategory(category);


        when(productRepository.save(product)).thenReturn(product);
        when(categoryRepository.findByName("Test Category")).thenReturn(java.util.Optional.of(category));
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);

        // Act
        Product savedProduct = productService.createProduct(product);
        // Assert
        assert savedProduct != null;
        assert savedProduct.getId() == 1L;
        assert savedProduct.getTitle().equals("Test Product");
        assert savedProduct.getPrice() == 100.0;
        assert savedProduct.getDescription().equals("Test Description");
        assert savedProduct.getImageUrl().equals("Test Image URL");
        assert savedProduct.getCategory().getName().equals("Test Category");

    }

    @Test
    public void testCreateProductWithOutExistingCategory() {
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setPrice(100.0);
        product.setDescription("Test Description");
        product.setImageUrl("Test Image URL");
        product.setId(1L);

        Category category = new Category();
        category.setName("Test Category");
        category.setId(1L);
        category.setDescription("Test Description");
        product.setCategory(category);


        when(productRepository.save(product)).thenReturn(product);
        when(categoryRepository.findByName("Test Category")).thenReturn(java.util.Optional.empty());
        //when(categoryRepository.save(returnedCategory)).thenReturn(returnedCategory);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);

        // Act
        Product savedProduct = productService.createProduct(product);
        // Assert
        assert savedProduct != null;
        assert savedProduct.getId() == 1L;
        assert savedProduct.getTitle().equals("Test Product");
        assert savedProduct.getPrice() == 100.0;
        assert savedProduct.getDescription().equals("Test Description");
        assert savedProduct.getImageUrl().equals("Test Image URL");
        assert savedProduct.getCategory().getName().equals("Test Category");
    }

    @Test
    public void TestGetAllProducts() {
        // Arrange
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setTitle("Test Product");
        product1.setPrice(100.0);
        product1.setDescription("Test Description");
        product1.setImageUrl("Test Image URL");
        product1.setId(1L);

        Product product2 = new Product();
        product2.setTitle("Test Product2");
        product2.setPrice(200.0);
        product2.setDescription("Test Description2");
        product2.setImageUrl("Test Image URL2");
        product2.setId(2L);

        products.add(product1);
        products.add(product2);

        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> productList = productService.getAllProducts();

        // Assert
        assert productList.size() == 2;
        assert productList.getFirst().getId() == 1L;
        assert productList.getFirst().getTitle().equals("Test Product");
        assert productList.getFirst().getPrice() == 100.0;
        assert productList.getFirst().getDescription().equals("Test Description");
        assert productList.getFirst().getImageUrl().equals("Test Image URL");
        assert productList.getLast().getId() == 2L;
        assert productList.getLast().getTitle().equals("Test Product2");
        assert productList.getLast().getPrice() == 200.0;
        assert productList.getLast().getDescription().equals("Test Description2");
        assert productList.getLast().getImageUrl().equals("Test Image URL2");
    }

    @Test
    public void TestPartialUpdateProduct(){
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");
        product.setPrice(100.0);
        product.setDescription("Test Description");

        Category category = new Category();
        category.setName("Test Category");
        category.setId(1L);

        product.setCategory(category);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(categoryRepository.findByName("Test Category")).thenReturn(java.util.Optional.of(category));
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        // Act
        Product updatedProduct = productService.paritialUpdateProduct(1L, product);

        // Assert
        assert updatedProduct != null;
        assert updatedProduct.getId() == 1L;
        assert updatedProduct.getTitle().equals("Test Product");
        assert updatedProduct.getPrice() == 100.0;
        assert updatedProduct.getDescription().equals("Test Description");
        assert updatedProduct.getCategory().getName().equals("Test Category");
        assert updatedProduct.getCategory().getId() == 1L;
    }

    @Test
    public void TestPartialUpdateProductWithFewChanges(){
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");
        product.setPrice(100.0);
        product.setDescription("Test Description");

        Category category = new Category();
        category.setName("Test Category");
        category.setId(1L);

        product.setCategory(category);

        Product inputProduct = new Product();
        inputProduct.setId(1L);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        //when(categoryRepository.findByName("Test Category")).thenReturn(java.util.Optional.of(category));
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        // Act
        Product updatedProduct = productService.paritialUpdateProduct(1L, inputProduct);

        // Assert
        assert updatedProduct != null;
        assert updatedProduct.getId() == 1L;
        assert updatedProduct.getTitle().equals("Test Product");
        assert updatedProduct.getPrice() == 100.0;
        assert updatedProduct.getDescription().equals("Test Description");
        assert updatedProduct.getCategory().getName().equals("Test Category");
        assert updatedProduct.getCategory().getId() == 1L;
    }

    @Test
    public void TestPartialUpdateProductWithOutExistingProduct(){
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");
        product.setPrice(100.0);
        product.setDescription("Test Description");

        Category category = new Category();
        category.setName("Test Category");
        category.setId(1L);

        product.setCategory(category);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> productService.paritialUpdateProduct(1L, product));
        assertTrue(exception.getMessage().contains("Product not found"));
    }



    @Test
    public void TestGetProductWithoutRedis(){
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");
        product.setPrice(100.0);
        product.setDescription("Test Description");

        Category category = new Category();
        category.setName("Test Category");
        category.setId(1L);

        product.setCategory(category);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        // Act
        Product getProduct = productService.getProduct(1L);

        // Assert
        assert getProduct != null;
        assert getProduct.getId() == 1L;
        assert getProduct.getTitle().equals("Test Product");
        assert getProduct.getPrice() == 100.0;
        assert getProduct.getDescription().equals("Test Description");
        assert getProduct.getCategory().getName().equals("Test Category");
        assert getProduct.getCategory().getId() == 1L;
    }

    @Test
    public void TestGetProductWithRedis(){
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Test Product");
        product.setPrice(100.0);
        product.setDescription("Test Description");

        Category category = new Category();
        category.setName("Test Category");
        category.setId(1L);

        product.setCategory(category);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(hashOperations.get("product", "1")).thenReturn(product);
        // Act
        Product getProduct = productService.getProduct(1L);
        // Assert
        assert getProduct != null;
        assert getProduct.getId() == 1L;
        assert getProduct.getTitle().equals("Test Product");
        assert getProduct.getPrice() == 100.0;
        assert getProduct.getDescription().equals("Test Description");
        assert getProduct.getCategory().getName().equals("Test Category");
        assert getProduct.getCategory().getId() == 1L;
    }

    @Test
    public void TestGetProductWithOutExistingProduct(){
        // Arrange
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProduct(1L));
        assertTrue(exception.getMessage().contains("Product not found"));
    }


    @Test
    public void TestDeleteProductWithExistingProduct() {
        // Arrange
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        doNothing().when(productRepository).deleteById(1L);
        when(hashOperations.delete("product", "1")).thenReturn(1L);
        // Act
        boolean isDeleted = productService.deleteProduct(product.getId());

        // Assert
        assert isDeleted;
    }

    @Test
    public void TestDeleteProductWithOutExistingProduct() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> productService.deleteProduct(product.getId()));
        assertTrue(exception.getMessage().contains("Product not found"));
    }

    @Test
    public void TestGetProductByCategoryName(){
        // Arrange
        List<Product> products = new ArrayList<>();

        Category category = new Category();
        category.setName("Test Category");
        category.setId(1L);

        Product product1 = new Product();
        product1.setTitle("Test Product");
        product1.setPrice(100.0);
        product1.setDescription("Test Description");
        product1.setImageUrl("Test Image URL");
        product1.setId(1L);
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setTitle("Test Product2");
        product2.setPrice(200.0);
        product2.setDescription("Test Description2");
        product2.setImageUrl("Test Image URL2");
        product2.setId(2L);
        product2.setCategory(category);

        products.add(product1);
        products.add(product2);

        when(productRepository.findByCategory_NameEquals("Test Category")).thenReturn(Optional.of(products));

        // Act
        List<Product> productList = productService.getProductByCategoryName("Test Category");

        // Assert
        assert productList.size() == 2;
        assert productList.getFirst().getId() == 1L;
        assert productList.getFirst().getTitle().equals("Test Product");
        assert productList.getFirst().getPrice() == 100.0;
        assert productList.getFirst().getDescription().equals("Test Description");
        assert productList.getFirst().getImageUrl().equals("Test Image URL");
        assert productList.getLast().getId() == 2L;
        assert productList.getLast().getTitle().equals("Test Product2");
        assert productList.getLast().getPrice() == 200.0;
        assert productList.getLast().getDescription().equals("Test Description2");
        assert productList.getLast().getImageUrl().equals("Test Image URL2");
        assert productList.getFirst().getCategory().getName().equals("Test Category");
        assert productList.getFirst().getCategory().getId() == 1L;
        assert productList.getLast().getCategory().getName().equals("Test Category");
        assert productList.getLast().getCategory().getId() == 1L;
    }

    @Test
    public void TestGetProductByCategoryNameWithOutProduct(){
        // Arrange
        when(productRepository.findByCategory_NameEquals("Test Category")).thenReturn(java.util.Optional.empty());
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductByCategoryName("Test Category"));
        assertTrue(exception.getMessage().contains("Product not found"));
    }

}
