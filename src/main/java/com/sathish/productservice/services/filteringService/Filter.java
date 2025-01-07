package com.sathish.productservice.services.filteringService;

import com.sathish.productservice.models.Product;

import java.util.List;

public interface Filter {
    public List<Product> apply(List<Product> products, String key, List<String> allowedValues);
}
