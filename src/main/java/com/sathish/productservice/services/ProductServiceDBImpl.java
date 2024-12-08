package com.sathish.productservice.services;

import com.sathish.productservice.models.Category;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.repositories.CategoryRepository;
import com.sathish.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("databaseProductService")
public class ProductServiceDBImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceDBImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product createProduct(Product product) {
        Category toBeCreatedInProduct = getCategory(product);
        //toBeCreatedInProduct.setName(product.getCategory().getName());
                //getCategory(product);
        product.setCategory(toBeCreatedInProduct);

        return productRepository.save(product);
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
            Category toBeCreatedInProduct = getCategory(product);
            tobeUpdatedProduct.setCategory(toBeCreatedInProduct);
        }
        return productRepository.save(tobeUpdatedProduct);
    }

    @Override
    public Product getProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        return optionalProduct.get();
    }

    @Override
    public Boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
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
