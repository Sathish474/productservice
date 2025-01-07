package com.sathish.productservice.services;

import com.sathish.productservice.dto.search.FilterDto;
import com.sathish.productservice.dto.search.SortingCriteria;
import com.sathish.productservice.services.filteringService.Filter;
import com.sathish.productservice.models.Product;
import com.sathish.productservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

   public Page<Product> searchProducts(
                String query,
                List<FilterDto> filters,
                SortingCriteria sortBy,
                int pageNumber,
                int pageSize
   ) {
        // Search products logic
       List<Product> products = productRepository.findByTitleContaining(query);

       for (FilterDto filter : filters) {
           Filter dynamicFilter = (productsList, key, allowedValues) -> {
               return productsList.stream()
                       .filter(product -> {
                           System.out.println("product: " + product.getTitle());
                           for (String allowedValue : allowedValues) {
                               if (matchesKeyAndValue(product, key, allowedValue)) {
                                   return true;
                               }
                           }
                           return false;
                       })
                       .collect(Collectors.toList());
           };

           products = dynamicFilter.apply(products, filter.getKey(), filter.getValues());

       }

       //products = Objects.requireNonNull(SorterFactory.getSorter(sortBy)).sort(products);

       List<Product> productsOnPage = new ArrayList<>();
       for (int i = (pageNumber - 1) * pageSize; i <= (pageNumber * pageSize) -1; i++) {
           if (i >= products.size()) break;
           productsOnPage.add(products.get(i));
       }

       Pageable pageable = PageRequest.of(pageNumber, pageSize);

       return new PageImpl<>(productsOnPage, pageable, products.size());
  }


    private static boolean matchesKeyAndValue(Product product, String key, String allowedValue) {
        try {
            // Use reflection to get the value of the field
            Field field = Product.class.getDeclaredField(key);
            field.setAccessible(true); // Make private fields accessible

            Object value = field.get(product); // Get the field value

            // Check if the value matches
            if (value instanceof String) {
                return value.toString().contains(allowedValue);
            } else if (value instanceof Number) {
                return Double.parseDouble(allowedValue) == ((Number) value).doubleValue();
            }
            // Add other type checks as needed
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

}
