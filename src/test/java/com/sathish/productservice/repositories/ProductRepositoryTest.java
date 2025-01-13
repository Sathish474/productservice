package com.sathish.productservice.repositories;

import com.sathish.productservice.models.Category;
import com.sathish.productservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveMethod() {
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        // Act
        Product product1 = productRepository.save(product);
        // Assert
        assert product1.getId() != null;
        assert product1.getTitle().equals("Test Product");
        assert product1.getDescription().equals("Test Description");
        assert product1.getPrice() == 100.0;
        assert product1.getCategory().getName().equals("Test Category");

    }


    @Test
    public void TestDeleteProductMethod(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        // Act
        Product product1 = productRepository.save(product);
        productRepository.delete(product1);

        // Assert
        assert productRepository.findById(product1.getId()).isEmpty();
    }

    @Test
    public void TestFindAll(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        Product product1 = new Product();
        product1.setTitle("Test Product1");
        product1.setDescription("Test Description1");
        product1.setPrice(100.0);
        product1.setCategory(category);

        // Act
        productRepository.save(product);
        productRepository.save(product1);

        // Assert
        assert productRepository.findAll().size() == 2;
        assert productRepository.findAll().getFirst().getTitle().equals("Test Product");
        assert productRepository.findAll().getLast().getTitle().equals("Test Product1");
        assert productRepository.findAll().getFirst().getDescription().equals("Test Description");
        assert productRepository.findAll().getLast().getDescription().equals("Test Description1");
        assert productRepository.findAll().getFirst().getPrice() == 100.0;
        assert productRepository.findAll().getLast().getPrice() == 100.0;
        assert productRepository.findAll().getFirst().getCategory().getName().equals("Test Category");
        assert productRepository.findAll().getLast().getCategory().getName().equals("Test Category");

    }


    @Test
    public void TestFindById(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        // Act
        Product product1 = productRepository.save(product);

        // Assert
        assert productRepository.findById(product1.getId()).isPresent();
        assert productRepository.findById(product1.getId()).get().getTitle().equals("Test Product");
        assert productRepository.findById(product1.getId()).get().getDescription().equals("Test Description");
        assert productRepository.findById(product1.getId()).get().getPrice() == 100.0;
        assert productRepository.findById(product1.getId()).get().getCategory().getName().equals("Test Category");

    }

    @Test
    public void TestFindByCategoryNameEqualsMethod(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        Product product1 = new Product();
        product1.setTitle("Test Product1");
        product1.setDescription("Test Description1");
        product1.setPrice(100.0);
        product1.setCategory(category);

        // Act
        Product savedProduct1 = productRepository.save(product);
        Product savedProduct2 = productRepository.save(product1);

        Optional<List<Product>> products = productRepository.findByCategory_NameEquals("Test Category");

        // Assert
        assert products.isPresent();
        assert products.get().size() == 2;
        assert products.get().getFirst().getTitle().equals("Test Product");
        assert products.get().getFirst().getDescription().equals("Test Description");
        assert products.get().getFirst().getPrice() == 100.0;
        assert products.get().getFirst().getCategory().getName().equals("Test Category");
        assert products.get().getLast().getTitle().equals("Test Product1");
        assert products.get().getLast().getDescription().equals("Test Description1");
        assert products.get().getLast().getPrice() == 100.0;
        assert products.get().getLast().getCategory().getName().equals("Test Category");
    }

    @Test
    public void TestNativeQueryExample(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        Product product1 = new Product();
        product1.setTitle("Test Product1");
        product1.setDescription("Test Description1");
        product1.setPrice(100.0);
        product1.setCategory(category);

        // Act
        Product savedProduct1 = productRepository.save(product);
        Product savedProduct2 = productRepository.save(product1);

        List<Product> products = productRepository.nativeQueryExample("Test Category");

        // Assert
        assert products.size() == 2;
        assert products.getFirst().getTitle().equals("Test Product");
        assert products.getFirst().getDescription().equals("Test Description");
        assert products.getFirst().getPrice() == 100.0;
        assert products.getFirst().getCategory().getName().equals("Test Category");
        assert products.getLast().getTitle().equals("Test Product1");
        assert products.getLast().getDescription().equals("Test Description1");
        assert products.getLast().getPrice() == 100.0;
        assert products.getLast().getCategory().getName().equals("Test Category");
    }

    @Test
    public void testSomeThingMethod(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        Product product1 = new Product();
        product1.setTitle("Test Product1");
        product1.setDescription("Test Description1");
        product1.setPrice(100.0);
        product1.setCategory(category);

        // Act
        Product savedProduct1 = productRepository.save(product);
        Product savedProduct2 = productRepository.save(product1);

        List<Product> products = productRepository.someThing(savedProduct1.getId());

        // Assert
        assert products.size() == 1;
        assert products.getFirst().getTitle().equals("Test Product1");
        assert products.getFirst().getDescription().equals("Test Description1");
        assert products.getFirst().getPrice() == 100.0;
        assert products.getFirst().getCategory().getName().equals("Test Category");
    }

    @Test
    public void TestFindByTitleContainingMethod(){
        // Arrange
        Product product = new Product();
        product.setTitle("Test Product");
        product.setDescription("Test Description");
        product.setPrice(100.0);

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        Product product1 = new Product();
        product1.setTitle("Test Product1");
        product1.setDescription("Test Description1");
        product1.setPrice(100.0);
        product1.setCategory(category);

        // Act
        Product savedProduct1 = productRepository.save(product);
        Product savedProduct2 = productRepository.save(product1);

        List<Product> products = productRepository.findByTitleContaining("Test Product1");

        // Assert
        assert products.size() == 1;
        assert products.getFirst().getTitle().equals("Test Product1");
        assert products.getFirst().getDescription().equals("Test Description1");
        assert products.getFirst().getPrice() == 100.0;
        assert products.getFirst().getCategory().getName().equals("Test Category");
    }
}
