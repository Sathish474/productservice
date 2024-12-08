package com.sathish.productservice.services;

import com.sathish.productservice.models.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    List<Product> getAllProducts();

    Product paritialUpdateProduct(Long id, Product product);

    Product getProduct(Long id);

    Boolean deleteProduct(Long id);

    List<Product> getProductByCategoryName(String name);
}
