package com.sathish.productservice.services;

import com.sathish.productservice.models.Category;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.repositories.CategoryRepository;
import com.sathish.productservice.repositories.ProductRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("databaseProductService")
public class ProductServiceDBImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public ProductServiceDBImpl(ProductRepository productRepository,
                                CategoryRepository categoryRepository,
                                RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.redisTemplate = redisTemplate;
    }
    @Override
    public Product createProduct(Product product) {
        Category toBeCreatedInProduct = getCategory(product);
        //toBeCreatedInProduct.setName(product.getCategory().getName());
                //getCategory(product);
        product.setCategory(toBeCreatedInProduct);
        Product savedProduct = productRepository.save(product);
        // Insert product in cache
        redisTemplate.opsForHash().put("product", savedProduct.getId().toString(), savedProduct);
        return savedProduct;
    }

    private Category getCategory(Product product) {
        String categoryName = product.getCategory().getName();
        Optional<Category> category = categoryRepository.findByName(categoryName);
        Category toBeCreatedInProduct = null;
        if (category.isEmpty()) {
            Category toSaveCategory = new Category();
            toSaveCategory.setName(categoryName);
            toBeCreatedInProduct = toSaveCategory;//categoryRepository.save(toSaveCategory);
        } else {
            toBeCreatedInProduct = category.get();
        }
        return toBeCreatedInProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product paritialUpdateProduct(Long productId, Product product) {
        Optional<Product> optionalProductToBeUpdated = productRepository.findById(productId);
        if (optionalProductToBeUpdated.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        Product tobeUpdatedProduct = optionalProductToBeUpdated.get();
        if(product.getDescription() != null){
            tobeUpdatedProduct.setDescription(product.getDescription());
        }

        if (product.getPrice() != null){
            tobeUpdatedProduct.setPrice(product.getPrice());
        }

        if (product.getTitle() != null){
            tobeUpdatedProduct.setTitle(product.getTitle());
        }


        if (product.getCategory() != null){
            Category toBeCreatedInProduct = getCategory(tobeUpdatedProduct);
            tobeUpdatedProduct.setCategory(toBeCreatedInProduct);
        }
        Product updatedProduct = productRepository.save(tobeUpdatedProduct);
        // update product in cache
        redisTemplate.opsForHash().put("product", updatedProduct.getId().toString(), updatedProduct);
        return updatedProduct;
    }

    @Override
    public Product getProduct(Long id) {
        // Check if prouduct is present in cache
        Product product = (Product) redisTemplate.opsForHash().get("product", id.toString());
        // cache hit
        if (product != null) {
            return product;
        }
        // cache miss
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        product = optionalProduct.get();
        // save product in cache
        redisTemplate.opsForHash().put("product", id.toString(), product);
        return optionalProduct.get();
    }

    @Override
    public Boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
        redisTemplate.opsForHash().delete("product", id.toString());
        return true;
    }

    @Override
    public List<Product> getProductByCategoryName(String name) {
        Optional<List<Product>> optionalProduct = productRepository.findByCategory_NameEquals(name);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        return optionalProduct.get();
    }
}
