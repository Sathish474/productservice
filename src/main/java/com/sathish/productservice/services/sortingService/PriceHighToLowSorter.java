package com.sathish.productservice.services.sortingService;

import com.sathish.productservice.dto.search.SortingCriteria;
import com.sathish.productservice.models.Product;

import java.util.List;

public class PriceHighToLowSorter implements Sorter {
    @Override
    public List<Product> sort(List<Product> products) {
        return List.of();
    }
}
