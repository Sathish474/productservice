package com.sathish.productservice.services.sortingService;

import com.sathish.productservice.models.Product;

import java.util.List;

public interface Sorter {
    List<Product> sort(List<Product> products);
}
